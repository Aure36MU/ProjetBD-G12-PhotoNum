package src.photo;

import java.sql.Connection;
import java.sql.Date;

public class FichierImage {
	
	int idFichier;
	int idUser;
	String chemin;
	String infoPVue;
	int pixelImg;
	boolean partage;
	Date dateUtilisation;
	boolean fileAttModif;
	boolean fileAttSuppr;
	
	public FichierImage(int idfichier, int idutilisateur, String chemin, String info, int pixels, boolean partage,
			Date dateutilisation, boolean attentemodification, boolean attentesuppression) {
		this.idFichier = idfichier;
		this.idUser = idutilisateur;
		this.chemin = chemin;
		this.infoPVue = info;
		this.pixelImg = pixels;
		this.partage = partage;
		this.dateUtilisation = dateutilisation;
		this.fileAttModif = attentemodification;
		this.fileAttSuppr = attentesuppression;
	}

	public int getIdFichier() {
		return idFichier;
	}

	public void setIdFichier(int idFichier) {
		this.idFichier = idFichier;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public String getInfoPVue() {
		return infoPVue;
	}

	public void setInfoPVue(String infoPVue) {
		this.infoPVue = infoPVue;
	}

	public int getPixelImg() {
		return pixelImg;
	}

	public void setPixelImg(int pixelImg) {
		this.pixelImg = pixelImg;
	}

	public boolean isPartage() {
		return partage;
	}

	public void setPartage(boolean partage) {
		this.partage = partage;
	}

	public Date getDateUtilisation() {
		return dateUtilisation;
	}

	public void setDateUtilisation(Date dateUtilisation) {
		this.dateUtilisation = dateUtilisation;
	}

	public boolean isFileAttModif() {
		return fileAttModif;
	}

	public void setFileAttModif(boolean fileAttModif) {
		this.fileAttModif = fileAttModif;
	}

	public boolean isFileAttSuppr() {
		return fileAttSuppr;
	}

	public void setFileAttSuppr(boolean fileAttSuppr) {
		this.fileAttSuppr = fileAttSuppr;
	}
	
	public String toString(){
		return "idFichier : "+idFichier+"     idUser : "+idUser+"     nombrePixels : "+pixelImg+"     chemin : "+chemin+"     infoPriseVue : "+infoPVue+"     partage : "+partage+"     dateUtilisation : "+dateUtilisation+"     fileModif : "+fileAttModif+"     fileSuppr : "+fileAttSuppr;
	}

}
