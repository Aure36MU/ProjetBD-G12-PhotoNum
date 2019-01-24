package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CatalogueDAO {

	/**
	 * Sélectionne tous les Catalogues (quels que soient leurs modèles) sans conditions.
	 *
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Catalogue
	 * @throws SQLException
	 */

	public static ArrayList<Catalogue> selectAll(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Catalogue;");
        
        return getCatalogues(result);

    }
	public static ArrayList<Catalogue> selectStats(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT type,format,modele,(select count FROM Catalogue;");
        
        return getStat(result);

    }
	
    /**
     * Sélectionne toutes les Catalogues  avec des conditions paramètres.
     *
     * @param conn Connection SQL
     * @param condition chaï¿½ne de caractï¿½res formatï¿½ comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Catalogue sï¿½lectionnï¿½s
     * @throws SQLException
     */
    public static ArrayList<Catalogue> selectAll(Connection conn, String condition) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Catalogue WHERE "+condition+";");
        return getCatalogues(result);

    }
    
    /**
     * Retourne les objets Catalogue construits ï¿½ partir d'un rï¿½sultat de requï¿½te.
     *
     * @param result le ResultSet de la requï¿½te SQL
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
                    result.getInt("qteStock")
            ));
        }
        return Catalogues;
	}
	//a modifier
	public static ArrayList<Catalogue> getStat(ResultSet result) throws SQLException {
        ArrayList<Catalogue> Catalogues = new ArrayList<Catalogue>();

        while (result.next()) {
            Catalogues.add(new Catalogue(
                    result.getString("type"),
                    result.getInt("prix"),
                    result.getString("format"),
                    result.getString("modele"),
                    result.getInt("qteStock")
            ));
        }
        return Catalogues;
	}
}
