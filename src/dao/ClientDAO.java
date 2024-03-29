package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.domain.Client;

public class ClientDAO {
	
	static private Connection c;
	static private Statement statement;
	
	static public void init(Connection c){
			try{
				ClientDAO.c = c;
				ClientDAO.statement = c.createStatement();
				deleteAll();
				createClients();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	private static void createClients() {
		Client c = new Client("Agnieszka","M","kkk");
		save(c);
		c = new Client("Ala","K","mmm");
		save(c);
		c = new Client("Monika","K","mmm");
		save(c);
		c = new Client("Ela","K","mmm");
		save(c);
	}
	
	public ClientDAO(){}
	
	public static Client get(int id){
		Client client = new Client();
		String query = "Select * from Clients where id="+id;
		try {
			ResultSet result = statement.executeQuery(query);
			if(result.next()){
				client.setId(result.getInt(1));
				client.setName(result.getString(2));
				client.setSurname(result.getString(3));
				client.setPassword(result.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}
	
	public static List<Client> getAll(){
		List<Client> list = new ArrayList<Client>();
		String query = "Select * from Clients";
		try{
			
			ResultSet result = statement.executeQuery(query);
			while(result.next()){
				Client client = new Client();
				client.setId(result.getInt(1));
				client.setName(result.getString(2));
				client.setSurname(result.getString(3));
				client.setPassword(result.getString(4));
				list.add(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static Client save(Client client){
		try {
			String query ="Select max(id) from Clients";
			ResultSet result;
				result = statement.executeQuery(query);

			if(result.next())
				client.setId(result.getInt(1)+1);

			PreparedStatement ps= c.prepareStatement("Insert into Clients (id,first_name, second_name, password) values(?,?,?,?)");

			ps.setInt(1, client.getId());
			ps.setString(2,client.getName());
			ps.setString(3,client.getSurname());
			ps.setString(4,client.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return client;
	}
	
	public static void update(Client client){
		
		try {
			PreparedStatement ps= c.prepareStatement("UPDATE Clients SET first_name=?, second_name=?, password=? where id=?");
			
			ps.setString(1,client.getName());
			ps.setString(2,client.getSurname());
			ps.setString(3,client.getPassword());
			ps.setInt(4, client.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean delete(int id){
		try {
			PreparedStatement ps= c.prepareStatement("DELETE FROM Clients WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	static public Boolean deleteAll(){
		try {
			PreparedStatement ps = c.prepareStatement("DELETE FROM Clients cascade");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
