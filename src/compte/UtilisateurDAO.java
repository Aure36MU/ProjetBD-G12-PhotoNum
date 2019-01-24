package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.impression.album.Album;

public class UtilisateurDAO {
	
	public static int getHigherId(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idUser) FROM Utilisateur");
			return res.getInt(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Utilisateur createUtilisateur(Connection c, String nom, String prenom, String mdp, String mail, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		int id = (getHigherId(c)+1);
		String query= "insert into Utilisateur (idUser, nom , prenom, mdp , mail, active, statut) values ('"+id+"','"+nom+"','"+prenom+"','"+mdp+"','"+mail+"','"+true+"','"+statut+"')";
		stat.executeUpdate(query);
		return new Utilisateur(id, nom, prenom, mdp, mail, true,statut);
	}
	
	public static ArrayList<Utilisateur> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	/*
	public static Utilisateur selectAllFromUser(Connection c, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where idUser='"+idu+"' ";
		ResultSet result =stat.executeQuery(query);
		
		if(result.next()){	return new Utilisateur(
				result.getInt("idUser"),
				result.getString("nom"),
				result.getString("prenom"),
				result.getString("mdp"),
				result.getString("email"),
				result.getBoolean("active"),
				(Statut)result.getObject("statut")
			);
		}else{
			return null;
		}
	}
	
	public static Utilisateur selectUserCondition(Connection c, String condition) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where "+condition+";";
		ResultSet result =stat.executeQuery(query);
		if(result.next()){	return new Utilisateur(
				result.getInt("idUser"),
				result.getString("nom"),
				result.getString("prenom"),
				result.getString("mdp"),
				result.getString("email"),
				result.getBoolean("active"),
				(Statut)result.getObject("statut")
			);
		}else{
			return null;
		}
	}
	
	public static ArrayList<Utilisateur> selectAllUserFromStatut(Connection c, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where statut='"+statut+"'";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	*/
	public static ArrayList<Utilisateur> selectWithCondition(Connection c, String condition) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where "+condition+";";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static void deleteUtilisateur(Connection c, int mail) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active='"+false+"' where email='"+mail+"' ";
		stat.executeUpdate(query);
	}
	
	public static void updateUtilisateur(Connection c, int idUtilisateur, String nom, String prenom, String mdp, String email, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set nom='"+nom+"',prenom='"+prenom+"',mdp='"+mdp+"',email='"+email+"', statut='"+statut+"' where idUser='"+idUtilisateur+"'";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Utilisateur> getUtilisateurs(ResultSet result) {
		ArrayList<Utilisateur> Utilisateurs = new ArrayList<Utilisateur>();
		try {
			while (result.next()) {
				Utilisateurs.add(new Utilisateur(
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
			e.printStackTrace();
			return null;
		}
		return Utilisateurs;
	}
}
