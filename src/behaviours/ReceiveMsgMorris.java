package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.Serializable;

import agents.MorrisColumn;
import dao.OfferDAO;
import dao.domain.Annoucement;
import dao.domain.Offer;
import data.Annoucements;
import data.Preference;

/**
 * Odbiór wiadomosci od BiddingAgent przez MorrisColumn
 * 
 * @author Kasia
 * 
 */

public class ReceiveMsgMorris extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5643002166027664796L;

	private MorrisColumn a;
	private Preference p;

	public ReceiveMsgMorris(Agent a) {
		super(a);
		this.a = (MorrisColumn) a;

	}

	@Override
	public void action() {
		ACLMessage msg = a.receive();
		if (msg == null) {

			block();

			return;
		}

		try {
			Object content = msg.getContentObject();
			if (msg.getOntology().equals("aukcja")) {
				if (content instanceof Preference) {
					System.out
							.println("ODBIOR - "
									+ myAgent.getLocalName()
									+ " <- "
									+ msg.getSender().getLocalName()
									+ " odbieram preferencje od agenta i szukam aukcji");
					/* znajdz aukcje! i odpowiedz */

					sendReply(msg);
				}
			} else if (msg.getOntology().equals("bid")) {
				if (content instanceof Annoucement) {
					Annoucement anounce = (Annoucement) content;
					if (!anounce.isEmpty()) {

						a.addBehaviour(new OneShotBidBehaviour(anounce, Integer
								.parseInt(msg.getSender().getLocalName()
										.substring(7)), p));
						System.out.println("ODBIOR - " + myAgent.getLocalName()
								+ " <- " + msg.getSender().getLocalName()
								+ " podbijam aukcje");

					} else {
						System.out.println("Annoucement jest pusty");
					}
				}
			} else if ((msg.getOntology().equals("oferta"))) {
				if (content instanceof Annoucement) {
					Annoucement anounce = (Annoucement) content;
					if (!anounce.isEmpty()) {
						Offer wygrana = OfferDAO.getLastByAnnoucement(anounce
								.getId());
						sendInfo(wygrana.getClientId(), msg);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Morris: Nie udalo sie odebrac preferencji");
		}

	}

	private void sendInfo(int clientId, ACLMessage msg) {
		ACLMessage reply = new ACLMessage(ACLMessage.INFORM);

		reply.setContent(Integer.toString(clientId));
		reply.setOntology("oferta");
		reply.addReceiver(msg.getSender());
		a.send(reply);

	}

	private void sendReply(ACLMessage from) {

		try {
			/* odpowiedz */
			ACLMessage reply = new ACLMessage(ACLMessage.REQUEST);
			Annoucements found = new Annoucements();
			p = (Preference) from.getContentObject();
			if (!a.searchAnnounces(p).isEmpty()) {
				found = a.searchAnnounces(p);
				if (found != null) {
					reply.setContentObject((Serializable) found);
					reply.setOntology("aukcja");
					reply.addReceiver(from.getSender());
					a.send(reply);
				}
			} else {
				System.out.println("Brak interesuj¹cej aukcji!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Nie udalo sie odpowiedziec");
		}
	}
}
