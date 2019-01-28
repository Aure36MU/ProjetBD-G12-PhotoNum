package src.app;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import src.commande.Article;
import src.commande.ArticleDAO;
import src.commande.CatalogueDAO;
import src.commande.CommandeDAO;
import src.commande.Stat;
import src.compte.StatutUtilisateur;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;
import src.impression.Format;
import src.impression.ImpressionDAO;
import src.impression.Modele;
import src.impression.Qualite;
import src.impression.Type;
import src.impression.agenda.ModeleAgenda;
import src.impression.agenda.Ornement;
import src.impression.calendrier.ModeleCalendrier;
import src.photo.FichierImage;
import src.photo.FichierImageDAO;
import src.photo.Photo;
import src.photo.PhotoDAO;



public class Application {
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	static String USER;
	static String PASSWD;
	static Connection c; 

	/* TODO se référer à StatutUtilisateur.definir() qui fait essentiellement la même chose en interactif
	public static StatutUtilisateur choixStatut(){
		System.out.println("Vous pouvez vous inscrire en temps que client ou en tant que gestionnaire.");
		return StatutUtilisateur.valueOf(LectureClavier.lireChaine("CLIENT ou GESTIONNAIRE ?"));
	}
	*/

	private static Utilisateur inscription(Connection c) throws SQLException{
		System.out.println("***********************");
		System.out.println("      INSCRIPTION ");
		System.out.println("***********************");
		System.out.println("Bienvenue sur le site PhotoNum ! Nous allons vous demander quelques infos pour la creation de votre compte");

		String statut= StatutUtilisateur.definir();			
		String mail= LectureClavier.lireChaine("Votre adresse mail : ");	
		String mdp= LectureClavier.lireChaine("Votre mot de passe : ");	
		String nom= LectureClavier.lireChaine("Votre nom : ");
		String prenom= LectureClavier.lireChaine("Votre prenom : ");
		System.out.println("   ");
		System.out.println("Bienvenue e vous " + nom + " "+ prenom + " ! ");
		return UtilisateurDAO.createUtilisateur(c,  nom,  prenom,  mdp,  mail,  statut);
	}	

	private static Utilisateur connexion(Connection c) throws SQLException{
		System.out.println("**********************");
		System.out.println("      CONNEXION ");
		System.out.println("**********************");

		String mail = LectureClavier.lireChaine("Pour vous connecter, saisissez votre mail : ");
		Utilisateur utilisateur = UtilisateurDAO.selectWithCondition(c, "email = '"+mail+"'").get(0);
		String mdp = null;
		while(!mdp.equals(utilisateur.getMdp())){
			mdp = LectureClavier.lireChaine("Veuillez entrer le mot de passe correspondant ou entrez \"return to menu\" pour retourner au Menu Principal");
			if(mdp.equals("return to menu")){
				return null;
			}
		}
		return utilisateur;
	}
	
	
	//revoir l'organisation de ce menu et 
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
		Boolean continuer= true;
		while(continuer==true);{
			String chemin= LectureClavier.lireChaine("Ou ce trouve votre fichier? ");
			String infoPVue= LectureClavier.lireChaine("Commentaire sur le fichier: ");
			int pixelImg= LectureClavier.lireEntier("Quel est la taille en pixel : ");	
			Boolean partage= LectureClavier.lireOuiNon("Souhaitez vous que n'importe qui puisse utiliser cette image?");
			String dateUse = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
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
		Boolean votreChoix=false;
		while(votreChoix==false) {
			String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
			String type= Type.definir();
			String format= Format.definir();
			String qualite= Qualite.definir();
			switch(type){ 
				case "AGENDA":
					String modeleAgenda= ModeleAgenda.definir();
					String ornement = Ornement.definir();
					break;
				case "CALENDRIER":
					String modeleCalendrier = ModeleCalendrier.definir();
					break;
				case "ALBUM":
					
					break;	
					
				case "CADRE":
	
					break;
			}
			System.out.println("Récapitulatif: ");
			System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
			String leChoix=LectureClavier.lireChaine("Cela vous conviens? (oui/non)");
			if(leChoix=="oui") {
				ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite );
				votreChoix=true;
				System.out.println("Vous venez de crée votre nouvelle impression");
			}
		}
	}

/*-----------------------------------------------------------------------------------------------------------------------------
 * -----------------------------------------Partie de Pauline------------------------------------------------------------------
 * --------------------------------------------Impression-------------------------------------------------------------------
 * -----------------------------------------------------------------------------------------------------------------------------
 */
	
	
	public static void main(String[] args) throws SQLException {
		try{
			USER=LectureClavier.lireChaine("Saississez l'identifiant pour la connexion de la base : ");
			PASSWD=LectureClavier.lireChaine("Quel est le mot de passe ? ");

			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  	    
			System.out.print("Connecting to the database... "); 
			c = DriverManager.getConnection(CONN_URL,USER,PASSWD);
			System.out.println("connected");
			System.out.println("**********************************************************************");
			System.out.println("    ");

			Utilisateur utilisateur = null;
			while(utilisateur == null){
				System.out.println("Souhaitez-vous, vous connectez ou vous inscrire sur PhotoNum ? ");
				int choix = LectureClavier.lireEntier("tapez 1 pour vous connecter, ou tapez 2 pour vous inscrire");

				switch(choix){ 
				case 1:  
					utilisateur = connexion(c);
					System.out.println("Vous etes maintenant connecte.");
					break;
				case 2:
					utilisateur = inscription(c);
					System.out.println("Vous avez ete inscrit.");
					break;
				default : System.out.println("Veuillez faire un choix. ");
				}
				if(utilisateur.getStatut() == StatutUtilisateur.valueOf("STATUT")){
					menuClient(c, utilisateur);
				}else {
					menuGestionnaire(c, utilisateur);
				}
			} 
			c.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void menuClient(Connection c, Utilisateur utilisateur) throws SQLException {
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
						new Utilitaire<Article>().afficher(panier);
					}
					
					break;
				case 5:
					CommandeDAO.selectAllFromUser(c, utilisateur.getIdUser());		
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}

	private static void menuGestionnaire(Connection c, Utilisateur utilisateur) throws SQLException {
		while(utilisateur != null){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Enregistrer une livraison");
			System.out.println("3 : Modifier le prix d'un article du catalogue.");
			System.out.println("4 : Consulter la liste des clients.");
			System.out.println("5 : Gerer les commandes clients.");
			System.out.println("7 : Voir la liste des fichiers et le nom de leur propriétaire.");
			int choixAction = LectureClavier.lireEntier("8 : Voir les statistiques de vente ");

			switch(choixAction){ 
				case 1:  
					utilisateur = null;
					System.out.println("Vous avez ete deconnecte");
					break;
				case 2:
					String type = Type.definir();
					String format = Format.definir();
					String modele = Modele.definir();
					int qteLivraison = LectureClavier.lireEntier("Combien en avez vous reçus ?");
					CatalogueDAO.simulerLivraison( c,  qteLivraison,  type,  format,  modele);
					break;
				case 3:
					type = Type.definir();
					format = Format.definir();
					modele = Modele.definir();
					int newPrix = LectureClavier.lireEntier("Nouveau prix ?");
					CatalogueDAO.updateCataloguePrix( c,  newPrix,  type,  format,  modele);
					break;
				case 4:
					new Utilitaire<Utilisateur>().afficher(UtilisateurDAO.selectAllUserFromStatut(c, StatutUtilisateur.CLIENT));
					//fonction gestion clients
					break;
				case 5:
					gererCommandeClients(c,utilisateur);
					break;
				case 6:
					
					break;
				case 7:
					
					break;
				case 8:
					new Utilitaire<Stat>().afficher(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererCommandeClients(Connection c, Utilisateur utilisateur) {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Consulter la liste des commandes clients en cours.");
			int choixAction = LectureClavier.lireEntier("4 : Creer une nouvelle impression.\"");

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
				
				break;
			case 4:
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	/*
	 * Consulter tous utilisateurs
Supprimer ⇔ désactiver un client
Consulter tous fichiers images et leur createur
Suppression fichiers images 
Consulter commandes (filtrer par article → filtrage dans ArticleDAO)
1*/



}
