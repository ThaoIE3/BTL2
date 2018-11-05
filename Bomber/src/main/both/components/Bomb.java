package main.both.components;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bomb extends GameComponent{
	private int x,y;
	private double add,time=3000;
	private int dist;
	private MyPlayer player;
	
	public Bomb(float x,float y,MyPlayer player){
		this.x = (int)(Math.round(x/Map.block)/**Map.block*/);
		this.y = (int)(Math.round(y/Map.block)/**Map.block*/);
		add = System.currentTimeMillis();
		//this.dist = dist;
		this.player = player;
		//System.out.println("vytvorila sa bomba v "+add);
	}
	
	public void render(Graphics2D g2){
		g2.setColor(Color.BLACK);
		g2.fillRect((int)(x*Map.block-Player.offset.getX()),(int)(y*Map.block-Player.offset.getY()),Map.block,Map.block);
		
//		for(int i=1 ; i<=dist ; i++){
//			if(Map.mapa[x][y+i]==0){
//				g2.setColor(Color.white);
//				g2.fillRect((int)(x*Map.block-player.offset.getX()),(int)((y+i)*Map.block-player.offset.getY()),Map.block,Map.block);
//			}
//			if(Map.mapa[x][y-i]==0){
//				g2.setColor(Color.white);
//				g2.fillRect((int)(x*Map.block-player.offset.getX()),(int)((y-i)*Map.block-player.offset.getY()),Map.block,Map.block);
//			}
//			if(Map.mapa[x+i][y]==0){
//				g2.setColor(Color.white);
//				g2.fillRect((int)((x+i)*Map.block-player.offset.getX()),(int)((y)*Map.block-player.offset.getY()),Map.block,Map.block);
//			}
//			if(Map.mapa[x-i][y]==0){
//				g2.setColor(Color.white);
//				g2.fillRect((int)((x-i)*Map.block-player.offset.getX()),(int)((y)*Map.block-player.offset.getY()),Map.block,Map.block);
//			}
//		}
		
//		if(checkExplosion()){
//			startExplosion();
//			return true;
//		}
//		return false;
	}
	
	private void startExplosion() {
//		//System.out.println("BUM");
//		Map.mapa[x][y] = 0;
//		for(int i=0 ; i<=dist ; i++){
//			Map.mapa[x][y+i] = 0;
//			//if(Map.mapa[x].length<y-i)
//			Map.mapa[x][y-i] = 0;
//			Map.mapa[x+i][y] = 0;
//			Map.mapa[x-i][y] = 0;
//		}
	}

	public boolean checkExplosion(){
		if(System.currentTimeMillis()-add>time){
			return true;
		}
		return false;
	}
}
