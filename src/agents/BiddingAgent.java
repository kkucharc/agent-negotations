package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import behaviours.AskAboutWinning;
import behaviours.BidOnTime;
import behaviours.CyclicBidBehaviour;
import behaviours.ReceiveMsgBidding;
import dao.domain.Annoucement;
import data.Annoucements;
import data.Preference;

public class BiddingAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5540096653106418668L;
	private ClientAgent client; // klient ktorego przedstawia
	private AID[] systems;

	private Preference p;
	private Annoucements myAnnoces;
	private Annoucement favourite; // faworyzowana oferta
	private Behaviour loop;
	private ParallelBehaviour parentBehaviour;

	protected void setup() {
		favourite = null;
		client = (ClientAgent) getArguments()[0];

		System.out.println("Jestem " + getLocalName()
				+ "\n\t-reprezentuje klienta o id " + client.getLocalName()
				+ " - " + client.getClientInfo().getName());

		systems = findServices("system aukcyjny");

		sendAskMsgToMorris();

		receiveMsgs();
	}

	/* szuka morris column */
	public AID[] findServices(String type) {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		dfd.addServices(sd);

		SearchConstraints ALL = new SearchConstraints();
		ALL.setMaxResults(new Long(-1));

		try {
			DFAgentDescription[] result = DFService.search(this, dfd, ALL);
			AID[] agents = new AID[result.length];
			for (int i = 0; i < result.length; i++)
				agents[i] = result[i].getName();
			return agents;

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		return null;

	}

	/* to bid */
	private void sendMsgToClient() {

		/* wysylanie wygranej aukcji */

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

		msg.setOntology("preferencje");

		System.out.println("WYSYLANIE - " + getLocalName() + " -> "
				+ client.getLocalName() + msg.getContent());
		msg.addReceiver(client.getAID());
		send(msg);
	}

	/* to bid */
	public void sendBidMsgToMorris() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

		if (favourite != null
				&& !favourite.isBidderWinning(client.getClientInfo().getId())) {
			try {

				msg.setContentObject(favourite);

				msg.setOntology("bid");

				for (AID id : systems) {
					System.out
							.println("WYSYLANIE - " + getLocalName() + " -> "
									+ id.getLocalName() + " licytuje "
									+ favourite.getType() + " "
									+ favourite.getId() + " o cenie "
									+ favourite.getCurrentPrice()
									+ " i czasie zakonczenia "
									+ favourite.getEndDate());
					msg.addReceiver(id);
					send(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Nie udalo sie wyslac podbicia aukcji");
			}
		} else {
			System.out.println(getLocalName() + ": Nie moge przebic oferty");
		}

	}

	/* to ask */
	public void sendAskMsgToMorris() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

		try {
			msg.setContentObject(p);
			msg.setOntology("aukcja");

			for (AID id : systems) {
				System.out.println("WYSYLANIE - " + getLocalName() + " -> "
						+ id.getLocalName() + " pytam o preferowane aukcje");
				msg.addReceiver(id);
				send(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Nie udalo sie wyslac zapytania do morrisColumn");
		}
	}

	/* dodanie zachowania odbierania cyklicznego widaomosci */
	public void receiveMsgs() {

		parentBehaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);

		parentBehaviour.addSubBehaviour(new ReceiveMsgBidding(this,
				parentBehaviour));

		addBehaviour(parentBehaviour);

	}

	public void findInterestingAnnoucement() {
		/* prosba o aktualizacje ofert */

		if (this.favourite != null) {
			updateFavourite();
			if (this.favourite.getHighestOffer().getClientId() == client
					.getClientInfo().getId()) {

				if (this.favourite.getStatus().equals("ended")) {

					endBidding();
				} else {
					System.out.println(getLocalName()
							+ ": moja preferowana oferta to "
							+ favourite.getHighestOffer().getAnnoucementId()
							+ " o cenie "
							+ favourite.getHighestOffer().getPrice());
					if (favourite.isBidderWinning(client.getClientInfo()
							.getId())) {

						System.out.println("Bidding"
								+ client.getClientInfo().getId()
								+ " Wygrywam i nie musze podbijac!");

					} else {
						System.out.println("Bidding"
								+ client.getClientInfo().getId()
								+ " Przegrywam i podbijam nowe!");
						favourite = null;
						compare();
					}
				}
			} else {
				compare();
			}

		} else {
			compare();
		}
	}

	private void updateFavourite() {

		Annoucement k = isInMyAnnouces();
		if (k != null) {
			favourite = k;

		} else {
			favourite.setStatus("ended");
		}

	}

	private Annoucement isInMyAnnouces() {

		for (Annoucement a : myAnnoces.getAnnoucements()) {
			if (a.getId() == favourite.getId()) {

				return a;
			}
		}
		return null;
	}

	private void compare() {

		if (myAnnoces.isEmpty()) {
			this.favourite = null;
			endBidding();
		} else if (myAnnoces.getSize() == 1) {
			this.favourite = myAnnoces.getFirst();
			System.out.println(getLocalName()
					+ " interesuje mnie tylko pierwsza "
					+ favourite.getCurrentPrice());

		} else {
			if (favourite == null) {
				this.favourite = myAnnoces.getFirst();
			}
			if (p.isCenapriority()) {
				for (Annoucement a : myAnnoces.getAnnoucements()) {
					if (!a.equals(favourite)) {
						if (a.getCurrentPrice() < favourite.getCurrentPrice()) {
							favourite = a;
						} else if (favourite.getCurrentPrice() == a
								.getCurrentPrice()
								&& favourite.getEndDate().after(a.getEndDate())) {
							favourite = a;

						}

					}

				}
				System.out.println(getLocalName()
						+ " mam preferencje co do ceny i wybralem "
						+ favourite.getId());
			} else if (p.isDatapriority()) {
				for (Annoucement a : myAnnoces.getAnnoucements()) {
					if (!a.equals(favourite)) {
						if (favourite.getEndDate().after(a.getEndDate())) {
							favourite = a;
						} else if (favourite.getEndDate()
								.equals(a.getEndDate())
								&& a.getCurrentPrice() < favourite
										.getCurrentPrice()) {
							favourite = a;
						}
					}

				}
				System.out.println(getLocalName()
						+ " mam preferencje co do daty i wybralem "
						+ favourite.getId());
			}

		}

	}

	private void endBidding() {
		// todo wyslanie wiadomosci o pytanie kto wygral to nizej jest zle
		((TickerBehaviour) loop).stop();
		removeBehaviour(loop);
		if (favourite.isBidderWinning(client.getClientInfo().getId())) {

			System.out.println("Bidding" + client.getClientInfo().getId()
					+ " Wygralem!");
			sendPositiveAnswer();
		} else {
			System.out.println("Bidding" + client.getClientInfo().getId()
					+ " Przegralem!");
			sendPositiveAnswer();
		}

	}

	private void sendPositiveAnswer() {
		// TODO Auto-generated method stub

	}

	public void addAnnounce(Annoucement a) {
		myAnnoces.add(a);
	}

	public ClientAgent getClient() {
		return client;
	}

	public void setClient(ClientAgent client) {
		this.client = client;
	}

	public Preference getPreference() {
		return p;
	}

	public void setPreference(Preference p) {
		this.p = p;
	}

	public Annoucements getMyAnnoces() {
		return myAnnoces;
	}

	public void setMyAnnoces(Annoucements myAnnoces) {
		this.myAnnoces = myAnnoces;
	}

	public AID[] getSystems() {
		return systems;
	}

	public void setSystems(AID[] systems) {
		this.systems = systems;
	}

	public void addLoopBehavior() {
		loop = new CyclicBidBehaviour(this, p);
		parentBehaviour.addSubBehaviour(loop);

	}

	public void setStrategy() {
		if (p.getStrategy().equals("cyclic")) {
			addLoopBehavior();
		} else {
			sendAskMsgToMorris();
		}

	}

	public void setBid() {
		findInterestingAnnoucement();

		if (p.getStrategy().equals("cyclic")) {
			System.out.println(getLocalName() + " Podbijam cyklicznie o "
					+ p.getShotPrice());
			sendBidMsgToMorris();
		} else {
			if (p.getStrategy().equals("acyclic_end")) {
				if (p.getShotPrice() == 0) {
					System.out.println(getLocalName()
							+ " Podbijam jednorazowo na koñcu o max "
							+ p.getMaxcena());
				} else {
					System.out.println(getLocalName()
							+ " Podbijam jednorazowo na koñcu o "
							+ p.getShotPrice());
				}
				Calendar cal = new GregorianCalendar();
				int time = p.getShotTime();
				cal.setTime(favourite.getEndDate());
				cal.add(Calendar.SECOND, -time);
				Date wakeupDate = new Date(cal.getTime().getTime());
				System.out.println("wyszedl czas " + wakeupDate);
				addBehaviour(new BidOnTime(this, wakeupDate));
				addBehaviour(new AskAboutWinning(this, favourite.getEndDate()));
			} else if (p.getStrategy().equals("acyclic_start")) {
				if (p.getShotPrice() == 0) {
					System.out.println(getLocalName()
							+ " Podbijam jednorazowo na poczatku o max "
							+ p.getMaxcena());
				} else {
					System.out.println(getLocalName()
							+ " Podbijam jednorazowo na poczatku o "
							+ p.getShotPrice());
				}
				addBehaviour(new BidOnTime(this, new Date()));
				addBehaviour(new AskAboutWinning(this, favourite.getEndDate()));
			}
		}
	}

	public void sendOfferAskToMorris() {

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

		try {
			msg.setContentObject(favourite);
			msg.setOntology("oferta");

			for (AID id : systems) {
				System.out.println("WYSYLANIE - " + getLocalName() + " -> "
						+ id.getLocalName() + " pytam czy wygralem");
				msg.addReceiver(id);
				send(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Nie udalo sie wyslac zapytania do morrisColumn");
		}
	}

	public void sendWinMsgToClient() {

		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);

		try {
			msg.setContentObject(favourite);
			msg.setOntology("oferta");

			System.out.println("WYSYLANIE - " + getLocalName() + " -> "
					+ client.getLocalName() + " wygral!");
			msg.addReceiver(client.getAID());
			send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Nie udalo sie odp do Klienta");
		}
	}

	public void sendLoseMsgToClient() {
		ACLMessage msg = new ACLMessage(ACLMessage.FAILURE);

		try {

			msg.setOntology("oferta");

			System.out.println("WYSYLANIE - " + getLocalName() + " -> "
					+ client.getLocalName() + " przegral :(");
			msg.addReceiver(client.getAID());
			send(msg);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Nie udalo sie odp do Klienta");
		}

	}
}
