package src.impression.calendrier;

import src.impression.Impression;

public class Calendrier extends Impression {
	
	int idImp;
	ModeleCalendrier modele;
	
	public Calendrier(int idImp, ModeleCalendrier modele) {
		super();
		this.idImp = idImp;
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
