package src.photo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class FichierImageDAO {
	

	/**
	 * S�lectionne tous les fichiers images sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets FichierImage
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage;");
		return getFichiersImage(result);


	}
	
	/**
	 * S�lectionne tous les fichiers images avec des conditions param�tr�es.
	 * 
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets FichierImage s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn, String condition) throws SQLException {
			

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE "+condition+";");
		return getFichiersImage(result);

		
	}
	
	/**
	 * S�lectionne tous les fichiers images cr��s par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets FichierImage s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAllFromUser(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idUser="+id+";");
		return getFichiersImage(result);

	
	}
	
	/**
	 * Retourne les objets FichierImage construits � partir d'un r�sultat de requ�te.
	 * 
	 * @param result le ResultSet de la requ�te SQL
	 * @return ArrayList contenant les objets FichierImage
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> getFichiersImage(ResultSet result) throws SQLException {
		ArrayList<FichierImage> fichiersimage = new ArrayList<FichierImage>();

		while (result.next()) {

			fichiersimage.add(new FichierImage(
					result.getInt("idFichier"),
					result.getInt("idUser"),
					result.getString("chemin"),
					result.getString("infoPVue"),
					result.getInt("pixelImg"),
					result.getBoolean("partage"),
					result.getDate("dateUtilisation"),
					result.getBoolean("fileAttModif"),
					result.getBoolean("fileAttSuppr")
					));
						
		}

		return fichiersimage;
	}


}
