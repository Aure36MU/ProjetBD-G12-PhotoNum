package src.impression.agenda;

import src.app.LectureClavier;

public enum Ornement {
	CHATONS,
	FLEURS,
	NATURE,
	BASIQUE;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un ornement d'agenda : 1) CHATONS ; 2) FLEURS ; 3) NATURE ; 4) BASIQUE ");
		while (Ornement.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un ornement d'agenda : 1) CHATONS ; 2) FLEURS ; 3) NATURE ; 4) BASIQUE ");
		}
		return Ornement.values()[choix-1].toString();
	}
}
