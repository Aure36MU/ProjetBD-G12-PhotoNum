package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArticleDAO {
	
	/**
	 * Sélectionne tous les Articles (quels que soient leurs modèles) sans conditions.
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
     * Sélectionne toutes les Articles  avec des conditions paramètres.
     *
     * @param conn Connection SQL
     * @param condition chaï¿½ne de caractï¿½res formatï¿½ comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article sï¿½lectionnï¿½s
     * @throws SQLException
     */
    public static ArrayList<Article> selectAll(Connection conn, String condition) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE "+condition+";");
        return getArticles(result);

    }
    
    /**
     * Sélectionne toutes les Articles  envoyée  : represente archivage des Articles déjà faites
     * Partie de la base exportable dans une zone de stockage
     *
     * @param conn Connection SQL
     * @param condition chaï¿½ne de caractï¿½res formatï¿½ comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article sï¿½lectionnï¿½s
     * @throws SQLException
     */
    public static ArrayList<Article> selectEnvoyer(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE statut='envoyé';");
        return getArticles(result);

    }

    /**
     * Sï¿½lectionne tous les Articles crï¿½ï¿½s d'une commande.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Article sï¿½lectionnï¿½s
     * @throws SQLException
     */
    public static ArrayList<Article> selectAllFromCommande(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE idComm="+id+";");
        return getArticles(result);

    }
    /**
     * Retourne les objets Article construits ï¿½ partir d'un rï¿½sultat de requï¿½te.
     *
     * @param result le ResultSet de la requï¿½te SQL
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
