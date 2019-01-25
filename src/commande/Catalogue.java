package src.commande;

public class Catalogue {

	String type;
	int prix;
	String format;
	String modele;
	String qualite;
	int qteStock;
	

	public Catalogue(String type, int prix, String format, String modele, String qualite, int qteStock) {
		this.type = type;
		this.prix = prix;
		this.format = format;
		this.modele = modele;
		this.qualite = qualite;
		this.qteStock = qteStock;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public String getQualite() {
		return qualite;
	}
	public void setQualite(String qualite) {
		this.qualite = qualite;
	}
	public int getQteStock() {
		return qteStock;
	}
	public void setQteStock(int qteStock) {
		this.qteStock = qteStock;
	}
	
	
}
