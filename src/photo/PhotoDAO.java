package src.photo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.cadre.Cadre;

public class PhotoDAO {
	

	/**
	 * Sélectionne toutes les photos sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Photo
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAll(Connection conn) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo;");
		return getPhotos(result);

	}
	
	/**
	 * Sélectionne toutes les photos avec des conditions paramétrées.
	 * 
	 * @param conn Connection SQL
	 * @param condition chaîne de caractères formaté comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Photo sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAll(Connection conn, String condition) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE "+condition+";");
		return getPhotos(result);

		
	}
	
	/**
	 * Sélectionne toutes les photos créés par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets Photo sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromUser(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo JOIN FichierImage ON (Photo.idFichier = FichierImage.idFichier) WHERE FichierImage.idUser="+id+";");
		return getPhotos(result);

	}
	public static ArrayList<Photo> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Photo') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp; and i.type='Photo");
        return getPhotos(result);
    }
	
	/**
	 * Sélectionne toutes les photos d'une certaine impression.
	 * 
	 * @param conn Connection SQL
	 * @param id id impression
	 * @return ArrayList contenant les objets Photo sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromImpression(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo JOIN Impression_Photo ON (Photo.idPh = Impression_Photo.idPh) WHERE Impression_Photo.idImp="+id+";");
		return getPhotos(result);

	}
	
	
	/**
	 * Sélectionne toutes les photos basés sur un certain FichierImage.
	 * 
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @return ArrayList contenant les objets Photo sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromFichierImage(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE idFichier="+id+";");
		return getPhotos(result);

	}
	
	
	/**
	 * Ajoute une photo dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idPh
	 * @param idFichier
	 * @param retouche
	 * @throws SQLException
	 */
	public static void insertPhoto(Connection conn, int idPh, int idFichier, String retouche) throws SQLException {

		PreparedStatement state = conn.prepareStatement("INSERT INTO Photo VALUES (?, ?, ?);");
		state.setInt(1, idPh);
		state.setInt(2, idFichier);
		state.setString(3, retouche);
		state.executeUpdate();
	}
	
	
	/**
	 * Modifie une Photo d'un certain idPh dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idPh
	 * @param idFichier
	 * @param retouche
	 * @throws SQLException
	 */
	public static void updatePhoto(Connection conn, int idPh, int idFichier, String retouche) throws SQLException {

		PreparedStatement state = conn.prepareStatement("UPDATE FichierImage SET (idFichier=?, retouche=?) WHERE idPh=?;");
		state.setInt(1, idFichier);
		state.setString(2, retouche);
		state.setInt(3, idPh);
		state.executeUpdate();
	
	}
	
	
	/**
	 * Supprime une Photo d'un certain idPh de la base.
	 * 
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @throws SQLException 
	 */
	public static void deletePhoto(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Photo WHERE idPh="+id+";");
	}
	
	/**
	 * Retourne les objets Photo construits à partir d'un résultat de requête.
	 * 
	 * @param result le ResultSet de la requête SQL
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
