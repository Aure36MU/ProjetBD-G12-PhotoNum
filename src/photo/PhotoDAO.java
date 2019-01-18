package src.photo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhotoDAO {
	

	/**
	 * S�lectionne toutes les photos sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Photo
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAll(Connection conn) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo;");
		return getPhotos(result);


	}
	
	/**
	 * S�lectionne toutes les photos avec des conditions param�tr�es.
	 * 
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Photo s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAll(Connection conn, String condition) throws SQLException {
			

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE "+condition+";");
		return getPhotos(result);

		
	}
	
	/**
	 * S�lectionne toutes les photos cr��s par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets Photo s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromUser(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo JOIN FichierImage ON (Photo.idFichier = FichierImage.idFichier) WHERE FichierImage.idUser="+id+";");
		return getPhotos(result);

	}
	
	
	/**
	 * S�lectionne toutes les photos bas�s sur un certain FichierImage.
	 * 
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @return ArrayList contenant les objets Photo s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromFichierImage(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE idFichier="+id+";");
		return getPhotos(result);

	}
	
	/**
	 * Retourne les objets Photo construits � partir d'un r�sultat de requ�te.
	 * 
	 * @param result le ResultSet de la requ�te SQL
	 * @return ArrayList contenant les objets Photo
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> getPhotos(ResultSet result) throws SQLException {
		ArrayList<Photo> photos = new ArrayList<Photo>();

		while (result.next()) {
			
			photos.add(new Photo(
					result.getInt("idPh"),
					result.getInt("idFichier"),
					result.getString("retouche")
					));
						
		}

		return photos;
	}


}
