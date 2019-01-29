package src.compte;

public class Utilisateur {


	int idUser;
	String nom;
	String prenom;
	String mdp;
	String email;
	int active;
	StatutUtilisateur statut;
	
	public Utilisateur(int idutilisateur, String nom, String prenom, String motdepasse, String adressemail, int active, StatutUtilisateur statut) {
		this.idUser = idutilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.mdp = motdepasse;
		this.email = adressemail;
		this.active = active;
		this.statut=statut;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int isActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	public void setStatut(StatutUtilisateur statut) {
		this.statut = statut;
	}
	
	public StatutUtilisateur getStatut() {
		return statut;
	}

	public String toString(){
		return "idUser : "+idUser+"     nom : "+nom+"     prenom : "+prenom+"     mdp : "+mdp+"     email : "+email+"     actif : "+active+"     statut : "+statut.toString();
	}
	
}
