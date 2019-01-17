package src.impression.agenda;

import src.impression.Impression;

public abstract class Agenda extends Impression {
	private Modele modele;

	public Modele getModele() {
		return modele;
	}
	public void setModele(Modele modele) {
		this.modele = modele;
	}
}
