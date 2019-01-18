package src.impression.tirage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TirageDAO {

	
	
	public static ArrayList<Tirage> getTirages(ResultSet result) {
		ArrayList<Tirage> Tirage = new ArrayList<Tirage>();
		try {
			while (result.next()) {
				Tirage.add(new Tirage(
					result.getInt("idImp")
				));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return Tirage;
	}
}
