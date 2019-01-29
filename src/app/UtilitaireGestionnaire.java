package src.app;

import java.sql.Connection;
import java.sql.SQLException;

import src.commande.CatalogueDAO;
import src.commande.Commande;
import src.commande.CommandeDAO;
import src.commande.Stat;
import src.compte.Utilisateur;
import src.compte.UtilisateurDAO;
import src.impression.Format;
import src.impression.Modele;
import src.impression.Type;
import src.photo.FichierImageDAO;
import src.photo.Owners;

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
				case 1:  utilisateur = null;
					System.out.println("Vous avez ete deconnecte");
					break;
				case 2:
					String type = Type.definir();		String format = Format.definir();			String modele = Modele.definir();
					int qteLivraison = LectureClavier.lireEntier("Combien en avez vous reçu ?");
					CatalogueDAO.simulerLivraison( c,  qteLivraison,  type,  format,  modele);
					break;
				case 3:
					type = Type.definir();			format = Format.definir();		modele = Modele.definir();
					int newPrix = LectureClavier.lireEntier("Nouveau prix ?");
					CatalogueDAO.updateCataloguePrix( c,  newPrix,  type,  format,  modele);
					break;
				case 4:	UtilisateurDAO.gererClients(c);		
					break;
				case 5: 	FichierImageDAO.gererFichiersClients(c);
					break;				
				case 6:	menuCommandeClients(c,utilisateur);	
					break;
				case 7:	new Affichage<Stat>().afficher(CatalogueDAO.getStat(c,(CatalogueDAO.selectAll(c))));
					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void menuCommandeClients(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Notifier l'envoi d'une commande.");
			int choixAction = LectureClavier.lireEntier("4 : Lancer l'impression d'une commande.");
			switch(choixAction){ 
				case 1:  	utilisateur = null;	back = true;
					System.out.println("Vous avez ete deconnecte");
					break;
				case 2:	back = true;
					System.out.println("retour au menu precedent");
					break;
				case 3:	CommandeDAO.gererEnvoiCommande(c);				break;
				case 4:	CommandeDAO.gererImpressionCommande(c);		break;				
				default : System.out.println("Veuillez faire un choix. ");
			}	
		}
	}
	
}
