package src.app;
import java.sql.*;
import src.compte.StatutUtilisateur;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;


public class Application {
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	 static String USER;
	 static String PASSWD;
	static Connection c; 

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
		System.out.println("Bienvenue a vous " + nom + " "+ prenom + " ! ");
		return UtilisateurDAO.createUtilisateur(c,  nom,  prenom,  mdp,  mail,  statut);
	}	

	private static Utilisateur connexion(Connection c) throws SQLException{
		System.out.println("**********************");
		System.out.println("      CONNEXION ");
		System.out.println("**********************");

		String mail = "";
		Utilisateur utilisateur = null;
		while(utilisateur == null) {
			mail = LectureClavier.lireChaine("Pour vous connecter, saisissez votre mail : ");
			try {
				utilisateur = UtilisateurDAO.selectWithCondition(c, "email = '"+mail+"'").get(0);
			} catch (IndexOutOfBoundsException ie) {
				
			}
		}
		
		String mdp = "";
		while(!mdp.equals(utilisateur.getMdp())){
			mdp = LectureClavier.lireChaine("Veuillez entrer le mot de passe correspondant ou entrez \"return to menu\" pour retourner au Menu Principal");
			if(mdp.equals("return to menu")){
				return null;
			}
		}
		return utilisateur;
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
				default : 
				}
			}
		
			System.out.println("Veuillez faire un choix. ");
			if(utilisateur.getStatut() == StatutUtilisateur.valueOf("CLIENT")){
				UtilitaireClient.menuClient(c, utilisateur);
			}else {
				UtilitaireGestionnaire.menuGestionnaire(c, utilisateur);
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
