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
import src.photo.FichierImageDAO;

public class UtilitaireGestionnaire {

	static void menuGestionnaire(Connection c, Utilisateur utilisateur) throws SQLException {
		while(utilisateur != null){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Enregistrer une livraison");
			System.out.println("3 : Modifier le prix d'un article du catalogue.");
			System.out.println("4 : Gerer les clients (suppression).");
			System.out.println("5 : Gerer les commandes clients.");
			System.out.println("6 : Gerer les fichiers et le nom de leur propri�taire (controle de contenu).");
			int choixAction = LectureClavier.lireEntier("7 : Voir les statistiques de vente ");

			switch(choixAction){ 
				case 1:  
					utilisateur = null;
					System.out.println("Vous avez ete deconnecte");
					break;
				case 2:
					String type = Type.definir();
					String format = Format.definir();
					String modele = Modele.definir();
					int qteLivraison = LectureClavier.lireEntier("Combien en avez vous re�us ?");
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
					gererClients(c);
					break;
				case 5:
					gererCommandeClients(c,utilisateur);
					break;
				case 6:
					gererFichiersClients(c);
					break;
				case 7:
					new Affichage<Stat>().afficher(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererFichiersClients(Connection c) {
		new Affichage<Utilisateur>().afficher(FichierImageDAO.selectWithCondition(c, "statut = 'CLIENT' and active = 1"));
		int idUser = 0;
		while(!UtilisateurDAO.idExists(c,idUser)){
			idUser = LectureClavier.lireEntier("Pour selectionner un client, entrez son idUser (dans la liste pr�sent�e ci-dessus).");
		}
		//FichierImageDAO.deleteUtilisateur(c, idUser);
	}

	private static void gererClients(Connection c) throws SQLException {
		new Affichage<Utilisateur>().afficher(UtilisateurDAO.selectWithCondition(c, "statut = 'CLIENT' and active = 1"));
		int idUser = 0;
		while(!UtilisateurDAO.idExists(c,idUser)){
			idUser = LectureClavier.lireEntier("Pour selectionner un client, entrez son idUser (dans la liste pr�sent�e ci-dessus).");
		}
		UtilisateurDAO.deleteUtilisateur(c, idUser);
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
				back = true;
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
