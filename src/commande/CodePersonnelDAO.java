package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CodePersonnelDAO {

	public static int getHigherIdCodeP(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idCodeP) FROM CodePromo");
			return res.getInt(0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void createCodePersonnel(Connection c, Date dateAcqui, Date dateUtil, String code, int taux, int idUser){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodePersonnel "
					+ "(idCodeP,dateAcqui,dateUtil,code,taux,idUser)"
					+ "VALUES ("+(getHigherIdCodeP(c)+1)+ ", " + dateAcqui + ", " + dateUtil + ", " + code + ", " + taux + ", " + idUser + "); " );
			
			
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static void delete(Connection c, int idCodeP) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'CodePromo' where 'idCodeP'='"+idCodeP+"' ";
		stat.executeUpdate(query);
	}
}
