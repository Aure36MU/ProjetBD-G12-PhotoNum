package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		 *
		 * @param conn Connection SQL
		 * @return ArrayList contenant tous les objets Commande
		 * @throws SQLException
		 */
		public static ArrayList<Commande> selectAll(Connection conn) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande");
	        return getCommandes(result);
	    }
		
	    /**
	     * Selectionne toutes les commandes  avec des conditions parametres.
	     *
	     * @param conn Connection SQL
	     * @param condition chaene de caracteres formate comme suit : "condition1 {AND condition2}"
	     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	     * @return ArrayList contenant les objets commande selectionnes
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectPretEnvoi(Connection conn) throws SQLException{
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE statutCommande='PRET_A_L_ENVOI'");
	        return getCommandes(result);
	    }
	    
	    /**
	     * Selectionne toutes les commandes  envoyee  : represente archivage des commandes deje faites
	     * Partie de la base exportable dans une zone de stockage
	     * @param conn Connection SQL
	     * @param condition chaene de caracteres formate comme suit : "condition1 {AND condition2}"
	     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	     * @return ArrayList contenant les objets commande selectionnes
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectEnCours(Connection conn) throws SQLException {
	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE statutCommande='EN_COURS'");
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
	     * -S'il n'existe pas de Commande 'brouillon' sur cet utilisateur, on va la cr�er (INSERT).
	     * -La quantit� de l'article est connue � l'avance, mettre une valeur de 1 par d�faut.
	     * -Pour une commande existante, si l'idImp de l'article est d�j� pr�sent, alors il suffit d'ajouter la quantit� voulue � celle-ci (UPDATE).
	     * 
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
	    		state.executeUpdate("INSERT INTO Commande VALUES("+(getHigherId(conn)+1)+", "+idUser+", 0, 'NULL', 'NULL', 'BROUILLON')");
	    		try {
					ArticleDAO.insertArticleFromImpression(conn, idImp, (getHigherId(conn)+1), qte);
				} catch (Exception e) {
					// EXCEPTION stock insuffisant !
					e.printStackTrace();
				}
	    	}
	    	conn.commit();
	    }

		public static int getHigherId(Connection c){
			try {
				Statement state = c.createStatement();
				ResultSet res = state.executeQuery("SELECT max(idComm) FROM Commande");
				if (res.next()) {
					return res.getInt(1);
				}
			} catch (SQLException e) { e.printStackTrace();	}
			return 0;
		}
	    
	    /**
	     * Retourne les objets Commande construits e partir d'un resultat de requete.
	     *
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

}
