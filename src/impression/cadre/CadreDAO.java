package src.impression.cadre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CadreDAO {
	public static void createCadre(Connection c, ModeleCadre modele, int idImp) throws SQLException {
		//verifier si l'idImp existe dans la table IMPRESSION ?
		Statement stat= c.createStatement();
		stat.executeUpdate("insert into Cadre (modele,idImp) values ('"+modele+"', "+idImp+")");
	}
	
	public static ArrayList<Cadre> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery("select * from Cadre");
		return CadreDAO.getCadres(result);
	}
	
	public static ArrayList<Cadre> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Cadre NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result =stat.executeQuery(query);
		return CadreDAO.getCadres(result);
	}
	
	public static ArrayList<Cadre> getCadres(ResultSet result) {
		ArrayList<Cadre> cadre = new ArrayList<Cadre>();
		try {
			while (result.next()) {
				cadre.add(new Cadre(
					(ModeleCadre)result.getObject("modele"),
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cadre;
	}
	
	
}
