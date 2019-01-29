package src.app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.commande.Article;
import src.commande.ArticleDAO;
import src.commande.CommandeDAO;
import src.compte.Utilisateur;
import src.impression.Format;
import src.impression.ImpressionDAO;
import src.impression.Qualite;
import src.impression.Type;
import src.impression.agenda.ModeleAgenda;
import src.impression.agenda.Ornement;
import src.impression.cadre.ModeleCadre;
import src.impression.calendrier.ModeleCalendrier;
import src.photo.FichierImage;
import src.photo.FichierImageDAO;
import src.photo.Photo;
import src.photo.PhotoDAO;

public class UtilitaireClient {

	static void menuClient(Connection c, Utilisateur utilisateur) throws SQLException {
		while(utilisateur != null){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Gerer mes impressions.");
			System.out.println("3 : Gerer mes fichiers images.");
			System.out.println("4 : Voir le contenu de mon panier.");
			int choixAction = LectureClavier.lireEntier("5 : voir mon historique de commandes.");

			switch(choixAction){ 
				case 1:  
					utilisateur = null;
					System.out.println("Vous avez ete deconnecte");
					break;
				case 2:
					gererImpression(c,utilisateur);
					break;
				case 3:
					gererFichierImages(c,utilisateur);
					break;
				case 4:
					ArrayList<Article> panier = new ArrayList<Article>();
					panier = ArticleDAO.selectAllFromPanier(c, utilisateur.getIdUser());
					if(panier.isEmpty()){ System.out.println("Vous n'avez aucun article dans votre panier"); }
					else{
						new Affichage<Article>().afficher(panier);
					}
					
					break;
				case 5:
					CommandeDAO.selectAllFromUser(c, utilisateur.getIdUser());		
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererFichierImages(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Consulter la liste de mes fichiers de base.");
			System.out.println("4 : Consulter la liste de mes photos.");
			System.out.println("5 : Consulter la liste des fichiers partages.");
			int choixAction = LectureClavier.lireEntier("6 : Ajouter un nouveau fichier.");

			switch(choixAction){ 
			case 1:  
				utilisateur = null;
				System.out.println("Vous avez ete deconnecte");
				break;
			case 2:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 3:
				FichierImageDAO.selectAllFromUser(c,utilisateur.getIdUser());
				gererUnFichier(c,utilisateur);
				break;
			case 4:
				PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
				gererUnePhoto(c,utilisateur);
				break;
			case 5:
				FichierImageDAO.selectAll(c, "partage=true");
				gererFichierPartager(c, utilisateur);
				//PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
				break;
			case 6:
				gererAjoutFichier(c,utilisateur);
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
		
	}
	private static void gererAjoutFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean continuer= true;
		while(continuer==true);{
			String chemin= LectureClavier.lireChaine("Ou ce trouve votre fichier? ");
			String infoPVue= LectureClavier.lireChaine("Commentaire sur le fichier: ");
			int pixelImg= LectureClavier.lireEntier("Quel est la taille en pixel : ");	
			boolean partage= LectureClavier.lireOuiNon("Souhaitez vous que n'importe qui puisse utiliser cette image?");
			String dateUse = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/mm/yyyy"));
			FichierImageDAO.insertFichierImage(c, utilisateur.getIdUser(), chemin, infoPVue, pixelImg, partage, Date.valueOf(dateUse) , false, false);
			continuer= LectureClavier.lireOuiNon("Voulez vous ajouter un nouveau fichier? ");
		}
	}

	private static void gererFichierPartager(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			int choixAction = LectureClavier.lireEntier("3 : Retoucher un fichier.");
			switch(choixAction){ 
			case 1:  
				utilisateur = null;
				System.out.println("Vous avez ete deconnecte");
				break;
			case 2:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 3:
				gereRetoucheFichier(c,utilisateur);
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererUnFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Modifier un fichier");
			System.out.println("4 : Supprimer un fichier");
			int choixAction = LectureClavier.lireEntier("5 : Retoucher un fichier.");
			
			switch(choixAction){ 
			case 1:  
				utilisateur = null;
				System.out.println("Vous avez ete deconnecte");
				break;
			case 2:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 3:
				gereModifFichier(c,utilisateur);
				break;
			case 4:
				gereSupprFichier(c,utilisateur);
				break;
			case 5:
				gereRetoucheFichier(c,utilisateur);
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gereModifFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int fichier = LectureClavier.lireEntier("Quel fichier voulez vous modifier? (Entre le numero du fichier)");
		ArrayList<FichierImage> tab = FichierImageDAO.selectAll(c, "idFichier=" + fichier);
		if(utilisateur.getIdUser()==tab.get(0).getIdUser()) {
			boolean part=tab.get(0).isPartage();
			System.out.println("Votre photo est partager: "+ part);
			boolean choix1 = LectureClavier.lireOuiNon("Voulez vous modifier le partage?");
			boolean choix2 = LectureClavier.lireOuiNon("Voulez vous modifier le commentaire?");
			if(choix1) {
				part= !tab.get(0).isPartage();
			}
			if(choix2) {
				String comm= LectureClavier.lireChaine("Quel est le nouveau commentaire?");
				FichierImageDAO.updateFichierImage(c, fichier, tab.get(0).getChemin(), comm, tab.get(0).getPixelImg(), part);
				System.out.println("Fichier modifier avec succes!");
			}else {
				FichierImageDAO.updateFichierImage(c, fichier, tab.get(0).getChemin(), tab.get(0).getInfoPVue(), tab.get(0).getPixelImg(), part);
				System.out.println("Fichier modifier avec succes!");
			}
		}else {
			System.out.println("Vous n'avez pas le droit de modifier ce fichier, il n'est pas e vous");
		}
		
	}
	
	private static void gereSupprFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int fichier = LectureClavier.lireEntier("Quel fichier voulez vous supprimer? (Entre le numero du fichier)");
		ArrayList<FichierImage> tab = FichierImageDAO.selectAll(c, "idFichier=" + fichier);
		if(utilisateur.getIdUser()==tab.get(0).getIdUser()) {
			System.out.println("Le fichier que vous souhaitez supprimer est: "+ tab.get(0).getIdFichier() + " avec comme chemin :"+ tab.get(0).getChemin());
			boolean choix = LectureClavier.lireOuiNon("Voulez vous supprimer ce fichier?");
			if(choix) {
				FichierImageDAO.deleteFichierImage(c, tab.get(0).getIdFichier());
				System.out.println("Fichier supprimer avec succes!");
			}
		}else {
			System.out.println("Vous n'avez pas le droit de supprimer ce fichier, il n'est pas e vous");
		}
	}
	
	private static void gereRetoucheFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int fichier = LectureClavier.lireEntier("Quel fichier voulez vous supprimer? (Entre le numero du fichier)");
		ArrayList<FichierImage> tab = FichierImageDAO.selectAll(c, "idFichier=" + fichier);
		if((utilisateur.getIdUser()==tab.get(0).getIdUser()) || (tab.get(0).isPartage()==true)) {
			System.out.println("Le fichier que vous souhaitez supprimer est: "+ tab.get(0).getIdFichier() + " avec comme chemin :"+ tab.get(0).getChemin());
			boolean choix = LectureClavier.lireOuiNon("Voulez vous retoucher ce fichier?");
			if(choix) {
				String retouche = LectureClavier.lireChaine("Quelles retouche voulez vous faire?");
				PhotoDAO.insertPhoto(c, tab.get(0).getIdFichier(), retouche);
				System.out.println("Fichier retoucher avec succes!");
			}
		}else {
			System.out.println("Vous n'avez pas le droit de retoucher ce fichier, il n'est pas e vous et il n'est pas en libre service");
		}
	}
	
	private static void gererUnePhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Modifier une photo");
			int choixAction = LectureClavier.lireEntier("4 : Supprimer une photo");
			
			switch(choixAction){ 
			case 1:  
				utilisateur = null;
				System.out.println("Vous avez ete deconnecte");
				break;
			case 2:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 3:
				gereModifPhoto(c,utilisateur);
				break;
			case 4:
				gereSupprPhoto(c,utilisateur);
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	private static void gereModifPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		int photo = LectureClavier.lireEntier("Quelle photo voulez vous modifier? (Entre le numero de la photo)");
		ArrayList<Photo> tab = PhotoDAO.selectAll(c, "idPh=" + photo);
		System.out.println("La photo que vous souhaitez modifier est: "+ tab.get(0).getIdPh() + " du fichier :"+ tab.get(0).getIdFichier() + " avec la retouche :" + tab.get(0).getRetouche());
		boolean choix = LectureClavier.lireOuiNon("Voulez vous modifier cette photo?");
		if(choix) {
			String retouche = LectureClavier.lireChaine("Quelles retouche voulez vous faire?");
			PhotoDAO.updatePhoto(c, tab.get(0).getIdPh(), tab.get(0).getIdFichier(), retouche);
		}
	}
	
	private static void gereSupprPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		int photo = LectureClavier.lireEntier("Quel photo voulez vous supprimer? (Entre le numero de la photo)");
		ArrayList<Photo> tab = PhotoDAO.selectAll(c, "idPh=" + photo);
		System.out.println("La photo que vous souhaitez supprimer est: "+ tab.get(0).getIdPh() + " du fichier :"+ tab.get(0).getIdFichier());
		boolean choix = LectureClavier.lireOuiNon("Voulez vous supprimer cette photo?");
		if(choix) {
			PhotoDAO.deletePhoto(c, tab.get(0).getIdPh());
			System.out.println("Photo supprimer avec succes!");
		}
	}
	/*-----------------------------------------------------------------------------------------------------------------------------
	 * -----------------------------------------Partie de Pauline------------------------------------------------------------------
	 * --------------------------------------------Impression-------------------------------------------------------------------
	 * -----------------------------------------------------------------------------------------------------------------------------
	 */
	//TODO: 
	private static void gererImpression(Connection c, Utilisateur utilisateur) throws SQLException {

		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Consulter la liste de mes impressions.");
			int choixAction = LectureClavier.lireEntier("4 : Creer une nouvelle impression.");

			switch(choixAction){ 
			case 1:  
				utilisateur = null;
				System.out.println("Vous avez ete deconnecte");
				break;
			case 2:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 3:
				ImpressionDAO.selectAllFromUserImpressionWait(c,utilisateur.getIdUser());
				break;
			case 4:
				gereInsertImp(c,utilisateur);
				String leChoix= LectureClavier.lireChaine("Souhaitez vous commencer a remplir votre nouvelle impression? (oui/non)");
				if(leChoix=="oui") {
					
				}
				break;
			case 5:
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}

	
	private static void gereInsertImp(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean votreChoix=false;
		while(votreChoix==false) {
			String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
			String type= Type.definir();
			String format= Format.definir();
			String qualite= Qualite.definir();
			
			System.out.println("R�capitulatif: ");
			System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
			String leChoix=LectureClavier.lireChaine("Cela vous conviens? (oui/non)");
			if(leChoix.equals("oui")) {
				//ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite );
				votreChoix=true;
				System.out.println("Vous venez de cr�e votre nouvelle impression");
			}
			
			switch(type){ 
				case "AGENDA":
					String modeleAgenda= ModeleAgenda.definir();
					String ornement = Ornement.definir();
					ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,ornement,modeleAgenda );
					System.out.println("Vous venez de cr�e votre nouvelle impression");
					break;
				case "CALENDRIER":
					String modeleCalendrier = ModeleCalendrier.definir();
					ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCalendrier);
					System.out.println("Vous venez de cr�e votre nouvelle impression");
					break;
				case "ALBUM":
					String titreCouv=LectureClavier.lireChaine("Quel nom souhaitez vous sur la couverture?: ");
					ArrayList<Photo> tab =PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
					int i=0;
					boolean trouve=false;
					new Affichage<Photo>().afficher(tab);
					while(trouve==false) {
						int photo= LectureClavier.lireEntier("Quel photo voulez vous mettre en couverture? Entr�e le numero de la photo");
						while(i<tab.size() && tab.get(i).getIdPh()!=photo) {
							i++;
						}
						if(i<tab.size()) {
							trouve=true;
							ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,photo,titreCouv );
						}else {
							System.out.println("Vous n'avez pas selectionner une photo que vous possedez");
						}
					}	
					System.out.println("Vous venez de cr�e votre nouvelle impression");
					break;	
				case "CADRE":
					String modeleCadre = ModeleCadre.definir();
					ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCadre);
					System.out.println("Vous venez de cr�e votre nouvelle impression");
					break;
				case "TIRAGE":
					ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite);
					System.out.println("Vous venez de cr�e votre nouvelle impression");
					break;
			}
			/*System.out.println("R�capitulatif: ");
			System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
			String leChoix=LectureClavier.lireChaine("Cela vous conviens? (oui/non)");
			if(leChoix=="oui") {
				ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite );
				votreChoix=true;
				System.out.println("Vous venez de cr�e votre nouvelle impression");
			}*/
		}
	}
		
	private static void gererInsertPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		
	}
}
