package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CodePromoDAO {

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
	
	public static void createCodePromo(Connection c, Date dateAcqui, Date dateUtil, String code, int taux, int idUser){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodePromo "
					+ "(idCodeP,dateAcqui,dateUtil,code,taux,idUser)"
					+ "VALUES ("+(getHigherIdCodeP(c)+1)+ ", " + dateAcqui + ", " + dateUtil + ", " + code + ", " + taux + ", " + idUser + "); " );
			
			
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<CodePromo> selectAll(Connection c) throws SQLException{
		ArrayList<CodePromo> tab = new ArrayList<CodePromo>();
		tab.addAll(CodeUniverselDAO.selectAll(c));
		tab.addAll(CodePersonnelDAO.selectAll(c));
		return tab;
	}
	
	public static void delete(Connection c, int idCodeP) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'CodePromo' where 'idCodeP'='"+idCodeP+"' ";
		stat.executeUpdate(query);
	}
}
