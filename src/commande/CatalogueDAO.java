package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CatalogueDAO {


	/*---------------------------------------------------------------------------------------------------
	public static ArrayList<Catalogue> selectStats(Connection conn) throws SQLException {

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT type,format,modele,(select count FROM Catalogue");
        
        return CatalogueDAO.getStat(result);

    }
	---------------------------------------------------------------------------------------------------*/
    /**
     * S�lectionne toutes les Catalogues  avec des conditions param�tres.
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Catalogue s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Catalogue> selectAll(Connection conn, String condition) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Catalogue WHERE "+condition);
        return getCatalogues(result);
    }
	/**
	 * S�lectionne tous les Catalogues (quels que soient leurs mod�les) sans conditions.
	 *
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Catalogue
	 * @throws SQLException
	 */

	public static ArrayList<Catalogue> selectAll(Connection conn) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Catalogue");
        return getCatalogues(result);
    }
    
    
    
    public static void updateCataloguePrix(Connection c, int prix, String type, String format, String modele) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Catalogue set prix='"+prix+"' where type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
	}
    
    public static void updateCatalogueQte(Connection c, int qte, String type, String format, String modele) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Catalogue set qteStock='"+qte+"' where type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
	}
    public static void simulerLivraison(Connection c, int qteLivraison, String type, String format, String modele) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Catalogue set qteStock= qteStock + "+qteLivraison+" where type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
	}
    
    /**
     * Retourne les objets Catalogue construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets Catalogue
     * @throws SQLException
     */
	public static ArrayList<Catalogue> getCatalogues(ResultSet result) throws SQLException {
        ArrayList<Catalogue> Catalogues = new ArrayList<Catalogue>();

        while (result.next()) {
            Catalogues.add(new Catalogue(
                    result.getString("type"),
                    result.getInt("prix"),
                    result.getString("format"),
                    result.getString("modele"),
                    result.getString("qualite"),
                    result.getInt("qteStock")
            ));
        }
        return Catalogues;
	}
	//a modifier
	/*---------------------------------------------------------------------------------------------------*/
	public static int getNbVentes(Connection conn, Catalogue catalogue) throws SQLException {
		int nbVentes = 0;
		Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT sum(a.qte) FROM Commande c "
        		+ "JOIN Article a ON (c.idComm=a.idComm) "
        		+ "JOIN Impression i ON (a.idImp=i.idImp) "
        		+ "NATURAL JOIN Agenda ag"
        		+ "WHERE "+catalogue.format+"=i.format and "+catalogue.modele+"=ag.modele and "
        		+catalogue.type+"=ag.type and c.statut<>'Brouillon' "
        				+ "UNION "
        				+ "SELECT sum(a.qte) FROM Commande c "
        				+ "JOIN Article a ON (c.idComm=a.idComm) "
        				+ "JOIN Impression i ON (a.idImp=i.idImp) "
        				+ "NATURAL JOIN Album al"
        				+ "WHERE "+catalogue.format+"=i.format and c.statut<>'Brouillon' "
        						+ "UNION "
        						+ "SELECT sum(a.qte) FROM Commande c "
        						+ "JOIN Article a ON (c.idComm=a.idComm) "
        						+ "JOIN Impression i ON (a.idImp=i.idImp) "
        						+ "NATURAL JOIN Tirage t"
        						+ "WHERE "+catalogue.format+"=i.format and c.statut<>'Brouillon' "
        								+ "UNION "
        								+ "SELECT sum(a.qte) FROM Commande c "
        								+ "JOIN Article a ON (c.idComm=a.idComm) "
        								+ "JOIN Impression i ON (a.idImp=i.idImp) "
        								+ "NATURAL JOIN Calendrier ca"
        								+ "WHERE "+catalogue.format+"=i.format and "+catalogue.modele+"=ca.modele and c.statut<>'Brouillon' "
        										+ "UNION "
        										+ "SELECT sum(a.qte) FROM Commande c "
        										+ "JOIN Article a ON (c.idComm=a.idComm) "
        										+ "JOIN Impression i ON (a.idImp=i.idImp) "
        										+ "NATURAL JOIN Cadre ca"
        										+ "WHERE "+catalogue.format+"=i.format and "+catalogue.modele+"=ca.modele and c.statut<>'Brouillon'; ");
        nbVentes=result.getInt(0);
        return nbVentes;
	}
	
	public static ArrayList<Stat> getStat(Connection conn, ArrayList<Catalogue> catalogues) throws SQLException {
	      ArrayList<Stat> stats = new ArrayList<Stat>();
	      Catalogue c;
	      	int i = 0;
	        while (i < catalogues.size()) {
	        	c = catalogues.get(i);
	            stats.add(new Stat(c.type, c.format, c.modele, getNbVentes(conn,c)));
	        	i++;
	        }
        return stats;
	}

	/*---------------------------------------------------------------------------------------------------*/
}
