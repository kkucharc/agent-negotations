package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.domain.Seller;

public class SellerDAO {
	static private Connection c;
	static private Statement statement;
	
	static public void init(Connection c){
			try{
				SellerDAO.c = c;
				SellerDAO.statement = c.createStatement();		
			} catch (SQLException e) {}
	}
	
	public SellerDAO(){}
	
	public static Seller get(int id){
		Seller seller = new Seller();
		String query = "Select * from Sellers where id="+id;
		try {
			ResultSet result = statement.executeQuery(query);
			if(result.next()){
				seller.setId(result.getInt(1));
				seller.setName(result.getString(2));
				seller.setSurname(result.getString(3));
				seller.setPassword(result.getString(4));
			}
		} catch (SQLException e) {
			return null;
		}
		return seller;
	}
	
	public static List<Seller> getAll(){
		List<Seller> list = new ArrayList<Seller>();
		String query = "Select * from Sellers";
		try{
			
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				Seller seller = new Seller();
				seller.setId(result.getInt(1));
				seller.setName(result.getString(2));
				seller.setSurname(result.getString(3));
				seller.setPassword(result.getString(4));
				list.add(seller);
			}
		} catch (SQLException e) {}
		return list;
	}
	
	public static Seller save(Seller seller){
		try {
			String query ="Select max(id) from Sellers";
			ResultSet result;
				result = statement.executeQuery(query);

			if(result.next())
				seller.setId(result.getInt(1)+1);

			PreparedStatement ps= c.prepareStatement("Insert into Sellers (id,first_name, second_name, password) values(?,?,?,?)");
			ps.setInt(1,seller.getId());
			ps.setString(2,seller.getName());
			ps.setString(3,seller.getSurname());
			ps.setString(4,seller.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return seller;
	}
	
	public static void update(Seller seller){
		
		try {
			PreparedStatement ps= c.prepareStatement("UPDATE Sellers SET first_name=?, second_name=?, password=? where id=?");
			
			ps.setString(1,seller.getName());
			ps.setString(2,seller.getSurname());
			ps.setString(3,seller.getPassword());
			ps.setInt(4, seller.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean delete(int id){
		try {
			PreparedStatement ps= c.prepareStatement("DELETE FROM Sellers WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
