package src.photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhotoDAO {
	

	public static int getHigherIdFichier(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idPh) FROM Photo");
			if (res.next()) {
				return res.getInt(1);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * S�lectionne toutes les photos sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Photo
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAll(Connection conn) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo");
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
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE "+condition);
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
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo JOIN FichierImage ON (Photo.idFichier = FichierImage.idFichier) WHERE FichierImage.idUser="+id+" OR FichierImage.partage=1");
		return getPhotos(result);
	}
	
	public static ArrayList<Photo> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Photo') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp; and i.type='Photo");
        return getPhotos(result);
    }
	
	/**
	 * S�lectionne toutes les photos d'une certaine impression.
	 * 
	 * @param conn Connection SQL
	 * @param id id impression
	 * @return ArrayList contenant les objets Photo s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Photo> selectAllFromImpression(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo JOIN Impression_Photo ON (Photo.idPh = Impression_Photo.idPh) WHERE Impression_Photo.idImp="+id);
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
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Photo WHERE idFichier="+id);
		return getPhotos(result);
	}
	
	
	/**
	 * Ajoute une photo dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idPh
	 * @param idFichier
	 * @param retouche
	 * @throws SQLException
	 */

	public static void insertPhoto(Connection conn, int idFichier, String retouche) throws SQLException {
		PreparedStatement state = conn.prepareStatement("INSERT INTO Photo VALUES (?, ?, ?)");
		state.setInt(1, getHigherIdFichier(conn)+1);
		state.setInt(2, idFichier);
		state.setString(3, retouche);
		state.executeUpdate();
	}
	
	
	/**
	 * Modifie une Photo d'un certain idPh dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idPh
	 * @param idFichier
	 * @param retouche
	 * @throws SQLException
	 */
	public static void updatePhoto(Connection conn, int idPh, int idFichier, String retouche) throws SQLException {
		PreparedStatement state = conn.prepareStatement("UPDATE FichierImage SET (idFichier=?, retouche=?) WHERE idPh=?");
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
		state.executeUpdate("DELETE FROM Photo WHERE idPh="+id);
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
