package src.impression;

public abstract class Impression {
	private int idImp;
	private int nbrPages;
	private Qualite qualite;
	private Format format;
	
	public int getIdImp() {
		return idImp;
	}
	public void setIdImp(int idImp) {
		this.idImp = idImp;
	}
	public int getNbrPages() {
		return nbrPages;
	}
	public void setNbrPages(int nbrPages) {
		this.nbrPages = nbrPages;
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
	
	
}