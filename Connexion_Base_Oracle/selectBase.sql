select * from commande
SELECT * from impression
insert into Article (prix, qte, idImp, idComm)  values (20, 1,42,1)
select * from Utilisateur where email = 'a@b.com'
select * from tirage
SELECT max(idImp) FROM Impression
SELECT max(idUser) FROM Utilisateur
select * from Impression where type='CALENDRIER'
(SELECT idImp FROM Impression i WHERE i.idUser=2 and i.type='CALENDRIER') MINUS (SELECT idImp FROM Article NATURAL JOIN Impression WHERE type='CALENDRIER')
SELECT table_name FROM user_tables

