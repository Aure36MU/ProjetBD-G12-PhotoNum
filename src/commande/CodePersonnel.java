package src.commande;

import java.util.Date;

public class CodePersonnel extends CodePromo {

	int idCodeP;
	Date dateAcqui;
	Date dateUtil;
	String code;
	int taux;
	int idUser;
	
	public CodePersonnel(int idcodepromo, Date dateacquisition, Date dateutilisation, String code, int taux, int idutilisateur) {
		super(idcodepromo, dateacquisition, dateutilisation, code, taux, idutilisateur);
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
