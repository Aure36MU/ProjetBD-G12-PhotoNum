package src.impression.calendrier;

import src.app.LectureClavier;

public enum ModeleCalendrier {
	BUREAU,
	MURAL;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un modèle de calendrier : 1) BUREAU ; 2) MURAL ");
		while (ModeleCalendrier.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un modèle de calendrier : 1) BUREAU ; 2) MURAL ");
		}
		return ModeleCalendrier.values()[choix-1].toString();
	}
}
