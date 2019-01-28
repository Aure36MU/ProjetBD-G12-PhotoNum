package src.compte;

public class Adresse {
	
	int idAdre;
	String ville;
	int codePostal;
	String rue;
	String pays;
	int idUser;
	
	public Adresse(int idadresse, String ville, int codePostal, String rue, String pays, int idutilisateur) {
		
		this.idAdre = idadresse;
		this.ville = ville;
		this.codePostal = codePostal;
		this.rue = rue;
		this.pays = pays;
		this.idUser = idutilisateur;
	}

	public int getIdAdre() {
		return idAdre;
	}

	public void setIdAdre(int idAdre) {
		this.idAdre = idAdre;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public String toString(){
		return "idAdre : "+idAdre+"     ville : "+ville+"     codePostal : "+codePostal+"     rue : "+rue+"     pays : "+pays+"     idUser : "+idUser;
	}
	
	
}
