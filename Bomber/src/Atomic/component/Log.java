package Atomic.component;

import java.awt.Color;
import java.awt.Graphics2D;

import Atomic.map.Map;
import Atomic.object.GameObject;

public class Log extends GameObject{
	private Level level;
	private int fps;
	
	//CONSTRUCTORS
	
	public Log(Level level){
		this.level = level;
	}
	
	//OVERRIDES
	
	public void render(Graphics2D g2){
		if(level==null)
			return;
		String[] logs = new String[]{"FPS: "+fps,
									 "Nepriatelove: "+level.getNumOfEnemies(),
									 "Striel: "+level.getNumOfWeapons(),
									 "Bômb: "+level.getNumOfBombs(),
									 "Èastíc: "+level.getNumOfParticles(),
									 "Blokov: "+level.getMap().getDrawable()+" / "+Map.NUM_X*Map.NUM_Y,
									// "Znièitelných blokov: "+level.getMap().getDestructible(),
									 "Pozicia hráèa: "+level.getPlayer().getPosition().toDecimal(5),
									 "offset: "+level.getOffset().toDecimal(5)
								};
		
		int max = logs[0].length();
    	for(int i=1 ; i<logs.length ; i++)
    		if(logs[i].length() > max)
    			max = logs[i].length();
		
		g2.setColor(new Color(0,0,0,127));
		g2.fillRect(0, 0, max*6, logs.length*10+10);
		
		g2.setColor(Color.WHITE);
		for(int i=0 ; i<logs.length ; i++){
			g2.drawString(logs[i], 0, i*10+10);
		}
		
	}

	//SETTERS
	
	public void setLevel(Level level) {
		this.level = level;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}
}
