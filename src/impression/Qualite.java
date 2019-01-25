package src.impression;

import src.app.LectureClavier;

public enum Qualite {
	BASSE,
	MEDIUM,
	PREMIUM;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez une qualité d'impression : 1) BASSE ; 2) MEDIUM ; 3) PREMIUM ");
		while (Qualite.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez une qualité d'impression : 1) BASSE ; 2) MEDIUM ; 3) PREMIUM ");
		}
		return Qualite.values()[choix-1].toString();
	}
}
