package src.app;
import java.sql.*;

import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;



public class Application {
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	static String USER;
	static String PASSWD;
	static Connection c; 
	
	public static void main(String[] args) throws SQLException {
		try{
			USER=LectureClavier.lireChaine("saississez l'identifiant pour la connexion à la base : ");
			PASSWD=LectureClavier.lireChaine("Quel est le mot de passe ? ");
			
			// Enregistrement du driver Oracle
		    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());  	    
		    // Etablissement de la connection
		    System.out.print("Connecting to the database... "); 
		    c = DriverManager.getConnection(CONN_URL,USER,PASSWD);
		    System.out.println("connected");
		    System.out.println("*************************************************************************************************");
			System.out.println("Souhaitez-vous, vous connectez ou vous inscrire? ");
			
			int choix = LectureClavier.lireEntier("********* tapez 1 pour vous connecter, ou tapez 2 pour vous inscrire");
			switch(choix){ 
				//connexion
				case 1:  
					String mail = LectureClavier.lireChaine("Pour vous connecter, saisissez votre mail : ");
			    	Utilisateur utilisateur = UtilisateurDAO.selectUserWithMail(c, mail);
			    	String mdp;
					while(mdp != utilisateur.getMdp()){
						mdp = LectureClavier.lireChaine("Veuillez entrer le mot de passe correspondant.");
					}
			    // inscription
				case 2:
			}

		    
		   
			
				System.out.println("Vous voilà connecter ");
				if(i==2) {
					System.out.println("Bienvenue sur le site PhotoNum! Nous allons vous demander quelque information pour la création de votre compte");
					
					String mailInsc= LectureClavier.lireChaine("Inscrire votre adresse mail:");
					
					String mdpInsc= LectureClavier.lireChaine("Inscrire votre mot de passe:");
					
					String nomInsc= LectureClavier.lireChaine("Inscrire votre nom:");
					
					String prenomInsc= LectureClavier.lireChaine("Inscrire votre prenom:");
					System.out.println("Bienvenue à vous " + nomInsc + " "+ prenomInsc);
				}else {
					System.out.println("Vous n'avez pas choisi entre connecter: 1 et inscrire: 2 ");
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
