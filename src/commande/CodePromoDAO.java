package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CodePromoDAO {

	public static void createCodePromo(Connection c, Date dateAcqui, Date dateUtil, String code, int taux, int idUser){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodePromo "
					+ "(dateAcqui,dateUtil,code,taux,idUser)"
					+ "VALUES ("+ ", " + dateAcqui + ", " + dateUtil + ", " + code + ", " + taux + ", " + idUser + "); " );
			
			
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
