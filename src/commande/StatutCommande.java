package src.commande;

import src.app.LectureClavier;

public enum StatutCommande {
	BROUILLON,
	EN_COURS,
	PRET_A_L_ENVOI,
	ENVOYEE;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un statut de commande : 1) BROUILLON ; 2) EN_COURS ; 3) PRET_A_L_ENVOI ; 4) ENVOYEE ");
		while (StatutCommande.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un statut de commande : 1) BROUILLON ; 2) EN_COURS ; 3) PRET_A_L_ENVOI ; 4) ENVOYEE ");
		}
		return StatutCommande.values()[choix-1].toString();
	}
}
