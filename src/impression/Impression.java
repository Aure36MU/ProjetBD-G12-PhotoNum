package src.impression;

public class Impression {

	private int idImp;
	private String nomImp;
	private int nbPages;
	private int idUser;
	private Qualite qualite;
	private Type type;
	private Format format;
	
	public Impression() {
		
	}
	
	public Impression(int idImp, String nomImp, int nbPages, int idUser, Qualite qualite, Type type, Format format) {
		this.idImp = idImp;
		this.nomImp = nomImp;
		this.nbPages = nbPages;
		this.idUser = idUser;
		this.qualite = qualite;
		this.type = type;
		this.format = format;
	}
	
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
		return "id : "+idImp +" nomImp : "+nomImp+" type : "+type+" qualite : "+qualite+" format : "+format+" nbPages : "+nbPages;
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
	public void setNomImp(String nomImp) {
		this.nomImp = nomImp;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	
}