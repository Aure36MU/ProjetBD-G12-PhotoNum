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
import src.compte.Utilisateur;

import java.sql.Date;


public class FichierImageDAO {
	
	/**
	 * Sélectionne tous les fichiers images avec une id donnée
	 * @param conn Connection SQL
	 * @param condition chaîne de caractères formaté comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets FichierImage sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAll(Connection conn, int idFichier) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idFichier = '"+idFichier+"'");
		return getFichiersImage(result);
	}
	
	/**
	 * Sélectionne tous les fichiers images créés par un certain utilisateur.
	 * @param conn Connection SQL
	 * @param id id utilisateur
	 * @return ArrayList contenant les objets FichierImage sélectionnés
	 * @throws SQLException 
	 */
	public static ArrayList<FichierImage> selectAllFromUser(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM FichierImage WHERE idUser="+id);
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
	 * Ajoute un fichier image dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
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
			PreparedStatement state = conn.prepareStatement("INSERT INTO FichierImage (idUser,chemin,infoPVue,pixelImg,partager,dateUtilisation,fileAttModif,fileAttSuppr) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
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
	 * Modifie un fichier image d'un certain idFichier dans la base de données.
	 * ATTENTION la requête est préparée donc tous les paramètres doivent avoir une valeur.
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
	private static void update(Connection conn, int idFichier, int idUser, String chemin, String infoPVue,int pixelImg, int partage, Date dateUtilisation, int fileAttModif, int fileAttSuppr) throws SQLException {
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
	 * Modifie un fichier image d'un certain idFichier dans la base de données.
	 * Cette méthode vérifie si le FichierImage est éligible pour la modification dans le cas où il est partagé.
	 * Paramètres modifiables : chemin, infoPVue, pixelImg, partage.
	 * @param conn Connection SQL
	 * @param id
	 * @param newChemin
	 * @param newInfoPVue
	 * @param newPixelImg
	 * @param newPartage
	 * @throws SQLException
	 */
	public static void updateFichierImage(Connection conn, int id, String newChemin, String newInfoPVue, int newPixelImg, int newPartage) throws SQLException {
		FichierImage leFichierImage = selectAll(conn,id).get(0);
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
	private static void deleteFromTable(Connection conn, int id) throws SQLException {
		Impression_PhotoDAO.deleteAllFromFichierImage(conn, id);
		PhotoDAO.deletePhotosFromFichierImage(conn, id);
		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM FichierImage WHERE idFichier="+id);
	}
	

	/**
	 * Supprime un fichier image du point de vue Client : supression reelle ou mise en file d'attente pour suppression
	 * @param conn Connection SQL
	 * @param id id fichier
	 * @throws SQLException 
	 */
	public static void deleteFichierImage(Connection conn, int id) throws SQLException {
		FichierImage leFichierImage = selectAll(conn,id).get(0);
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
			deleteFromTable(conn, id);
		}
	}
	
	
	/**
	 * Supprime un fichier image du point de vue Gestionnaire : supression reelle ou mise en file d'attente pour suppression
	 * et annulation de toutes les commandes 'BROUILLON' ou 'EN_COURS' qui utilisent ce fichier.
	 * @param conn Connection SQL
	 * @param id id du fichier
	 * @throws SQLException 
	 */
	public static void deleteFichierImageModeGestion(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet lesCommandes = state.executeQuery("SELECT idComm FROM Commande NATURAL JOIN Article NATURAL JOIN Impression NATURAL JOIN Impression_Photo NATURAL JOIN Photo NATURAL JOIN FichierImage WHERE idFichier="+id);
		FichierImage leFichierImage = selectAll(conn,id).get(0);
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
		} else { deleteFromTable(conn, id);}
	}

	/**
	 * Vérifie si un FichierImage est partagé et utilisé par quelqu'un d'autre à partir de l'id fichier.
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	//TODO : a refaire : condition pas respectée !!! requete pas bonne, il faut verifier que ce soit d'autres utilisateurs qui utilisent.
	//TODO: verifier que c'est valable pour tout type de commande dans le sujet
	public static boolean isSharedAndUsedBySomeone(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT count(idUser) FROM FichierImage NATURAL JOIN Photo NATURAL JOIN Impression_Photo NATURAL JOIN Impression WHERE idFichier="+id);
		if(result.next()) {
			return (result.getInt(1) > 1);
		}
		return false;
	}


	/**
	 * Retourne les objets FichierImage construits à partir d'un résultat de requête.
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
					result.getInt("partager"),
					result.getDate("dateUtilisation"),
					result.getInt("fileAttModif"),
					result.getInt("fileAttSuppr")
					));			
		}
		return fichiersimage;
	}

	/**
	 * Version interactive de uploadFichierImage. Demande à l'utilisateur un chemin, les infos de prise de vue et la dimension de l'image.
	 * @throws SQLException 
	 */
	public static void uploadFichierImage(Connection conn, int idUser) throws SQLException {
		String chemin = LectureClavier.lireChaine("Entrez un chemin :");
		String infoPVue = LectureClavier.lireChaine("Entrez les infos de prise de vue :");
		int pixelImg = LectureClavier.lireEntier("Entrez la dimension de l'image :");
		insertFichierImage(conn, idUser, chemin, infoPVue, pixelImg, 0, Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))), 0, 0);
	}
	
	public static Boolean idExists(Connection c, int idFichier) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from FichierImage where idFichier='"+idFichier+"'");
		if (result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}
	
	public static Boolean belongToUser(Connection c, int idFichier, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from FichierImage where idFichier='"+idFichier+"' and idUser = '"+idUser+"'");
		if (result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}

	public static Boolean isShared(Connection c, int idFichier) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from FichierImage where idFichier = '"+idFichier+"' and partager = 1");
		if (result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}
	
	public static void supprimerUnFichierClient(Connection c) throws SQLException {
		new Affichage<Owners>().afficher(selectAllWithOwner(c));
		int idFichier = -2;
		while(!idExists(c,idFichier)){
			idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste ci-dessus ou -1 pour annuler).");
			if(idFichier==-1) {return;}
		}
		deleteFichierImage(c, idFichier);
		System.out.println("fichier supprime !");
	}
	
	public static void supprimerUnFichierClient(Connection c, Utilisateur u) throws SQLException {
		int idFichier = -2;
		while(!idExists(c,idFichier) || !belongToUser(c, idFichier, u.getIdUser())){
			idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste ci-dessus ou -1 pour annuler).");
			if(idFichier==-1) {return;}
		}
		deleteFichierImage(c, idFichier);
		System.out.println("fichier supprime !");
	}
	
	public static void modifierFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int idFichier = -1;
		while(!idExists(c,idFichier) || !belongToUser(c, idFichier, utilisateur.getIdUser())){
			idFichier = LectureClavier.lireEntier("Pour modifier un fichier, entrez son idFichier (dans la liste présentée ci-dessus).");
		}
		FichierImage f = selectAll(c, idFichier).get(0);
		boolean back = false;
		while (!back){
			System.out.println(" \n"+f.toString());
			if(LectureClavier.lireOuiNon("modifier les infos de prise de vue ?")){
				f.setInfoPVue(LectureClavier.lireChaine("Nouvelles infos ?"));
			}
			if(LectureClavier.lireOuiNon("modifier le chemin ?")){
				f.setChemin(LectureClavier.lireChaine("Nouveau chemin ?"));			
			}
			if(LectureClavier.lireOuiNon("modifier le partage ?")){
				f.setPartage(1-f.isPartage());
			}
			if(LectureClavier.lireOuiNon("Sauvegarder les changements ?")){
				updateFichierImage(c, idFichier, f.getChemin(), f.getInfoPVue(), f.getPixelImg(), f.isPartage());
			} else {
				back = true;
			}
		}
		
	}
	
	public static void ajouterFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean continuer= true;
		while(continuer==true){
			
			String 		chemin 		= LectureClavier.lireChaine("Ou se trouve votre fichier? ");
			String 		infoPVue 	= LectureClavier.lireChaine("Commentaire sur le fichier: ");
			int 		pixelImg 	= LectureClavier.lireEntier("Quel est la taille en pixels : ");	
			boolean 	partage 	= LectureClavier.lireOuiNon("Souhaitez vous que n'importe qui puisse utiliser cette image?");
			String 		dateUse 	= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			FichierImageDAO.insertFichierImage(c, utilisateur.getIdUser(), chemin, infoPVue, pixelImg, partage?1:0, Date.valueOf(dateUse) , 0, 0);
			continuer= LectureClavier.lireOuiNon("Voulez vous ajouter un nouveau fichier? ");
		}
	}
	
}
