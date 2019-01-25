package src.impression;

import src.app.LectureClavier;

public enum Format {
	PETIT,
	MOYEN,
	GRAND;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un format d'impression : ");
		int index=1;
		for(Format i : Format.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > Format.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return Format.values()[choix-1].toString();
	}
}
