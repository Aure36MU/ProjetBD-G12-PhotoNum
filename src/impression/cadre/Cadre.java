package src.impression.cadre;

import src.impression.Impression;
import src.impression.cadre.ModeleCadre;

public class Cadre extends Impression {
	ModeleCadre modeleCadre;
	int idImp;

	public Cadre(ModeleCadre modeleCadre, int idImp){
		this.modeleCadre = modeleCadre;
		this.idImp = idImp;
	}
	
	public ModeleCadre getModeleCadre() {
		return modeleCadre;
	}
	public void setModeleCadre(ModeleCadre modeleCadre) {
		this.modeleCadre = modeleCadre;
	}
	

	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
}
