package src.impression.agenda;

import src.impression.agenda.ModeleAgenda;
import src.impression.Impression;

public abstract class Agenda extends Impression {
	int idImp;
	ModeleAgenda modele;

	public Agenda(int idimpression, ModeleAgenda modele) {
		this.idImp = idimpression;
		this.modele = modele;
	}
	public ModeleAgenda getmodele() {
		return modele;
	}
	public void setModele(ModeleAgenda modele) {
		this.modele = modele;
	}


	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
}
