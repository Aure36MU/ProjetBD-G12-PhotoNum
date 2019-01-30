package src.commande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.app.LectureClavier;
import src.impression.Impression;
import src.impression.ImpressionDAO;
import src.impression.agenda.AgendaDAO;
import src.impression.cadre.CadreDAO;
import src.impression.calendrier.CalendrierDAO;

public class ArticleDAO {
	
	
	/**
	 * Sï¿½lectionne tous les Articles (quels que soient leurs modï¿½les) sans conditions.
	 *
	 * @param conn Connection SQL
	 * @return ArrayList contenant tous les objets Article
	 * @throws SQLException
	 */

	public static ArrayList<Article> selectAll(Connection conn) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article");
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
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE "+condition);
        return getArticles(result);
    }
    
    /**
     * Sï¿½lectionne toutes les Articles  envoyï¿½e  : represente archivage des Articles dï¿½jï¿½ faites
     * Partie de la base exportable dans une zone de stockage
     *
     * @param conn Connection SQL
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Article sï¿½lectionnï¿½s
     * @throws SQLException
     */
    public static ArrayList<Article> selectEnvoyer(Connection conn) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article NATURAL JOIN Commande WHERE statutCommande='ENVOYE'");
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
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article WHERE idComm="+id);
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
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Article a INNER JOIN Commande c ON (a.idComm = c.idComm) "
        									+ "WHERE c.statutCommande = 'BROUILLON' AND c.idUser = " +idUser);
        return getArticles(result);
    }
    
	public static Boolean idExists(Connection c, int idArticle) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from Article where idArt='"+idArticle+"'");
		if (result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}
	
	public static void ModifierQuantite(Connection conn) throws SQLException {
		conn.setAutoCommit(false);
		int idArticle = -1;
		while(!idExists(conn,idArticle)){
			idArticle = LectureClavier.lireEntier("Pour selectionner un Article, entrez son idArticle (dans la liste présentée ci-dessus).");
		}
		int Qte = LectureClavier.lireEntier("Combien d'exemplaire voulez vous ?");
		PreparedStatement state = conn.prepareStatement("UPDATE Article SET (qte=?,) WHERE idArt=?");
		state.setInt(1, Qte);
		state.setInt(2, idArticle);
		state.executeUpdate();
		
		conn.commit();
		conn.setAutoCommit(true);
	}
	
	public static void SupprimerUnArticle(Connection conn) throws SQLException {
		conn.setAutoCommit(false);
		int idArticle = -1;
		while(!idExists(conn,idArticle)){
			idArticle = LectureClavier.lireEntier("Pour supprimer un Article, entrez son idArticle (dans la liste présentée ci-dessus).");
		}
		deleteArticle(conn, idArticle);
		conn.commit();
		conn.setAutoCommit(true);
	}
	
    
    
    private static void deleteArticle(Connection conn, int idArticle){
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("DELETE FROM Article WHERE idArt="+idArticle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * A partir d'un id impression, id commande et quantitï¿½,
     * retrouve les informations nï¿½cessaires sur l'Impression,
     * vï¿½rifie dans le Catalogue le prix de cette Impression et son stock,
     * puis ajoute un nouvel Article dans la base.
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
    	 * Cela fera une requï¿½te ï¿½ faire en moins dans la base !*/
    	switch (newImp.getType()) {
			case CALENDRIER:		newModele = CalendrierDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModeleCalendrier().toString();			break;
			case AGENDA:				newModele = AgendaDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModeleAgenda().toString();					break;
			case CADRE:					newModele = CadreDAO.selectAll(conn, "idImp='"+idImp+"'").get(0).getModeleCadre().toString();						break;
			default:	break;
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
    		state.executeUpdate("INSERT INTO Article VALUES("+artDuCatalogue.getPrix()+", "+qte+", "+idImp+", "+idComm+")");
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
}
