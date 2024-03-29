package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.domain.Offer;

public class OfferDAO {
	static private Connection c;
	static private Statement statement;

	static public void init(Connection c) {
		try {
			OfferDAO.c = c;
			OfferDAO.statement = c.createStatement();
			deleteAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Offer get(int id) {
		Offer offer = new Offer();
		String query = "Select * from Offers where id=" + id;
		try {
			ResultSet result = statement.executeQuery(query);
			if (result.next()) {
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offer;
	}

	public static Offer getLastByAnnoucement(int id) {
		Offer offer = new Offer();
		String query = "Select * from Offers where annoucements_id=" + id
				+ " and price = (SELECT MAX(PRICE) FROM Offers) ORDER BY Date DESC";
		try {
			ResultSet result = statement.executeQuery(query);
			if (result.next()) {
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
			}
			return offer;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Offer> getAll() {
		List<Offer> list = new ArrayList<Offer>();
		String query = "Select * from Offers";
		try {

			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				Offer offer = new Offer();
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
				list.add(offer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Offer> getByAnnoucement(int annoucementId) {
		List<Offer> list = new ArrayList<Offer>();
		String query = "Select * from Offers where annoucements_id="
				+ annoucementId;
		try {

			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				Offer offer = new Offer();
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
				list.add(offer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Offer> getByClient(int clientId) {
		List<Offer> list = new ArrayList<Offer>();
		String query = "Select * from Offers where client_id=" + clientId;
		try {

			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				Offer offer = new Offer();
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
				list.add(offer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Offer save(Offer offer) {
		try {
			String query = "Select max(id) from Offers";
			ResultSet result;
			result = statement.executeQuery(query);

			if (result.next())
				offer.setId(result.getInt(1) + 1);

			PreparedStatement ps = c
					.prepareStatement("Insert into Offers (id,annoucements_id, client_id, date, price) values(?,?,?,?,?)");

			ps.setInt(1, offer.getId());
			ps.setInt(2, offer.getAnnoucementId());
			ps.setInt(3, offer.getClientId());
			ps.setDate(4, new Date(offer.getDate().getTime()));
			ps.setInt(5, offer.getPrice());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return offer;
	}

	public static void update(Offer offer) {
		try {
			PreparedStatement ps = c
					.prepareStatement("UPDATE Offers SET annoucements_id=?, client_id=?, date=?, price=? where id=?");

			ps.setInt(1, offer.getAnnoucementId());
			ps.setInt(2, offer.getClientId());
			ps.setDate(3, new Date(offer.getDate().getTime()));
			ps.setInt(4, offer.getPrice());
			ps.setInt(5, offer.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Boolean delete(int id) {
		try {
			PreparedStatement ps = c
					.prepareStatement("DELETE FROM Offers WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static Offer getMaxOffer(int annoucementId) {
		Offer offer = new Offer();
		try {
			PreparedStatement ps = c
					.prepareStatement("select * from offers where price = (select max(price) from offers where annoucements_id=?) and annoucements_id=? ");
			ps.setInt(1, annoucementId);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				offer.setId(result.getInt(1));
				offer.setAnnoucementId(result.getInt(2));
				offer.setClientId(result.getInt(3));
				offer.setDate(result.getTimestamp(4));
				offer.setPrice(result.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offer;
	}
	
	static public Boolean deleteAll(){
		try {
			PreparedStatement ps= c.prepareStatement("DELETE FROM Offers");
			ps.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}