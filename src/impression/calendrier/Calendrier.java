package src.impression.calendrier;

import src.impression.Impression;

public class Calendrier extends Impression {
	
	int idImp;
	ModeleCalendrier modele;
	
	public Calendrier(int idimpression, ModeleCalendrier modele) {
		this.idImp = idimpression;
		this.modele = modele;
	}

}
