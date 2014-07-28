package dao;

import jade.util.leap.Serializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.domain.Annoucement;

public class AnnoucementDAO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -318172438412151339L;
	static private Connection c;
	static private Statement statement;
	static final public String STATUS_ENDED = "ended";
	
	static public void init(Connection c){
			try{
				AnnoucementDAO.c = c;
				AnnoucementDAO.statement = c.createStatement();		
				deleteAll();
				fillTable();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	private static void fillTable() {

		Annoucement a = new Annoucement("komputer",10,15,50,0,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",15,15,20,1,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",5,5,10,2,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",0,0,10,0,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",0,0,0,0,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",15,15,0,1,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",5,5,55,1,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",1,1,0,2,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",2,2,5,2,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",12,12,13,0,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
		a = new Annoucement("telefon",10,15,50,0,"start");
		System.out.println("Aukcja " + a.getType()
				+ "\n\t - czas rozpoczêcia: " + a.getStartDate()
				+ "\n\t - czas zakonczenia: " + a.getEndDate()
				+ "\n\t - cena wywolawcza: " + a.getCurrentPrice());
		save(a);
	
		
	}

	static public Annoucement get(int id){
		Annoucement annoucement = new Annoucement();
		String query = "Select id,type,start_price,current_price,minimum_price,start_date,end_date,time,seller_id,status,winner_offer from Annoucements where id="+id;
		try {
			ResultSet result = statement.executeQuery(query);
			if(result.next()){
				annoucement.setId(result.getInt(1));
				annoucement.setType(result.getString(2));
				annoucement.setStartPrice(result.getInt(3));
				annoucement.setCurrentPrice(result.getInt(4));
				annoucement.setMinimumPrace(result.getInt(5));
				annoucement.setStartDate(result.getTimestamp(6));
				annoucement.setEndDate(result.getTimestamp(7));
				annoucement.setTime(result.getInt(8));
				annoucement.setSellerId(result.getInt(9));
				annoucement.setStatus(result.getString(10));
				annoucement.setWinnerOfert(result.getInt(11));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return annoucement;
	}
	
	static public List<Annoucement> getAll(){
		List<Annoucement> list = new ArrayList<Annoucement>();
		String query = "Select id,type,start_price,current_price,minimum_price,start_date,end_date,time,seller_id,status,winner_offer from Annoucements";
		try{	
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				Annoucement annoucement = new Annoucement();
				annoucement.setId(result.getInt(1));
				annoucement.setType(result.getString(2));
				annoucement.setStartPrice(result.getInt(3));
				annoucement.setCurrentPrice(result.getInt(4));
				annoucement.setMinimumPrace(result.getInt(5));
				annoucement.setStartDate(result.getTimestamp(6));
				annoucement.setEndDate(result.getTimestamp(7));
				annoucement.setTime(result.getInt(8));
				annoucement.setSellerId(result.getInt(9));
				annoucement.setStatus(result.getString(10));
				annoucement.setWinnerOfert(result.getInt(11));
				list.add(annoucement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	static public List<Annoucement> getByType(String type){
		List<Annoucement> list = new ArrayList<Annoucement>();
		String query = "Select id,type,start_price,current_price,minimum_price,start_date,end_date,time,seller_id,winner_offer from Annoucements where TYPE like '"+type+"%'";
		try{	
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				Annoucement annoucement = new Annoucement();
				annoucement.setId(result.getInt(1));
				annoucement.setType(result.getString(2));
				annoucement.setStartPrice(result.getInt(3));
				annoucement.setCurrentPrice(result.getInt(4));
				annoucement.setMinimumPrace(result.getInt(5));
				annoucement.setStartDate(result.getTimestamp(6));
				annoucement.setEndDate(result.getTimestamp(7));
				annoucement.setTime(result.getInt(8));
				annoucement.setSellerId(result.getInt(9));
				annoucement.setStatus(result.getString(10));
				annoucement.setWinnerOfert(result.getInt(11));
				list.add(annoucement);
			}
		} catch (SQLException e) {}
		return list;
	}
	
	static public Annoucement save(Annoucement annoucement){
		try {
			String query ="Select max(id) from Annoucements";
			ResultSet result;
				result = statement.executeQuery(query);

			if(result.next())
				annoucement.setId(result.getInt(1)+1);
			
			String query1 = "";
			String query2 = "";
			if(annoucement.getStatus()!= null && annoucement.getStatus().equals(STATUS_ENDED)){
				query1 = ",winner_offer";
				query2 = ",?";
			}
			PreparedStatement ps= c.prepareStatement("Insert into Annoucements (id,type,start_price,current_price,minimum_price,start_date,end_date,time,seller_id,status"+query1+") values(?,?,?,?,?,?,?,?,?,?"+query2+")");

			ps.setInt(1, annoucement.getId());
			ps.setString(2,annoucement.getType());
			ps.setInt(3,annoucement.getStartPrice());
			ps.setInt(4,annoucement.getCurrentPrice());
			ps.setInt(5,annoucement.getMinimumPrace());
			ps.setTimestamp(6,new Timestamp(annoucement.getStartDate().getTime()));
			ps.setTimestamp(7,new Timestamp(annoucement.getEndDate().getTime()));
			ps.setInt(8, annoucement.getTime());
			ps.setInt(9,annoucement.getSellerId());
			ps.setString(10,annoucement.getStatus());
			if(annoucement.getStatus().equals(STATUS_ENDED))
				ps.setInt(11, annoucement.getWinnerOfert());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return annoucement;
	}
	
	static public void update(Annoucement annoucement){
		try {
			String query1 = "";
			if(annoucement.getStatus()!= null && annoucement.getStatus().equals(STATUS_ENDED)){
				query1 = ",winner_offer=?";
			}
			PreparedStatement ps= c.prepareStatement("UPDATE Annoucements SET type=?, start_price=?, current_price=?, minimum_price=?, start_date=?, end_date=?,time=?, seller_id=?, status=? "+query1+" where id=?");
			
			ps.setString(1,annoucement.getType());
			ps.setInt(2,annoucement.getStartPrice());
			ps.setInt(3,annoucement.getCurrentPrice());
			ps.setInt(4,annoucement.getMinimumPrace());
			ps.setTimestamp(5,new Timestamp(annoucement.getStartDate().getTime()));
			ps.setTimestamp(6,new Timestamp(annoucement.getEndDate().getTime()));
			ps.setInt(7, annoucement.getTime());
			ps.setInt(8,annoucement.getSellerId());
			ps.setString(9,annoucement.getStatus());
			if(annoucement.getStatus()!= null && annoucement.getStatus().equals(STATUS_ENDED)){
				ps.setInt(10, annoucement.getWinnerOfert());
				ps.setInt(11, annoucement.getId());
			}else{
				ps.setInt(10, annoucement.getId());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static public Boolean delete(int id){
		try {
			PreparedStatement ps= c.prepareStatement("DELETE FROM Annoucements WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	static public Boolean deleteAll(){
		try {
			PreparedStatement ps= c.prepareStatement("DELETE FROM Annoucements");
			ps.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
}
