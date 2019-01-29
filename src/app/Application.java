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
import src.app.*;


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
				if(utilisateur.getStatut() == StatutUtilisateur.valueOf("CLIENT")){
					UtilitaireClient.menuClient(c, utilisateur);
				}else {
					UtilitaireGestionnaire.menuGestionnaire(c, utilisateur);
				}
			} 
			c.close();
		}
		catch (Exception e) {
			e.printStackTrace();
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
