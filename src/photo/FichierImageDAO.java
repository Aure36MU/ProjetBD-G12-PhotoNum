package src.photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.app.LectureClavier;

import java.sql.Date;


public class FichierImageDAO {
	
	/**
	 * Renvoie l'idFichier le plus grand de FichierImage. Utilisé pour les opérations INSERT.
	 * @param c Connection
	 * @return
	 */
	
	public static int getHigherIdFichier(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idFichier) FROM FichierImage;");
			return res.getInt(0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/** Renvoie la date du jour pour utilisation avec la base de données.
	 * 
	 * @return La date du jour formatté comme suit : "yyyy-mm-dd"
	 */
	public static String today() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
	}
	

	/**
	 * Sélectionne tous les fichiers images sans conditions.
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
	 * Sélectionne tous les fichiers images avec des conditions paramétrées.
	 * 
	 * @param conn Connection SQL
	 * @param condition chaîne de caractères formaté comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets FichierImage sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn, String condition) throws SQLException {
			

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE "+condition+";");
		return getFichiersImage(result);

		
	}
	
	/**
	 * Sélectionne tous les fichiers images créés par un certain utilisateur.
	 * 
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets FichierImage sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAllFromUser(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idUser="+id+";");
		return getFichiersImage(result);

	}
	
	
	/**
	 * Ajoute un fichier image dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idUser
	 * @param chemin
	 * @param infoPVue
	 * @param pixelImg
	 * @param partage
	 * @param dateUtilisation
	 * @param fileAttModif
	 * @param fileAttSuppr
	 * @throws SQLException
	 */
	public static void addFichierImage(Connection conn, int idUser, String chemin, String infoPVue,
			int pixelImg, boolean partage, Date dateUtilisation, boolean fileAttModif, boolean fileAttSuppr) throws SQLException {

		conn.setAutoCommit(true);

		PreparedStatement state = conn.prepareStatement("INSERT INTO FichierImage VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
		state.setInt(1, getHigherIdFichier(conn));
		state.setInt(2, idUser);
		state.setString(3, chemin);
		state.setString(4, infoPVue);
		state.setInt(5, pixelImg);
		state.setBoolean(6, partage);
		state.setDate(7, dateUtilisation);
		state.setBoolean(8, fileAttModif);
		state.setBoolean(9, fileAttSuppr);
		state.executeUpdate();
	}
	
	
	/**
	 * Modifie un fichier image d'un certain idFichier dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
	 * 
	 * @param conn Connection SQL
	 * @param idFichier
	 * @param idUser
	 * @param chemin
	 * @param infoPVue
	 * @param pixelImg
	 * @param partage
	 * @param dateUtilisation
	 * @param fileAttModif
	 * @param fileAttSuppr
	 * @throws SQLException
	 */
	public static void updateFichierImage(Connection conn, int idFichier, int idUser, String chemin, String infoPVue,
			int pixelImg, boolean partage, Date dateUtilisation, boolean fileAttModif, boolean fileAttSuppr) throws SQLException {

		conn.setAutoCommit(true);

		PreparedStatement state = conn.prepareStatement("UPDATE FichierImage SET (idUser=?, chemin=?, infoPVue=?, pixelImg=?, partage=?, dateUtilisation=?, fileAttModif=?, fileAttSuppr=?) WHERE idFichier=?;");
		state.setInt(1, idUser);
		state.setString(2, chemin);
		state.setString(3, infoPVue);
		state.setInt(4, pixelImg);
		state.setBoolean(5, partage);
		state.setDate(6, dateUtilisation);
		state.setBoolean(7, fileAttModif);
		state.setBoolean(8, fileAttSuppr);
		state.setInt(9, idFichier);
		state.executeUpdate();
	
	}
	
	
	/**
	 * Supprime un FichierImage d'un certain idFichier de la base.
	 * 
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @throws SQLException 
	 */
	public static void deleteFichierImage(Connection conn, int id) throws SQLException {

		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM FichierImage WHERE idFichier="+id+";");
	}
	
	
	/**
	 * Retourne les objets FichierImage construits à partir d'un résultat de requête.
	 * 
	 * @param result le ResultSet de la requête SQL
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

	
		
	/** Permet d'insérer un nouveau FichierImage à partir de certains paramètres.*
	 * Les autres (partage, fileAttModif, fileAttSuppr) prennent des valeurs par défaut.
	 * 
	 * @param conn Connection
	 * @param idUser id utilisateur
	 * @param chemin chemin de l'image
	 * @param infoPVue infos prise de vue
	 * @param pixelImg dimensions de l'image
	 * @throws SQLException
	 */
	public static void uploadFichierImage(Connection conn, int idUser, String chemin, String infoPVue, int pixelImg) throws SQLException {
		addFichierImage(conn, idUser, chemin, infoPVue, pixelImg, false, Date.valueOf(today()), false, false);

	}
	
	
	
	
	/**
	 * Version interactive de uploadFichierImage. Demande à l'utilisateur un chemin, les infos de prise de vue et la dimension de l'image.
	 * 
	 * @throws SQLException 
	 */
	public static void uploadFichierImage(Connection conn, int idUser) throws SQLException {
		String chemin = LectureClavier.lireChaine("Entrez un chemin :");
		String infoPVue = LectureClavier.lireChaine("Entrez les infos de prise de vue :");
		int pixelImg = LectureClavier.lireEntier("Entrez la dimension de l'image :");
		uploadFichierImage(conn, idUser, chemin, infoPVue, pixelImg);
	}

}
