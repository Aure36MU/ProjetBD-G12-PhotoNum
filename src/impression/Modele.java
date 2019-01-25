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

		StringBuilder invite = new StringBuilder("Choisissez un modèle d'impression : ");
		int index=1;
		for(Modele i : Modele.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > Modele.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return Modele.values()[choix-1].toString();
	}
}
