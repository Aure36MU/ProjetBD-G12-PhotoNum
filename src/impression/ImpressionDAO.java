package src.impression;

import java.sql.*;
import java.util.ArrayList;

import src.app.LectureClavier;
import src.compte.Utilisateur;
import src.impression.agenda.Agenda;
import src.impression.agenda.AgendaDAO;
import src.impression.album.Album;
import src.impression.album.AlbumDAO;
import src.impression.cadre.Cadre;
import src.impression.cadre.CadreDAO;

import src.impression.calendrier.CalendrierDAO;
import src.impression.tirage.Tirage;
import src.impression.tirage.TirageDAO;
import src.photo.FichierImage;
import src.impression.calendrier.Calendrier;
import src.photo.Photo;

public class ImpressionDAO {
	
	public static int getHigherIdImp(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idImp) FROM Impression");
			if (res.next()) {
				return res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static ArrayList<Impression> selectAll(Connection c) throws SQLException{
		ArrayList<Impression> tab = new ArrayList<Impression>();
		tab.addAll(CalendrierDAO.selectAll(c));
		tab.addAll(AgendaDAO.selectAll(c));
		tab.addAll(TirageDAO.selectAll(c));
		tab.addAll(CadreDAO.selectAll(c));
		tab.addAll(AlbumDAO.selectAll(c));
		return tab;
	}
	
	public static ArrayList<Impression> selectAllFromType(Connection c, String type) throws SQLException{
		ArrayList<Impression> tab = new ArrayList<Impression>();
		switch(type) {
		case "CALENDRIER":		tab.addAll(CalendrierDAO.selectAll(c));		break;
		case "AGENDA":			tab.addAll(AgendaDAO.selectAll(c));			break;
		case "TIRAGE":				tab.addAll(TirageDAO.selectAll(c));				break;
		case "CADRE":				tab.addAll(CadreDAO.selectAll(c));				break;
		case "ALBUM":				tab.addAll(AlbumDAO.selectAll(c));				break;				
		}
		return tab;
	}
	
	public static Impression selectSousImpressionFromId(Connection conn, int id) throws SQLException{

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression WHERE idImp="+id);
        if (result.next()) {
        	switch (result.getString("type")) {
			case "CALENDRIER":		return CalendrierDAO.selectAll(conn, "idImp='"+id+"'").get(0);
			case "AGENDA":			return AgendaDAO.selectAll(conn, "idImp='"+id+"'").get(0);
			case "TIRAGE":				return TirageDAO.selectAll(conn, "idImp='"+id+"'").get(0);
			case "CADRE":				return CadreDAO.selectAll(conn, "idImp='"+id+"'").get(0);
			case "ALBUM":				return AlbumDAO.selectAll(conn, "idImp='"+id+"'").get(0);
			}
        }
		return null;
	}
	
	public static Impression selectImpressionFromId(Connection conn, int id) throws SQLException{
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression WHERE idImp="+id);
		if(result.next()){
			return new Impression(
					result.getInt("idImp"),
					result.getString("nomImp"),
					result.getInt("nbrPageTotal"),
					result.getInt("idUser"),
					Qualite.valueOf(result.getString("qualite")),
					Type.valueOf(result.getString("type")),
					Format.valueOf(result.getString("format")));
		}
		return null;
	}
	
	public static ArrayList<Impression> selectAllFromUser(Connection c,int idUser) throws SQLException{
		ArrayList<Impression> tab = new ArrayList<Impression>();
		tab.addAll(CalendrierDAO.selectAllFromUser(c,idUser));
		tab.addAll(AgendaDAO.selectAllFromUser(c,idUser));
		tab.addAll(TirageDAO.selectAllFromUser(c,idUser));
		tab.addAll(CadreDAO.selectAllFromUser(c,idUser));
		tab.addAll(AlbumDAO.selectAllFromUser(c,idUser));
		return tab;
	}
	
	public static ArrayList<Impression> selectAllFromUserImpressionNotArticle(Connection c,int idUser) throws SQLException{
		ArrayList<Impression> tab = new ArrayList<Impression>();
		tab.addAll(CalendrierDAO.selectAllFromUserNotArticle(c,idUser));
		tab.addAll(AgendaDAO.selectAllFromUserNotArticle(c,idUser));
		tab.addAll(TirageDAO.selectAllFromUserNotArticle(c,idUser));
		tab.addAll(CadreDAO.selectAllFromUserNotArticle(c,idUser));
		tab.addAll(AlbumDAO.selectAllFromUserNotArticle(c,idUser));
		return tab;
	}
	
	public static ArrayList<FichierImage> selectAllFichierImages(Connection c,int id) throws SQLException{
		ArrayList<FichierImage> tab = new ArrayList<FichierImage>();
		Statement state = c.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression NATURAL JOIN Impression_Photo NATURAL JOIN Photo NATURAL JOIN FichierImage WHERE Impression.idImp="+id);
		
		while (result.next()) {
			tab.add(new FichierImage(
					result.getInt("idFichier"),
					result.getInt("idUser"),
					result.getString("chemin"),
					result.getString("infoPVue"),
					result.getInt("pixelImg"),
					result.getInt("partage"),
					result.getDate("dateUtilisation"),
					result.getInt("fileAttModif"),
					result.getInt("fileAttSuppr")
					));
		}		
		return tab;
	}
	
	
	public static ArrayList<Photo> selectAllPhotos(Connection c,int id) throws SQLException{
		ArrayList<Photo> tab = new ArrayList<Photo>();
		Statement state = c.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression NATURAL JOIN Impression_Photo NATURAL JOIN Photo NATURAL JOIN FichierImage WHERE Impression.idImp="+id);
		while (result.next()) {
			tab.add(new Photo(
					result.getInt("idPhoto"),
					result.getInt("idFichier"),
					result.getString("retouche")
					));
		}
		return tab;
	}

	

	/**
	 * Ajoute une impression dans la base.
	 * 
	 * @param c
	 * @param nomImp
	 * @param nbPages
	 * @param idUser
	 * @param type
	 * @param format
	 * @param qualite
	 * @throws SQLException
	 */
	public static void insertImpression(Connection conn, String nomImp, int nbPages, int idUser, String type, String format, String qualite, String modele) throws SQLException {
		Statement state = conn.createStatement();
		int newId = getHigherIdImp(conn)+1;
		state.executeUpdate("INSERT INTO Impression "
				+ "(idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)"
				+ "VALUES ("+newId+ ", '" + nomImp + "', " + nbPages + ", " + idUser + ", '" + type + "', '" + format + "', '" + qualite + "')");
		switch(type) {
			case "CALENDRIER":		CalendrierDAO.insertCalendrier(conn, newId, modele);		break;
			case "CADRE":				CadreDAO.insertCadre(conn, newId, modele);						break;
		}
	}
	

	public static void insertImpression(Connection conn, String nomImp, int nbPages, int idUser, String type, String format, String qualite, String modele, String ornement) throws SQLException {
		Statement state = conn.createStatement();
		int newId = getHigherIdImp(conn)+1;
		state.executeUpdate("INSERT INTO Impression "
				+ "(idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)"
				+ "VALUES ("+newId+ ", '" + nomImp + "', " + nbPages + ", " + idUser + ", '" + type + "', '" + format + "', '" + qualite + "')");
			AgendaDAO.insertAgenda(conn, newId, ornement, modele);
			System.out.println("L'impression a l'identifiant :" + (getHigherIdImp(conn)+1));
	}
	
	public static void insertImpression(Connection conn, String nomImp, int nbPages, int idUser, String type, String format, String qualite) throws SQLException {
		Statement state = conn.createStatement();
		int newId = getHigherIdImp(conn)+1;
		state.executeUpdate("INSERT INTO Impression "
				+ "(idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)"
				+ "VALUES ("+newId+ ", '" + nomImp + "', " + nbPages + ", " + idUser + ", '" + type + "', '" + format + "', '" + qualite + "')");
		TirageDAO.insertTirage(conn, newId);
		System.out.println("L'impression a l'identifiant :" + (getHigherIdImp(conn)+1));
	}
	
	public static void insertImpression(Connection conn, String nomImp, int nbPages, int idUser, String type, String format, String qualite, int photo, String titre) throws SQLException {
		Statement state = conn.createStatement();
		int newId = getHigherIdImp(conn)+1;
		state.executeUpdate("INSERT INTO Impression "
				+ "(idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)"
				+ "VALUES ("+newId+ ", '" + nomImp + "', " + nbPages + ", " + idUser + ", '" + type + "', '" + format + "', '" + qualite + "')");
		AlbumDAO.insertAlbum(conn, newId, photo, titre);
		System.out.println("L'impression a l'identifiant :" + (getHigherIdImp(conn)+1));
	}
	
	/**
	 * Modifie une Impression d'un idImp donné dans la base.
	 * 
	 * @param conn
	 * @param nomImp
	 * @param nbPages
	 * @param idUser
	 * @param type
	 * @param format
	 * @param qualite
	 * @throws SQLException
	 */
	
	public static void updateImpression(Connection conn, int idImp, String nomImp, int nbPages, int idUser, String type, String format, String qualite) throws SQLException {
		Statement state = conn.createStatement();
		state.executeUpdate("UPDATE Impression SET nomImp='"+nomImp+"', nbrPageTotal="+nbPages+", idUser="+idUser+", type='"+type+"', format='"+format+"', qualite='"+qualite+"' WHERE idImp="+idImp);
		
	}

    /**
     * Supprime un Impression d'un idImp donné de la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void deleteImpression(Connection conn, int id) throws SQLException {
        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM Impression WHERE idImp="+id);
    }
	
	
	public static void changeTypeIfCompatible(Connection c, Impression from, String to) throws Exception {
		//Si le type de sortie 'to' est compatible avec celui en entrée, on exécute la requête INSERT sur le nouveau type d'Impression,
		//sinon on renvoie une Exception.
		try {
			if (to.equals("Agenda")) { 
				throw new Exception("Impossible de convertir du type +"+from.getClass().getName()+" vers "+to);
			} else if (to.equals("Album")) { 
				if (from instanceof Cadre)
					throw new Exception("Impossible de convertir du type +"+from.getClass().getName()+" vers "+to);
				String titre = LectureClavier.lireChaine("Quel titre d'album voulez-vous ?");
				AlbumDAO.insertAlbum(c, from.getIdImp(), selectAllPhotos(c, from.getIdImp()).get(0).getIdPh(), titre);
			} else if (to.equals("Cadre")) {
				String modeleCadre = LectureClavier.lireChaine("Quel modèle de cadre voulez-vous ?");
				CadreDAO.insertCadre(c, from.getIdImp(), modeleCadre);
			} else if (to.equals("Calendrier")) { 
				throw new Exception("Impossible de convertir du type +"+from.getClass().getName()+" vers "+to);
			} else if (to.equals("Tirage")) {  
				TirageDAO.insertTirage(c, from.getIdImp());
			} else { 
				throw new Exception("Type d'impression "+to+" non reconnu.");
			}
		} catch (SQLException e) {
			System.out.println("insert failed");
			e.printStackTrace();
		}
		
		//On exécute ensuite la requête DELETE de l'ancien type d'Impression.
		try {
			if (from instanceof Agenda) {
				AgendaDAO.deleteAgenda(c, from.getIdImp());
			} else if (from instanceof Album) {
				AlbumDAO.deleteAlbum(c, from.getIdImp());
			} else if (from instanceof Cadre) {
				CadreDAO.deleteCadre(c, from.getIdImp());
			} else if (from instanceof Calendrier) {
				CalendrierDAO.deleteCalendrier(c, from.getIdImp());
			} else if (from instanceof Tirage) {
				TirageDAO.deleteTirage(c, from.getIdImp());
			}
		} catch (SQLException e) {
			System.out.println("delete failed");
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void changeQualityFrom(Connection c, Impression i,Qualite qualite){
		try {
			i.setQualite(qualite);
			Statement state = c.createStatement();
			state.executeUpdate("UPDATE Impression SET qualite = '"+qualite+"' "
								+ "WHERE idImp = '" + i.getIdImp()+"'");
		} catch (SQLException e) {
			System.out.println("update failed");
			e.printStackTrace();
		}
	}
	public static void changeFormatFrom(Connection c, Impression i,Format format){
		try {
			i.setFormat(format);
			Statement state = c.createStatement();
			state.executeUpdate("UPDATE Impression SET format = '"+format+"' "
								+ "WHERE idImp = '" + i.getIdImp()+"'");
			
		} catch (SQLException e) {
			System.out.println("update failed");
			e.printStackTrace();
		}
	}
	public static void changeNbPagesFrom(Connection c, Impression i, int nb){
		try {
			i.setNbPages(nb);
			Statement state = c.createStatement();
			state.executeUpdate("UPDATE Impression SET nbPages = '"+nb+"' "
								+ "WHERE idImp = '" + i.getIdImp()+"'");
		} catch (SQLException e) {
			System.out.println("update failed");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Impression> getImpressions(ResultSet result) throws SQLException {
        ArrayList<Impression> impressions = new ArrayList<Impression>();

        while (result.next()) {
        	impressions.add(new Impression(
                    result.getInt("idImp"),
                    result.getString("nomImp"),
                    result.getInt("nbPages"),
                    result.getInt("idUser"),
                    Qualite.valueOf(result.getString("qualite")),
                    Type.valueOf(result.getString("type")),
                    Format.valueOf(result.getString("format"))
            ));
        }
        return impressions;
	}
	public static void gererDeleteImp(Connection c, Utilisateur utilisateur) throws SQLException {
		int impSupp=LectureClavier.lireEntier("Quel impression voulez vous supprimer?");
		ArrayList <Impression> tabImp = selectAllFromUser(c, utilisateur.getIdUser());
		int i=0;	while(i<tabImp.size() && tabImp.get(i).getIdImp()!=impSupp) {i++;}
		
		if(i<tabImp.size()) {
			boolean sur=LectureClavier.lireOuiNon("Vous êtes sur de vouloir supprimer:" + impSupp + "?");
			if(sur) {deleteImpression(c, impSupp);}
		} else {	System.out.println("Vous n'avez pas selectionner une impression que vous controler");	}
	}
	
	
}
