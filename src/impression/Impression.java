package src.impression;

public abstract class Impression {
	private int idImp;
	private String nomImp;
	private int nbPages;
	private int idUser;
	private Qualite qualite;
	private Format format;
	
	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
	public int getNbPages() {
		return nbPages;
	}
	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}
	public Qualite getQualite() {
		return qualite;
	}
	public void setQualite(Qualite qualite) {
		this.qualite = qualite;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	
	
	public String toString(){
		return "id : "+this.idImp +" qualite : "+this.qualite+" format : "+this.format+" nbPages : "+this.nbPages;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getNomImp() {
		return nomImp;
	}
	public void setNom(String nomImp) {
		this.nomImp = nomImp;
	}
	
	
}