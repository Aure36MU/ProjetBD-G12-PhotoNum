package src.impression.calendrier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.Format;
import src.impression.Qualite;

public class CalendrierDAO {
	
	/**
	 * Sélectionne tous les calendriers (quels que soient leurs modèles) sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Calendrier
	 * @throws SQLException 
	 */
	public static ArrayList<Calendrier> selectAll(Connection conn) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Calendrier;");
		return getCalendriers(result);


	}
	
	/**
	 * Sélectionne tous les calendriers avec des conditions paramétrées.
	 * 
	 * @param conn Connection SQL
	 * @param condition chaîne de caractères formaté comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Calendrier sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Calendrier> selectAll(Connection conn, String condition) throws SQLException {
			

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Calendrier WHERE "+condition+";");
		return getCalendriers(result);

		
	}
	
	/**
	 * Sélectionne tous les Calendriers créés par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets Calendrier sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Calendrier> selectAllFromUser(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Calendrier JOIN Impression ON (Calendrier.idImp = Impression.idImp) WHERE Impression.idUser="+id+";");
		return getCalendriers(result);

	
	}
	
	
	/**
	 * Ajoute un calendrier dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void addCalendrier(Connection conn, int id, ModeleCalendrier modele) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO Calendrier VALUES("+id+", '"+modele.toString()+"');");
		
	}
	
	
	/**
	 * Modifie un calendrier d'un idImp donné dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void updateCalendrier(Connection conn, int id, ModeleCalendrier modele) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("UPDATE Calendrier SET modele='"+modele.toString()+"' WHERE idImp="+id+";");
		
	}
	
	
	/**
	 * Supprime un calendrier d'un idImp donné de la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void deleteCalendrier(Connection conn, int id) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Calendrier WHERE idImp="+id+";");
		
	}

	
	
	/**
	 * Retourne les objets Calendrier construits à partir d'un résultat de requête.
	 * 
	 * @param result le ResultSet de la requête SQL
	 * @return ArrayList contenant les objets Calendrier
	 * @throws SQLException 
	 */
	public static ArrayList<Calendrier> getCalendriers(ResultSet result) throws SQLException {
		ArrayList<Calendrier> calendriers = new ArrayList<Calendrier>();

		while (result.next()) {
			calendriers.add(new Calendrier(
				result.getInt("idImp"),
				ModeleCalendrier.valueOf(result.getString("modele"))
				));	
		}

		return calendriers;
	}


}
