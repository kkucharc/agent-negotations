package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	private static String basePath = "jdbc:hsqldb:file:./DB/sag;ifexists=true";
	private static String user = "sag";
	private static String password = "sag";
	
	public static void init(String baseFile, String user, String password){
		DAOFactory.basePath = "jdbc:hsqldb:file:"+baseFile+";ifexists=true";
		DAOFactory.user = user;
		DAOFactory.password = password;
		
		DAOFactory.init();
	}
	
	public static void init(){
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver" );
			Connection c = DriverManager.getConnection(basePath, "sag", "sag");
			
			AnnoucementDAO.init(c);
			OfferDAO.init(c);
			ClientDAO.init(c);
			SellerDAO.init(c);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
