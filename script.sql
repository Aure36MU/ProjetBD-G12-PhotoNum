create table Adresse{
    idAdre INT PRIMARY KEY NOT NULL,
    ville VARCHAR(100),
    codePostal INT,
    rue VARCHAR(100),
    pays VARCHAR(100),
    idUser INT,
    CONSTRAINT FK_Adresse FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
}

create table Utilisateur{
    idUser INT PRIMARY KEY NOT NULL,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    mdp VARCHAR(100),
    email VARCHAR(100),
    active BOOLEAN
}

create table CodePromo{
    idCodeP INT PRIMARY KEY NOT NULL,
    dateAcqui DATE,
    dateUtil DATE,
    code VARCHAR(100),
    taux INT,
    idUser INT,
    idComm INT,
    CONSTRAINT FK_CodePromo1 FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser),
    CONSTRAINT FK_CodePromo2 FOREIGN KEY (idComm) REFERENCES Commande(idComm)
}

create table CodeUniversel{
    idCodeP INT PRIMARY KEY NOT NULL,
CONSTRAINT FK_CodeUniversel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP ),
}

create table CodePersonnel{
    idCodeP INT PRIMARY KEY NOT NULL,
CONSTRAINT FK_CodePersonnel FOREIGN KEY (idCodeP ) REFERENCES CodePromo(idCodeP),
}

create table FichierImg{
    idFichier INT PRIMARY KEY NOT NULL,
    idUser INT,
    chemin VARCHAR(100),
    infoPVue VARCHAR(100),
    pixelImg INT,
    partager BOOLEAN,
    dateUtilisation BOOLEAN,
    fileAttModif BOOLEAN,
    fileAttSuppr BOOLEAN,
    CONSTRAINT FK_FichierImg FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
}

create table Photo{
    idPh INT PRIMARY KEY NOT NULL,
    idFichier INT,
    retouche VARCHAR(100),
    CONSTRAINT FK_Photo FOREIGN KEY (idFichier) REFERENCES FichierImg(idFichier)
}

create table Impression_Photo{
    idImp INT,
    idPh INT,
    num_page INT,
    text VARCHAR(100),
    qte INT,
    PRIMARY KEY(idImp,idPh),
    CONSTRAINT FK_Impression_Photo FOREIGN KEY (idPh) REFERENCES Photo(idPh)
}

create table Impression{
    idImp INT PRIMARY KEY NOT NULL,
    qualite ENUM('haute','moyenne','basse'),
    format ENUM('petit','moyen','grand'),
    idUser INT,
    nbrPageTotal INT,
    CONSTRAINT FK_Impression FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
}

create table Article{
    idArt INT PRIMARY KEY NOT NULL,
    prix INT,
    qte INT,
    idImp INT,
    idComm INT,
    CONSTRAINT FK_Article1 FOREIGN KEY (idImp) REFERENCES Impression(idImp),
    CONSTRAINT FK_Article2 FOREIGN KEY (idComm) REFERENCES Commande(idComm),
}

create table Commande{
    idComm INT PRIMARY KEY NOT NULL,
    idUser INT,
    dateC DATE,
    modeLivraison ENUM('PointRelais','Adresse'),
    statut ENUM(‘brouillon’,'en cours','prêt à l'envoi','envoyer'),
    CONSTRAINT FK_Commande FOREIGN KEY (idUser) REFERENCES Utilisateur(idUser)
}

create table Catalogue{
    type VARCHAR(100),
    format VARCHAR(100),
    modele VARCHAR(100),
    prix INT,
    qteStock INT,
    PRIMARY KEY(type,format,modele)
}

create table Tirage{
    idImp INT PRIMARY KEY NOT NULL,
    CONSTRAINT FK_Tirage FOREIGN KEY (idImp) REFERENCES Impression(idImp)
}

create table Calendrier{
    idImp INT PRIMARY KEY NOT NULL,
    modele ENUM,
    CONSTRAINT FK_Calendrier FOREIGN KEY (idImp) REFERENCES Impression(idImp)
}

create table Album{
    idImp INT PRIMARY KEY NOT NULL,
    photoCouv INT,
    titreCouv VARCHAR(100),
    CONSTRAINT FK_Album1 FOREIGN KEY (photoCouv) REFERENCES Utilisateur(idPh),
    CONSTRAINT FK_Album2 FOREIGN KEY (idImp) REFERENCES Impression(idImp)
}

create table Agenda{
    idImp INT PRIMARY KEY NOT NULL,
    type ENUM,
    CONSTRAINT FK_Agenda FOREIGN KEY (idImp) REFERENCES Impression(idImp)
}

create table AgendaJournalier{
    idImp INT PRIMARY KEY NOT NULL,
    CONSTRAINT FK_AgendaSemainier FOREIGN KEY (idImp) REFERENCES Agenda(idImp)
}

create table AgendaJournalier{
    idImp INT PRIMARY KEY NOT NULL,
    CONSTRAINT FK_AgendaSemainier FOREIGN KEY (idImp) REFERENCES Agenda(idImp)
}

create table Cadre{
    idImp INT PRIMARY KEY NOT NULL,
    modele ENUM(“Bois”,”Aluminium”),
    CONSTRAINT FK_Cadre FOREIGN KEY (idImp) REFERENCES Impression(idImp)
}
