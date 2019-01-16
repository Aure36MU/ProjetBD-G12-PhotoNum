package src.commande;

import java.util.Date;

public class Commande {
	
	int idComm;
	int idUser;
	int idCodeP;
	Date dateC;
	ModeLivraison modeLivraison;
	Statut statut;
	
	public Commande(int idcommande, int idutilisateur, int idcodepromo, Date datecommande, ModeLivraison modelivraison, Statut statut) {
		this.idComm = idcommande;
		this.idUser = idutilisateur;
		this.idCodeP = idcodepromo;
		this.dateC = datecommande;
		this.modeLivraison = modelivraison;
		this.statut = statut;
	}

	public int getIdComm() {
		return idComm;
	}

	public void setIdComm(int idComm) {
		this.idComm = idComm;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdCodeP() {
		return idCodeP;
	}

	public void setIdCodeP(int idCodeP) {
		this.idCodeP = idCodeP;
	}

	public Date getDateC() {
		return dateC;
	}

	public void setDateC(Date dateC) {
		this.dateC = dateC;
	}

	public ModeLivraison getModeLivraison() {
		return modeLivraison;
	}

	public void setModeLivraison(ModeLivraison modeLivraison) {
		this.modeLivraison = modeLivraison;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

}
