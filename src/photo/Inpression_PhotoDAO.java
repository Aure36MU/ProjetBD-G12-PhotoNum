package src.photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.commande.Catalogue;

public class Inpression_PhotoDAO {

	public static void insert(Connection conn, int idPh, int idImp, int num, String text, int nbEx) throws SQLException {
		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO Impression_Photo VALUES("+idPh+","+idImp+","+num+", '"+text+"', "+nbEx+")");
	}
	
	public static ArrayList<Page> selectAll(Connection conn) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression_Photo");
		return getImpression_Photo(result);
	}
	
	public static ArrayList<Page> selectAll(Connection conn, String condition) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression_Photo WHERE "+ condition);
		return getImpression_Photo(result);
	}
	
	public static ArrayList<Page> selectAllUser(Connection conn, int idPh, int idImp) throws SQLException {
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Impression_Photo WHERE idImp="+idImp+" and idPh="+idPh);
		return getImpression_Photo(result);
	}
	
	public static void updateImpPhoto(Connection conn, int idPh, int idImp, String text, int num_page, int nbExemplaire) throws SQLException {
		PreparedStatement state = conn.prepareStatement("UPDATE Impression_Photo SET (text=?, num_page=?, nbExemplaire=?) WHERE idPh=? and idImp=?");
		state.setString(1, text);
		state.setInt(2, num_page);
		state.setInt(2, nbExemplaire);
		state.setInt(4, idPh);
		state.setInt(5, idImp);
		state.executeUpdate();
	}
	
	public static void deleteId(Connection conn, int idImp, int idPh) throws SQLException {
		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Impression_Photo WHERE idPh="+idPh+" and idImp="+ idImp);
	}
	
	public static void deletePage(Connection conn, int idImp, int num) throws SQLException {
		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Impression_Photo WHERE num_page="+num+" and idImp="+ idImp);
	}
	
	public static void deleteAll(Connection conn, int id) throws SQLException {
		Statement state = conn.createStatement();
		state.executeUpdate("DELETE FROM Impression_Photo WHERE idImp="+id);
	}
	
	public static ArrayList<Page> getImpression_Photo(ResultSet result) throws SQLException {
        ArrayList<Page> Impression_Photos = new ArrayList<Page>();

        while (result.next()) {
            Impression_Photos.add(new Page(
                    result.getInt("idPh"),
                    result.getInt("idImp"),
                    result.getInt("num_page"),
                    result.getString("text"),
                    result.getInt("nbExemplaire")
            ));
        }
        return Impression_Photos;
	}
}
