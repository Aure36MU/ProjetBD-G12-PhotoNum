package src.impression.agenda;

import src.impression.agenda.ModeleAgenda;
import src.impression.Impression;

public class Agenda extends Impression {
	int idImp;
	Ornement ornement;
	ModeleAgenda modeleAgenda;

	public Agenda(int idImp, Ornement ornement , ModeleAgenda modeleAgenda) {
		super();
		this.idImp = idImp;
		this.ornement = ornement;
		this.modeleAgenda = modeleAgenda;
	}

	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
	
	public Ornement getOrnement() {
		return ornement;
	}
	public void setOrnement(Ornement ornement) {
		this.ornement = ornement;
	}
	
	public ModeleAgenda getModeleAgenda() {
		return modeleAgenda;
	}
	public void setModeleAgenda(ModeleAgenda modeleAgenda) {
		this.modeleAgenda = modeleAgenda;
	}
}
