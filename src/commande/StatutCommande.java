package src.commande;

import src.app.LectureClavier;

public enum StatutCommande {
	BROUILLON,
	EN_COURS,
	PRET_A_L_ENVOI,
	ENVOYEE,
	ANNULEE;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un statut de commande : ");
		int index=1;
		for(StatutCommande i : StatutCommande.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > StatutCommande.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return StatutCommande.values()[choix-1].toString();
	}
}
