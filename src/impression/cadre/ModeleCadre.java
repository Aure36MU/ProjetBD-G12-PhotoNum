package src.impression.cadre;

import src.app.LectureClavier;

public enum ModeleCadre {
	BOIS,
	ALUMINIUM,
	CARBONE;
	
	public static String definir() {
		
		int choix = LectureClavier.lireEntier("Choisissez un modèle de cadre : 1) BOIS ; 2) ALUMINIUM ; 3) CARBONE ");
		while (ModeleCadre.values()[choix-1] == null) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. Choisissez un modèle de cadre : 1) BOIS ; 2) ALUMINIUM ; 3) CARBONE ");
		}
		return ModeleCadre.values()[choix-1].toString();
	}
}
