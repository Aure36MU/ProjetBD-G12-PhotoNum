package src.app;
import java.util.ArrayList;

public class Utilitaire<T>{
	
		public void afficher(ArrayList<T> tab){
			int i = 0;
			while(i<= tab.size()){
				System.out.println(tab.get(i).toString());
				i++;
			}
		}
		
}
