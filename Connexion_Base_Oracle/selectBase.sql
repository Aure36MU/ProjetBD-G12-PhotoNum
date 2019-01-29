select * from Utilisateur where email = 'a@b.com'
select * from Impression
select * from tirage
SELECT max(idImp) FROM Impression
SELECT max(idUser) FROM Utilisateur
select * from Impression where type='CALENDRIER'
(SELECT idImp FROM Impression i WHERE i.idUser=2 and i.type='CALENDRIER') MINUS (SELECT idImp FROM Article NATURAL JOIN Impression WHERE type='CALENDRIER')
SELECT table_name FROM user_tables