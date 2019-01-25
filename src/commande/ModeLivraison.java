package src.commande;

import src.app.LectureClavier;

public enum ModeLivraison {
	DOMICILE,
	RELAIS_COLIS;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un mode de livraison : 1) DOMICILE ; 2) RELAIS_COLIS ");
		while (ModeLivraison.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un mode de livraison : 1) DOMICILE ; 2) RELAIS_COLIS ");
		}
		return ModeLivraison.values()[choix-1].toString();
	}
}
