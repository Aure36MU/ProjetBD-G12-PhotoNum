package src.impression.agenda;

public class AgendaJournalierDAO {

    /**
     * S�lectionne tous les AgendaJournaliers (quels que soient leurs mod�les) sans conditions.
     *
     * @param conn Connection SQL
     * @return ArrayList contenant tous les objets AgendaJournalier
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda;");
        return getAgendas(result);


    }

    /**
     * S�lectionne tous les AgendaJournaliers avec des conditions param�tr�es.
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets AgendaJournalier s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn, String condition) throws SQLException {


        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda WHERE "+condition+";");
        return getAgendas(result);


    }

    /**
     * S�lectionne tous les AgendaJournaliers cr��s par un certain utilisateur.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets AgendaJournalier s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAllFromUser(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda JOIN Impression ON (AgendaJournalier.idImp = Impression.idImp) WHERE Impression.idUser="+id+";");
        return getAgendas(result);


    }


    /**
     * Ajoute un AgendaJournalier dans la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void addAgendaJournalier(Connection conn, int id, ModeleAgendaJournalier modele) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("INSERT INTO AgendaJournalier VALUES("+id+", '"+modele.toString()+"');");

    }


    /**
     * Modifie un AgendaJournalier d'un idImp donn� dans la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void updateAgendaJournalier(Connection conn, int id, ModeleAgendaJournalier modele) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("UPDATE AgendaJournalier SET modele='"+modele.toString()+"' WHERE idImp="+id+";");

    }


    /**
     * Supprime un AgendaJournalier d'un idImp donn� de la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void deleteAgendaJournalier(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM AgendaJournalier WHERE idImp="+id+";");// on cascade

    }



    /**
     * Retourne les objets AgendaJournalier construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets AgendaJournalier
     * @throws SQLException
     */
    public static ArrayList<AgendaJournalier> getAgendaJournaliers(ResultSet result) throws SQLException {
        ArrayList<AgendaJournalier> AgendaJournaliers = new ArrayList<AgendaJournalier>();

        while (result.next()) {
            AgendaJournaliers.add(new AgendaJournalier(
                    result.getInt("idImp"),
                    (ModeleAgenda) result.getObject("modele")
            ));
        }

        return AgendaJournaliers;
    }
}
