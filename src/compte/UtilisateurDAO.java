package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.album.Album;

public class UtilisateurDAO {
	public static void createUtilisateur(Connection c, String nom, String prenom, String mdp, String email, Boolean active, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into Utilisateur (nom , prenom, mdp , email, active, statut) values ('"+nom+"','"+prenom+"','"+mdp+"','"+email+"','"+active+"','"+statut+"')";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Utilisateur> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static ArrayList<Utilisateur> selectAllFromUser(Connection c, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where idUser='"+idu+"' ";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static ArrayList<Utilisateur> selectAllUserFromStatut(Connection c, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where statut='"+statut+"'";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static ArrayList<Utilisateur> selectAllUserFromActive(Connection c, Boolean active) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where active='"+active+"'";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static void deleteUtilisateur(Connection c, int idUtilisateur) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active='"+false+"' where idUser='"+idUtilisateur+"' ";
		stat.executeUpdate(query);
	}
	
	public static void updateUtilisateur(Connection c, int idUtilisateur, String nom, String prenom, String mdp, String email, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set nom='"+nom+"',prenom='"+prenom+"',mdp='"+mdp+"',email='"+email+"', statut='"+statut+"' where idUser='"+idUtilisateur+"'";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Utilisateur> getUtilisateurs(ResultSet result) {
		ArrayList<Utilisateur> Utilisateur = new ArrayList<Utilisateur>();
		try {
			while (result.next()) {
				Utilisateur.add(new Utilisateur(
					result.getInt("idUser"),
					result.getString("nom"),
					result.getString("prenom"),
					result.getString("mdp"),
					result.getString("email"),
					result.getBoolean("active"),
					(Statut)result.getObject("statut")
				));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return Utilisateur;
	}
}
