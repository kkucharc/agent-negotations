package dao.domain;

import jade.util.leap.Serializable;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class Annoucement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3363826837861656577L;
	private int id;
	private int sellerId = -1;

	private String type;
	private int startPrice = -1;
	private int currentPrice = -1;
	private int minimumPrace = -1;
	private Timestamp startDate;
	private Timestamp endDate;
	private String status = "";
	private int winnerOfert;
	private int Time;

	private Offer highestOffer;

	public Annoucement() {
	}

	public Annoucement(String type, int startPrice, int currentPrice,
			int minimumPrice, int sellerId, String status) {
		this.type = type;
		this.startPrice = startPrice;
		this.currentPrice = currentPrice;
		this.minimumPrace = minimumPrice;
		this.sellerId = sellerId;
		this.status = status;

		Random generator = new Random();

		Date now = new Date();
		Calendar cal = new GregorianCalendar();
		Timestamp stamp = new Timestamp(now.getTime());

		now = new Date();
		stamp = new Timestamp(now.getTime());
		setStartDate(stamp);

		setTime(generator.nextInt(3) + 1);
		cal.setTime(now);
		cal.add(Calendar.MINUTE, this.Time);
		stamp = new Timestamp(cal.getTime().getTime());

		setEndDate(stamp);
		setStatus("start");

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getWinnerOfert() {
		return winnerOfert;
	}

	public void setWinnerOfert(int winnerOfert) {
		this.winnerOfert = winnerOfert;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getMinimumPrace() {
		return minimumPrace;
	}

	public void setMinimumPrace(int minimumPrace) {
		this.minimumPrace = minimumPrace;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public boolean isEmpty() {
		if (type.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public Offer getHighestOffer() {
		return highestOffer;
	}

	public void setHighestOffer(Offer highestOffer) {
		this.highestOffer = highestOffer;
	}

	public boolean isHighestOfferEmpty() {
		if (highestOffer.getId() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBidderWinning(int idClienta) {
		if (idClienta == highestOffer.getClientId()) {
			return true;
		} else {
			return false;
		}
	}

	public int getTime() {
		return Time;
	}

	public void setTime(int time) {
		Time = time;
	}

//	public void print() {
//		System.out.println("Aukcja " + id + " " + type + " cena "
//				+ currentPrice + " status: " + status + " offer "
//				+ highestOffer.getId() + " idAN "
//				+ highestOffer.getAnnoucementId() + " client id "
//				+ highestOffer.getClientId());
//	}
}
