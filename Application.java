
public class Application {

	public static void main(String[] args) {
		System.out.println("Souhaitez-vous, vous connectez ou vous inscrire? ");
		int i = LectureClavier.lireEntier("connecter: 1/inscrire: 2");
		if(i==1) {
			char mailConnect= LectureClavier.lireChar("Quel est votre adresse mail?");
			char mdp= LectureClavier.lireChar("Quel est votre mot de passe?");
			System.out.println("Vous voilà connecter ");
		}else {
			if(i==2) {
				System.out.println("Bienvenue sur le site PhotoNum! Nous allons vous demander quelque information pour la création de votre compte");
				char mailInsc= LectureClavier.lireChar("Inscrire votre adresse mail:");
				char mdpInsc= LectureClavier.lireChar("Inscrire votre mot de passe:");
				char nomInsc= LectureClavier.lireChar("Inscrire votre nom:");
				char prenomInsc= LectureClavier.lireChar("Inscrire votre prenom:");
				System.out.println("Bienvenue à vous " + nomInsc + " "+ prenomInsc);
			}else {
				System.out.println("Vous n'avez pas choisi entre connecter: 1 et inscrire: 2 ");
			}
		}
		
		
		
		/*
		 * Partie de code pour charger un jeu de test 
		 * 
		 * charger fichier... chaque requete sur une ligne
		 * for ([all lines in file]) {
		 * String line = [une ligne];
		 * String keyword = line.split(" ")[0];
		 * switch (keyword) {
		 * 	case "INSERT": smth.executeUpdate(line);
		 * 	case "UPDATE": smth.executeUpdaet(line);
		 * 	case "SELECT": smth.executeQuery(line);
		 * 	case "DELETE": smth.executeUpdate(line);
		 * }
		 * }
		 */
	}
	

}
