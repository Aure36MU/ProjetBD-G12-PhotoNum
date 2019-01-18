package src.impression.album;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import src.compte.Statut;

public class AlbumDAO {
	static void createAlbum(Connection c, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into Album (photoCouv , titreCouv) values ('"+photo+"','"+titre+"')";
		stat.executeUpdate(query);
	}
	
	static void selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album";
		stat.executeUpdate(query);
	}
	
	static void selectAllFromAlbum(Connection c, int idi) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Album where idImp='"+idi+"' ";
		stat.executeUpdate(query);
	}
	
	static void deleteUtilisateur(Connection c, int idi) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'Album' where 'idImp'='"+idi+"' ";
		stat.executeUpdate(query);
	}
	
	static void updateUtilisateur(Connection c, int idi, int photo, String titre) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Album set photoCouv='"+photo+"' ,titreCouv='"+titre+"' where idImp='"+idi+"'";
		stat.executeUpdate(query);
	}
}
