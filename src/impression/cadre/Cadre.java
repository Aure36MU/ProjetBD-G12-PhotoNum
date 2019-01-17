package src.impression.cadre;

import src.impression.Impression;

public class Cadre extends Impression {
	private EnumModele modele;
	public enum EnumModele{
		BOIS,
		ALUMINIUM,
		CARBONE
	}
	public EnumModele getModele() {
		return modele;
	}
	public void setModele(EnumModele modele) {
		this.modele = modele;
	}
}