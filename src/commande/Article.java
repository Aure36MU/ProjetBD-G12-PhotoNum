package src.commande;


public class Article {

	int idArt;
	int prix;
	int qte;
	int idImp;
	
	public Article(int idArt, int prix, int qte,int idImp) {
		this.idArt = idArt;
		this.prix = prix;
		this.qte = qte;
		this.idImp = idImp;
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
	public int getidImp() {
		return idImp;
	}

	public void setidImp(int idImp) {
		this.idImp = idImp;
	}
	
	public String toString(){
		return "idArt : "+idArt+"     prix : "+prix+"     qte : "+qte+"     idImp : "+idImp;
	}
}
