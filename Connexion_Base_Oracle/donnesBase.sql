insert into Utilisateur (idUser,prenom,nom,email,mdp,active,statut) values (1, 'pauline','fauto','p.fauto@mail.fr','12345',1,'CLIENT')
insert into Utilisateur (idUser,prenom,nom,email,mdp,active,statut) values (2, 'Aurelien','FLASH','AF@mail.fr','12ZD',1,'CLIENT')
insert into Utilisateur (idUser,prenom,nom,email,mdp,active,statut) values (3, 'THOMAS','Trigger','tt@mail.fr','tt$',1,'CLIENT')
insert into Utilisateur (idUser,prenom,nom,email,mdp,active,statut) values (4, 'sylvain','me','Smi@mail.us','AZERTY$00',1,'CLIENT')
insert into Utilisateur (idUser,prenom,nom,email,mdp,active,statut) values (5, 'chouette','bidule','a@b.com','truc',1,'GESTIONNAIRE')

insert into Adresse (idAdre, rue , codePostal, ville , pays, idUser)  values (1, '16 rue Malifaud' , 38100,'Grenoble' , 'france' , (select idUser from utilisateur where email='p.fauto@mail.fr'))
insert into Adresse (idAdre, rue , codePostal, ville , pays, idUser)  values (2, '16 rue Malifaud' , 38100, 'Grenoble' , 'france' , (select idUser from utilisateur where email='AF@mail.fr'))
insert into Adresse (idAdre, rue , codePostal, ville , pays, idUser)  values (3, '44 avenue maison neuve' , 38000, 'Grenoble' , 'france' , (select idUser from utilisateur where email='AF@mail.fr'))
insert into Adresse (idAdre, rue , codePostal, ville , pays, idUser)  values (4, '1 boulevard des bienheureux' , 97400, 'Saint Denis' , 'La reunion' , (select idUser from utilisateur where email='tt@mail.fr'))
insert into Adresse (idAdre, rue , codePostal, ville , pays, idUser)  values (5, '2424 greenway', 65806, 'Springfield' , 'USA' , (select idUser from utilisateur where email='Smi@mail.us'))

insert into CodePromo (idCodeP, idUser) values (1,2)

insert into FichierImage (idFichier, chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values (1, 'c:/MesDocuments/image/chat.png' , 'chat', 256 , 0, (select idUser from utilisateur where email='Smi@mail.us'), '24/01/2019', 0, 0)
insert into FichierImage (idFichier, chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values (2, 'c:/MesDocuments/image/chien.png' , 'chien', 256 , 0, (select idUser from utilisateur where email='Smi@mail.us'), '24/01/2019', 0, 0)
insert into FichierImage (idFichier, chemin, infoPVue, pixelImg, partager, idUser, dateUtilisation, fileAttModif, fileAttSuppr)  values (3, 'c:/MesDocuments/image/hamster.png' , 'hamster', 256 , 0, (select idUser from utilisateur where email='Smi@mail.us'), '24/01/2019', 0, 0)

insert into Photo (idPh, idFichier, retouche) values (1, 1,' ')
insert into Photo (idPh, idFichier, retouche) values (2, 2,'filtre sequoia ')
insert into Photo (idPh, idFichier, retouche) values (3, 3,' ')

insert into Impression (idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)  values (1, 'mon_tirage', 1, 2, 'TIRAGE', 'PETIT', 'BASSE')
insert into Impression (idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)  values (2, 'mon_album', 1, 2, 'ALBUM', 'MOYEN', 'BASSE')
insert into Impression (idImp, nomImp, nbrPageTotal, idUser, type, format, qualite)  values (3, 'mon_calendrier', 1, 2, 'CALENDRIER', 'MOYEN', 'BASSE')

insert into  Impression_Photo (idImp,idPh,num_page,text,nbExemplairePHOTO) values (1,1,1,'chat',1)
insert into  Impression_Photo (idImp,idPh,num_page,text,nbExemplairePHOTO) values (2,2,2,'chien',2)
insert into  Impression_Photo (idImp,idPh,num_page,text,nbExemplairePHOTO) values (1,2,3,'hamster',2)

insert into Commande (idComm, idUser, idCodeP, dateC, modeLivraison, statutCommande) values (1, 2, 1, '27/01/2019', 'DOMICILE', 'BROUILLON')

insert into Article (idArt, prix, qte, idImp, idComm)  values (1, 20, 1,3,1)