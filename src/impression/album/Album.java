package src.impression.album;

import src.impression.Format;
import src.impression.Impression;
import src.impression.Qualite;

public class Album extends Impression {
	
	String titreCouv;
	int photoCouv;
	int idImp;

	public Album(int idImp, int photoCouv, String titreCouv) {
		this.titreCouv=titreCouv;
		this.photoCouv=photoCouv;
		this.idImp=idImp;
	}
	
	public String getTitreCouv() {
		return titreCouv;
	}

	public void setTitreCouv(String titreCouv) {
		this.titreCouv = titreCouv;
	}
	
}
