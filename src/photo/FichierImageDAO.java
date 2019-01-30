package src.photo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.app.Affichage;
import src.app.LectureClavier;
import java.sql.Date;


public class FichierImageDAO {
	
	/** Renvoie la date du jour pour utilisation avec la base de donn�es.
	 * @return La date du jour formatt� comme suit : "dd/mm/yyyy"
	 */
	public static String today() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
	}
	
	/**
	 * S�lectionne tous les fichiers images sans conditions.
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets FichierImage
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage");
		return getFichiersImage(result);
	}
	
	public static ArrayList<Owners> selectAllWithOwner(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT idFichier,chemin,infoPVue,partager,dateUtilisation,prenom, nom  FROM FichierImage NATURAL JOIN Utilisateur ");

		ArrayList<Owners> owners = new ArrayList<Owners>();
		while (result.next()) {
			owners.add(new Owners(result.getInt("idFichier"),
					result.getString("chemin"),
					result.getString("infoPVue"),
					result.getInt("partager"),
					result.getDate("dateUtilisation"),
					result.getString("prenom"),
					result.getString("nom"))
					);			
		}
		return owners;
	}
	
	/**
	 * S�lectionne tous les fichiers images avec des conditions param�tr�es.
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets FichierImage s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn, String condition) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE "+condition);
		return getFichiersImage(result);
	}
	
	/**
	 * S�lectionne tous les fichiers images cr��s par un certain utilisateur.
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets FichierImage s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAllFromUser(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idUser="+id);
		return getFichiersImage(result);
	}

	/**
	 * Ajoute un fichier image dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
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
			int pixelImg, int partage, Date dateUtilisation, int fileAttModif, int fileAttSuppr) throws SQLException {
			PreparedStatement state = conn.prepareStatement("INSERT INTO FichierImage VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			state.setInt(1, idUser);
			state.setString(2, chemin);
			state.setString(3, infoPVue);
			state.setInt(4, pixelImg);
			state.setInt(5, partage);
			state.setDate(6, dateUtilisation);
			state.setInt(7, fileAttModif);
			state.setInt(8, fileAttSuppr);
			state.executeUpdate();
	}
	
	
	/**
	 * Modifie un fichier image d'un certain idFichier dans la base de donn�es.
	 * ATTENTION la requ�te est pr�par�e donc tous les param�tres doivent avoir une valeur.
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
			int pixelImg, int partage, Date dateUtilisation, int fileAttModif, int fileAttSuppr) throws SQLException {
			PreparedStatement state = conn.prepareStatement("UPDATE FichierImage SET idUser=?, chemin=?, infoPVue=?, pixelImg=?, partager=?, dateUtilisation=?, fileAttModif=?, fileAttSuppr=? WHERE idFichier=?");
			state.setInt(1, idUser);
			state.setString(2, chemin);
			state.setString(3, infoPVue);
			state.setInt(4, pixelImg);
			state.setInt(5, partage);
			state.setDate(6, dateUtilisation);
			state.setInt(7, fileAttModif);
			state.setInt(8, fileAttSuppr);
			state.setInt(9, idFichier);
			state.executeUpdate();
	}
	
	
	/**
	 * Modifie un fichier image d'un certain idFichier dans la base de donn�es.
	 * Cette m�thode v�rifie si le FichierImage est �ligible pour la modification dans le cas o� il est partag�.
	 * Param�tres modifiables : chemin, infoPVue, pixelImg, partage.
	 * @param conn Connection SQL
	 * @param id
	 * @param newChemin
	 * @param newInfoPVue
	 * @param newPixelImg
	 * @param newPartage
	 * @throws SQLException
	 */
	public static void updateFichierImage(Connection conn, int id, String newChemin, String newInfoPVue, int newPixelImg, int newPartage) throws SQLException {
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
					1,
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
					1,
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
		Impression_PhotoDAO.deleteAllFromFichierImage(conn, id);
		PhotoDAO.deletePhotosFromFichierImage(conn, id);
		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM FichierImage WHERE idFichier="+id);
	}
	

	/**
	 * Supprime un FichierImage d'un certain idFichier de la base.
	 * Cette m�thode v�rifie si le FichierImage est �ligible pour la suppression dans le cas o� il est partag�.
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
					1);
		} else {
			delete(conn, id);
		}
	}
	
	
	/**
	 * Supprime un FichierImage d'un certain idFichier de la base.
	 * Cette m�thode v�rifie si le FichierImage est �ligible pour la suppression dans le cas o� il est partag�.
	 * En mode Gestionnaire, on doit �galement annuler toutes les commandes de statut 'BROUILLON' ou 'EN_COURS' qui utilisent ce fichier image.
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @throws SQLException 
	 */
	public static void deleteFichierImageModeGestion(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet lesCommandes = state.executeQuery("SELECT idComm FROM Commande NATURAL JOIN Article NATURAL JOIN Impression NATURAL JOIN Impression_Photo NATURAL JOIN Photo NATURAL JOIN FichierImage WHERE idFichier="+id);
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
					1);
			while (lesCommandes.next()) {
				state.executeQuery("UPDATE Commande SET statutCommande='ANNULEE' WHERE statutCommande<>'ENVOYEE' AND idComm="+lesCommandes.getInt("idComm"));
			}
		} else { delete(conn, id);}
	}

	/**
	 * V�rifie si un FichierImage est partag� et utilis� par quelqu'un d'autre � partir de l'id fichier.
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public static boolean isSharedAndUsedBySomeone(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT count(idUser) FROM FichierImage NATURAL JOIN Photo NATURAL JOIN Impression_Photo NATURAL JOIN Impression WHERE idFichier="+id);
		if(result.next()) {
			return (result.getInt(1) > 1);
		}
		return false;
	}


	/**
	 * Retourne les objets FichierImage construits � partir d'un r�sultat de requ�te.
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
					result.getInt("partager"),
					result.getDate("dateUtilisation"),
					result.getInt("fileAttModif"),
					result.getInt("fileAttSuppr")
					));			
		}
		return fichiersimage;
	}

	/** Permet d'ins�rer un nouveau FichierImage � partir de certains param�tres.*
	 * Les autres (partage, fileAttModif, fileAttSuppr) prennent des valeurs par d�faut.
	 * @param conn Connection
	 * @param idUser id utilisateur
	 * @param chemin chemin de l'image
	 * @param infoPVue infos prise de vue
	 * @param pixelImg dimensions de l'image
	 * @throws SQLException
	 */
	public static void uploadFichierImage(Connection conn, int idUser, String chemin, String infoPVue, int pixelImg) throws SQLException {
		insertFichierImage(conn, idUser, chemin, infoPVue, pixelImg, 0, Date.valueOf(today()), 0, 0);
	}

	/**
	 * Version interactive de uploadFichierImage. Demande � l'utilisateur un chemin, les infos de prise de vue et la dimension de l'image.
	 * @throws SQLException 
	 */
	public static void uploadFichierImage(Connection conn, int idUser) throws SQLException {
		String chemin = LectureClavier.lireChaine("Entrez un chemin :");
		String infoPVue = LectureClavier.lireChaine("Entrez les infos de prise de vue :");
		int pixelImg = LectureClavier.lireEntier("Entrez la dimension de l'image :");
		uploadFichierImage(conn, idUser, chemin, infoPVue, pixelImg);
	}
	
	public static Boolean idExists(Connection c, int idFichier) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from FichierImage where idFichier='"+idFichier+"'");
		if (result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}

	public static void gererFichiersClients(Connection c) throws SQLException {
		new Affichage<Owners>().afficher(selectAllWithOwner(c));
		int idFichier = -1;
		while(!idExists(c,idFichier)){
			idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste pr�sent�e ci-dessus).");
		}
		deleteFichierImage(c, idFichier);
	}
	
}
