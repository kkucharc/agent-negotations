package behaviours;

import jade.core.behaviours.OneShotBehaviour;

import java.sql.Timestamp;
import java.util.Date;

import dao.AnnoucementDAO;
import dao.OfferDAO;
import dao.domain.Annoucement;
import dao.domain.Offer;
import data.Preference;

public class OneShotBidBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3931665454780629431L;
	private Annoucement annouce;
	private int client;
	Preference p;

	public OneShotBidBehaviour(Annoucement a, int client, Preference p) {
		this.p = p;
		this.annouce = a;
		this.client = client;
	}

	@Override
	public void action() {
		Offer o = new Offer();

		Date d = new Date();

		o.setDate(new Timestamp(d.getTime()));
		o.setAnnoucementId(annouce.getId());
		o.setClientId(client);
		int newPrice;
		
		if(p.getShotPrice()==0){
			newPrice =  p.getMaxcena();
		}else{
			newPrice = annouce.getCurrentPrice() + p.getShotPrice();
		}
		o.setPrice(newPrice);

		annouce.setCurrentPrice(newPrice);

		AnnoucementDAO.update(annouce);
		OfferDAO.save(o);

	}

}
