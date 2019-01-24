package src.impression.tirage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.cadre.Cadre;

public class TirageDAO {

	public static void createTirage(Connection c, int idImp) throws SQLException {
		Statement stat= c.createStatement();
		stat.executeUpdate("insert into Tirage (idImp)  values ('"+idImp+"')");
	}
	
	public static ArrayList<Tirage> selectAll(Connection c) throws SQLException {
		Statement stat = c.createStatement();
		ResultSet result = stat.executeQuery("select * from Tirage");
		return TirageDAO.getTirages(result);
	}
	
	public static ArrayList<Tirage> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query = "select * from Tirage NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result = stat.executeQuery(query);
		return TirageDAO.getTirages(result);
	}
	
	public static ArrayList<Tirage> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Tirage') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp; and i.type='Tirage");
        return getTirages(result);
    }
	
	/**
	 * Ajoute un tirage dans la base.
	 * 
	 * @param id id impression
	 * @throws SQLException 
	 */
	public static void addTirage(Connection conn, int id) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO Tirage VALUES("+id+");");
		
	}
	
	
	/**
	 * updateTirage() : est inutile car il n'y a aucun attribut à modifier à part l'idImp
	 * 
	 */
	public static void updateTirage() {}
	
	
	/**
	 * Supprime un tirage d'un idImp donné de la base.
	 * 
	 * @param id id impression
	 * @throws SQLException 
	 */
	public static void deleteTirage(Connection conn, int id) throws SQLException {
		
		conn.setAutoCommit(true);

		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Tirage WHERE idImp="+id+";");
		
	}
	
	
	public static ArrayList<Tirage> getTirages(ResultSet result) {
		ArrayList<Tirage> Tirage = new ArrayList<Tirage>();
		try {
			while (result.next()) {
				Tirage.add(new Tirage(
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Tirage;
	}
}
