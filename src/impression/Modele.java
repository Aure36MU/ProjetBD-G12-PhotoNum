package src.impression;

import src.app.LectureClavier;

public enum Modele {
	BUREAU,
	MURAL,
	BOIS,
	ALUMINIUM,
	CARBONE,
	JOURNALIER,
	SEMAINIER,
	AUCUN;
	
	public static String definir() {
		int choix = LectureClavier.lireEntier("Choisissez un modèle de calendrier : 1) BUREAU ; 2) MURAL ; 3) BOIS ; 4) ALUMINIUM ; 5) CARBONE ; 6) JOURNALIER ; 7) SEMAINIER ; 8) AUCUN");
		while (Modele.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un modèle de calendrier : 1) BUREAU ; 2) MURAL ; 3) BOIS ; 4) ALUMINIUM ; 5) CARBONE ; 6) JOURNALIER ; 7) SEMAINIER ; 8) AUCUN");
		}
		return Modele.values()[choix-1].toString();
	}
}
