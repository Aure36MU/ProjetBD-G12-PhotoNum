package src.impression.cadre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class CadreDAO {

	
	public static ArrayList<Cadre> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery("select * from Cadre");
		return CadreDAO.getCadres(result);
	}
	
	/**
	 * S�lectionne tous les cadres avec des conditions param�tr�es.
	 * 
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Calendrier s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Cadre> selectAll(Connection conn, String condition) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Cadre WHERE "+condition+";");
		return getCadres(result);

		
	}
	
	public static ArrayList<Cadre> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Cadre NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result =stat.executeQuery(query);
		return CadreDAO.getCadres(result);
	}
	
	public static ArrayList<Cadre> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Cadre') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp and i.type='Cadre'");
        return getCadres(result);
    }
	
	/**
	 * Ajoute un cadre dans la base.
	 * 
	 * @param id id impression
	 * @param modeleCadre modele
	 * @throws SQLException 
	 */
	public static void insertCadre(Connection conn, int id, String modeleCadre) throws SQLException {

		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO Cadre VALUES("+id+", '"+modeleCadre+"');");
		
	}
	
	
	/**
	 * Modifie un cadre d'un idImp donn� dans la base.
	 * 
	 * @param id id impression
	 * @param modeleCadre modele
	 * @throws SQLException 
	 */
	public static void updateCadre(Connection conn, int id, String modeleCadre) throws SQLException {

		Statement state = conn.createStatement();
		state.executeUpdate("UPDATE Cadre SET modeleCadre='"+modeleCadre+"' WHERE idImp="+id+";");
		
	}
	
	
	/**
	 * Supprime un cadre d'un idImp donn� de la base.
	 * 
	 * @param id id impression
	 * @throws SQLException 
	 */
	public static void deleteCadre(Connection conn, int id) throws SQLException {

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Cadre WHERE idImp="+id+";");
		
	}
	
	public static ArrayList<Cadre> getCadres(ResultSet result) {
		ArrayList<Cadre> cadres = new ArrayList<Cadre>();
		try {
			while (result.next()) {
				cadres.add(new Cadre(
					ModeleCadre.valueOf(result.getString("modele")),
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cadres;
	}

	
	
}
