Create trigger Inc_User_Contrainte Before insert on Utilisateur for each row BEGIN Select idUser_seq.nextval 	into :new.idUser From dual;END;/