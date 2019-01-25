package src.compte;

import src.app.LectureClavier;

public enum StatutUtilisateur {
	GESTIONNAIRE,
	CLIENT;
	
		
	public static String definir() {
			
		int choix = LectureClavier.lireEntier("Choisissez un statut utilisateur : 1) GESTIONNAIRE ; 2) CLIENT ");
		while (StatutUtilisateur.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un statut utilisateur : 1) GESTIONNAIRE ; 2) CLIENT ");
		}
		return StatutUtilisateur.values()[choix-1].toString();
	}
}
