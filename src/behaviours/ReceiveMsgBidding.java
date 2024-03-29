package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import agents.BiddingAgent;
import dao.domain.Annoucement;
import data.Annoucements;
import data.Preference;

public class ReceiveMsgBidding extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2422534988840559391L;

	private BiddingAgent a;
	private ParallelBehaviour parentBehaviour;

	public ReceiveMsgBidding(Agent a, ParallelBehaviour parentB) {
		super(a);
		this.a = (BiddingAgent) a;
		this.parentBehaviour = parentB;
	}

	@Override
	public void action() {
		ACLMessage msg = a.receive();
		if (msg == null) {
			block();
			return;
		}
		if (msg.getOntology().equals("oferta")) {
			int id = Integer.parseInt(msg.getContent());
			System.out.println("Aukcje wygral "+id);
			if(id == a.getClient().getClientInfo().getId()){
				a.sendWinMsgToClient();
			}else{
				a.sendLoseMsgToClient();
			}
		} else {
			try {
				Object content = msg.getContentObject();

				if (msg.getOntology().equals("preferencje")) {
					if (content instanceof Preference) {
						Preference p = (Preference) content;
						a.setPreference(p);

						a.setStrategy();

						System.out.println("ODBIOR od KLIENTA - "
								+ myAgent.getLocalName() + " <- "
								+ msg.getSender().getLocalName() + " "
								+ " odbieram preferencje klienta: "
								+ p.getType());

					}
				} else if (msg.getOntology().equals("aukcja")) {
					if (content instanceof Annoucements) {
						Annoucements announces = (Annoucements) content;
						if (!announces.isEmpty()) {
							a.setMyAnnoces(announces);
							System.out.println("ODBIOR od MORRISA - "
									+ myAgent.getLocalName() + " <- "
									+ msg.getSender().getLocalName() + " "
									+ "Mam do wyboru aukcje:");

							/* wybierz i podbij */
							for (Annoucement a : announces.getAnnoucements()) {
								System.out.println(" - " + "id: " + a.getId()
										+ " typ: " + a.getType() + " cena: "
										+ a.getCurrentPrice()+" data konca "+a.getEndDate());

							}

							a.setBid();
							// a.sendBidMsgToMorris();
						} else
							System.out.println("Puste announce");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Nie udalo sie odebrac wiadomosci");
			}

		}
	}
}
