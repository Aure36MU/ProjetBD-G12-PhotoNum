package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class CommandeDAO {
	
		/**
		 * Sélectionne tous les Commandes (quels que soient leurs modèles) sans conditions.
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
	     * Sélectionne toutes les commandes  avec des conditions paramètres.
	     *
	     * @param conn Connection SQL
	     * @param condition chaï¿½ne de caractï¿½res formatï¿½ comme suit : "condition1 {AND condition2}"
	     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	     * @return ArrayList contenant les objets commande sï¿½lectionnï¿½s
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectAll(Connection conn, String condition) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE "+condition+";");
	        return getCommandes(result);

	    }
	    
	    /**
	     * Sélectionne toutes les commandes  envoyée  : represente archivage des commandes déjà faites
	     * Partie de la base exportable dans une zone de stockage
	     *
	     * @param conn Connection SQL
	     * @param condition chaï¿½ne de caractï¿½res formatï¿½ comme suit : "condition1 {AND condition2}"
	     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	     * @return ArrayList contenant les objets commande sï¿½lectionnï¿½s
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectEnvoyer(Connection conn) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE statut='envoyé';");
	        return getCommandes(result);

	    }

	    /**
	     * Sï¿½lectionne tous les Commandes crï¿½ï¿½s par un certain utilisateur.
	     *
	     * @param conn Connection SQL
	     * @param id id utilisateur
	     * @return ArrayList contenant les objets Commande sï¿½lectionnï¿½s
	     * @throws SQLException
	     */
	    public static ArrayList<Commande> selectAllFromUser(Connection conn, int id) throws SQLException {

	        conn.setAutoCommit(true);

	        Statement state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM Commande WHERE idUser="+id+";");
	        return getCommandes(result);

	    }
	    /**
	     * Retourne les objets Commande construits ï¿½ partir d'un rï¿½sultat de requï¿½te.
	     *
	     * @param result le ResultSet de la requï¿½te SQL
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
