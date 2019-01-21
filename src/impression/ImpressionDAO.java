package src.impression;

import java.sql.*;
import java.util.ArrayList;

import src.impression.album.AlbumDAO;
import src.impression.cadre.CadreDAO;
import src.impression.calendrier.CalendrierDAO;
import src.impression.tirage.TirageDAO;

public class ImpressionDAO {
	
	public static int getHigherIdImp(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idImp) FROM Impression");
			return res.getInt(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void createImpression(Connection c, int idUser, Format format, Qualite qualite, int nbPages){
		try {
			Statement state = c.createStatement();
			state.executeUpdate("INSERT INTO Impression "
					+ "(idImp,qualite,format,idUser,nbPageTotal)"
					+ "VALUES ("+(getHigherIdImp(c)+1)+ ", " + qualite + ", " + format + ", " + idUser + ", " + nbPages + "); " );		
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Impression> selectAll(Connection c){
		ArrayList<Impression> tab = new ArrayList<Impression>();
		tab.addAll(CalendrierDAO.selectAll(c));
		tab.addAll(AgendaDAO.selectAll(c));
		tab.addAll(TirageDAO.selectAll(c));
		tab.addAll(CadreDAO.selectAll(c));
		tab.addAll(AlbumDAO.selectAll(c));
		return tab;
	}
	
	public static ArrayList<Impression> selectAllFromUser(Connection c,int idUser){
		ArrayList<Impression> tab = new ArrayList<Impression>();
		tab.addAll(CalendrierDAO.selectAllFromUser(c,idUser));
		tab.addAll(AgendaDAO.selectAllFromUser(c,idUser));
		tab.addAll(TirageDAO.selectAllFromUser(c,idUser));
		tab.addAll(CadreDAO.selectAllFromUser(c,idUser));
		tab.addAll(AlbumDAO.selectAllFromUser(c,idUser));
		return tab;
	}
	
	public static void changeQualityFrom(Connection c, Impression i,Qualite qualite){
		try {
			i.setQualite(qualite);
			Statement state = c.createStatement();
			state.executeUpdate("UPDATE Impression SET qualite = '"+qualite+"' "
								+ "WHERE idImp = '" + i.getIdImp()+"';");
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
								+ "WHERE idImp = '" + i.getIdImp()+"';");
			
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
								+ "WHERE idImp = '" + i.getIdImp()+"';");
		} catch (SQLException e) {
			System.out.println("update failed");
			e.printStackTrace();
		}
	}
	
	
}
