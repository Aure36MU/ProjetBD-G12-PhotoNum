package src.photo;

public class Page {

	int idPh;
	int idImp;
	int num_page;
	String text;
	int nbExemplaire;
	
	public Page(int idphoto, int idImp, int num_page ,String text, int nbExemplaire) {
		this.idPh = idphoto;
		this.idImp = idImp;
		this.text = text;
		this.num_page=num_page;
		this.nbExemplaire=nbExemplaire;
	}

	public int getIdPh() {
		return idPh;
	}

	public void setIdPh(int idPh) {
		this.idPh = idPh;
	}

	public int getidImp() {
		return idImp;
	}

	public void setidImp(int idImp) {
		this.idImp = idImp;
	}

	public int getnum_page() {
		return num_page;
	}

	public void setnum_page(int num_page) {
		this.num_page = num_page;
	}
	public String gettext() {
		return text;
	}

	public int getnbExemplaire() {
		return nbExemplaire;
	}

	public void setnbExemplaire(int nbExemplaire) {
		this.nbExemplaire = nbExemplaire;
	}
	
	public void settext(String text) {
		this.text = text;
	}
	public String toString(){
		return "idPh : "+idPh+"     idImp : "+idImp+"     text : "+text+"     num_page : "+num_page+"     nbExemplaire : "+nbExemplaire;
	}
}
