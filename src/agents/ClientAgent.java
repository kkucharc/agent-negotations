package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dao.ClientDAO;
import dao.domain.Client;
import data.Preference;

public class ClientAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6591905014750630453L;
	String nameBidder;
	Client clientInfo;

	static Map<Integer, Preference> clientPreferences = new HashMap<Integer, Preference>();

	protected void setup() {
		clientInfo = ClientDAO.get(Integer
				.parseInt(getLocalName().substring(6)));

		System.out.println("Jestem klientem o id " + getLocalName()
				+ " nazwywam sie " + clientInfo.getName());

		generateBiddingAgent();

		Preference preference = clientPreferences.get(Integer
				.parseInt(getLocalName().substring(6)));

		if (preference != null) {
			System.out.println("Client o id " + getLocalName() + " chce kupi� "
					+ preference.getType() + " za maksymalna cene "
					+ preference.getMaxcena() + " ze strategia "
					+ preference.getStrategy());
			sendMsg(preference);
		}

	}

	protected void generateBiddingAgent() {

		nameBidder = "Bidding" + clientInfo.getId();
		AgentContainer c = getContainerController();
		Object[] args = new Object[1];
		args[0] = this;
		try {
			AgentController a = c.createNewAgent(nameBidder,
					"agents.BiddingAgent", args);
			a.start();
		} catch (Exception e) {
			System.out.println("Nie udalo sie stworzyc BiddingAgenta");
		}

	}

	/* wysyla wiadomosc do swojego aukcyjnego przedstawiciela */
	private void sendMsg(Preference msgContent) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID(nameBidder, AID.ISLOCALNAME));
		msg.setOntology("preferencje");
		try {
			System.out.println("WYSYLANIE Client" + clientInfo.getId()
					+ " -> " + nameBidder
					+ "Wysylam preferencje do mojego Biddera "
					+ msgContent.getType());
			msg.setContentObject(msgContent);
		} catch (IOException e) {

			e.printStackTrace();
		}
		send(msg);
	}

	public Client getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Client clientInfo) {
		this.clientInfo = clientInfo;
	}

	static public Boolean loadPreferences(String filePath) {
		FileReader fr;
		try {
			fr = new FileReader(filePath);

			BufferedReader br = new BufferedReader(fr);
			String s;

			while ((s = br.readLine()) != null) {
				String[] data = s.split("\t");
				if (data.length == 9) {
					int clientId = Integer.parseInt(data[0].split(":")[1]);
					Preference p = new Preference();
					p.setType(data[1].split(":")[1]);
					p.setMaxcena(Integer.parseInt(data[2].split(":")[1]));
					if (data[3].split(":")[1].equals("0"))
						p.setCenapriority(false);
					else
						p.setCenapriority(true);

					if (data[4].split(":")[1].equals("0"))
						p.setDatapriority(false);
					else
						p.setDatapriority(true);
					p.setTimeBid(Integer.parseInt(data[5].split(":")[1]));
					p.setStrategy(data[6].split(":")[1]);
					p.setShotTime(Integer.parseInt(data[7].split(":")[1]));
					p.setShotPrice(Integer.parseInt(data[8].split(":")[1]));
					clientPreferences.put(clientId, p);
				}
			}

			fr.close();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return true;
	}
	


}