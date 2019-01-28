package src.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import src.commande.CatalogueDAO;
import src.commande.Stat;
import src.compte.StatutUtilisateur;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;
import src.impression.Format;
import src.impression.Modele;
import src.impression.Type;

public class UtilitaireGestionnaire {

	static void menuGestionnaire(Connection c, Utilisateur utilisateur) throws SQLException {
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
					ArrayList<Utilisateur> users = UtilisateurDAO.selectAllUserFromStatut(c, StatutUtilisateur.CLIENT);
					new Affichage<Utilisateur>().afficher(users);
					int idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier.");
					gererClients(c, users, idFichier);
					break;
				case 5:
					gererCommandeClients(c,utilisateur);
					break;
				case 6:
					
					break;
				case 7:
					
					break;
				case 8:
					new Affichage<Stat>().afficher(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererClients(Connection c, ArrayList<Utilisateur> users, int idFichier) {
			
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
	
}
