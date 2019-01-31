Create or replace trigger Personne_existant_contrainte
after insert or update On UTILISATEUR
DECLARE
 valeur INTEGER;
 BEGIN
 select count(ndr) into valeur
from (select email , count(email) as ndr from utilisateur group by email)
where ndr >1;
 If valeur >0 then raise_application_error(-20001, 'impossible d ajouter car cet adresse mail est deja utilise');
 END IF;
END;
/
-----------------------------------------------------------------------------------------------------------------
//NE FONCTIONNE PAS !!!!!!!!!!!!!!!!!!!!!!!!!!!
Create or replace drop trigger Adresse_existant_contrainte;
After insert or update on Adresse
DECLARE
Nombre INTEGER;
BEGIN
Select count(A1.idAdre) into Nombre
From Adresse A1, Adresse A2 Where A1.idUser=A2.idUser and A1.ville=A2.ville and A1.codePostal=A2.codePostal and A1.rue=A2.rue and A1.pays=A2.pays;
if(Nombre>1) then raise_application_error(-20002,'Adresse de livraison deja renseigne');
end if;
END;
/
-----------------------------------------------------------------------------------------------------------------
Si un agenda semainier possède plus de 365 pages on a un message d’erreur.
Create or replace trigger PageSemainier_Depas_contrainte
After insert or update on impression_Photo
DECLARE
	NbrPage int;
BEGIN
	Select count(distinct(num_page)) into NbrPage
	From Agenda a INNER JOIN Impression_Photo ip ON (a.idImp = ip.idImp)
	where a.modeleAgenda = 'SEMAINIER';

	if(NbrPage>365)then raise_application_error(-20003,'Vous ne pouvez pas ajouter une nouvelle page');
	end if;
END;
/
-----------------------------------------------------------------------------------------------------------------
//NE FONCTIONNE PAS !!!!!!!!!!!!!!!!!!!!!!!!!!!
Si un agenda journalier possède plus de 52 pages on a un message d’erreur.
Create trigger or replace NbrPJour_Depas_cons
After insert or update on impression_Photo
DECLARE
	NbrPage int;
BEGIN
	Select count(distinct(num_page)) into NbrPage
	From Agenda a INNER JOIN Impression_Photo ip ON (a.idImp = ip.idImp)
	where a.modeleAgenda = 'JOURNALIER';

	if(NbrPage>52)then raise_application_error(-20004,'Vous ne pouvez pas ajouter une nouvelle page');
	end if;
END;
/
-----------------------------------------------------------------------------------------------------------------
Create sequence of all table;
create sequence idAdre_seq
create sequence idUser_seq
create sequence idCodeP_seq
create sequence idFichier_seq
create sequence idPh_seq
create sequence idImp_seq
create sequence idArt_seq
create sequence idComm_seq
Limplémentation des identifiants de chaque table


Create trigger Inc_User_Contrainte
 Before insert on Utilisateur
 for each row
 BEGIN
 Select idUser_seq.nextval
 into :new.idUser From dual;
 END;
 /
Create trigger Inc_Adresse_contrainte
Before insert on Adresse
for each row
BEGIN
	Select idAdre_seq.nextval
	into :new.idAdre
From dual;
END;
 /
Create trigger Inc_Article_contrainte
Before insert on Article
for each row
BEGIN
	Select idArt_seq.nextval
	into :new.idArt
From dual;
END;
/
Create trigger Inc_Impr_contrainte
Before insert on Impression
for each row
BEGIN
	Select idImp_seq.nextval
	into :new.idImp
From dual;
END;
/
Create trigger Inc_Photo_contrainte
Before insert on Photo
for each row
BEGIN
	Select idPh_seq.nextval
	into :new.idPh
From dual;
END;
/
Create trigger Inc_CodePromo_contrainte
Before insert on CodePromo
for each row
BEGIN
	Select idCodeP_seq.nextval
	into :new.idCodeP
From dual;
END;
/
