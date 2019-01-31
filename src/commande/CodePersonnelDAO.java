package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;

public class CodePersonnelDAO {

	public static void createCodePersonnel(Connection c, int idUser){
		try {
			String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String code= generateCode(10);
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO CodePersonnel "
					+ "(dateAcqui,dateUtil,code,taux,idUser)"
					+ "VALUES ("+ today + ", " + today + ", " + code + ", 0.1 , " + idUser + "); " );
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static String generateCode(int length)
	{
		    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
		    String pass = "";
		    for(int x=0;x<length;x++)
		    {
		       int i = (int)Math.floor(Math.random() * 62); // Si tu supprimes des lettres tu diminues ce nb
		       pass += chars.charAt(i);
		    }
		    System.out.println(pass);
		    return pass;
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
			e.printStackTrace();
			return null;
		}
		return codePersonnel;
	}
}
