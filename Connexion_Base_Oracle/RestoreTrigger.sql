Create or replace trigger Inc_User_Contrainte
Before insert on Utilisateur
 for each row
 BEGIN
 Select idUser_seq.nextval
 into :new.idUser From dual;
 END;
 /
Create or replace trigger Inc_Adresse_contrainte
Before insert on Adresse
for each row
BEGIN
	Select idAdre_seq.nextval
	into :new.idAdre
From dual;
END;
 /
 Create or replace trigger Inc_fichierImage_contrainte
Before insert on FichierImage
for each row
BEGIN
	Select idFichier_seq.nextval
	into :new.idFichier
From dual;
END;
 /
Create or replace trigger Inc_Article_contrainte
Before insert on Article
for each row
BEGIN
	Select idArt_seq.nextval
	into :new.idArt
From dual;
END;
/
Create or replace trigger Inc_Impr_contrainte
Before insert on Impression
for each row
BEGIN
	Select idImp_seq.nextval
	into :new.idImp
From dual;
END;
/
Create or replace trigger Inc_Photo_contrainte
Before insert on Photo
for each row
BEGIN
	Select idPh_seq.nextval
	into :new.idPh
From dual;
END;
/
Create or replace trigger Inc_CodePromo_contrainte
Before insert on CodePromo
for each row
BEGIN
	Select idCodeP_seq.nextval
	into :new.idCodeP
From dual;
END;
/
Create or replace trigger Inc_commande_contrainte
Before insert on Commande
for each row
BEGIN
	Select idComm_seq.nextval
	into :new.idComm
From dual;
END;
/
