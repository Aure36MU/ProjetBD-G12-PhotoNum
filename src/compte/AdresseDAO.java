package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdresseDAO {

	public static void createAdresse(Connection c, String vil, int codep, String ru, String pays, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into adresse (rue , codePostal, ville , pays, idUser)  values ('"+vil+"','"+codep+"','"+ru+"','"+pays+"','"+idu+"')";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Adresse> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse";
		ResultSet result =stat.executeQuery(query);
		return AdresseDAO.getAdresses(result);
	}
	
	public static ArrayList<Adresse> selectAllFromUser(Connection c, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse where idUser='"+idu+"' ";
		ResultSet result =stat.executeQuery(query);
		return AdresseDAO.getAdresses(result);
	}
	
	public static ArrayList<Adresse> selectAllFromPointRelais(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Adresse where idUser=null";
		ResultSet result =stat.executeQuery(query);
		return AdresseDAO.getAdresses(result);
	}
	public static void delete(Connection c, int idAdresse) throws SQLException {
		Statement stat= c.createStatement();
		String query= "delete from 'Adresse' where 'idAdre'='"+idAdresse+"' ";
		stat.executeUpdate(query);
	}
	public static void update(Connection c, int idAdresse, String vil, int codep, String ru, String pays) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Adresse set ville='"+vil+"',codePostal='"+codep+"',rue='"+ru+"',pays='"+pays+"' where idAdre='"+idAdresse+"'";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Adresse> getAdresses(ResultSet result) {
		ArrayList<Adresse> Adresse = new ArrayList<Adresse>();
		try {
			while (result.next()) {
				Adresse.add(new Adresse(
					result.getInt("idAdre"),
					result.getString("ville"),
					result.getInt("codePostal"),
					result.getString("rue"),
					result.getString("pays"),
					result.getInt("idUser")
				));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return Adresse;
	}
}
