package src.commande;

import src.app.LectureClavier;

public enum ModeLivraison {
	DOMICILE,
	RELAIS_COLIS;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un mode de livraison : ");
		int index=1;
		for(ModeLivraison i : ModeLivraison.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > ModeLivraison.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return ModeLivraison.values()[choix-1].toString();
	}
}
