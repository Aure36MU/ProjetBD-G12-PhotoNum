package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.app.Affichage;
import src.app.LectureClavier;
import src.impression.Impression;
import src.impression.ImpressionDAO;
import src.impression.agenda.Agenda;
import src.impression.agenda.AgendaDAO;
import src.impression.cadre.Cadre;
import src.impression.cadre.CadreDAO;
import src.impression.calendrier.Calendrier;
import src.impression.calendrier.CalendrierDAO;

public class CommandeDAO {

		/**
		 * Selectionne tous les Commandes (quels que soient leurs modeles) sans conditions.
		 * @param conn Connection SQL
		 * @return ArrayList contenant tous les objets Commande
		 * @throws SQLException
		 */
		public static ArrayList<Commande> selectAll(Connection conn) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande");
	        return getCommandes(result);
	    }
	    
	    public static ArrayList<Commande> selectWithStatut(Connection conn,String STATUT) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE statutCommande='"+STATUT+"'");
	        return getCommandes(result);
	    }

	    /**
	     * Selectionne tous les Commandes crees par un certain utilisateur.
	     * @param conn Connection SQL
	     * @param id id utilisateur
	     * @return ArrayList contenant les objets Commande selectionnes
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectAllFromUser(Connection conn, int id) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE idUser="+id);
	        return getCommandes(result);
	    }
	    
	    public static Commande selectWithId(Connection conn, int id) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE idComm="+id);
	        return getCommandes(result).get(0);
	    }
	    
	    public static void updateCommandeCommeEnvoyee(Connection c, int id) throws SQLException {
	    	Statement stat= c.createStatement();
			String query= "update Commande set statutCommande = 'ENVOYEE' where idComm='"+id+"'";
			stat.executeUpdate(query);
	    }
	    
	    public static void updateCommandeCommeImprimee(Connection c, int id) throws SQLException {
	    	Statement stat= c.createStatement();
	    	c.setAutoCommit(false);	    	
	    	ArrayList<Article> articles = ArticleDAO.selectAllFromCommande(c,id);
	    	int i=0; Article a; Impression imp; String modele;
	    	while (i<articles.size()){
	    		a = articles.get(i);
	    		imp = ImpressionDAO.selectImpressionFromId(c, id);
	    		switch(imp.getType().toString()){
	    			case "AGENDA" : 
				    				Agenda agenda = AgendaDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"'").get(0);
				    				modele = agenda.getModeleAgenda().toString();
				    				break;
	    			case "CADRE" : 
				    				Cadre cadre = CadreDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"'").get(0);
				    				modele = cadre.getModeleCadre().toString();
				    				break;
	    			case "CALENDRIER" : 
				    				Calendrier calendrier = CalendrierDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"'").get(0);
				    				modele = calendrier.getModeleCalendrier().toString();
				    				break;
	    			default : modele = "AUCUN";
	    		}
	    		CatalogueDAO.updateCatalogueQte(c, a.qte, imp.getType().toString(), imp.getFormat().toString(), modele);
	    		i++;
	    	}
			stat.executeUpdate("update Commande set statutCommande = 'PRET_A_L_ENVOI' where idComm='"+id+"'");
			c.commit();
	    }
	    
	    
		public static Boolean idExists(Connection c, int idComm) throws SQLException {
			Statement stat= c.createStatement();
			ResultSet result =stat.executeQuery( "select count(*) from Commande where idComm='"+idComm+"'");
			if (result.next()) {
				return result.getInt(1)==1;
			}
			return false;
		}
	    
	    /**
	     * Ajoute un nouvel Article au panier, sous les conditions suivantes :
	     * -S'il n'existe pas de Commande 'brouillon' sur cet utilisateur, on va la crï¿½er (INSERT).
	     * -La quantitï¿½ de l'article est connue ï¿½ l'avance, mettre une valeur de 1 par dï¿½faut.
	     * -Pour une commande existante, si l'idImp de l'article est dï¿½jï¿½ prï¿½sent, alors il suffit d'ajouter la quantitï¿½ voulue ï¿½ celle-ci (UPDATE).
	     * @param conn
	     * @param idUser
	     * @param idImp
	     * @param idComm
	     * @param qte
	     * @throws SQLException 
	     */
	    public static void ajouterAuPanier(Connection conn, int idUser, int idImp, int qte) throws SQLException {
	    	conn.setAutoCommit(false);
	    	//TODO: isolation level a changer => serialisable pour tout faire d'un coup sans modification.
	    	Statement state = conn.createStatement();
	    	ResultSet result = state.executeQuery("SELECT idComm FROM Commande WHERE idUser="+idUser+" AND statutCommande='BROUILLON'");
	    	

	    	if (result.next()) { //Il existe deja un panier
	    		int idComm = result.getInt(1);
	    		result = state.executeQuery("SELECT * FROM Article WHERE idImp="+idImp);
	    		if (result.next()) { //Cet article existe deja dans le panier
	    			state.executeUpdate("UPDATE Article SET qte= qte+"+qte);
	    		} else { //L'Article est a ajouter dans le panier
	    			try {
						ArticleDAO.insertArticleFromImpression(conn, idImp, idComm, qte);
					} catch (Exception e) {
						// EXCEPTION stock insuffisant !
						e.printStackTrace();
					}
	    		}

	    	} else { // Il n'existe pas encore de commande
	    		state.executeUpdate("INSERT INTO Commande VALUES("+", "+idUser+", 0, 'NULL', 'NULL', 'BROUILLON')");
	    		try {
	    			
					ArticleDAO.insertArticleFromImpression(conn, idImp, selectWithStatut(conn,"BROUILLON").get(0).idComm, qte);
				} catch (Exception e) {
					// EXCEPTION stock insuffisant !
					e.printStackTrace();
				}
	    	}
	    	conn.commit();
	    }

		/**
	     * Retourne les objets Commande construits e partir d'un resultat de requete.
	     * @param result le ResultSet de la requete SQL
	     * @return ArrayList contenant les objets Agenda
	     * @throws SQLException
	     */
		public static ArrayList<Commande> getCommandes(ResultSet result) throws SQLException {
	        ArrayList<Commande> commandes = new ArrayList<Commande>();
	        while (result.next()) {
	            commandes.add(new Commande(
	                    result.getInt("idComm"),
	                    result.getInt("idUser"),
	                    result.getInt("idCodeP"),
	                    result.getDate("dateC"),
	                    ModeLivraison.valueOf(result.getString("modeLivraison")),
	                    StatutCommande.valueOf(result.getString("statutCommande"))
	            ));
	        }
	        return commandes;
		}

		public static void gererEnvoiCommande(Connection c) throws SQLException {
			new Affichage<Commande>().afficher(selectWithStatut(c,"PRET_A_L_ENVOI"));
			int idComm = -1;
			while(!idExists(c,idComm)){
				idComm = LectureClavier.lireEntier("Pour selectionner une commande, entrez son idComm (dans la liste présentée ci-dessus).");
			}
			updateCommandeCommeEnvoyee(c, idComm);
		}
		
		public static void gererImpressionCommande(Connection c) throws SQLException {
			new Affichage<Commande>().afficher(selectWithStatut(c,"EN_COURS"));
			int idComm = -1;
			while(!idExists(c,idComm)){
				idComm = LectureClavier.lireEntier("Pour selectionner une commande, entrez son idComm (dans la liste présentée ci-dessus).");
			}
			updateCommandeCommeImprimee(c, idComm);
		}
}
