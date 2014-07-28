package behaviours;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import agents.ClientAgent;
import dao.domain.Annoucement;

public class ReceiveMsgClient extends CyclicBehaviour {

	private ClientAgent a;

	public ReceiveMsgClient(Agent a) {
		super(a);
		this.a = (ClientAgent) a;

	}

	@Override
	public void action() {
		ACLMessage msg = a.receive();
		if (msg == null) {

			block();

			return;
		}
		if (msg.getOntology().equals("oferta")) {
			if (msg.getPerformative() == ACLMessage.FAILURE) {
				System.out.println(a.getLocalName()+ " nic nie mam :(");

			} else if (msg.getPerformative() == ACLMessage.REQUEST) {
				try {
					Object content = msg.getContentObject();

					if (content instanceof Annoucement) {
						Annoucement anounce = (Annoucement) content;
						if (!anounce.isEmpty()) {
							System.out.println(a.getLocalName() + ": wygralem "
									+ anounce.getId() + " " + anounce.getType()
									+ " za " + anounce.getCurrentPrice());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
