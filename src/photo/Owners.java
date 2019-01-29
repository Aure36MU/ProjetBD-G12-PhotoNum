package src.photo;

import java.sql.Date;

public class Owners {

	int idFichier;
	String chemin;
	String infoPVue;
	int partager;
	Date dateUtilisation;
	String prenom;
	String nom;
	
	public Owners(int idFichier, String chemin, String infoPVue, int partager, Date dateUtilisation, String prenom,
			String nom) {
		super();
		this.idFichier = idFichier;
		this.chemin = chemin;
		this.infoPVue = infoPVue;
		this.partager = partager;
		this.dateUtilisation = dateUtilisation;
		this.prenom = prenom;
		this.nom = nom;
	}

	public int getIdFichier() {
		return idFichier;
	}

	public void setIdFichier(int idFichier) {
		this.idFichier = idFichier;
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

	public int getPartager() {
		return partager;
	}

	public void setPartager(int partager) {
		this.partager = partager;
	}

	public Date getDateUtilisation() {
		return dateUtilisation;
	}

	public void setDateUtilisation(Date dateUtilisation) {
		this.dateUtilisation = dateUtilisation;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String toString(){
		return "idFichier : "+idFichier+"     chemin : "+chemin+"     commentaire : "+infoPVue+"     partage : "+partager+"     dateUtilisation : "+dateUtilisation+"     prenom : "+prenom+"     nom : "+nom;
	}
	
	
}
