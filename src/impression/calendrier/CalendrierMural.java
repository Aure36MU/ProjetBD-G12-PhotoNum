package src.impression.calendrier;

import src.impression.Format;
import src.impression.Qualite;

public class CalendrierMural extends Calendrier {

	int idImp;
	Qualite qualite;
	Format format;
	int idUser;
	int nbrPageTotal;
	
	public CalendrierMural(int idimpression, Qualite qualite, Format format, int idutilisateur, int nombrepagestotal) {
		this.idImp = idimpression;
		this.qualite = qualite;
		this.format = format;
		this.idUser = idutilisateur;
		this.nbrPageTotal = nombrepagestotal;
	}

}
