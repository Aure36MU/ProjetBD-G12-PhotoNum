package src.commande;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.compte.Utilisateur;

public class CatalogueDAO {

    /**
     * Selectionne toutes les Catalogues  avec des conditions parametres.
     *
     * @param conn Connection SQL
     * @param condition chaine de caracteres formate comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Catalogue selectionnes
     * @throws SQLException
     */
    public static ArrayList<Catalogue> selectAll(Connection conn, String condition) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Catalogue WHERE "+condition);
        return getCatalogues(result);
    }
	/**
	 * Selectionne tous les Catalogues (quels que soient leurs modeles) sans conditions.
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
		ArrayList<Catalogue> Catalogues= selectAll(c,"type='"+type+"'and format='"+format+"' and modele='"+modele+"'");
		System.out.println(" l ancien prix de : "+format+" "+type+" "+modele+" etait de "+Catalogues.get(0).prix);
		String query= "update Catalogue set prix='"+prix+"' where type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
		Catalogues= selectAll(c,"type='"+type+"'and format='"+format+"' and modele='"+modele+"'");
		System.out.println(" le nouveau prix de : "+format+" "+type+" "+modele+" est de "+Catalogues.get(0).prix);
	}
    
    public static void updateCatalogueQte(Connection c, int qte, String type, String format, String modele) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Catalogue set qteStock='"+qte+"type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
		ArrayList<Catalogue> Catalogues= selectAll(c,"type='"+type+"'and format='"+format+"' and modele='"+modele+"'");
		System.out.println(" le stock de : "+format+" "+type+"  "+modele+" est de "+Catalogues.get(0).qteStock);
	}
    public static void simulerLivraison(Connection c, int qteLivraison, String type, String format, String modele) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Catalogue set qteStock= qteStock + "+qteLivraison+" where type='"+type+"'and format='"+format+"' and modele='"+modele+"'";
		stat.executeUpdate(query);
		ArrayList<Catalogue> Catalogues= selectAll(c,"type='"+type+"'and format='"+format+"' and modele='"+modele+"'");
		System.out.println(" le stock de : "+format+" "+type+"  "+modele+" est de "+Catalogues.get(0).qteStock);
	}
    
    public static Article verifierStockPanier(Connection c, Utilisateur utilisateur) throws SQLException{
    	ArrayList<Article> panier = ArticleDAO.selectAllFromPanier(c, utilisateur.getIdUser());
    	int i = 0; Article a;
    	boolean stockInsuffisant = false;
    	
    	while (i<panier.size() && !stockInsuffisant){
    		 a = panier.get(i);			
			Statement state = c.createStatement();
			String query= "SELECT i.type FROM Article a, Impression i where a.idImp = i.idImp and i.idUser = '"+utilisateur.getIdUser()+"'"; 
			ResultSet result = state.executeQuery(query);
			String type = result.getString(1);
			
			query = "SELECT count(*) FROM Article a, Catalogue cat, Impression i, ";
			String where = "WHERE a.idArt = '"+a.getIdArt()+"'"
					+ "a.qte > cat.qteStock"
					+ "cat.format+=i.format "
					+ "and cat.type=i.type "
					+ "and cat.modele";
			
			switch(type){
					case "AGENDA" : 			where += " = ag.modeleAgenda";
															query += " , Agenda ag "+where;
															break;
					case "CALENDRIER" : 
															where += " = cal.modeleCalendrier";
															query += ", Calendrier cal "+where;
															break;
					case "CADRE" : 
															where += " = cad.modeleCadre";
															query += ", Cadre cad" +where;
															break;
					default :							where += " = 'AUCUN'" ;
															query += where;
			}
			ResultSet result2 = state.executeQuery(query); 
			if(result2.next() && result2.getInt(1)!=0){
				stockInsuffisant = true;
			}
			i++;
    	}
    	if(i<panier.size()){
    		return null;
    	}else {
    		return panier.get(i);
    	}
    }
    
    /**
     * Retourne les objets Catalogue construits e partir d'un resultat de requete.
     *
     * @param result le ResultSet de la requete SQL
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

	public static int getNbVentes(Connection conn, Catalogue catalogue) throws SQLException {
		Statement state = conn.createStatement();
		String query = "SELECT sum(a.qte) FROM Commande c "
        		+ "JOIN Article a ON (c.idComm=a.idComm) "
        		+ "JOIN Impression i ON (a.idImp=i.idImp) ";
		String where = "WHERE "+catalogue.format+"=i.format and "
        											+catalogue.type+"=i.type and "
        											+ catalogue.modele;
		
		switch(catalogue.type){
				case "AGENDA" : 			where += " = Agenda.modeleAgenda";
														query += "NATURAL JOIN Agenda "+where;
														break;
				case "CALENDRIER" : 
														where += " = Calendrier.modeleCalendrier";
														query += "NATURAL JOIN Calendrier"+where;
														break;
				case "CADRE" : 
														where += " = Cadre.modeleCadre";
														query += "NATURAL JOIN Cadre" +where;
														break;
				default :							where += " = 'AUCUN'" ;
														query += where;
		}
		query +=  " AND c.statut<>'Brouillon'";
		ResultSet result = state.executeQuery(query);        		
        return result.getInt(1); //nbVentes
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
}
