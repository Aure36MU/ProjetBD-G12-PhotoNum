package src.impression;

import src.app.LectureClavier;

public enum Type {
	AGENDA,
	TIRAGE,
	CALENDRIER,
	ALBUM,
	CADRE;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un type d'impression : ");
		int index=1;
		for(Type i : Type.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > Type.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return Type.values()[choix-1].toString();
	}
}
