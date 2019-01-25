package src.impression.agenda;

import src.app.LectureClavier;

public enum Ornement {
	CHATONS,
	FLEURS,
	NATURE,
	BASIQUE;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un ornement d'Agenda : ");
		int index=1;
		for(Ornement i : Ornement.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > Ornement.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return Ornement.values()[choix-1].toString();
	}
}
