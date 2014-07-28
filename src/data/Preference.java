package data;

import java.io.Serializable;

public class Preference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813369407330672552L;
	private String type;
	private int maxcena;
	private int shotTime; // ile przed koncem przebic w sekundach
	private boolean cenapriority;
	private boolean datapriority;
	private int timeBid; /*jak czesto podbijac cene*/
	private String strategy;
	private int shotPrice;
	
	
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Preference() {
		setTimeBid(0);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMaxcena() {
		return maxcena;
	}

	public void setMaxcena(int maxcena) {
		this.maxcena = maxcena;
	}

	public boolean isCenapriority() {
		return cenapriority;
	}

	public void setCenapriority(boolean cenapriority) {
		this.cenapriority = cenapriority;
	}

	public boolean isDatapriority() {
		return datapriority;
	}

	public void setDatapriority(boolean datapriority) {
		this.datapriority = datapriority;
	}

	public int getShotTime() {
		return shotTime;
	}

	public void setShotTime(int startSec) {
		this.shotTime = startSec;
	}

	public int getTimeBid() {
		return timeBid;
	}

	public void setTimeBid(int timeBid) {
		this.timeBid = timeBid;
	}

	public int getShotPrice() {
		return shotPrice;
	}

	public void setShotPrice(int shotPrice) {
		this.shotPrice = shotPrice;
	}


}
