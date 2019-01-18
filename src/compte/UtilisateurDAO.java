package src.compte;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilisateurDAO {
	static void createUtilisateur(Connection c, String nom, String prenom, String mdp, String email, Boolean active, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "insert into Utilisateur (nom , prenom, mdp , email, active, statut) values ('"+nom+"','"+prenom+"','"+mdp+"','"+email+"','"+active+"','"+statut+"')";
		stat.executeUpdate(query);
	}
	
	static void selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur";
		stat.executeUpdate(query);
	}
	
	static void selectAllFromUser(Connection c, int idu) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where idUser='"+idu+"' ";
		stat.executeUpdate(query);
	}
	
	static void selectAllUserFromStatut(Connection c, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where statut='"+statut+"'";
		stat.executeUpdate(query);
	}
	
	static void selectAllUserFromActive(Connection c, Boolean active) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where active='"+active+"'";
		stat.executeUpdate(query);
	}
	
	static void deleteUtilisateur(Connection c, int idUtilisateur) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active='"+false+"' where idUser='"+idUtilisateur+"' ";
		stat.executeUpdate(query);
	}
	
	static void updateUtilisateur(Connection c, int idUtilisateur, String nom, String prenom, String mdp, String email, Statut statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set nom='"+nom+"',prenom='"+prenom+"',mdp='"+mdp+"',email='"+email+"', statut='"+statut+"' where idUser='"+idUtilisateur+"'";
		stat.executeUpdate(query);
	}
}
