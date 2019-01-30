insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('pauline','fauto','p.fauto@mail.fr','12345',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('Aurelien','FLASH','AF@mail.fr','12ZD',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('THOMAS','Trigger','tt@mail.fr','tt$',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('sylvain','me','Smi@mail.us','AZERTY$00',1,'CLIENT')
insert into Utilisateur (prenom,nom,email,mdp,active,statutUtilisateur) values ('chouette','bidule','a@b.com','truc',1,'GESTIONNAIRE')

insert into adresse (rue , codePostal, ville , pays, idUser)  values ('16 rue Malifaud' , 38100,'Grenoble' , 'france' , (select idUser from utilisateur where email='p.fauto@mail.fr'))
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('16 rue Malifaud' , 38100, 'Grenoble' , 'france' , (select idUser from utilisateur where email='AF@mail.fr'))
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('44 avenue maison neuve' , 38000, 'Grenoble' , 'france' , (select idUser from utilisateur where email='AF@mail.fr'))
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('1 boulevard des bienheureux' , 97400, 'Saint Denis' , 'La reunion' , (select idUser from utilisateur where email='tt@mail.fr'))
insert into adresse (rue , codePostal, ville , pays, idUser)  values ('2424 greenway', 65806, 'Springfield' , 'USA' , (select idUser from utilisateur where email='Smi@mail.us'))

insert into CodePromo (code,taux, idUser) values ('noel',10,21)

insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/chat.png' , 'chat', 256 , 0, 21, '24/01/2019', 0, 0)
insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/chien.png' , 'chien', 256 , 0, 22, '24/01/2019', 0, 0)
insert into FichierImage (chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values ('c:/MesDocuments/image/hamster.png' , 'hamster', 256 , 0, 22, '24/01/2019', 0, 0)

insert into Photo (idPh, idFichier, retouche) values (1, 1,' ')
insert into Photo (idPh, idFichier, retouche) values (2, 2,'filtre sequoia ')
insert into Photo (idPh, idFichier, retouche) values (3, 3,' ')

insert into Impression (nomImp, nbrPageTotal, idUser, type, format, qualite)  values ('mon_tirage', 1, 21, 'tirage', 'petit', 'basse')
insert into Impression (nomImp, nbrPageTotal, idUser, type, format, qualite)  values ('mon_album', 1, 22, 'album', 'moyen', 'basse')

insert into  Impression_Photo (idImp , idPh,num_page,text,nbExemplairePHOTO) values (61,27,1,'chat',1)
insert into  Impression_Photo (idImp ,idPh,num_page,text,nbExemplairePHOTO) values (61,29,2,'chien',2)
insert into  Impression_Photo (idImp ,idPh,num_page,text,nbExemplairePHOTO) values (61,28,3,'hamster',2)

insert into Commande (idUser, idCodeP, dateC, modeLivraison, statutCommande) values (22, 24, '27/01/2019', 'DOMICILE', 'BROUILLON')

insert into Article (prix, qte, idImp, idComm)  values (20, 1,1,1)