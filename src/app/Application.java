package src.app;
import java.sql.*;
import java.util.ArrayList;

import src.commande.Article;
import src.commande.ArticleDAO;
import src.commande.CommandeDAO;
import src.compte.Statut;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;
import src.impression.ImpressionDAO;



public class Application {
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	static String USER;
	static String PASSWD;
	static Connection c; 

	public static Statut choixStatut(){
		System.out.println("Vous pouvez vous inscrire en temps que client ou en tant que gestionnaire.");
		return Statut.valueOf(LectureClavier.lireChaine("CLIENT ou GESTIONNAIRE ?"));
	}

	public static Utilisateur inscription(Connection c) throws SQLException{
		System.out.println("***********************");
		System.out.println("      INSCRIPTION ");
		System.out.println("***********************");
		System.out.println("Bienvenue sur le site PhotoNum ! Nous allons vous demander quelques infos pour la creation de votre compte");

		Statut statut= choixStatut();			
		String mail= LectureClavier.lireChaine("Votre adresse mail : ");	
		String mdp= LectureClavier.lireChaine("Votre mot de passe : ");	
		String nom= LectureClavier.lireChaine("Votre nom : ");
		String prenom= LectureClavier.lireChaine("Votre prenom : ");
		System.out.println("   ");
		System.out.println("Bienvenue e vous " + nom + " "+ prenom + " ! ");
		return UtilisateurDAO.createUtilisateur(c,  nom,  prenom,  mdp,  mail,  statut);
	}	

	public static Utilisateur connexion(Connection c) throws SQLException{
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
	
	
	//revoir l'organisation de ce menu
	//difference consulter fichier/photo ?
	//est ce quon fait les deux ?
	
	private static void gererFichierImages(Connection c, Utilisateur utilisateur) {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Retourner au menu précédent.");
			System.out.println("2 : Consulter la liste de mes fichiers de base.");
			System.out.println("3 : Consulter la liste de mes photos.");
			System.out.println("4 : Ajouter un nouveau fichier.");
			int choixAction = LectureClavier.lireEntier("5 : Retoucher un fichier.");

			switch(choixAction){ 
			case 1:  
				back = true;
				System.out.println("retour au menu précédent");
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
		
	}

	
	//TODO: 
	private static void gererImpression(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Retourner au menu précédent.");
			System.out.println("2 : Consulter la liste de mes impressions.");
			System.out.println("3 : Creer une nouvelle impression.");
			int choixAction = LectureClavier.lireEntier("4 : .");

			switch(choixAction){ 
			case 1:  
				back = true;
				System.out.println("retour au menu précédent");
				break;
			case 2:
				ImpressionDAO.selectAllFromUserImpressionWait(c,utilisateur.getIdUser());
				break;
			case 3:
				String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
				Type type= LectureClavier.lireChaine("Quel est le type de votre impression?: ");
				Format format= LectureClavier.lireChaine("Quel format souhaitez vous? : ");	
				QUalite qualite= LectureClavier.lireChaine("Quel qualit� souhaitez vous?: ");
				String nbPages= LectureClavier.lireChaine("Combien de pages souhaitez vous?: ");
				ImpressionDAO.createImpression(c, utilisateur.getIdUser(),nomI, type, format, qualite, nbPages);
				break;
			case 4:
				break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}
	
	
	private static void afficherArticles(ArrayList<Article> panier) {
		// TODO:afficher les tostring() de chaque article en plus du nom de l'impressions associée a l'article en question.
		//ne pas oublier le formatage avec un titre "votre panier" et des ptites étoiles
		
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
						if(panier.isEmpty()){  System.out.println("Vous n'avez aucun article dans votre panier"); }
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



			/*
			 * Partie de code pour charger un jeu de test 
			 * 
			 * charger fichier... chaque requete sur une ligne
			 * for ([all lines in file]) {
			 * String line = [une ligne];
			 * String keyword = line.split(" ")[0];
			 * switch (keyword) {
			 * 	case "INSERT": smth.executeUpdate(line);
			 * 	case "UPDATE": smth.executeUpdate(line);
			 * 	case "SELECT": smth.executeQuery(line);
			 * 	case "DELETE": smth.executeUpdate(line);
			 * }
			 * }
			 */
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}





}
