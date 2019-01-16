package src.impression;

public abstract class Impression {
	private int idImp;
	private int nbrPages;
	private EnumQualite qualite;
	private EnumFormat format;

	public enum EnumQualite{
		BASSE,
		MEDIUM,
		PREMIUM
	}
	public enum EnumFormat{
		PETIT,
		MOYEN,
		GRAND
	}
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
	public EnumQualite getQualite() {
		return qualite;
	}
	public void setQualite(EnumQualite qualite) {
		this.qualite = qualite;
	}
	public EnumFormat getFormat() {
		return format;
	}
	public void setFormat(EnumFormat format) {
		this.format = format;
	}
	
	
}