package src.commande;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	//SELECTIONS : 
	
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
	    	ArrayList<Article> articles = ArticleDAO.selectAllFromCommande(c,id);
	    	int i=0; Article a; Impression imp; String modele;
	    	while (i<articles.size()){
	    		a = articles.get(i);
	    		imp = ImpressionDAO.selectImpressionFromId(c, id);
	    		switch(imp.getType().toString()){
	    			case "AGENDA" : 
				    				Agenda agenda = AgendaDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"' ").get(0);
				    				modele = agenda.getModeleAgenda().toString();
				    				break;
	    			case "CADRE" : 
				    				Cadre cadre = CadreDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"' ").get(0);
				    				modele = cadre.getModeleCadre().toString();
				    				break;
	    			case "CALENDRIER" : 
				    				Calendrier calendrier = CalendrierDAO.selectAll(c, " idImp = '"+imp.getIdImp()+"' ").get(0);
				    				modele = calendrier.getModeleCalendrier().toString();
				    				break;
	    			default : modele = "AUCUN";
	    		}
	    		CatalogueDAO.updateCatalogueQte(c, a.qte, imp.getType().toString(), imp.getFormat().toString(), modele);
	    		i++;
	    	}
			stat.executeUpdate("update Commande set statutCommande = 'PRET_A_L_ENVOI' where idComm='"+id+"' ");
			c.commit();
	    }
	    
	    public static void updateCommandeCommePayee(Connection c, int idUser) throws SQLException {
	    	Statement stat= c.createStatement();
			String query= "update Commande set statutCommande = 'EN_COURS' where idUser="+idUser+" AND statutCommande='BROUILLON'";
			stat.executeUpdate(query);
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
	     * @throws Exception 
	     */
	    public static void ajouterAuPanier(Connection conn, int idUser, int idImp, int qte) throws SQLException {
	    	conn.setAutoCommit(false);
	    	Statement state = conn.createStatement();
	    	ResultSet result = state.executeQuery("SELECT idComm FROM Commande WHERE idUser="+idUser+" AND statutCommande='BROUILLON'");
	    	

	    	if (result.next()) { //Il existe deja un panier
	    		int idComm = result.getInt(1);
	    		result = state.executeQuery("SELECT * FROM Article WHERE idImp="+idImp);
	    		if (result.next()) { //Cet article existe deja dans le panier
	    			state.executeUpdate("UPDATE Article SET qte= qte+"+qte);
	    		} else { //L'Article est a ajouter dans le panier
	    			ArticleDAO.insertArticleFromImpression(conn, idImp, idComm, qte);

	    		}
	    	} else { // Il n'existe pas encore de commande
	    		Date today = Date.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    		state.executeUpdate("INSERT INTO Commande (idUser,idCodeP, dateC,modeLivraison,statutCommande) VALUES("+idUser+", 0, TO_DATE('"+today+"', 'YYYY-MM-DD'), 'NULL', 'BROUILLON')");
	    		try {
	    			ResultSet result2 = state.executeQuery("SELECT max(idComm) FROM Commande");
	    			result2.next();
					ArticleDAO.insertArticleFromImpression(conn, idImp, result2.getInt(1), qte);
				} catch (Exception e) {
					// EXCEPTION stock insuffisant !
					e.printStackTrace();
				}
	    	}
	    	conn.commit();
	    	conn.setAutoCommit(true);
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
			c.setAutoCommit(false);
			new Affichage<Commande>().afficher(selectWithStatut(c,"PRET_A_L_ENVOI"));
			int idComm = -1;
			while(!idExists(c,idComm)){
				idComm = LectureClavier.lireEntier("Pour selectionner une commande, entrez son idComm (dans la liste présentée ci-dessus). -1 pour annuler.");
				if (idComm==-1) {return;}
			}
			updateCommandeCommeEnvoyee(c, idComm);
			c.commit();
			c.setAutoCommit(true);
		}
		
		public static void gererImpressionCommande(Connection c) throws SQLException {
			c.setAutoCommit(false);
			new Affichage<Commande>().afficher(selectWithStatut(c,"EN_COURS"));
			int idComm = -1;
			while(!idExists(c,idComm)){
				idComm = LectureClavier.lireEntier("Pour selectionner une commande, entrez son idComm (dans la liste présentée ci-dessus). -1 pour annuler.");
				if (idComm==-1) {c.setAutoCommit(true); return;}
			}
			updateCommandeCommeImprimee(c, idComm);
			c.commit();
			c.setAutoCommit(true);
		}
}
