package src.impression.album;

import src.impression.Impression;

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
	
	public int getPhotoCouv() {
		return photoCouv;
	}

	public void setPhotoCouv(int PhotoCouv) {
		this.photoCouv = PhotoCouv;
	}
}
