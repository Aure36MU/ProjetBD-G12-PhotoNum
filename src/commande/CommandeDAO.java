package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CommandeDAO {
	
	
		/**
		 * Selectionne tous les Commandes (quels que soient leurs modeles) sans conditions.
		 *
		 * @param conn Connection SQL
		 * @return ArrayList contenant tous les objets Commande
		 * @throws SQLException
		 */
	
		public static ArrayList<Commande> selectAll(Connection conn) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande;");
	        
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
	    public static ArrayList<Commande> selectAll(Connection conn, String condition) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE "+condition+";");
	        return getCommandes(result);

	    }
	    
	    /**
	     * Selectionne toutes les commandes  envoyee  : represente archivage des commandes deje faites
	     * Partie de la base exportable dans une zone de stockage
	     *
	     * @param conn Connection SQL
	     * @param condition chaene de caracteres formate comme suit : "condition1 {AND condition2}"
	     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	     * @return ArrayList contenant les objets commande selectionnes
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectEnvoyer(Connection conn) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE statut='envoye';");
	        return getCommandes(result);

	    }

	    /**
	     * Selectionne tous les Commandes crees par un certain utilisateur.
	     *
	     * @param conn Connection SQL
	     * @param id id utilisateur
	     * @return ArrayList contenant les objets Commande selectionnes
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectAllFromUser(Connection conn, int id) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE idUser="+id+";");
	        return getCommandes(result);

	    }
	    
	    
	    /**
	     * Ajoute un nouvel Article au panier, sous les conditions suivantes :
	     * -S'il n'existe pas de Commande 'brouillon' sur cet utilisateur, on va la créer (INSERT).
	     * -La quantité de l'article est connue à l'avance, mettre une valeur de 1 par défaut.
	     * -Pour une commande existante, si l'idImp de l'article est déjà présent, alors il suffit d'ajouter la quantité voulue à celle-ci (UPDATE).
	     * 
	     * @param conn
	     * @param idUser
	     * @param idImp
	     * @param idComm
	     * @param qte
	     * @throws SQLException 
	     */
	    public static void ajouterAuPanier(Connection conn, int idUser, int idImp, int idComm, int qte) throws SQLException {
	    	conn.setAutoCommit(false);
	    	
	    	Statement state = conn.createStatement();
	    	ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE idUser="+idUser+" AND statut='BROUILLON';");
	    	
	    	if (result.next()) { //Il existe déjà une commande
	    		result = state.executeQuery("SELECT * FROM Article WHERE idImp="+idImp+";");
	    		if (result.next()) { //Cet article existe déjà dans le panier
	    			state.executeUpdate("UPDATE Article SET qte= qte+"+qte+";");
	    		} else { //L'Article est à ajouter dans le panier
	    			try {
						ArticleDAO.insertArticleFromImpression(conn, idImp, idComm, qte);
					} catch (Exception e) {
						// EXCEPTION stock insuffisant !
						e.printStackTrace();
					}
	    		}

	    	} else { // Il n'existe pas encore de commande
	    		state.executeUpdate("INSERT INTO Commande VALUES("+idComm+", "+idUser+", 0, 'NULL', 'NULL', 'BROUILLON')");
	    	}
	    	
	    	conn.commit();
	    	
	    }
	    
	    
	    /**
	     * Retourne les objets Commande construits e partir d'un resultat de requete.
	     *
	     * @param result le ResultSet de la requete SQL
	     * @return ArrayList contenant les objets Agenda
	     * @throws SQLException
	     */
		public static ArrayList<Commande> getCommandes(ResultSet result) throws SQLException {
	        ArrayList<Commande> Commandes = new ArrayList<Commande>();

	        while (result.next()) {
	            Commandes.add(new Commande(
	                    result.getInt("idComm"),
	                    result.getInt("idUser"),
	                    result.getInt("idCodeP"),
	                    (Date) result.getObject("dateC"),
	                    (ModeLivraison) result.getObject("modeLivraison"),
	                    (Statut) result.getObject("statut")
	            ));
	        }
	        return Commandes;
		}

}
