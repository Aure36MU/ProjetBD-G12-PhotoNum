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
	 * Renvoie l'idFichier le plus grand de FichierImage. Utilis� pour les op�rations INSERT.
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
	
	
	/** Renvoie la date du jour pour utilisation avec la base de donn�es.
	 * 
	 * @return La date du jour formatt� comme suit : "yyyy-mm-dd"
	 */
	public static String today() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
	}
	
	/**
	 * S�lectionne tous les fichiers images sans conditions.
	 * 
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets FichierImage
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn) throws SQLException {
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

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idUser="+id+";");
		return getFichiersImage(result);

	}

	
	
	
	/**
	 * Ajoute un fichier image dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
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
	public static void insertFichierImage(Connection conn, int idUser, String chemin, String infoPVue,
			int pixelImg, boolean partage, Date dateUtilisation, boolean fileAttModif, boolean fileAttSuppr) throws SQLException {

		PreparedStatement state = conn.prepareStatement("INSERT INTO FichierImage VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
		state.setInt(1, getHigherIdFichier(conn)+1);
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
	 * Modifie un fichier image d'un certain idFichier dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
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
	private static void update(Connection conn, int idFichier, int idUser, String chemin, String infoPVue,
			int pixelImg, boolean partage, Date dateUtilisation, boolean fileAttModif, boolean fileAttSuppr) throws SQLException {

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
	 * Modifie un fichier image d'un certain idFichier dans la base de donn�es.
	 * Cette m�thode v�rifie si le FichierImage est �ligible pour la modification dans le cas o� il est partag�.
	 * Param�tres modifiables : chemin, infoPVue, pixelImg, partage.
	 * 
	 * @param conn Connection SQL
	 * @param id
	 * @param newChemin
	 * @param newInfoPVue
	 * @param newPixelImg
	 * @param newPartage
	 * @throws SQLException
	 */
	public static void updateFichierImage(Connection conn, int id, String newChemin, String newInfoPVue, int newPixelImg, boolean newPartage) throws SQLException {
		
		FichierImage leFichierImage = selectAll(conn, "idFichier="+id).get(0);
		if (isSharedAndUsedBySomeone(conn, id)) {
			update(conn,
					leFichierImage.getIdFichier(),
					leFichierImage.getIdUser(),
					leFichierImage.getChemin(),
					leFichierImage.getInfoPVue(),
					leFichierImage.getPixelImg(),
					leFichierImage.isPartage(),
					leFichierImage.getDateUtilisation(),
					true,
					leFichierImage.isFileAttSuppr());
		} else {
			update(conn,
					leFichierImage.getIdFichier(),
					leFichierImage.getIdUser(),
					newChemin,
					newInfoPVue,
					newPixelImg,
					newPartage,
					leFichierImage.getDateUtilisation(),
					true,
					leFichierImage.isFileAttSuppr());
		}
	}
	
	
	
	/**
	 * Supprime un FichierImage d'un certain idFichier de la base.
	 * @param conn
	 * @param id
	 * @throws SQLException
	 */
	private static void delete(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM FichierImage WHERE idFichier="+id+";");
	}
	

	/**
	 * Supprime un FichierImage d'un certain idFichier de la base.
	 * Cette m�thode v�rifie si le FichierImage est �ligible pour la suppression dans le cas o� il est partag�.
	 * 
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @throws SQLException 
	 */
	public static void deleteFichierImage(Connection conn, int id) throws SQLException {
		
		FichierImage leFichierImage = selectAll(conn, "idFichier="+id).get(0);
		if (isSharedAndUsedBySomeone(conn, id)) {
			update(conn,
					leFichierImage.getIdFichier(),
					leFichierImage.getIdUser(),
					leFichierImage.getChemin(),
					leFichierImage.getInfoPVue(),
					leFichierImage.getPixelImg(),
					leFichierImage.isPartage(),
					leFichierImage.getDateUtilisation(),
					leFichierImage.isFileAttModif(),
					true);
		} else {
			delete(conn, id);
		}
	}


	/**
	 * V�rifie si un FichierImage est partag� et utilis� par quelqu'un d'autre � partir de l'id fichier.
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public static boolean isSharedAndUsedBySomeone(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT count(Impression.id_user) FROM FichierImage NATURAL JOIN Photo NATURAL JOIN Impression_Photo NATURAL JOIN Impression WHERE idFichier="+id+";");
		result.next();
		return (result.getInt(0) > 1);
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

	
		
	/** Permet d'ins�rer un nouveau FichierImage � partir de certains param�tres.*
	 * Les autres (partage, fileAttModif, fileAttSuppr) prennent des valeurs par d�faut.
	 * 
	 * @param conn Connection
	 * @param idUser id utilisateur
	 * @param chemin chemin de l'image
	 * @param infoPVue infos prise de vue
	 * @param pixelImg dimensions de l'image
	 * @throws SQLException
	 */
	public static void uploadFichierImage(Connection conn, int idUser, String chemin, String infoPVue, int pixelImg) throws SQLException {
		insertFichierImage(conn, idUser, chemin, infoPVue, pixelImg, false, Date.valueOf(today()), false, false);

	}
	
	
	
	
	/**
	 * Version interactive de uploadFichierImage. Demande � l'utilisateur un chemin, les infos de prise de vue et la dimension de l'image.
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
