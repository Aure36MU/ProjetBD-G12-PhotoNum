Create trigger Personne_existant_contrainte After insert On Utilisateur DECLARE Valeur int; BEGIN Select count(Mail) as Valeur From (Select distinct(eMail) as Mail, count(eMail) into NbMail From Utilisateur;)Where NbMail>=1;If(Valeur>=1){raise_application_error(20001, “Impossible de l’ajouter car cet adresse mail est déjà utilisé”);}END;

Create or replace trigger Adresse_existant_contrainte
After insert or update on Utilisateur
DECLARE
Nombre int;
BEGIN
Select count(A1.idAdre) into Nombre
From (Select * From Adresse) A1 JOIN (Select * From Adresse) A2 ON (A1.idUser=A2.idUser)Where A1.idAdre!=A2.idUser and A1.ville=A2.ville and A1.codePostale=A2.codePostale and A1.rue=A2.rue and A1.pays=A2.pays;
if(Nombre>=1){
raise_application_error(20002,”L’adresse mail à déjà été ajouter dans votre liste de choix”)};
END;
-----------------------------------------------------------------------------------------------------------------
Si un agenda semainier possède plus de 365 pages on a un message d’erreur.
Create trigger NbrPageSemainier_Depassement_contrainte
After insert on impression_Photo
DECLARE
	NbrPage int;
BEGIN
	Select count(idImp) into NbrPage
	From Semainier NATURAL JOIN Impression_Photo
Group by num_page;
-------------------------------------------OU---------------------------------------------------
	Select count(distinct(idImp)) into NbrPage
	From Semainier NATURAL JOIN Impression_Photo;
----------------------------------------------------------------------------------------------
	if(NbrPage>365){
raise_application_error(20002,”Vous ne pouvez pas ajouter une nouvelle page”)
}
END;
-----------------------------------------------------------------------------------------------------------------
Si un agenda journalier possède plus de 52 pages on a un message d’erreur.
Create trigger NbrPageJournalier_Depassement_contrainte
After insert on impression_Photo
DECLARE
	NbrPage int;
BEGIN
	Select count(idImp) into NbrPage
	From AgendaJournalier NATURAL JOIN Impression_Photo
Group by num_page;
-------------------------------------------OU---------------------------------------------------
	Select count(distinct(idImp)) into NbrPage
	From AgendaJournalier NATURAL JOIN Impression_Photo;
----------------------------------------------------------------------------------------------
	if(NbrPage>365){
raise_application_error(20002,”Vous ne pouvez pas ajouter une nouvelle page”)
}
END;
-----------------------------------------------------------------------------------------------------------------
Create sequence of all table;
create sequence idAdre_seq;
create sequence idUser_seq;
create sequence idCodeP_seq;
create sequence idFichier_seq;
create sequence idPh_seq;
create sequence idImp_seq;
create sequence idArt_seq;
create sequence idComm_seq;
Limplémentation des identifiants de chaque table



Create trigger Inc_User_Contrainte
Before insert on Utilisateur
for each row
BEGIN
	Select idUser_seq.nextval
	into :new.idUser
From dual;
END;


Create trigger Inc_Adresse_contrainte
Before insert on Adresse
for each row
BEGIN
	Select idAdre_seq.nextval
	into :new.idAdre
From dual;
END;


Create trigger Inc_Article_contrainte
Before insert on Article
for each row
BEGIN
	Select idArt_seq.nextval
	into :new.idArt
From dual;
END;


Create trigger Inc_Impr_contrainte
Before insert on Impression
for each row
BEGIN
	Select idImp_seq.nextval
	into :new.idImp
From dual;
END;


Create trigger Inc_ImpPhoto_contrainte
Before insert on Impression_Photo
for each row
BEGIN
	Select idFichier_seq.nextval
	into :new.idFichier
From dual;
END;


Create trigger Inc_Photo_contrainte
Before insert on Photo
for each row
BEGIN
	Select idPh_seq.nextval
	into :new.idPh
From dual;
END;


Create trigger Inc_CodePromo_contrainte
Before insert on CodePromo
for each row
BEGIN
	Select idCodeP_seq.nextval
	into :new.idCodeP
From dual;
END;
