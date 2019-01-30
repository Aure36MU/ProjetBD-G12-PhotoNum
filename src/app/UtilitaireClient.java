package src.app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import src.commande.Article;
import src.commande.ArticleDAO;
import src.commande.Commande;
import src.commande.CommandeDAO;
import src.compte.Utilisateur;
import src.impression.Format;
import src.impression.Impression;
import src.impression.ImpressionDAO;
import src.impression.Qualite;
import src.impression.Type;
import src.impression.agenda.ModeleAgenda;
import src.impression.agenda.Ornement;
import src.impression.cadre.ModeleCadre;
import src.impression.calendrier.ModeleCalendrier;
import src.photo.FichierImage;
import src.photo.FichierImageDAO;
import src.photo.Impression_PhotoDAO;
import src.photo.Photo;
import src.photo.PhotoDAO;

public class UtilitaireClient {

	static void menuClient(Connection c, Utilisateur utilisateur) throws SQLException {
		while(utilisateur != null){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Gerer mes impressions.");
			System.out.println("3 : Gerer mes fichiers.");
			System.out.println("4 : Gerer mon panier.");
			int choixAction = LectureClavier.lireEntier("5 : Voir mon historique de commandes.");

			switch(choixAction){ 
				case 1:  	utilisateur = null;		System.out.println("Vous avez ete deconnecte");			break;
				case 2:	gererImpression(c,utilisateur);				break;
				case 3:	gererFichierImages(c,utilisateur);		break;
				case 4:	gererPanier(c, utilisateur);					break;
				case 5:
					ArrayList<Commande> commandes = CommandeDAO.selectAllFromUser(c, utilisateur.getIdUser());
					new Affichage<Commande>().afficher(commandes);		break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	public static void gererPanier(Connection c, Utilisateur utilisateur) throws SQLException {
		
		boolean back = false;
		while(!back){
			ArrayList<Article> panier = ArticleDAO.selectAllFromPanier(c, utilisateur.getIdUser());
				if(panier.isEmpty()){ System.out.println("Vous n'avez aucun article dans votre panier"); return; }
				else{	new Affichage<Article>().afficher(panier);
							
							System.out.println("*****************************************************************************");
							System.out.println("Que voulez vous faire ?");
							System.out.println("1 : Revenir au menu precedent.");
							System.out.println("2 : Modifier la quantit� d'un article.");
							System.out.println("3 : Retirer un article.");
							int choixAction = LectureClavier.lireEntier("4 : Valider la commande.");
							
							switch(choixAction){ 
										case 1: 		back = true; 											break;
										case 2:  		ArticleDAO.ModifierQuantite(c);			break;
										case 3:  		ArticleDAO.SupprimerUnArticle(c);		break;
										case 4:	
											boolean payer=LectureClavier.lireOuiNon("Voulez vous valider et payer votre commande?");
											if(payer) {System.out.println("Vous avez paye :)"); }
											break;
										default : System.out.println("Veuillez faire un choix. ");
							}
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
			System.out.println("3 : Consulter la liste de mes fichiers.");
			System.out.println("4 : Consulter la liste de mes photos.");
			System.out.println("5 : Consulter la liste des fichiers partages.");
			int choixAction = LectureClavier.lireEntier("6 : Ajouter un nouveau fichier.");

			switch(choixAction){
			case 1:  	utilisateur = null;	back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:   	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	new Affichage<FichierImage>().afficher(FichierImageDAO.selectAllFromUser(c,utilisateur.getIdUser()));
							gererFichiers(c,utilisateur);
							break;
			case 4:	new Affichage<Photo>().afficher(PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser()));
							gererUnePhoto(c,utilisateur);
							break;
			case 5:	new Affichage<FichierImage>().afficher(FichierImageDAO.selectAll(c, "partager=1"));
							gererFichierPartages(c, utilisateur);
							break;
			case 6:	gererAjoutFichier(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
		
	}
	private static void gererAjoutFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean continuer= true;
		while(continuer==true){
			
			String 		chemin 		= LectureClavier.lireChaine("Ou se trouve votre fichier? ");
			String 		infoPVue 	= LectureClavier.lireChaine("Commentaire sur le fichier: ");
			int 			pixelImg 		= LectureClavier.lireEntier("Quel est la taille en pixels : ");	
			boolean partage 		= LectureClavier.lireOuiNon("Souhaitez vous que n'importe qui puisse utiliser cette image?");
			String 		dateUse 		= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			FichierImageDAO.insertFichierImage(c, utilisateur.getIdUser(), chemin, infoPVue, pixelImg, partage?1:0, Date.valueOf(dateUse) , 0, 0);
			continuer= LectureClavier.lireOuiNon("Voulez vous ajouter un nouveau fichier? ");
		}
	}

	private static void gererFichierPartages(Connection c, Utilisateur utilisateur) throws SQLException {
		System.out.println("*****************************");
		System.out.println("  voici vos fichiers partages : ");
		System.out.println("****************************** ");
		while(LectureClavier.lireOuiNon("Voulez vous modifier le partage d'un fichier ?")){
			c.setAutoCommit(false);
			int idFichier = -1;
			while(!FichierImageDAO.idExists(c,idFichier)){
				idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste pr�sent�e ci-dessus).");
			}
			Statement state = c.createStatement();
			state.executeUpdate("UPDATE FichierImage SET (partager = (1-partager)) WHERE idFichier = '" +idFichier+"'");
			
			c.commit();
			c.setAutoCommit(true);
		}
	}
	
	private static void gererFichiers(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Modifier un fichier");
			System.out.println("4 : Supprimer un fichier");
			int choixAction = LectureClavier.lireEntier("5 : Creer une photo avec ce fichier (retouche).");
			
			switch(choixAction){ 
				case 1:  	utilisateur = null;	back = true;
								System.out.println("Vous avez ete deconnecte");
								break;
				case 2:  	back = true;
								System.out.println("retour au menu precedent");
								break;
				case 3:	gereModifFichier(c,utilisateur);			break;
				case 4:	gereSupprFichier(c,utilisateur);			break;
				case 5:	gereRetoucheFichier(c,utilisateur);		break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gereModifFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int idFichier = -1;
		while(!FichierImageDAO.idExists(c,idFichier) || !FichierImageDAO.belongToUser(c, idFichier, utilisateur.getIdUser())){
			idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste pr�sent�e ci-dessus).");
		}
		FichierImage f = FichierImageDAO.selectAll(c, "idFichier=" + idFichier).get(0);
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
				FichierImageDAO.updateFichierImage(c, idFichier, f.getChemin(), f.getInfoPVue(), f.getPixelImg(), f.isPartage());
			} else {
				back = true;
			}
		}
		
	}
	
	private static void gereSupprFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int fichier = LectureClavier.lireEntier("Quel fichier voulez vous supprimer ? (Entre le numero du fichier)");
		ArrayList<FichierImage> tab = FichierImageDAO.selectAll(c, "idFichier=" + fichier);
		if(utilisateur.getIdUser()==tab.get(0).getIdUser()) {
			System.out.println("Le fichier que vous souhaitez supprimer est: "+ tab.get(0).getIdFichier() + " avec comme chemin :"+ tab.get(0).getChemin());
			boolean choix = LectureClavier.lireOuiNon("Voulez vous supprimer ce fichier?");
			
			if(choix) {			FichierImageDAO.deleteFichierImage(c, tab.get(0).getIdFichier());
										System.out.println("Fichier supprimer avec succes!");
			}
		}else {
			System.out.println("Vous n'avez pas le droit de supprimer ce fichier, il n'est pas a vous");
		}
	}
	
	private static void gereRetoucheFichier(Connection c, Utilisateur utilisateur) throws SQLException {
		int fichier = LectureClavier.lireEntier("Quel fichier voulez vous retoucher? (Entrez le numero du fichier)");
		ArrayList<FichierImage> tab = FichierImageDAO.selectAll(c, "idFichier=" + fichier);
		if((utilisateur.getIdUser()==tab.get(0).getIdUser()) || (tab.get(0).isPartage()==1)) {
			System.out.println("Le fichier que vous souhaitez retoucher est: "+ tab.get(0).getIdFichier() + " avec comme chemin :"+ tab.get(0).getChemin());
			boolean choix = LectureClavier.lireOuiNon("Voulez vous retoucher ce fichier?");
			if(choix) {
				String retouche = LectureClavier.lireChaine("Quelles retouches voulez vous faire?");
				PhotoDAO.insertPhoto(c, tab.get(0).getIdFichier(), retouche);
				System.out.println("Fichier retouche avec succes!");
			}
		}else {
			System.out.println("Vous n'avez pas le droit de retoucher ce fichier, il n'est pas a vous et il n'est pas en libre service");
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
				case 1:  	utilisateur = null; back = true;
								System.out.println("Vous avez ete deconnecte");
								break;
				case 2:  	back = true;
								System.out.println("retour au menu precedent");
								break;
				case 3:	gereModifPhoto(c,utilisateur);			break;
				case 4:	gereSupprPhoto(c,utilisateur);			break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	private static void gereModifPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		int photo = LectureClavier.lireEntier("Quelle photo voulez vous modifier? (Entrez le numero de la photo)");
		ArrayList<Photo> tab = PhotoDAO.selectAll(c, "idPh=" + photo);
		System.out.println("La photo que vous souhaitez modifier est: "+ tab.get(0).getIdPh() + " du fichier :"+ tab.get(0).getIdFichier() + " avec la retouche :" + tab.get(0).getRetouche());
		
		boolean choix = LectureClavier.lireOuiNon("Voulez vous modifier cette photo?");
		if(choix) {	String retouche = LectureClavier.lireChaine("Quelles retouches voulez vous faire?");
							PhotoDAO.updatePhoto(c, tab.get(0).getIdPh(), tab.get(0).getIdFichier(), retouche);
		}
		else{
			//TODO : tu fais quoi si tu veux pas modifier ? tu reviens comment en arriere ?
		}
	}
	
	private static void gereSupprPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		int photo = LectureClavier.lireEntier("Quel photo voulez vous supprimer? (Entrez le numero de la photo)");
		ArrayList<Photo> tab = PhotoDAO.selectAll(c, "idPh=" + photo);
		
		System.out.println("La photo que vous souhaitez supprimer est: "+ tab.get(0).getIdPh() + " du fichier :"+ tab.get(0).getIdFichier());
		boolean choix = LectureClavier.lireOuiNon("Voulez vous supprimer cette photo?");
		if(choix) {		PhotoDAO.deletePhoto(c, tab.get(0).getIdPh());
								System.out.println("Photo supprimee avec succes!");
		}else {
			//TODO : tu fais quoi sinon ? tu reviens comment en arriere ?
		}
	}

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
			case 1:  	utilisateur = null;
							back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:  	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	ArrayList<Impression> tab =ImpressionDAO.selectAllFromUserImpressionNotArticle(c,utilisateur.getIdUser());
							new Affichage<Impression>().afficher(tab);
							gererModifImpression(c,utilisateur);
							break;
			case 4:	gereInsertImp(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}

	private static void gererModifImpression(Connection c, Utilisateur utilisateur) throws SQLException {

		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Continuer une impression.");
			int choixAction = LectureClavier.lireEntier("4 : Supprimer une impression.");

			switch(choixAction){ 
			case 1:  	utilisateur = null;
							back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:  	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	int imp=LectureClavier.lireEntier("Quel impression voulez vous continuer?");
							gererInsertPhoto(c,utilisateur,imp);
							break;
			case 4:	ImpressionDAO.gererDeleteImp(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}
	
	
	private static void gereInsertImp(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean continuer=true;
		boolean correct = false;
		while(continuer) {
			
			String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
			String type= Type.definir();
			String format= Format.definir();
			String qualite= Qualite.definir();
			
			System.out.println("R�capitulatif: ");
			System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
			correct=LectureClavier.lireOuiNon("Cela vous conviens? (oui/non)");
			
			while(!correct) {
				if (LectureClavier.lireOuiNon("Abandonner ? (oui/non)")) {
					return;
				}
				nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
				type= Type.definir();
				format= Format.definir();
				qualite= Qualite.definir();
				
				System.out.println("R�capitulatif: ");
				System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
				correct=LectureClavier.lireOuiNon("Cela vous conviens? (oui/non)");
			}

			switch(type){ 
				case "AGENDA":
								String modeleAgenda= ModeleAgenda.definir();
								String ornement = Ornement.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,ornement,modeleAgenda );
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "CALENDRIER":
								String modeleCalendrier = ModeleCalendrier.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCalendrier);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "ALBUM":
								String titreCouv=LectureClavier.lireChaine("Quel nom souhaitez vous sur la couverture?: ");
								ArrayList<Photo> tab =PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
								int i=0;
								boolean trouve=false;
								new Affichage<Photo>().afficher(tab);
								while(trouve==false) {
									int photo= LectureClavier.lireEntier("Quel photo voulez vous mettre en couverture? Entrez le numero de la photo");
									while(i<tab.size() && tab.get(i).getIdPh()!=photo) {i++;}
									if(i<tab.size()) {
										trouve=true;
										ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,photo,titreCouv );
									}else {	System.out.println("Vous n'avez pas selectionne une photo que vous possedez"); }
								}	
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;	
				case "CADRE":
								String modeleCadre = ModeleCadre.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCadre);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "TIRAGE":
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
			}
			continuer = LectureClavier.lireOuiNon("Continuer de cr�er des impressions? (oui/non)");
		}
	}
		
	private static void gererInsertPhoto(Connection c, Utilisateur utilisateur, int idImp) throws SQLException {
		ArrayList <Impression> tabImp = ImpressionDAO.selectAllFromUser(c, utilisateur.getIdUser());
		int i=0;	while(i<tabImp.size() && tabImp.get(i).getIdImp()!=idImp) {i++;}
		
		if(i<tabImp.size()) {
			int choix = LectureClavier.lireEntier("Voulez vous ajouter ou retirer une photo d'une page? (0: Ajouter/ 1:Retirer / 2: Finir)");
			boolean boucle=true;
			while(boucle) {
					switch(choix) {
						case 0:	ArrayList<Photo> tab =PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
										new Affichage<Photo>().afficher(tab);
										int ph= LectureClavier.lireEntier("Quel photo voulez vous ajouter?");
										int j=0;		while(j<tab.size() && tab.get(j).getIdPh()!=ph) {	j++;	}
										if(j<tabImp.size()) {
														String txt		= LectureClavier.lireChaine("Quel texte voulez vous ajouter a la photo?");
														int nb			= LectureClavier.lireEntier("Combien de fois voulez vous cette photo?");
														int page		= LectureClavier.lireEntier("Sur quel page?");
														Impression_PhotoDAO.insert(c, ph, idImp, page, txt, nb);
														choix 			= LectureClavier.lireEntier("Voulez vous faire autre chose? (0: Ajouter/ 1:Retirer / 2: Finir)");
										}else {		System.out.println("Vous n'avez pas selectionne une photo que vous possedez");	}
										break;
										
						case 1:	int num = LectureClavier.lireEntier("Quel page voulez vous retirer?");
										Impression_PhotoDAO.deletePage(c, idImp, num);
										choix = LectureClavier.lireEntier("Voulez vous faire autre chose? (0: Ajouter/ 1:Retirer / 2: Finir)");
										break;
										
						case 2:	boucle=false;	 break;
					}
					boolean comm=LectureClavier.lireOuiNon("Voulez vous ajouter cette impression dans votre panier?");
					if(comm) {		int qte= LectureClavier.lireEntier("combien d'exemplaires souhaitez vous?");
											CommandeDAO.ajouterAuPanier(c, utilisateur.getIdUser(), idImp, qte);
					}
			}
		} else {	System.out.println("Vous n'avez pas selectionne une impression que vous possedez");}
	}
}
