package src.photo;

public class Photo {


	int idPh;
	int idFichier;
	String retouche;
	
	public Photo(int idphoto, int idfichier, String retouche) {
		this.idPh = idphoto;
		this.idFichier = idfichier;
		this.retouche = retouche;
	}

	public int getIdPh() {
		return idPh;
	}

	public void setIdPh(int idPh) {
		this.idPh = idPh;
	}

	public int getIdFichier() {
		return idFichier;
	}

	public void setIdFichier(int idFichier) {
		this.idFichier = idFichier;
	}

	public String getRetouche() {
		return retouche;
	}

	public void setRetouche(String retouche) {
		this.retouche = retouche;
	}
	public String toString(){
		return "idPh : "+idPh+"     idFichier : "+idFichier+"     retouche : "+retouche;
	}

}
