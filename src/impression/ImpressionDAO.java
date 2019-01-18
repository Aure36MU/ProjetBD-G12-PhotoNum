package src.impression;

import java.sql.*;
import java.util.ArrayList;

import src.impression.calendrier.CalendrierDAO;

public class ImpressionDAO {
	/*static createImpression (params id qualite format ) {
		string query = “insert into ……..”
		executequery(query).... (voir jdbc exemple)
	}
	static select 1 impression (idImp)
	static select impressions utilisateurs (id User)
	static update()
	static delete from ()*/
	
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
					+ "(idImp,qualite,format,idUser,nbPages)"
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
					+ "WHERE idImp = " + i.getIdImp()++;
			
		} catch (SQLException e) {
			System.out.println("creation failed");
			e.printStackTrace();
		}
	}
	
	
}
