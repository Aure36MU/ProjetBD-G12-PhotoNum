package src.commande;

import java.util.Date;

public abstract class CodePromo {
	
	int idCodeP;
	Date dateAcqui;
	Date dateUtil;
	String code;
	int taux;
	int idUser;
	
	public CodePromo(int idcodepromo, Date dateacquisition, Date dateutilisation, String code, int taux, int idutilisateur) {
		this.idCodeP = idcodepromo;
		this.dateAcqui = dateacquisition;
		this.dateUtil = dateutilisation;
		this.code = code;
		this.taux = taux;
		this.idUser = idutilisateur;
	}
	
	public CodePromo() {
		
	}

	public int getIdCodeP() {
		return idCodeP;
	}

	public void setIdCodeP(int idCodeP) {
		this.idCodeP = idCodeP;
	}

	public Date getDateAcqui() {
		return dateAcqui;
	}

	public void setDateAcqui(Date dateAcqui) {
		this.dateAcqui = dateAcqui;
	}

	public Date getDateUtil() {
		return dateUtil;
	}

	public void setDateUtil(Date dateUtil) {
		this.dateUtil = dateUtil;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTaux() {
		return taux;
	}

	public void setTaux(int taux) {
		this.taux = taux;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	

}
