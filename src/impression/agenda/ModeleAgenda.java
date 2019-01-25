package src.impression.agenda;

import src.app.LectureClavier;

public enum ModeleAgenda {
	JOURNALIER,
	SEMAINIER;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un mod�le d'agenda : 1) JOURNALIER ; 2) SEMAINIER ");
		while (ModeleAgenda.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un mod�le d'agenda : 1) JOURNALIER ; 2) SEMAINIER ");
		}
		return ModeleAgenda.values()[choix-1].toString();
	}
}
