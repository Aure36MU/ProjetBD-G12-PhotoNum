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
import src.photo.FichierImageDAO;
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
			System.out.println("1 : Retourner au menu precedent.");
			System.out.println("2 : Consulter la liste de mes fichiers de base.");
			System.out.println("3 : Consulter la liste de mes photos.");
			System.out.println("4 : Ajouter un nouveau fichier.");
			int choixAction = LectureClavier.lireEntier("5 : Retoucher un fichier.");

			switch(choixAction){ 
			case 1:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 2:
				FichierImageDAO.selectAllFromUser(c,utilisateur.getIdUser());
				break;
			case 3:
				PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
				break;
			case 4:
				String chemin= LectureClavier.lireChaine("Ou ce trouve votre fichier? ");
				String infoPVue= LectureClavier.lireChaine("Commentaire sur le fichier: ");
				int pixelImg= LectureClavier.lireEntier("Quel est la taille en pixell : ");	
				Boolean partage= LectureClavier.lireOuiNon("Souhaitez vous que n'importe qui puisse utiliser cette image?");
				String dateUse = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd"));
				FichierImageDAO.insertFichierImage(c, utilisateur.getIdUser(), chemin, infoPVue, pixelImg, partage, Date.valueOf(dateUse) , false, false);
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
		
	}


	
	//TODO: 
	private static void gererImpression(Connection c, Utilisateur utilisateur) throws SQLException {

		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Retourner au menu precedent.");
			System.out.println("2 : Consulter la liste de mes impressions.");
			System.out.println("3 : Creer une nouvelle impression.");
			int choixAction = LectureClavier.lireEntier("4 : .");

			switch(choixAction){ 
			case 1:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 2:
				ImpressionDAO.selectAllFromUserImpressionWait(c,utilisateur.getIdUser());
				break;
			case 3:
				String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
				String type= Type.definir();
				String format= Format.definir();
				String qualite= Qualite.definir();
				int nbPages= LectureClavier.lireEntier("Combien de pages souhaitez vous?: ");
				ImpressionDAO.insertImpression(c, nomI, nbPages, utilisateur.getIdUser(), type, format, qualite );
				break;
			case 4:
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}
	
	
	private static void afficherArticles(ArrayList<Article> panier) {
		// TODO:afficher les tostring() de chaque article en plus du nom de l'impressions associee a l'article en question.
		//ne pas oublier le formatage avec un titre "votre panier" et des ptites etoiles
		
	}
	
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
						afficherArticles(panier);
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
					afficherUtilisateur(UtilisateurDAO.selectAllUserFromStatut(c, StatutUtilisateur.CLIENT));
					//fonction gestion clients
					break;
				case 5:
					gererCommandeClients(c);
					break;
				case 6:
					
					break;
				case 7:
					
					break;
				case 8:
					afficherStats(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererCommandeClients(Connection c) {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Retourner au menu precedent.");
			System.out.println("2 : Consulter la liste des commandes clients en cours.");
			System.out.println("3 : Creer une nouvelle impression.");
			int choixAction = LectureClavier.lireEntier("4 : .");

			switch(choixAction){ 
			case 1:  
				back = true;
				System.out.println("retour au menu precedent");
				break;
			case 2:
				break;
			case 3:
				
				break;
			case 4:
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
	}

	private static void afficherStats(ArrayList<Stat> stat) {
		// TODO Auto-generated method stub
		
	}

	private static void afficherUtilisateur(ArrayList<Utilisateur> users) {
		// TODO:afficher les tostring() de chaque user avec les ptites etoiles
		
	}
	
	
	/*
	 * Consulter tous utilisateurs
Supprimer ⇔ désactiver un client
Consulter tous fichiers images et leur createur
Suppression fichiers images 
Consulter commandes (filtrer par article → filtrage dans ArticleDAO)
1*/



}
