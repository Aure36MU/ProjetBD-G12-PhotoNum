package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CodeUniverselDAO {

	public static void createCodeUniversel(Connection c, Date dateAcqui, Date dateUtil, String code, int taux){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodeUniversel "
					+ "(idCodeP,dateAcqui,dateUtil,code,taux)"
					+ "VALUES ("+dateAcqui+ ", " +dateUtil+ ", " +code+ ", " +taux+ ") " );
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<CodeUniversel> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from CodePromo natural join CodeUniversel";
		ResultSet result =stat.executeQuery(query);
		return CodeUniverselDAO.getCodeUniversel(result);
	}
	
	public static void delete(Connection c, int idCodeP) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'CodePromo' where 'idCodeP'='"+idCodeP+"' ";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<CodeUniversel> getCodeUniversel(ResultSet result) {
		ArrayList<CodeUniversel> codeUniversel = new ArrayList<CodeUniversel>();
		try {
			while (result.next()) {
				codeUniversel.add(new CodeUniversel(
					result.getInt("idCodeP"),
					result.getDate("dateAcqui"),
					result.getDate("dateUtil"),
					result.getString("code"),
					result.getInt("taux"),
					result.getInt("idUser")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return codeUniversel;
	}
}
