package main2.server.component;

import java.awt.Graphics2D;

public class Map {
	private static int numX;
	private static int numY;
	private Block[][] blocks;
	
	public Map(){
		
	}
	
	public void render(Graphics2D g2){
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numY ; j++){
				blocks[i][j].render(g2);
			}
		}
	}
}
