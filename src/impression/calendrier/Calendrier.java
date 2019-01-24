package src.impression.calendrier;

import src.impression.Impression;

public class Calendrier extends Impression {
	
	int idImp;
	ModeleCalendrier modele;
	
	public Calendrier(int idimpression, ModeleCalendrier modele) {
		this.idImp = idimpression;
		this.modele = modele;
	}

	public int getIdImp() {
		return idImp;
	}

	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}

	public ModeleCalendrier getModele() {
		return modele;
	}

	public void setModele(ModeleCalendrier modele) {
		this.modele = modele;
	}

}
