package src.impression.cadre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.calendrier.Calendrier;

public class CadreDAO {

	
	public static ArrayList<Cadre> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery("select * from Cadre");
		return CadreDAO.getCadres(result);
	}
	
	/**
	 * Sélectionne tous les cadres avec des conditions paramétrées.
	 * 
	 * @param conn Connection SQL
	 * @param condition chaîne de caractères formaté comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Calendrier sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Cadre> selectAll(Connection conn, String condition) throws SQLException {
			

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Cadre WHERE "+condition+";");
		return getCadres(result);

		
	}
	
	public static ArrayList<Cadre> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Cadre NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result =stat.executeQuery(query);
		return CadreDAO.getCadres(result);
	}
	
	
	/**
	 * Ajoute un cadre dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void insertCadre(Connection conn, int id, ModeleCadre modele) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO Cadre VALUES("+id+", '"+modele.toString()+"');");
		
	}
	
	
	/**
	 * Modifie un cadre d'un idImp donné dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void updateCadre(Connection conn, int id, ModeleCadre modele) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("UPDATE Cadre SET modele='"+modele.toString()+"' WHERE idImp="+id+";");
		
	}
	
	
	/**
	 * Supprime un cadre d'un idImp donné de la base.
	 * 
	 * @param id id impression
	 * @throws SQLException 
	 */
	public static void deleteCadre(Connection conn, int id) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Cadre WHERE idImp="+id+";");
		
	}
	
	public static ArrayList<Cadre> getCadres(ResultSet result) {
		ArrayList<Cadre> cadre = new ArrayList<Cadre>();
		try {
			while (result.next()) {
				cadre.add(new Cadre(
					ModeleCadre.valueOf(result.getString("modele")),
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cadre;
	}

	
	
}
