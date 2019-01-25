package src.impression;

import src.app.LectureClavier;

public enum Qualite {
	BASSE,
	MEDIUM,
	PREMIUM;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez une qualité d'impression : ");
		int index=1;
		for(Qualite i : Qualite.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > Qualite.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return Qualite.values()[choix-1].toString();
	}
}
