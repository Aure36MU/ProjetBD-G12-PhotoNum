insert into utilisateur (prenom,nom,mail,mdp) values ('pauline','fauto','p.fauto@mail.fr','12345');
insert into utilisateur (prenom,nom,mail,mdp) values ('Aurelien','FLASH','AF@mail.fr','12ZD');
insert into utilisateur (prenom,nom,mail,mdp) values ('THOMAS','Trigger','tt@mail.fr','tt$');
insert into utilisateur (prenom,nom,mail,mdp) values ('sylvain','me','Smi@mail.us',’AZERTY$00’);

insert into adresse (rue , codePostal, ville , pays, idUser)  values (‘16 rue Malifaud’ , ‘38100’,’Grenoble’ , ‘france’ , (select idUser from utilisateur where mail=’p.fauto@mail.fr’));
insert into adresse (rue , codePostal, ville , pays, idUser)  values (‘16 rue Malifaud’ , ‘38100’, ‘Grenoble’ , ‘france’ , (select idUser from utilisateur where mail='AF@mail.fr'));
insert into adresse (rue , codePostal, ville , pays, idUser)  values (‘44 avenue maison neuve’ , ‘38000’, ‘Grenoble’ , ‘france’ , (select idUser from utilisateur where mail='AF@mail.fr’))
insert into adresse (rue , codePostal, ville , pays, idUser)  values (‘1 boulevard des bienheureux’ , ‘97400’, ‘Saint Denis’ , ‘La réunion’ , (select idUser from utilisateur where mail='tt@mail.fr’));
insert into adresse (rue , codePostal, ville , pays, idUser)  values (‘ 2424 greenway , 65806, Springfield , USA , (select idUser from utilisateur where mail=Smi@mail.us’));

insert into FichierImg (chemin , infoPVue,pixelImg , partager, idUser,dateUtilisation,fileAttModif,fileAttSuppr)  values (‘c:/MesDocuments/image/chat.png’ , ‘chat’,’256’ ,false , ‘24/01/2019’,false,false, (select idUser from utilisateur where mail=Smi@mail.us’));
insert into FichierImg (chemin , infoPVue,pixelImg , partager, idUser,dateUtilisation,fileAttModif,fileAttSuppr)  values (‘c:/MesDocuments/image/chien.png’ , ‘chien’,’256’ ,false , ‘24/01/2019’,false,false, (select idUser from utilisateur where mail=Smi@mail.us’));
insert into FichierImg (chemin , infoPVue,pixelImg , partager, idUser,dateUtilisation,fileAttModif,fileAttSuppr)  values (‘c:/MesDocuments/image/hamster.png’ , ‘hamster’,’256’ ,false , ‘24/01/2019’,false,false, (select idUser from utilisateur where mail=Smi@mail.us’));

insert into Photo (idFichier, retouche) values (1,’ ‘);
insert into Photo (idFichier, retouche) values (2,’filtre sequoia ‘);
insert into Photo (idFichier, retouche) values (3,’ ‘);

insert into  Impression_Photo (dImp,idPh,num_page,text,nbExemplaire,PHOTO INT) values (1,1,1,’chat’,1);
insert into  Impression_Photo (dImp,idPh,num_page,text,nbExemplaire,PHOTO INT) values (2,2,2,’chien’,2);
insert into  Impression_Photo (dImp,idPh,num_page,text,nbExemplaire,PHOTO INT) values (1,2,3,’hamster’,2);

insert into FichierImg (qualite , format,idUser , nbPageTotal,nomImp,...)  values (haute , petit,(select idUser from utilisateur where mail=Smi@mail.us’),52 ,essais1);

insert into Article (idArt , qte , idImp, idComm)  values (1 , 1,1,1);