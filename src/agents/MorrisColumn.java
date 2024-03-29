package agents;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import behaviours.ReceiveMsgMorris;
import dao.AnnoucementDAO;
import dao.OfferDAO;
import dao.domain.Annoucement;
import data.Annoucements;
import data.Preference;

/* kolumna mowiaca o tym jakie sa aukcje na danym serwisie aukcyjnym */
public class MorrisColumn extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7285052465628460036L;

	/* nazwa serwisu aucyjnego */
	private String bidServiceName;

	private Annoucements allAnouces;

	protected void setup() {
		System.out.println("Jestem " + getLocalName());

		/* testowe */

		allAnouces = new Annoucements();
		allAnouces.setAnnoucements(AnnoucementDAO.getAll());

//		runAnnoucements();

		registerService("system aukcyjny");

		receiveMsg();
	}

	/* rejestracja agenta */
	protected void registerService(String serviceType) {

		/* tworzy opis agenta dodawanego do DF */
		DFAgentDescription description = new DFAgentDescription();

		description.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType);
		sd.setName(getLocalName() + "-" + serviceType);
		description.addServices(sd);
		try {
			System.out
					.println(getLocalName() + " Rejestruje swoja usluge w DF");
			DFService.register(this, description);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(getLocalName()
					+ " Nie udalo mi sie zarejestrowac uslugi");
		}
	}

	/* dodanie zachowania odbierania cyklicznego widaomosci */
	public void receiveMsg() {

		ParallelBehaviour par = new ParallelBehaviour(
				ParallelBehaviour.WHEN_ALL);

		/* morris column */
		par.addSubBehaviour(new ReceiveMsgMorris(this));

		addBehaviour(par);

	}

	private void runAnnoucements() {
		Date now = new Date();
		Calendar cal = new GregorianCalendar();
		Timestamp stamp = new Timestamp(now.getTime());
		for (Annoucement a : allAnouces.getAnnoucements()) {

			now = new Date();
			stamp = new Timestamp(now.getTime());
			a.setStartDate(stamp);

			cal.setTime(now);
			cal.add(Calendar.MINUTE, 5);
			stamp = new Timestamp(cal.getTime().getTime());

			a.setEndDate(stamp);
			a.setStatus("start");

			AnnoucementDAO.update(a);

			System.out.println("Aukcja nr " + a.getId() + " " + a.getType()
					+ "\n\t - czas rozpoczęcia: " + a.getStartDate()
					+ "\n\t - czas zakonczenia: " + a.getEndDate()
					+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		}

	}

	public Annoucements searchAnnounces(Preference p) {
		allAnouces.setAnnoucements(AnnoucementDAO.getAll());
		Annoucements found = new Annoucements();

		for (Annoucement a : allAnouces.getAnnoucements()) {

			if (a.getEndDate().before(new Date())) {
				a.setStatus("ended");
				AnnoucementDAO.update(a);
			}

			
			if (a.getType().equals(p.getType())
					&& a.getStatus().equals("start") && a.getCurrentPrice() <= p.getMaxcena()) {
				found.add(a);

				a.setHighestOffer(OfferDAO.getLastByAnnoucement(a.getId()));
				

			}
		}

		return found;
	}

	public String getBidServiceName() {
		return bidServiceName;
	}

	public void setBidServiceName(String name) {
		this.bidServiceName = name;
	}

}
