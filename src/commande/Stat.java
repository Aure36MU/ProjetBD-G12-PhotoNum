package src.commande;

public class Stat {
	String type;
	String format;
	String modele;
	int nbVentes;

	public Stat(String type, String format, String modele,int nbVentes) {
		this.type = type;
		this.format = format;
		this.modele = modele;
		this.nbVentes = nbVentes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public void setNbVentes(int nbVentes) {
		this.nbVentes = nbVentes;
	}
	public void getNbVentes(int nbVentes) {
		this.nbVentes = nbVentes;
	}
	public String toString(){
		return "type : "+type+"     format : "+format+"     modele : "+modele+"     nbVentes : "+nbVentes;
	}
}
