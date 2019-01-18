package src.impression.tirage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TirageDAO {

	public static void createTirage(Connection c, int idImp) throws SQLException {
		Statement stat= c.createStatement();
		stat.executeUpdate("insert into Tirage (idImp)  values ('"+idImp+"')");
	}
	
	public static ArrayList<Tirage> selectAll(Connection c) throws SQLException {
		Statement stat = c.createStatement();
		ResultSet result = stat.executeQuery("select * from Tirage");
		return TirageDAO.getTirages(result);
	}
	
	public static ArrayList<Tirage> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query = "select * from Tirage NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result = stat.executeQuery(query);
		return TirageDAO.getTirages(result);
	}
	
	
	public static ArrayList<Tirage> getTirages(ResultSet result) {
		ArrayList<Tirage> Tirage = new ArrayList<Tirage>();
		try {
			while (result.next()) {
				Tirage.add(new Tirage(
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Tirage;
	}
}
