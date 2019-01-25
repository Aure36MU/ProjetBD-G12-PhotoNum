package src.impression;

import src.app.LectureClavier;

public enum Format {
	PETIT,
	MOYEN,
	GRAND;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un format d'impression : 1) PETIT ; 2) MOYEN ; 3) GRAND ");
		while (Format.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un format d'impression : 1) PETIT ; 2) MOYEN ; 3) GRAND ");
		}
		return Format.values()[choix-1].toString();
	}
}
