package src.impression.album;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AlbumDAO {

	
	public static ArrayList<Album> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album";
		ResultSet result =stat.executeQuery(query);
		return AlbumDAO.getAlbums(result);
	}
	
	/**
	 * S�lectionne tous les albums avec des conditions param�tr�es.
	 * 
	 * @param conn Connection SQL
	 * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
	 * Exemple : "foo=1 AND bar='bar' AND truc<>42"
	 * @return ArrayList contenant les objets Calendrier s�lectionn�s
	 * @throws SQLException 
	 */
	public static ArrayList<Album> selectAll(Connection conn, String condition) throws SQLException {

		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery("SELECT * FROM Album WHERE "+condition);
		return getAlbums(result);

	}
	
	public static ArrayList<Album> selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album NATURAL JOIN Impression where idUser='"+idUser+"' ";
		ResultSet result =stat.executeQuery(query);
		return AlbumDAO.getAlbums(result);
	}

    public static ArrayList<Album> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Album') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp; and i.type='Album')");
        return getAlbums(result);
    }
	
	public static void insertAlbum(Connection c, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into Album (photoCouv , titreCouv) values ('"+photo+"','"+titre+"')";
		stat.executeUpdate(query);
	}

	
	public static void updateAlbum(Connection c, int idi, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Album set photoCouv='"+photo+"' ,titreCouv='"+titre+"' where idImp='"+idi+"'";
		stat.executeUpdate(query);
	}
	
	public static void deleteAlbum(Connection c, int idi) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from Album where idImp='"+idi+"' ";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Album> getAlbums(ResultSet result) {
		ArrayList<Album> albums = new ArrayList<Album>();
		try {
			while (result.next()) {
				albums.add(new Album(
					result.getInt("idImp"),
					result.getInt("photoCouv"),
					result.getString("titreCouv")
				));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return albums;
	}
}
