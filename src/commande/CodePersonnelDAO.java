package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class CodePersonnelDAO {

	public static void createCodePersonnel(Connection c, Date dateAcqui, Date dateUtil, String code, int taux, int idUser){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodePersonnel "
					+ "(dateAcqui,dateUtil,code,taux,idUser)"
					+ "VALUES ("+ dateAcqui + ", " + dateUtil + ", " + code + ", " + taux + ", " + idUser + "); " );
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<CodePersonnel> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from CodePromo natural join CodePersonnel";
		ResultSet result =stat.executeQuery(query);
		return CodePersonnelDAO.getCodePersonnel(result);
	}
	
	public static ArrayList<CodePersonnel> selectAll(Connection c, int id) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from CodePromo natural join CodePersonnel where idUser="+id;
		ResultSet result =stat.executeQuery(query);
		return CodePersonnelDAO.getCodePersonnel(result);
	}
	
	public static void delete(Connection c, int idCodeP) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'CodePromo' where 'idCodeP'='"+idCodeP+"' ";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<CodePersonnel> getCodePersonnel(ResultSet result) {
		ArrayList<CodePersonnel> codePersonnel = new ArrayList<CodePersonnel>();
		try {
			while (result.next()) {
				codePersonnel.add(new CodePersonnel(
					result.getInt("idCodeP"),
					result.getDate("dateAcqui"),
					result.getDate("dateUtil"),
					result.getString("code"),
					result.getInt("taux"),
					result.getInt("idUser")
				));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return codePersonnel;
	}
}
