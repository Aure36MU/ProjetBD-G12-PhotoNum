package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArticleDAO {
	
	/**
	 * S�lectionne tous les Articles (quels que soient leurs mod�les) sans conditions.
	 *
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Article
	 * @throws SQLException
	 */

	public static ArrayList<Article> selectAll(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article;");
        
        return getArticles(result);

    }
	
    /**
     * S�lectionne toutes les Articles  avec des conditions param�tres.
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Article> selectAll(Connection conn, String condition) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE "+condition+";");
        return getArticles(result);

    }
    
    /**
     * S�lectionne toutes les Articles  envoy�e  : represente archivage des Articles d�j� faites
     * Partie de la base exportable dans une zone de stockage
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Article> selectEnvoyer(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE statut='envoy�';");
        return getArticles(result);

    }

    /**
     * S�lectionne tous les Articles cr��s d'une commande.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Article s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Article> selectAllFromCommande(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE idComm="+id+";");
        return getArticles(result);

    }
    
    /**
     * S�lectionne tous les Articles du panier en cours. Renvoie null si non existant.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Article s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Article> selectAllFromPanier(Connection conn, int idUser) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article a INNER JOIN Commande c ON (a.idComm = c.idComm) "
        									+ "WHERE c.statut = 'BROUILLON' AND c.idUser = '" +idUser+ "');");
        return getArticles(result);

    }
    
    
    
    
    
    /**
     * Retourne les objets Article construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets Agenda
     * @throws SQLException
     */
	public static ArrayList<Article> getArticles(ResultSet result) throws SQLException {
        ArrayList<Article> Articles = new ArrayList<Article>();

        while (result.next()) {
            Articles.add(new Article(
                    result.getInt("idArt"),
                    result.getInt("prix"),
                    result.getInt("qte"),
                    result.getInt("idImp")
            ));
        }
        return Articles;
	}
	public void AjoutAuPanier (Connection conn,int idIM) throws SQLException {
		
	}
}
