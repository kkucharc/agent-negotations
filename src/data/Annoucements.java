package data;

import jade.util.leap.Serializable;

import java.util.ArrayList;
import java.util.List;

import dao.domain.Annoucement;

public class Annoucements implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741748998221920730L;
	private List<Annoucement> annoucements;

	public Annoucements() {
		this.annoucements = new ArrayList<Annoucement>();
	}

	public List<Annoucement> getAnnoucements() {
		return annoucements;
	}

	public void setAnnoucements(List<Annoucement> annoucements) {
		this.annoucements = annoucements;
	}

	public void addAnnoucement(Annoucement a) {

	}

	public void add(Annoucement a) {
		annoucements.add(a);

	}

	public boolean isEmpty() {
		if (this.annoucements.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public int getSize() {
		return annoucements.size();
	}

	public Annoucement getFirst() {
		return annoucements.get(0);
	}


}
