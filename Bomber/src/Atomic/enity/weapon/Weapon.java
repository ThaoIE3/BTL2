package Atomic.enity.weapon;

import glib.util.GColor;
import glib.util.vector.GVector2f;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Atomic.component.Level;
import Atomic.enity.Enemy;
import Atomic.enity.Player;
import Atomic.map.Block;
import Atomic.map.Map;
import Atomic.object.GameObject;

public class Weapon extends GameObject{
	protected GVector2f dir;
	protected GColor color;
	protected Level level;
	protected float speed;
	protected int size;
	protected int damage;
	protected float stroke;
	
	//OVERRIDES
	
	public void update(float delta){
		setPosition(getPosition().add(dir.mul(speed)));
		
		if(isOutOfView()){
			setDead(true);
		}
		Block b = level.getMap().get(getPosition().add(dir));
		if(b.getType()!=0){
			b.hit(damage);
			setDead(true);
		}
		ArrayList<Enemy> enemies = level.getEnemies();
		for(Enemy e : enemies){
			if(getPosition().isInRect(e.getPosition(),  new GVector2f(Player.WIDTH, Player.HEIGHT))){
				e.hit(damage);
			}
		}
	}	
		
	public void render(Graphics2D g2){
		g2.setColor(color);
		GVector2f pos = getPosition().sub(level.getOffset());
		GVector2f sur  = pos.sub(dir.mul(speed));
		g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		
		g2.drawLine(pos.getXi(), pos.getYi(), sur.getXi(), sur.getYi());
	}

	//GETTERS 
	
	protected boolean isOutOfView() {
		return getPosition().getX() + size <= 0 || getPosition().getX() - size >= Map.NUM_X * Block.WIDTH || 
			   getPosition().getY() + size <= 0 || getPosition().getY() - size >= Map.NUM_Y * Block.HEIGHT;
	}
}


