package src.compte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.app.Affichage;
import src.app.LectureClavier;

public class UtilisateurDAO {
	
	public static Utilisateur createUtilisateur(Connection c, String nom, String prenom, String mdp, String mail, String statut) throws SQLException {
		Statement stat= c.createStatement();
		c.setAutoCommit(false);
		try {
		String query= "insert into Utilisateur ( nom , prenom, mdp , email, active, statutUtilisateur) values ('"+nom+"','"+prenom+"','"+mdp+"','"+mail+"', 1,'"+statut+"')";
		stat.executeUpdate(query);
		ResultSet result = stat.executeQuery("select idUser from Utilisateur where email = '"+mail+"' and nom = '"+nom+"'");
		result.next();
		int id = result.getInt("idUser");
		c.commit();
		return new Utilisateur(id, nom, prenom, mdp, mail, 1, StatutUtilisateur.valueOf(statut));
		}catch (Exception e) {
			System.out.println("EXCEPTION LANCE PAR LE TRIGGER ET CAPTURE PAR L APPLICATION :impossible d ajouter car cet adresse mail est deja utilise");
			System.out.println("Veuillez recommencer");
		}
		return null;
	}
	
	public static ArrayList<Utilisateur> selectAll(Connection c) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur";
		ResultSet result =stat.executeQuery(query);
		return getUtilisateurs(result);
	}
	
	public static ArrayList<Utilisateur> selectAllUserFromStatut(Connection c, StatutUtilisateur statut) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where statutUtilisateur='"+statut+"'";
		ResultSet result =stat.executeQuery(query);
		return getUtilisateurs(result);
	}

	public static Boolean idExists(Connection c, int idUser) throws SQLException {
		Statement stat= c.createStatement();
		ResultSet result =stat.executeQuery( "select count(*) from Utilisateur where idUser='"+idUser+"'");
		if(result.next()) {
			return result.getInt(1)==1;
		}
		return false;
	}
	
	public static ArrayList<Utilisateur> selectWithCondition(Connection c, String condition) throws SQLException {
		Statement stat= c.createStatement();
		String query= "select * from Utilisateur where "+condition;
		ResultSet result =stat.executeQuery(query);
		return getUtilisateurs(result);
	}
	
	public static void activerDesactiverUtilisateur(Connection c, int id) throws SQLException {
		Statement stat= c.createStatement();
		String query= "update Utilisateur set active=1-active where idUser = '"+id+"'";
		stat.executeUpdate(query);
	}
	
	public static void updateUtilisateur(Connection c, int idUtilisateur, String nom, String prenom, String mdp, String email, String statut) throws SQLException {
		Statement stat= c.createStatement();
		try {
		String query= "update Utilisateur set nom='"+nom+"',prenom='"+prenom+"',mdp='"+mdp+"',email='"+email+"', statutUtilisateur='"+statut+"' where idUser='"+idUtilisateur+"'";
		stat.executeUpdate(query);
		}catch (Exception e) {
			System.out.println("EXCEPTION LANCE PAR LE TRIGGER ET CAPTURE PAR L APPLICATION :impossible d ajouter car cet adresse mail est deja utilise");
		}
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
					result.getInt("active"),
					StatutUtilisateur.valueOf(result.getString("statutUtilisateur"))
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return utilisateurs;
	}


	public static void gererClients(Connection c) throws SQLException {
		c.setAutoCommit(false);
		new Affichage<Utilisateur>().afficher(selectWithCondition(c, "statutUtilisateur = 'CLIENT'"));
		int idUser =  -2;
		while(!idExists(c,idUser)){
			idUser = LectureClavier.lireEntier("Pour selectionner un client, entrez son idUser (dans la liste  ci-dessus ou -1 pour annuler).");
			if(idUser==-1) {
				c.setAutoCommit(true);
				return;
			}
		} 
		activerDesactiverUtilisateur(c, idUser);
		c.setAutoCommit(true);
	}
}
