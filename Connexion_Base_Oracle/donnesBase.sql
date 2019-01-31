insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('pauline','fauto','p.fauto@mail.fr','12345',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('Aurelien','FLASH','AF@mail.fr','12ZD',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('THOMAS','Trigger','tt@mail.fr','tt$',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('sylvain','me','Smi@mail.us','AZERTY$00',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('chouette','bidule','a@b.com','truc',1,'GESTIONNAIRE')

insert into adresse (rue , codePostal, ville , pays, idUser)  values ('16 rue Malifaud' , 38100,'Grenoble' , 'france' , 1)
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('16 rue Malifaud' , 38100, 'Grenoble' , 'france' , 2)
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('44 avenue maison neuve' , 38000, 'Grenoble' , 'france' , 3)
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('1 boulevard des bienheureux' , 97400, 'Saint Denis' , 'La reunion' , 4)
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('2424 greenway', 65806, 'Springfield' , 'USA' , 5)

insert into CodePromo (code,taux, idUser) values ('noel',10,2)

insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/chat.png' , 'chat', 256 , 0, 1, TO_DATE('24/01/2019', 'DD/MM/YYYY'), 0, 0)
insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/chien.png' , 'chien', 256 , 0, 2, TO_DATE('24/01/2019', 'DD/MM/YYYY'), 0, 0)
insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/hamster.png' , 'hamster', 256 , 0, 2, TO_DATE('29/01/2019', 'DD/MM/YYYY'), 0, 0)

insert into Photo (idPh, idFichier, retouche) values (1, 1,' ')
insert into Photo (idPh, idFichier, retouche) values (2, 2,'filtre sequoia ')
insert into Photo (idPh, idFichier, retouche) values (3, 3,' ')

insert into Impression (nomImp, nbrPageTotal, idUser, type, format, qualite)  values ('mon_tirage', 1, 2, 'tirage', 'petit', 'basse')
insert into Impression (nomImp, nbrPageTotal, idUser, type, format, qualite)  values ('mon_album', 1, 2, 'album', 'moyen', 'basse')

insert into  Impression_Photo (idImp , idPh,num_page,text,nbExemplairePHOTO) values (2,1,1,'chat',1)
insert into  Impression_Photo (idImp ,idPh,num_page,text,nbExemplairePHOTO) values (2,2,2,'chien',2)
insert into  Impression_Photo (idImp ,idPh,num_page,text,nbExemplairePHOTO) values (2,3,3,'hamster',2)

insert into Commande (idUser, idCodeP, dateC, modeLivraison, statutCommande) values (2, 1, TO_DATE('27/01/2019', 'DD/MM/YYYY'), 'DOMICILE', 'BROUILLON')

insert into Article (prix, qte, idImp, idComm)  values (20, 1,1,1)

insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',22,'PETIT','SEMAINIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',32,'MOYEN','SEMAINIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',42,'GRAND','SEMAINIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',27,'PETIT','JOURNALIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',37,'MOYEN','JOURNALIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('AGENDA',47,'GRAND','JOURNALIER',5)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',11,'PETIT','BOIS',105)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',21,'MOYEN','BOIS',45)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',31,'GRAND','BOIS',35)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',15,'PETIT','ALUMINIUM',1)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',25,'MOYEN','ALUMINIUM',1)
insert into Catalogue (type,prix,format,modele,qteStock) values ('TIRAGE',55,'GRAND','ALUMINIUM',0)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',9,'PETIT','MURAL',10)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',11,'MOYEN','MURAL',40)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',15,'GRAND','MURAL',30)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',1,'PETIT','BUREAU',1)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',9,'MOYEN','BUREAU',1)
insert into Catalogue (type,prix,format,modele,qteStock) values ('CALENDRIER',19,'GRAND','BUREAU',0)


