package src.impression.calendrier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.Format;
import src.impression.Qualite;

public class CalendrierDAO {
	
	public static ArrayList<Calendrier> selectAll(Connection conn) {
		
		ArrayList<Calendrier> calendriers = new ArrayList<Calendrier>();
		try {
			conn.setAutoCommit(true);
	
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM Calendrier;");
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
	
		
	public static ArrayList<Calendrier> selectAll(Connection conn, String condition) {
			
			ArrayList<Calendrier> calendriers = new ArrayList<Calendrier>();
			try {
				conn.setAutoCommit(true);
		
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM Calendrier WHERE "+condition+";");
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
