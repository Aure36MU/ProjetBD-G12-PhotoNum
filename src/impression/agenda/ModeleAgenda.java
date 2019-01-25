package src.impression.agenda;

import src.app.LectureClavier;

public enum ModeleAgenda {
	JOURNALIER,
	SEMAINIER;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un modèle d'Agenda : ");
		int index=1;
		for(ModeleAgenda i : ModeleAgenda.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > ModeleAgenda.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return ModeleAgenda.values()[choix-1].toString();
	}
}
