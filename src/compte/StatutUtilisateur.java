package src.compte;

import src.app.LectureClavier;

public enum StatutUtilisateur {
	GESTIONNAIRE,
	CLIENT;
	
		
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un statut utilisateur : ");
		int index=1;
		for(StatutUtilisateur i : StatutUtilisateur.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > StatutUtilisateur.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return StatutUtilisateur.values()[choix-1].toString();
	}
}
