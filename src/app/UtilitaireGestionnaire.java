package src.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import src.commande.CatalogueDAO;
import src.commande.Commande;
import src.commande.CommandeDAO;
import src.commande.Stat;
import src.compte.StatutUtilisateur;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;
import src.impression.Format;
import src.impression.Modele;
import src.impression.Type;
import src.photo.FichierImage;
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
			System.out.println("5 : Gerer les fichiers et le nom de leur propriétaire (controle de contenu).");
			System.out.println("6 : Gerer les commandes clients.");
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
				case 4:	gererClients(c);
					break;
				case 6: 	gererFichiersClients(c);
					break;				
				case 5:	gererCommandeClients(c,utilisateur);
					break;
				case 7:	new Affichage<Stat>().afficher(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererFichiersClients(Connection c) throws SQLException {
		new Affichage<FichierImage>().afficher(FichierImageDAO.selectAllWithOwner(c));
		int idFichier = -1;
		while(!FichierImage.idExists(c,idFichier)){
			idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste présentée ci-dessus).");
		}
		FichierImageDAO.deleteFichierImage(c, idFichier);
	}

	private static void gererClients(Connection c) throws SQLException {
		new Affichage<Utilisateur>().afficher(UtilisateurDAO.selectWithCondition(c, "statut = 'CLIENT' and active = 1"));
		int idUser = -1;
		while(!UtilisateurDAO.idExists(c,idUser)){
			idUser = LectureClavier.lireEntier("Pour selectionner un client, entrez son idUser (dans la liste présentée ci-dessus).");
		}
		UtilisateurDAO.deleteUtilisateur(c, idUser);
	}
	
	private static void gererCommandeClients(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Notifier l'envoi d'une commande.");
			int choixAction = LectureClavier.lireEntier("4 : Lancer l'impression d'une commande.");

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
				new Affichage<Commande>().afficher(CommandeDAO.selectPretEnvoi(c));
				break;
			case 4:
				new Affichage<Commande>().afficher(CommandeDAO.selectEnCours(c));
				break;
				
			default : System.out.println("Veuillez faire un choix. ");
			}
			
		}
	}
	
}
