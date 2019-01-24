package src.impression.cadre;

import src.impression.Impression;
import src.impression.cadre.ModeleCadre;

public class Cadre extends Impression {
	ModeleCadre modele;
	int idImp;

	public Cadre(ModeleCadre modele, int idImp){
		this.modele = modele;
		this.idImp = idImp;
	}
	
	public ModeleCadre getModele() {
		return modele;
	}
	public void setModele(ModeleCadre modele) {
		this.modele = modele;
	}
	

	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
}
