package src.commande;


public class Article {

	int idArt;
	int prix;
	int qte;
	
	public Article(int idArt, int prix, int qte) {
		this.idArt = idArt;
		this.prix = prix;
		this.qte = qte;
	}
	public int getIdArt() {
		return idArt;
	}

	public void setIdArt(int idArt) {
		this.idArt = idArt;
	}
	public int getprix() {
		return prix;
	}

	public void setprix(int prix) {
		this.prix = prix;
	}
	public int getqte() {
		return qte;
	}

	public void setqte(int qte) {
		this.qte = qte;
	}
}
