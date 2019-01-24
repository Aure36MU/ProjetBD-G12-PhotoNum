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
	 * Sï¿½lectionne tous les Articles (quels que soient leurs modï¿½les) sans conditions.
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
     * Sï¿½lectionne toutes les Articles  avec des conditions paramï¿½tres.
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
     * Sï¿½lectionne toutes les Articles  envoyï¿½e  : represente archivage des Articles dï¿½jï¿½ faites
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
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE statut='envoyï¿½';");
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
     * Sï¿½lectionne tous les Articles du panier en cours. Renvoie null si non existant.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Article sï¿½lectionnï¿½s
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
     * A partir d'un id impression, id commande et quantité,
     * retrouve les informations nécessaires sur l'Impression,
     * vérifie dans le Catalogue le prix de cette Impression et son stock,
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
    	 * Cela fera une requête à faire en moins dans la base !*/
    	switch (newImp.getType()) {
		case CALENDRIER:
			newModele = CalendrierDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModele().toString();
			break;
		case AGENDA:
			newModele = AgendaDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModele().toString();
			break;
		case CADRE:
			newModele = CadreDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModele().toString();
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
