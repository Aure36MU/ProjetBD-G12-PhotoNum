package src.impression.agenda;

public class SemainierDAO {

    /**
     * S�lectionne tous les Semainiers (quels que soient leurs mod�les) sans conditions.
     *
     * @param conn Connection SQL
     * @return ArrayList contenant tous les objets Semainier
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda;");
        return getAgendas(result);


    }

    /**
     * S�lectionne tous les Semainiers avec des conditions param�tr�es.
     *
     * @param conn Connection SQL
     * @param condition cha�ne de caract�res format� comme suit : "condition1 {AND condition2}"
     * Exemple : "foo=1 AND bar='bar' AND truc<>42"
     * @return ArrayList contenant les objets Semainier s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAll(Connection conn, String condition) throws SQLException {


        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda WHERE "+condition+";");
        return getAgendas(result);


    }

    /**
     * S�lectionne tous les Semainiers cr��s par un certain utilisateur.
     *
     * @param conn Connection SQL
     * @param id id utilisateur
     * @return ArrayList contenant les objets Semainier s�lectionn�s
     * @throws SQLException
     */
    public static ArrayList<Agenda> selectAllFromUser(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Agenda JOIN Impression ON (Semainier.idImp = Impression.idImp) WHERE Impression.idUser="+id+";");
        return getAgendas(result);


    }


    /**
     * Ajoute un Semainier dans la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void addSemainier(Connection conn, int id, ModeleSemainier modele) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("INSERT INTO Semainier VALUES("+id+", '"+modele.toString()+"');");

    }


    /**
     * Modifie un Semainier d'un idImp donn� dans la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void updateSemainier(Connection conn, int id, ModeleSemainier modele) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("UPDATE Semainier SET modele='"+modele.toString()+"' WHERE idImp="+id+";");

    }


    /**
     * Supprime un Semainier d'un idImp donn� de la base.
     *
     * @param id id impression
     * @param modele modele
     * @throws SQLException
     */
    public static void deleteSemainier(Connection conn, int id) throws SQLException {

        conn.setAutoCommit(true);

        Statement state = conn.createStatement();
        state.executeUpdate("DELETE FROM Semainier WHERE idImp="+id+";");// on cascade

    }



    /**
     * Retourne les objets Semainier construits � partir d'un r�sultat de requ�te.
     *
     * @param result le ResultSet de la requ�te SQL
     * @return ArrayList contenant les objets Semainier
     * @throws SQLException
     */
    public static ArrayList<Semainier> getSemainiers(ResultSet result) throws SQLException {
        ArrayList<Semainier> Semainiers = new ArrayList<Semainier>();

        while (result.next()) {
            Semainiers.add(new Semainier(
                    result.getInt("idImp"),
                    (ModeleAgenda) result.getObject("modele")
            ));
        }

        return Semainiers;
    }

}
