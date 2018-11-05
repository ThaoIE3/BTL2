package Bomberman.game.other;

import java.util.Set;
import java.util.TreeSet;

public class IdGenerator {
	private static Set<Integer> ides = new TreeSet<Integer>();
	
	public static int getId(){
		int result;
		do{
			result = (int)(Math.random() *  2147483647);
		}while(ides.contains(result));
		
		ides.add(result);
		
		return result;
	}
}
