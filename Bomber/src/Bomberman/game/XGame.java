package Bomberman.game;

import glib.util.vector.GVector2f;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.util.HashMap;

import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.entities.Bomb;
import Bomberman.game.entities.Item;
import Bomberman.game.level.Block;
import Bomberman.game.level.Level;
import Bomberman.game.multiplayer.Communicable;
import Bomberman.gui.Logger;

public class XGame implements XInteractable{
	private CoreGame parent;
	private Level level;
	private Communicable connection;
	private float zoom = 1f;
	private boolean ready;
	
	public XGame(CoreGame parent){
		this.parent = parent;
		connection = parent.getConnection();
		do{
			level = connection.getLevel();
		}while(level == null);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		//myPlayer = new MyPlayer(connection.getMyPosition(), parent.getCanvas(), this);
		//info = new PlayerInfo(myPlayer);
		
//		parent.getConnection().sendMyImage();
	}
	
	public void render(Graphics2D g2) {
		level.render(g2);
	}
	
	public void changeZoom(float value){
		if(!Options.ALLOW_ZOOMING)
			return;
		
		zoom += value;
		
		if(level.getMap().getNumberOfBlocks().getX() * Block.WIDTH * zoom < parent.getCanvas().getWidth()){
			zoom -= value; 
			return;
		}
		
		if(zoom > 5)
			zoom -= value;
	}
	

	public void checkReady(){
		ready = connection.isReady();
	}

	public void cleanUp() {
		level.cleanUp();
	}
	
	public float getZoom() {return zoom;}
	public Level getLevel() {return level;}
	public boolean isReady() {return ready;}
	public Canvas getCanvas(){return parent.getCanvas();}
	public Communicable getConnection() {return connection;}
	
}
