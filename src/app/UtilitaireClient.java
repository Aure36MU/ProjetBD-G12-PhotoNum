package src.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import src.commande.Article;
import src.commande.ArticleDAO;
import src.commande.CatalogueDAO;
import src.commande.Commande;
import src.commande.CommandeDAO;
import src.compte.Utilisateur;
import src.impression.Format;
import src.impression.Impression;
import src.impression.ImpressionDAO;
import src.impression.Qualite;
import src.impression.Type;
import src.impression.agenda.ModeleAgenda;
import src.impression.agenda.Ornement;
import src.impression.cadre.ModeleCadre;
import src.impression.calendrier.ModeleCalendrier;
import src.photo.FichierImage;
import src.photo.FichierImageDAO;
import src.photo.Impression_PhotoDAO;
import src.photo.Photo;
import src.photo.PhotoDAO;

public class UtilitaireClient {

	static void menuClient(Connection c, Utilisateur utilisateur) throws SQLException {
		while(utilisateur != null){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Gerer mes impressions.");
			System.out.println("3 : Gerer mes fichiers.");
			System.out.println("4 : Gerer mon panier.");
			int choixAction = LectureClavier.lireEntier("5 : Voir mon historique de commandes.");

			switch(choixAction){ 
				case 1:  	utilisateur = null;		System.out.println("Vous avez ete deconnecte");			break;
				case 2:	gererImpression(c,utilisateur);				break;
				case 3:	gererFichierImages(c,utilisateur);		break;
				case 4:	utilisateur = gererPanier(c, utilisateur);					break;
				case 5:
					ArrayList<Commande> commandes = CommandeDAO.selectAllFromUser(c, utilisateur.getIdUser());
					new Affichage<Commande>().afficher(commandes);		break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	

	public static Utilisateur gererPanier(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			ArrayList<Article> panier = ArticleDAO.selectAllFromPanier(c, utilisateur.getIdUser());
				if(panier.isEmpty()){ 
					System.out.println("Vous n'avez aucun article dans votre panier"); 
					return utilisateur; 
				}
				else{	new Affichage<Article>().afficher(panier);
							
						System.out.println("*****************************************************************************");
						System.out.println("Que voulez vous faire ?");
						System.out.println("1 : Se deconnecter.");
						System.out.println("2 : Retourner au menu precedent.");
						System.out.println("3 : Modifier la quantité d'un article.");
						System.out.println("4 : Retirer un article.");
						int choixAction = LectureClavier.lireEntier("5 : Valider la commande.");
							
						switch(choixAction){ 
								case 1: return null;
								case 2: back = true; 												break;
								case 3: ArticleDAO.ModifierQuantite(c);			break;
								case 4: ArticleDAO.SupprimerUnArticle(c);		break;
								case 5:	boolean payer=LectureClavier.lireOuiNon("Voulez vous valider et payer votre commande?");
								Article articleStockInsuffisant = CatalogueDAO.verifierStockPanier(c, utilisateur);
										if(payer && articleStockInsuffisant==null) {
											CommandeDAO.updateCommandeCommePayee(c, utilisateur.getIdUser());
											System.out.println("Vous avez paye :)"); 
										} else if(payer){
											System.out.println("Commande Impossible : stock insuffisant pour l'article d'idArt = "+articleStockInsuffisant.getIdArt()); 
										}
										break;
								default : System.out.println("Veuillez faire un choix. ");
							}
				}
		}
		return utilisateur;
	}
	
	private static void gererFichierImages(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Consulter la liste de mes fichiers.");
			System.out.println("4 : Consulter la liste de mes photos.");
			System.out.println("5 : Consulter la liste des fichiers partages.");
			int choixAction = LectureClavier.lireEntier("6 : Ajouter un nouveau fichier.");

			switch(choixAction){
			case 1:  	utilisateur = null;	back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:   	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	gererFichiers(c,utilisateur);
							break;
			case 4:	new Affichage<Photo>().afficher(PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser()));
							gererPhotos(c,utilisateur);
							break;
			case 5:	gererFichierPartages(c, utilisateur);
							break;
			case 6:	FichierImageDAO.ajouterFichier(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	
	private static void gererFichiers(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			new Affichage<FichierImage>().afficher(FichierImageDAO.selectAllFromUser(c,utilisateur.getIdUser()));
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Modifier un fichier");
			System.out.println("4 : Supprimer un fichier");
			int choixAction = LectureClavier.lireEntier("5 : Utiliser un fichier (retouche).");
			
			switch(choixAction){ 
				case 1:  	utilisateur = null;	back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
				case 2:  	back = true;
							System.out.println("retour au menu precedent");
							break;
				case 3:	FichierImageDAO.modifierFichier(c,utilisateur);				break;
				case 4:	FichierImageDAO.supprimerUnFichierClient(c, utilisateur);	break;
				case 5:	utiliserFichierPourPhoto(c,utilisateur);					break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererPhotos(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Modifier une photo");
			int choixAction = LectureClavier.lireEntier("4 : Supprimer une photo");
			
			switch(choixAction){ 
				case 1:  	utilisateur = null; back = true;
								System.out.println("Vous avez ete deconnecte");
								break;
				case 2:  	back = true;
								System.out.println("retour au menu precedent");
								break;
				case 3:	PhotoDAO.modifierPhoto(c,utilisateur);			break;
				case 4:	PhotoDAO.supprimerPhoto(c,utilisateur);			break;
				default : System.out.println("Veuillez faire un choix. ");
			}
		}
	}
	
	private static void gererFichierPartages(Connection c, Utilisateur utilisateur) throws SQLException {
		System.out.println("**********************************************************");
		System.out.println("             fichiers partages disponibles : ");
		System.out.println("********************************************************** ");
		new Affichage<FichierImage>().afficher(FichierImageDAO.selectAll(c, "partager=1"));
		utiliserFichierPourPhoto(c, utilisateur);
		
	}

	public static void utiliserFichierPourPhoto(Connection c, Utilisateur utilisateur) throws SQLException {
		if(LectureClavier.lireOuiNon("Voulez vous utiliser un de ces fichiers ?")) {
			int idFichier = -1;
			while(!FichierImageDAO.idExists(c,idFichier) || !(FichierImageDAO.belongToUser(c, idFichier, utilisateur.getIdUser()) || FichierImageDAO.isShared(c,idFichier))){
				idFichier = LectureClavier.lireEntier("Pour selectionner un fichier, entrez son idFichier (dans la liste ci-dessus ou -1 pour annuler).");
				if(idFichier == -1) {return;}
			}
			String retouche = LectureClavier.lireChaine("quelle retouche apporter au fichier ?");
			PhotoDAO.insertPhoto(c, idFichier, retouche);
			System.out.println("Votre photo a ete cree");
		} else {
			return;
		}
	}
	
	
	private static void creationPhoto(Connection c, Utilisateur utilisateur, int idImp) throws SQLException {
		ArrayList <Impression> tabImp = ImpressionDAO.selectAllFromUser(c, utilisateur.getIdUser());
		int i=0;	while(i<tabImp.size() && tabImp.get(i).getIdImp()!=idImp) {i++;}
		
		if(i<tabImp.size()) {
			int choix = LectureClavier.lireEntier("Voulez vous ajouter ou retirer une photo d'une page? (0: Ajouter/ 1:Retirer / 2: Finir)");
			boolean boucle=true;
			while(boucle) {
					switch(choix) {
						case 0:	ArrayList<Photo> tab =PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
										new Affichage<Photo>().afficher(tab);
										int ph= LectureClavier.lireEntier("Quel photo voulez vous ajouter?");
										int j=0;		while(j<tab.size() && tab.get(j).getIdPh()!=ph) {	j++;	}
										if(j<tabImp.size()) {
														String txt		= LectureClavier.lireChaine("Quel texte voulez vous ajouter a la photo?");
														int nb			= LectureClavier.lireEntier("Combien de fois voulez vous cette photo?");
														int page		= LectureClavier.lireEntier("Sur quel page?");
														Impression_PhotoDAO.insert(c, ph, idImp, page, txt, nb);
														choix 			= LectureClavier.lireEntier("Voulez vous faire autre chose? (0: Ajouter/ 1:Retirer / 2: Finir)");
										}else {		System.out.println("Vous n'avez pas selectionne une photo que vous possedez");	}
										break;
										
						case 1:	int num = LectureClavier.lireEntier("Quel page voulez vous retirer?");
										Impression_PhotoDAO.deletePage(c, idImp, num);
										choix = LectureClavier.lireEntier("Voulez vous faire autre chose? (0: Ajouter/ 1:Retirer / 2: Finir)");
										break;
										
						case 2:	boucle=false;
								boolean comm=LectureClavier.lireOuiNon("Voulez vous ajouter cette impression dans votre panier?");
								if(comm) {		int qte= LectureClavier.lireEntier("combien d'exemplaires souhaitez vous?");
														CommandeDAO.ajouterAuPanier(c, utilisateur.getIdUser(), idImp, qte);
								}
								break;
					}
			}
		} else {	System.out.println("Vous n'avez pas selectionne une impression que vous possedez");}
	}


	private static void gererImpression(Connection c, Utilisateur utilisateur) throws SQLException {

		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Consulter la liste de mes impressions.");
			int choixAction = LectureClavier.lireEntier("4 : Creer une nouvelle impression.");

			switch(choixAction){ 
			case 1:  	utilisateur = null;
							back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:  	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	ArrayList<Impression> tab =ImpressionDAO.selectAllFromUserImpressionNotArticle(c,utilisateur.getIdUser());
							new Affichage<Impression>().afficher(tab);
							gererModifImpression(c,utilisateur);
							break;
			case 4:	gereInsertImp(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}

	private static void gererModifImpression(Connection c, Utilisateur utilisateur) throws SQLException {

		boolean back = false;
		while(!back){
			System.out.println("*****************************************************************************");
			System.out.println("Que voulez vous faire ?");
			System.out.println("1 : Se deconnecter.");
			System.out.println("2 : Retourner au menu precedent.");
			System.out.println("3 : Continuer une impression.");
			int choixAction = LectureClavier.lireEntier("4 : Supprimer une impression.");

			switch(choixAction){ 
			case 1:  	utilisateur = null;
							back = true;
							System.out.println("Vous avez ete deconnecte");
							break;
			case 2:  	back = true;
							System.out.println("retour au menu precedent");
							break;
			case 3:	int imp=LectureClavier.lireEntier("Quel impression voulez vous continuer?");
			creationPhoto(c,utilisateur,imp);
							break;
			case 4:	ImpressionDAO.gererDeleteImp(c,utilisateur);		break;
			default : System.out.println("Veuillez faire un choix. ");
			}
		}		
	}
	
	
	private static void gereInsertImp(Connection c, Utilisateur utilisateur) throws SQLException {
		boolean continuer=true;
		boolean correct = false;
		while(continuer) {
			
			String nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
			String type= Type.definir();
			String format= Format.definir();
			String qualite= Qualite.definir();
			
			System.out.println("Récapitulatif: ");
			System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
			correct=LectureClavier.lireOuiNon("Cela vous conviens? (oui/non)");
			
			while(!correct) {
				if (LectureClavier.lireOuiNon("Abandonner ? (oui/non)")) {
					return;
				}
				nomI= LectureClavier.lireChaine("Quel nom souhaitez vous pour votre impression?: ");
				type= Type.definir();
				format= Format.definir();
				qualite= Qualite.definir();
				
				System.out.println("Récapitulatif: ");
				System.out.println("Nom du fichier: "+ nomI + " du type:"+ type + " au format: " + format + "de qualite: " + qualite);
				correct=LectureClavier.lireOuiNon("Cela vous conviens? (oui/non)");
			}

			switch(type){ 
				case "AGENDA":
								String modeleAgenda= ModeleAgenda.definir();
								String ornement = Ornement.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,ornement,modeleAgenda );
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "CALENDRIER":
								String modeleCalendrier = ModeleCalendrier.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCalendrier);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "ALBUM":
								String titreCouv=LectureClavier.lireChaine("Quel nom souhaitez vous sur la couverture?: ");
								ArrayList<Photo> tab =PhotoDAO.selectAllFromUser(c, utilisateur.getIdUser());
								int i=0;
								boolean trouve=false;
								new Affichage<Photo>().afficher(tab);
								while(trouve==false) {
									int photo= LectureClavier.lireEntier("Quel photo voulez vous mettre en couverture? Entrez le numero de la photo");
									while(i<tab.size() && tab.get(i).getIdPh()!=photo) {i++;}
									if(i<tab.size()) {
										trouve=true;
										ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,photo,titreCouv );
									}else {	System.out.println("Vous n'avez pas selectionne une photo que vous possedez"); }
								}	
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;	
				case "CADRE":
								String modeleCadre = ModeleCadre.definir();
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite,modeleCadre);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
				case "TIRAGE":
								ImpressionDAO.insertImpression(c, nomI, 0, utilisateur.getIdUser(), type, format, qualite);
								System.out.println("Vous venez de creer votre nouvelle impression");
								break;
			}
			continuer = LectureClavier.lireOuiNon("Continuer de créer des impressions? (oui/non)");
		}
	}
		
	
}