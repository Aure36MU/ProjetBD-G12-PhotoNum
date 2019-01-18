package src.impression.album;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import src.compte.Statut;

public class AlbumDAO {
	public static void createAlbum(Connection c, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into Album (photoCouv , titreCouv) values ('"+photo+"','"+titre+"')";
		stat.executeUpdate(query);
	}
	
	public static void selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album";
		stat.executeUpdate(query);
	}
	
	public static void selectAllFromUser(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album NATURAL JOIN Impression where idUser='"+idUser+"' ";
		stat.executeUpdate(query);
	}
	
	public static void deleteAlbum(Connection c, int idi) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'Album' where 'idImp'='"+idi+"' ";
		stat.executeUpdate(query);
	}
	
	public static void updateAlbum(Connection c, int idi, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Album set photoCouv='"+photo+"' ,titreCouv='"+titre+"' where idImp='"+idi+"'";
		stat.executeUpdate(query);
	}
}
