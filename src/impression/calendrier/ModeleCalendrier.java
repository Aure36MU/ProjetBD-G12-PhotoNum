package src.impression.calendrier;

import src.app.LectureClavier;

public enum ModeleCalendrier {
	BUREAU,
	MURAL;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un modèle de Calendrier : ");
		int index=1;
		for(ModeleCalendrier i : ModeleCalendrier.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > ModeleCalendrier.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return ModeleCalendrier.values()[choix-1].toString();
	}
}
