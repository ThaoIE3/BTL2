package Atomic.map;

import glib.util.GColor;
import glib.util.vector.GVector2f;

import java.awt.Color;
import java.awt.Graphics2D;

import Atomic.component.Explosion;
import Atomic.component.Level;
import Atomic.object.GameObject;

public class Block extends GameObject{
	public final static int WIDTH = 40;
	public final static int HEIGHT = 40;
	private Color color;
	private Level level;
	private int health;
	private int type;
	
	//CONSTRUCTORS
	
	public Block(GVector2f position, int type, Level level){
		super(position);
		this.level = level;
		this.type = type;
		this.health = 100;
		color = GColor.randomize(40, Color.GREEN);
		
		if(type==0)
			color = new GColor(0,0,0,0);
		else if(type == 1)
			color = GColor.randomize(50, Color.GREEN);
		else if(type == 2)
			color = GColor.randomize(50, Color.PINK);
		else if(type == 3)
			color = GColor.randomize(50, Color.CYAN);
		else if(type == 4)
			color = GColor.randomize(50, Color.DARK_GRAY);
	}
	
	//OTHERS
	
	public void hit(int val){
		health-=val;
		color = color.darker();
		if(health <= 0 || (color.getRed()==0 && color.getGreen()==0 && color.getBlue()==0)){
			Explosion e = new Explosion(getPosition(),(int)(Math.random()*3),level,20-(int)(Math.random()*6)-3);
			level.addExplosion(e);
			type = 0;
		}
	}
	
	//OVERRIDES
	
	public void render(Graphics2D g2){
		if(type==0)
			return;
		
		GVector2f pos = getPosition().sub(level.getOffset());
		g2.setColor(color);
		g2.fill3DRect(pos.getXi(), pos.getYi(), WIDTH, HEIGHT, true);
	}

	//GETTERS
	
	public int getType() {
		return type;
	}

}
