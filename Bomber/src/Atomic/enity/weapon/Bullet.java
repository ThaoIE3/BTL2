package Atomic.enity.weapon;

import glib.util.GColor;
import glib.util.vector.GVector2f;

import java.awt.Color;

import Atomic.component.Level;
import Atomic.enity.Player;

public class Bullet extends Weapon{
	public final static int WIDTH = 4;
	public final static int HEIGHT = 4;
	public final static int STROKE = 4;
	
	public final static int CADENCE = 100;
	
	//CONSTRUCTORS
	
	public Bullet(Level level, GVector2f dir, int accuracy, int demage){
		setPosition(level.getPlayer().getPosition().add(new GVector2f(Player.WIDTH/2, Player.HEIGHT/2)));
		this.level = level;
		this.dir = dir.add(new GVector2f((float)Math.random()/accuracy, (float)Math.random()/accuracy));
		this.damage = demage;
		init();
	}
	
	//OTHERS
	
	private void init(){
		speed = 15+(float)(Math.random()*10-5);
		size = 5;
		stroke = STROKE-1 + (float)Math.random()*STROKE-STROKE/2;
		color = GColor.randomize(100, Color.YELLOW);
	}
}
