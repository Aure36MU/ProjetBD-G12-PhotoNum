package src.impression.agenda;



public class Semainier extends Agenda {

    int idImp;
    src.impression.Qualite qualite;
    src.impression.Format format;
    int idUser;
    int nbrPageTotal = 52;

    public Semainier(int idimpression, src.impression.Qualite qualite, src.impression.Format format, int idutilisateur, int nombrepagestotal) {
        this.idImp = idimpression;
        this.qualite = qualite;
        this.format = format;
        this.idUser = idutilisateur;
        this.nbrPageTotal = nombrepagestotal;

    }
    public ModeleAgenda getmodele() {
        return modele;
    }
    public void setModele(ModeleAgenda modele) {
        this.modele = modele;
    }


    public int getIdImp() {
        return idImp;
    }
    public void setIdImp(int idImp) {
        this.idImp = idImp;
    }

}
