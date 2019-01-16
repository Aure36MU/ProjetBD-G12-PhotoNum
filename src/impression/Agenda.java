package src.impression;

public abstract class Agenda extends Impression {
	private EnumModele modele;
	public enum EnumModele{
		CHATONS,
		FLEURS,
		BASIQUE
	}
	public EnumModele getModele() {
		return modele;
	}
	public void setModele(EnumModele modele) {
		this.modele = modele;
	}
}
