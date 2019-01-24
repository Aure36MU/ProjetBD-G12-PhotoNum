package src.impression.agenda;

import src.impression.agenda.ModeleAgenda;
import src.impression.Impression;

public class Agenda extends Impression {
	int idImp;
	ModeleAgenda modele;
	TypeAgenda type;

	public Agenda(int idimpression, ModeleAgenda modele , TypeAgenda type) {
		this.idImp = idimpression;
		this.modele = modele;
		this.type = type;
	}
	public ModeleAgenda getModele() {
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
	
	public TypeAgenda gettype() {
		return type;
	}
	public void setType(TypeAgenda type) {
		this.type = type;
	}
}
