package main.both.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.both.core.utils.Logs;


public class Map extends GameComponent{
	public static int block = 40; //velkos jednécho bloku
	private static int numX; // poèet blokov na šírku
	private static int numY; // poèet blokov na vıšku
	private int[][] mapa;
	private MyPlayer player;
	public String fileName; 
	
	public Map(int numX, int numY, MyPlayer player){
		Logs.write("vytvorila sa nová mapa o velkosti "+numX+" a "+numY+" políèok");
		this.player = player;
		this.numX = numX;
		this.numY = numY;
		mapa = new int[numY][numX];
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numY ; j++){
				mapa[i][j]=(int)(Math.random()*5);
			}
		}
	}
	
	public Map(String fileName, MyPlayer player){
		this.fileName = fileName;
		this.player = player;
		loadMap(fileName);
		Logs.write("vytvorila sa nová zo súboru "+fileName);
	}
	
	private void loadMap(String fileName) {
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader("res/maps/"+fileName+".mp"));
		} catch (FileNotFoundException e1) {e1.printStackTrace(); }
		try {
			String line;
			line = reader.readLine();
			numX = Integer.valueOf(line.split(" ")[0]);
			numY = Integer.valueOf(line.split(" ")[1]);
			mapa = new int[numX][numY];
			int i = 0;
			int j=0;
			while((line = reader.readLine())!=null){
				String[] lines = line.split(" ");
				j=0;
				for(String b:lines){
					mapa[j][i] = Integer.valueOf(b);
					j++;
				}
				
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void render(Graphics2D g2){
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numY ; j++){
				switch(mapa[i][j]){
					case 1:
						g2.setColor(Color.BLUE);
						g2.fillRect((int)(i*block - Player.offset.getX()), (int)(j*block - Player.offset.getY()), block, block);
						break;
					case 0:
						break;
				}
			}
		}
	}
	
	public int getMapa(int x, int y){
		if(x>=0 && x<mapa.length){
			if(y>=0 && y<mapa[x].length){
				return mapa[x][y];
			}
		}
		return 0;
	}

	public static int getNumX() {
		return numX;
	}

	public static int getNumY() {
		return numY;
	}
}
