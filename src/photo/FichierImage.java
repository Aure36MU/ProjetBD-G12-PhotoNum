package src.photo;

import java.sql.Connection;
import java.sql.Date;

public class FichierImage {
	
	int idFichier;
	int idUser;
	String chemin;
	String infoPVue;
	int pixelImg;
	int partage;
	Date dateUtilisation;
	int fileAttModif;
	int fileAttSuppr;
	
	public FichierImage(int idfichier, int idutilisateur, String chemin, String info, int pixels, int partage,
			Date dateutilisation, int attentemodification, int attentesuppression) {
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

	public int isPartage() {
		return partage;
	}

	public void setPartage(int partage) {
		this.partage = partage;
	}

	public Date getDateUtilisation() {
		return dateUtilisation;
	}

	public void setDateUtilisation(Date dateUtilisation) {
		this.dateUtilisation = dateUtilisation;
	}

	public int isFileAttModif() {
		return fileAttModif;
	}

	public void setFileAttModif(int fileAttModif) {
		this.fileAttModif = fileAttModif;
	}

	public int isFileAttSuppr() {
		return fileAttSuppr;
	}

	public void setFileAttSuppr(int fileAttSuppr) {
		this.fileAttSuppr = fileAttSuppr;
	}
	
	public String toString(){
		return "idFichier : "+idFichier+"     idUser : "+idUser+"     nombrePixels : "+pixelImg+"     chemin : "+chemin+"     infoPriseVue : "+infoPVue+"     partage : "+partage+"     dateUtilisation : "+dateUtilisation+"     fileModif : "+fileAttModif+"     fileSuppr : "+fileAttSuppr;
	}

}
