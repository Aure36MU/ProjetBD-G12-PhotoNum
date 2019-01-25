package src.impression.cadre;

import src.app.LectureClavier;

public enum ModeleCadre {
	BOIS,
	ALUMINIUM,
	CARBONE;
	
	public static String definir() {

		StringBuilder invite = new StringBuilder("Choisissez un modèle de Cadre : ");
		int index=1;
		for(ModeleCadre i : ModeleCadre.values()) {
			invite.append(index + ") " + i + " ; ");
			index += 1;
		}
		invite.setLength(invite.length()-2);
		int choix = LectureClavier.lireEntier(""+invite);
		while (choix > ModeleCadre.values().length || choix <= 0) {
			if (choix == 0) { return null; }
			choix = LectureClavier.lireEntier("Choix incorrect. "+invite);
		}
		return ModeleCadre.values()[choix-1].toString();
	}
}
