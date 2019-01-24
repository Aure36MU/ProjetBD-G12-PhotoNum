package src.impression.agenda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AgendaDAO {

    /**
     * S�lectionne tous les Agendas (quels que soient leurs mod�les) sans conditions.
     *
     * @param conn Connection SQL
     * @return ArrayList contenant tous les objets Agenda
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda;");
        return getAgendas(result);
    }

    /**
     * S�lectionne tous les Agendas avec des conditions param�tr�es.
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Agenda s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn, String condition) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda WHERE "+condition+";");
        return getAgendas(result);
    }

    /**
     * S�lectionne tous les Agendas cr��s par un certain utilisateur.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Agenda s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAllFromUser(Connection conn, int id) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda JOIN Impression ON (Agenda.idImp = Impression.idImp) WHERE Impression.idUser="+id+";");
        return getAgendas(result);
    }
    
    /**
     * S�lectionne tous les Agendas cr��s par un certain utilisateur.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Agenda s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAllFromUserWait(Connection conn, int id) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("(SELECT * FROM Impression i WHERE i.idUser="+id+" and i.type='Agenda') MINUS (SELECT * FROM Article NATURAL JOIN Impression I a WHERE a.idImp=i.idImp;");
        return getAgendas(result);
    }
	/**
	 * Ajoute un agenda dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */

	/*public static void addAgenda(Connection conn, int id, ModeleAgenda modele, TypeAgenda type) throws SQLException {*/

	public static void insertAgenda(Connection conn, int id, ModeleAgenda modele, TypeAgenda type) throws SQLException {
		

		conn.setAutoCommit(true);
		Statement state = conn.createStatement();
		state.executeUpdate("INSERT INTO agenda VALUES("+id+", '"+modele.toString()+"');");
	}
	
	
	/**
	 * Modifie un agenda d'un idImp donn� dans la base.
	 * 
	 * @param id id impression
	 * @param modele modele
	 * @throws SQLException 
	 */
	public static void updateAgenda(Connection conn, int id, ModeleAgenda modele ,TypeAgenda type) throws SQLException {
		conn.setAutoCommit(true);
		Statement state = conn.createStatement();
		state.executeUpdate("UPDATE agenda SET modele='"+modele.toString()+"' WHERE idImp="+id+";");
	}

    /**
     * Supprime un Agenda d'un idImp donn� de la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void deleteAgenda(Connection conn, int id) throws SQLException {
        conn.setAutoCommit(true);
        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM Agenda WHERE idImp="+id+";");// on cascade
    }



    /**
     * Retourne les objets Agenda construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets Agenda
     * @throws SQLException
     */
    public static ArrayList<Agenda> getAgendas(ResultSet result) throws SQLException {
        ArrayList<Agenda> Agendas = new ArrayList<Agenda>();

        while (result.next()) {
            Agendas.add(new Agenda(
                    result.getInt("idImp"),
                    ModeleAgenda.valueOf(result.getString("modele")),
                    TypeAgenda.valueOf(result.getString("type"))
            ));
        }

        return Agendas; 
    } 
    
    
}
