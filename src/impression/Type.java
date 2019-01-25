package src.impression;

import src.app.LectureClavier;

public enum Type {
	AGENDA,
	TIRAGE,
	CALENDRIER,
	ALBUM,
	CADRE;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un type d'impression : 1) AGENDA ; 2) TIRAGE ; 3) CALENDRIER ; 4) ALBUM ; 5) CADRE ");
		while (Type.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un type d'impression : 1) AGENDA ; 2) TIRAGE ; 3) CALENDRIER ; 4) ALBUM ; 5) CADRE ");
		}
		return Type.values()[choix-1].toString();
	}
}
