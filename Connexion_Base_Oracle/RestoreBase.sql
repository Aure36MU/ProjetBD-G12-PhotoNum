create table Utilisateur (idUser INTEGER PRIMARY KEY NOT NULL,nom Varchar2(100),prenom Varchar2(100),mdp Varchar2(100),email Varchar2(100),active integer check (active in (0,1)), statutUtilisateur Varchar2(20))
create table Adresse (idAdre INTEGER PRIMARY KEY NOT NULL,ville Varchar2(100),codePostal INTEGER,rue Varchar2(100),pays Varchar2(100),idUser INTEGER)
create table CodePromo (idCodeP INTEGER PRIMARY KEY NOT NULL,dateAcqui DATE,dateUtil DATE,code Varchar2(100),taux INTEGER,idUser INTEGER,CONSTRAINT FK_CodePromo1 FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser))
create table CodeUniversel (idCodeP INTEGER PRIMARY KEY NOT NULL,CONSTRAINT FK_CodeUniversel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP ))
create table CodePersonnel (idCodeP INTEGER PRIMARY KEY NOT NULL,CONSTRAINT FK_CodePersonnel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP))
create table UtilisationCodeUtilisateur (idCodeP INTEGER,idUser INTEGER,CONSTRAINT FK_CodeUtilisateur FOREIGN KEY (idCodeP ) REFERENCES CodeUniversel(idCodeP ),CONSTRAINT FK_User FOREIGN KEY (idUser ) REFERENCES Utilisateur(idUser ))
create table FichierImage (idFichier INTEGER PRIMARY KEY NOT NULL,idUser INTEGER,chemin Varchar2(100),infoPVue Varchar2(100),pixelImg INTEGER,partager integer check (partager in (0,1)),dateUtilisation DATE,fileAttModif integer check (fileAttModif in (0,1)),fileAttSuppr integer check (fileAttSuppr in (0,1)),CONSTRAINT FK_FichierImg FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser))
create table Photo (idPh INTEGER PRIMARY KEY NOT NULL,idFichier INTEGER,retouche Varchar2(100),CONSTRAINT FK_Photo FOREIGN KEY (idFichier) REFERENCES FichierImage(idFichier))
create table Impression (idImp INTEGER PRIMARY KEY NOT NULL,type Varchar2(20),qualite Varchar2(20),format Varchar2(20),idUser INTEGER, nbrPageTotal INTEGER,nomImp Varchar2(100),CONSTRAINT FK_ImpUser FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser))
create table Impression_Photo (idImp INTEGER,idPh INTEGER,num_page INTEGER,text Varchar2(100),nbExemplairePHOTO INTEGER,CONSTRAINT primary_idImpIdPh PRIMARY KEY (idImp,idPh),CONSTRAINT FK_ImpPhoto1 FOREIGN KEY (idPh) REFERENCES Photo(idPh),CONSTRAINT FK_Impression FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create table Commande (idComm INTEGER PRIMARY KEY NOT NULL,idUser INTEGER, idCodeP INTEGER, dateC DATE, modeLivraison Varchar2(20), statutCommande Varchar2(20),CONSTRAINT FK_Commande FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser))
create table Catalogue (type Varchar2(100),format Varchar2(100),modele Varchar2(100),qualite Varchar2(100),prix INTEGER,qteStock INTEGER,CONSTRAINT primary_tfm PRIMARY KEY (type,format,modele))
create table Article (idArt INTEGER PRIMARY KEY NOT NULL,prix INTEGER,qte INTEGER,idImp INTEGER,idComm INTEGER,CONSTRAINT FK_Article1 FOREIGN KEY (idImp) REFERENCES impression(idImp),CONSTRAINT FK_Article2 FOREIGN KEY (idComm) REFERENCES Commande(idComm))
create table Tirage (idImp INTEGER PRIMARY KEY NOT NULL,CONSTRAINT FK_Tirage FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create table Calendrier (idImp INTEGER PRIMARY KEY NOT NULL,modeleCalendrier Varchar2(20),CONSTRAINT FK_Calendrier FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create table Album (idImp INTEGER PRIMARY KEY NOT NULL,photoCouv INTEGER,titreCouv Varchar2(100),CONSTRAINT FK_Album1 FOREIGN KEY (photoCouv) REFERENCES Photo(idPh),CONSTRAINT FK_Album2 FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create table Agenda (idImp INTEGER PRIMARY KEY NOT NULL,ornement Varchar2(20), modeleAgenda Varchar2(20), CONSTRAINT FK_Agenda FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create table Cadre (idImp INTEGER PRIMARY KEY NOT NULL,modeleCadre Varchar2(20),CONSTRAINT FK_Cadre FOREIGN KEY (idImp) REFERENCES Impression(idImp))
create sequence idPh_seq
create sequence idAdre_seq
create sequence idUser_seq
create sequence idCodeP_seq
create sequence idFichier_seq
create sequence idImp_seq
create sequence idArt_seq
create sequence idComm_seq