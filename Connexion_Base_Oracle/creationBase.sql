create table Adresse{idAdre INT PRIMARY KEY NOT NULL,ville Varchar2(100),codePostal INT,rue Varchar2(100),pays Varchar2(100),idUser INT,CONSTRAINT FK_Adresse FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)}
create table Utilisateur{idUser INT PRIMARY KEY NOT NULL,nom Varchar2(100),prenom Varchar2(100),mdp Varchar2(100),email Varchar2(100),active BOOLEAN,statut ENUM(‘Client’,’Gestionnaire’)}
create table CodePromo{idCodeP INT PRIMARY KEY NOT NULL,dateAcqui DATE,dateUtil DATE,code Varchar2(100),taux INT,idUser INT,CONSTRAINT FK_CodePromo1 FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)}
create table CodeUniversel{idCodeP INT PRIMARY KEY NOT NULL,CONSTRAINT FK_CodeUniversel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP ),}
create table CodePersonnel{idCodeP INT PRIMARY KEY NOT NULL,CONSTRAINT FK_CodePersonnel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP),}
create table UtilisationCodeUtilisateur{idCodeP INT,idUser INT,CONSTRAINT FK_CodeUniversel FOREIGN KEY (idCodeP ) REFERENCES CodeUniversel(idCodeP ),CONSTRAINT FK_User FOREIGN KEY (idUser ) REFERENCES Utilisateur(idUser ),}
create table FichierImg{idFichier INT PRIMARY KEY NOT NULL,idUser INT,chemin Varchar2(100),infoPVue Varchar2(100),pixelImg INT,partager BOOLEAN,dateUtilisation DATE,fileAttModif BOOLEAN,fileAttSuppr BOOLEAN,CONSTRAINT FK_FichierImg FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)}
create table Photo{idPh INT PRIMARY KEY NOT NULL,idFichier INT,retouche Varchar2(100),CONSTRAINT FK_Photo FOREIGN KEY (idFichier) REFERENCES FichierImg(idFichier)}
create table Impression_Photo{idImp INT,idPh INT,num_page INT,text Varchar2(100),nbExemplairePHOTO INT,CONSTRAINT primary_key1 PRIMARY KEY (idImp,idPh),CONSTRAINT FK_Photo FOREIGN KEY (idPh) REFERENCES Photo(idPh),CONSTRAINT FK_Impression FOREIGN KEY (idImp) REFERENCES Impression(idImp)}
create table Impression{idImp INT PRIMARY KEY NOT NULL,qualite ENUM('haute','moyenne','basse'),format ENUM('petit','moyen','grand'),idUser INT,nbPageTotal INT,nomImp Varchar2,CONSTRAINT FK_User FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)}
create table Article{idArt INT PRIMARY KEY NOT NULL,prix INT,qte INT,idImp INT,idComm INT,CONSTRAINT FK_Article1 FOREIGN KEY (idImp) REFERENCES impression(idImp),CONSTRAINT FK_Article2 FOREIGN KEY (idComm) REFERENCES Commande(idComm)}
create table Commande{idComm INT PRIMARY KEY NOT NULL,idUser INT,dateC DATE,modeLivraison ENUM('PointRelais','Adresse'),statut ENUM('en cours','prêt à lenvoi','envoyer'),CONSTRAINT FK_Commande FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)}
create table Catalogue{type Varchar2(100),format Varchar2(100),modele Varchar2(100),prix INT,qteStock INT,CONSTRAINT primary_key2 PRIMARY KEY (type,format,modele),}
create table Tirage{idImp INT PRIMARY KEY NOT NULL,CONSTRAINT FK_Tirage FOREIGN KEY (idImp) REFERENCES Impression(idImp)}
create table Calendrier{idImp INT PRIMARY KEY NOT NULL,modele ENUM,CONSTRAINT FK_Calendrier FOREIGN KEY (idImp) REFERENCES Impression(idImp)}
create table Album{idImp INT PRIMARY KEY NOT NULL,photoCouv INT,titreCouv Varchar2(100),CONSTRAINT FK_Album1 FOREIGN KEY (photoCouv) REFERENCES Utilisateur(idPh),CONSTRAINT FK_Album2 FOREIGN KEY (idImp) REFERENCES Impression(idImp)}
create table Agenda{idImp INT PRIMARY KEY NOT NULL,type ENUM,CONSTRAINT FK_Agenda FOREIGN KEY (idImp) REFERENCES Impression(idImp)}
create table Semainier{idImp INT PRIMARY KEY NOT NULL,CONSTRAINT FK_Semainier FOREIGN KEY (idImp) REFERENCES Agenda(idImp)}
create table AgendaJournalier{idImp INT PRIMARY KEY NOT NULL,CONSTRAINT FK_AgendaJournalier FOREIGN KEY (idImp) REFERENCES Agenda(idImp)}
create table Cadre{idImp INT PRIMARY KEY NOT NULL,modele ENUM(“Bois”,”Alluminium”),CONSTRAINT FK_Cadre FOREIGN KEY (idImp) REFERENCES Impression(idImp)}