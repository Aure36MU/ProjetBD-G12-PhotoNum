package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdresseDAO {

	static void createAdresse(Connection c, String vil, int codep, String ru, String pays, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into adresse (rue , codePostal, ville , pays, idUser)  values ('"+vil+"','"+codep+"','"+ru+"','"+pays+"','"+idu+"')";
		stat.executeUpdate(query);
	}
	
	static void selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse";
		stat.executeUpdate(query);
	}
	
	static void selectAllFromUser(Connection c, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse where idUser='"+idu+"' ";
		stat.executeUpdate(query);
	}
	
	static void selectAllFromPointRelais(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse where idUser=null";
		stat.executeUpdate(query);
	}
	static void delete(Connection c, int idAdresse) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'Adresse' where 'idAdre'='"+idAdresse+"' ";
		stat.executeUpdate(query);
	}
	static void update(Connection c, int idAdresse, String vil, int codep, String ru, String pays) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Adresse set ville='"+vil+"',codePostal='"+codep+"',rue='"+ru+"',pays='"+pays+"' where idAdre='"+idAdresse+"'";
		stat.executeUpdate(query);
	}
}
