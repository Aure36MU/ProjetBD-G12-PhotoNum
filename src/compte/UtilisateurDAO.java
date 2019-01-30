package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UtilisateurDAO {
	
	public static int getHigherId(Connection c){
		try {
			Statement state = c.createStatement();
			ResultSet res = state.executeQuery("SELECT max(idUser) FROM Utilisateur");
			if (res.next()) {
				return res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Utilisateur createUtilisateur(Connection c, String nom, String prenom, String mdp, String mail, String statut) throws SQLException {
		Statement stat= c.createStatement();
		int id = (getHigherId(c)+1);
		String query= "insert into Utilisateur (idUser, nom , prenom, mdp , email, active, statut) values ("+id+",'"+nom+"','"+prenom+"','"+mdp+"','"+mail+"', 1,'"+statut+"')";
		stat.executeUpdate(query);
		return new Utilisateur(id, nom, prenom, mdp, mail, true,StatutUtilisateur.valueOf(statut));
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
				StatutUtilisateur.valueOf(result.getString("statut"))
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
				StatutUtilisateur.valueOf(result.getString("statut"))
			);
		}else{
			return null;
		}
	}
	*/
	
	public static ArrayList<Utilisateur> selectAllUserFromStatut(Connection c, StatutUtilisateur statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where statut='"+statut+"';";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}

	public static Boolean idExists(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from Utilisateur where idUser='"+idUser+"';");
		return result.getInt(0)==1;
	}
	
	public static ArrayList<Utilisateur> selectWithCondition(Connection c, String condition) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where "+condition+";";
		ResultSet result =stat.executeQuery(query);
		return UtilisateurDAO.getUtilisateurs(result);
	}
	
	public static void deleteUtilisateur(Connection c, String mail) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active='"+false+"' where email='"+mail+"' ;";
		stat.executeUpdate(query);
	}
	
	public static void deleteUtilisateur(Connection c, int id) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active='"+false+"' where idUser = '"+id+"' ;";
		stat.executeUpdate(query);
	}
	
	public static void updateUtilisateur(Connection c, int idUtilisateur, String nom, String prenom, String mdp, String email, String statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set nom='"+nom+"',prenom='"+prenom+"',mdp='"+mdp+"',email='"+email+"', statut='"+statut+"' where idUser='"+idUtilisateur+"';";
		stat.executeUpdate(query);
	}
	
	public static ArrayList<Utilisateur> getUtilisateurs(ResultSet result) {
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try {
			while (result.next()) {
				utilisateurs.add(new Utilisateur(
					result.getInt("idUser"),
					result.getString("nom"),
					result.getString("prenom"),
					result.getString("mdp"),
					result.getString("email"),
					result.getBoolean("active"),
					StatutUtilisateur.valueOf(result.getString("statut"))
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return utilisateurs;
	}
}
