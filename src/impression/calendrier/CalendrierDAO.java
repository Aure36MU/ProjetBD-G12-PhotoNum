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
	 * S�lectionne tous les calendriers (quels que soient leurs types : Bureau, Mural...) sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Calendrier
	 */
	public static ArrayList<Calendrier> selectAll(Connection conn) {
		
		try {
			conn.setAutoCommit(true);
	
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Calendrier;");
			return CalendrierDAO.getCalendriers(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * S�lectionne tous les calendriers (quels que soient leurs types : Bureau, Mural...) sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Calendrier s�lectionn�s
	 */
	public static ArrayList<Calendrier> selectAll(Connection conn, String condition) {
			
		try {
			conn.setAutoCommit(true);
	
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Calendrier WHERE "+condition+";");
			return CalendrierDAO.getCalendriers(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * S�lectionne tous les Calendriers cr��s par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets Calendrier s�lectionn�s
	 */
	public static ArrayList<Calendrier> selectAllFromUser(Connection conn, int id) {
		
		try {
			conn.setAutoCommit(true);
	
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Calendrier WHERE idUser="+id+";");
			return CalendrierDAO.getCalendriers(result);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Retourne les objets Calendrier construits � partir d'un r�sultat de requ�te.
	 * 
	 * @param result le ResultSet de la requ�te SQL
	 * @return ArrayList contenant les objets Calendrier
	 */
	public static ArrayList<Calendrier> getCalendriers(ResultSet result) {
		ArrayList<Calendrier> calendriers = new ArrayList<Calendrier>();
		try {
			while (result.next()) {
				if (result.getString("modele").equalsIgnoreCase("Bureau")) {
					calendriers.add(new CalendrierBureau(
							result.getInt("idImp"),
							(Qualite) result.getObject("qualite"),
							(Format) result.getObject("format"),
							result.getInt("idUser"),
							result.getInt("nbrPageTotal")
							));
					
				} else if (result.getString("modele").equalsIgnoreCase("Mural")){
					calendriers.add(new CalendrierMural(
							result.getInt("idImp"),
							(Qualite) result.getObject("qualite"),
							(Format) result.getObject("format"),
							result.getInt("idUser"),
							result.getInt("nbrPageTotal")
							));
					
				}				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return calendriers;
	}


}
