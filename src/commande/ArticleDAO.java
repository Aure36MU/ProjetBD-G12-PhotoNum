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
import src.impression.album.Album;
import src.impression.album.AlbumDAO;
import src.impression.cadre.Cadre;
import src.impression.cadre.CadreDAO;
import src.impression.calendrier.Calendrier;
import src.impression.calendrier.CalendrierDAO;
import src.impression.tirage.Tirage;
import src.impression.tirage.TirageDAO;

public class ArticleDAO {
	
	
	public static int getHigherIdArt(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idArt) FROM Article");
			return res.getInt(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/**
	 * S�lectionne tous les Articles (quels que soient leurs mod�les) sans conditions.
	 *
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Article
	 * @throws SQLException
	 */

	public static ArrayList<Article> selectAll(Connection conn) throws SQLException {

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

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE "+condition+";");
        return getArticles(result);

    }
    
    /**
     * S�lectionne toutes les Articles  envoy�e  : represente archivage des Articles d�j� faites
     * Partie de la base exportable dans une zone de stockage
     *
     * @param conn Connection SQL
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Article> selectEnvoyer(Connection conn) throws SQLException {

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article NATURAL JOIN Commande WHERE statutCommande='ENVOYE';");
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

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article a INNER JOIN Commande c ON (a.idComm = c.idComm) "
        									+ "WHERE c.statutCommande = 'BROUILLON' AND c.idUser = '" +idUser+ "');");
        return getArticles(result);

    }
    
    
    /**
     * A partir d'un id impression, id commande et quantit�,
     * retrouve les informations n�cessaires sur l'Impression,
     * v�rifie dans le Catalogue le prix de cette Impression et son stock,
     * puis ajoute un nouvel Article dans la base.
     * 
     * @param conn
     * @param idImp
     * @param idComm
     * @param qte
     * @throws Exception
     */
    public static void insertArticleFromImpression(Connection conn, int idImp, int idComm, int qte) throws Exception {
    	
    	Impression newImp = ImpressionDAO.selectImpressionFromId(conn, idImp);
    	String newModele = "NULL";
    	/* TODO modifier le switch sur newImp.type par une comparaison d'instances : Calendrier, Agenda, Cadre, [autre].
    	 * Cela fera une requ�te � faire en moins dans la base !*/
    	switch (newImp.getType()) {
		case CALENDRIER:
			newModele = CalendrierDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModele().toString();
			break;
		case AGENDA:
			newModele = AgendaDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModeleAgenda().toString();
			break;
		case CADRE:
			newModele = CadreDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModeleCadre().toString();
			break;
		default:
			break;
		}
    	
    	Catalogue artDuCatalogue = CatalogueDAO.selectAll(conn,
    			"type='"+newImp.getType().toString()
    			+"' AND format='"+newImp.getFormat().toString()
    			+"' AND modele='"+newModele+"'").get(0);
    	
    	if (artDuCatalogue.getQteStock() <= 0) {
    		throw new Exception("Not enough of this Article in stock !");
    	} else {

    		//Ajout nouvel Article dans la base
    		Statement state = conn.createStatement();
    		state.executeUpdate("INSERT INTO Article VALUES("+getHigherIdArt(conn)+", "+artDuCatalogue.getPrix()+", "+qte+", "+idImp+", "+idComm+");");

    	}

    	
    }
    
    
    
    
    
    /**
     * Retourne les objets Article construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets Agenda
     * @throws SQLException
     */
	public static ArrayList<Article> getArticles(ResultSet result) throws SQLException {
        ArrayList<Article> articles = new ArrayList<Article>();

        while (result.next()) {
            articles.add(new Article(
                    result.getInt("idArt"),
                    result.getInt("prix"),
                    result.getInt("qte"),
                    result.getInt("idImp")
            ));
        }
        return articles;
	}
	public void AjoutAuPanier (Connection conn,int idIM) throws SQLException {
		
	}
}
