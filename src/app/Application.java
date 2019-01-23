package src.app;
import java.sql.*;

import src.compte.Statut;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;



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
		System.out.println("Bienvenue sur le site PhotoNum ! Nous allons vous demander quelques infos pour la création de votre compte");
		
		Statut statut= choixStatut();			
		String mail= LectureClavier.lireChaine("Votre adresse mail : ");	
		String mdp= LectureClavier.lireChaine("Votre mot de passe : ");	
		String nom= LectureClavier.lireChaine("Votre nom : ");
		String prenom= LectureClavier.lireChaine("Votre prenom : ");
		System.out.println("   ");
		System.out.println("Bienvenue à vous " + nom + " "+ prenom + " ! ");
		return UtilisateurDAO.createUtilisateur(c,  nom,  prenom,  mdp,  mail,  statut);
	}	

	public static Utilisateur connexion(Connection c) throws SQLException{
		System.out.println("**********************");
		System.out.println("      CONNEXION ");
		System.out.println("**********************");

		String mail = LectureClavier.lireChaine("Pour vous connecter, saisissez votre mail : ");
    	Utilisateur utilisateur = UtilisateurDAO.selectUserWithMail(c, mail);
    	String mdp = null;
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
			Utilisateur utilisateur = null;
			USER=LectureClavier.lireChaine("saississez l'identifiant pour la connexion à la base : ");
			PASSWD=LectureClavier.lireChaine("Quel est le mot de passe ? ");
			
			// Enregistrement du driver Oracle
		    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  	    
		    // Etablissement de la connection
		    System.out.print("Connecting to the database... "); 
		    c = DriverManager.getConnection(CONN_URL,USER,PASSWD);
		    System.out.println("connected");
		    System.out.println("**********************************************************************");
		    
		    while(utilisateur == null){
				System.out.println("Souhaitez-vous, vous connectez ou vous inscrire? ");
				int choix = LectureClavier.lireEntier("tapez 1 pour vous connecter, ou tapez 2 pour vous inscrire");
				
				switch(choix){ 
					case 1:  
						utilisateur = connexion(c);
						break;
					case 2:
						utilisateur = inscription(c);
						break;
					default : System.out.println("Veuillez faire un choix. ");
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
