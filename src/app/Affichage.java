package src.app;
import java.util.ArrayList;

public class Affichage<T>{
	
		public void afficher(ArrayList<T> tab){
			int i = 0;
			System.out.println("****************");
			System.out.println(tab.get(i).getClass().getSimpleName());
			System.out.println("****************");
			while(i< tab.size()){
				System.out.println(tab.get(i).toString());
				System.out.println("");
				i++;
				
			}
		}
		
}
