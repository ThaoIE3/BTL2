package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Game;
import Bomberman.game.Utils;
import Bomberman.game.level.Block;
import Bomberman.game.level.Map;

public class Enemy implements XInteractable, XVisible{
	private static GVector2f size = new GVector2f(Options.ENEMY_DEFAULT_WIDTH  - 2 * Options.ENEMY_DEFAULT_OFFSET, 
			  									  Options.ENEMY_DEFAULT_HEIGHT - 2 * Options.ENEMY_DEFAULT_OFFSET);
	private GVector2f position;
	private int direction;
	private int speed;
	private int bulletSpeed;
	private int cadence;
	private long lastShot;
	private int attack;
	private Game parent;
	private Color color;
	
	public Enemy(GVector2f position, Game parent) {
		this(position, parent, Options.ENEMY_DEFAULT_SPEED, Options.ENEMY_DEFAULT_BULLET_SPEED, Options.ENEMY_DEFAULT_CADENCE, Options.ENEMY_DEFAULT_ATTACK);
	}
	
	public Enemy(GVector2f position, Game parent, int speed, int bulletSpeed, int cadence, int attack) {
		this.bulletSpeed = bulletSpeed;
		this.position = position;
		this.cadence = cadence;
		this.parent = parent;
		this.attack = attack;
		this.speed = speed;
		color = Color.CYAN;
		setRandPossibleDir(parent.getLevel().getMap());
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(parent.getOffset()).add(Options.ENEMY_DEFAULT_OFFSET);
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.ENEMY_DEFAULT_ROUND, Options.ENEMY_DEFAULT_ROUND);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.ENEMY_DEFAULT_ROUND, Options.ENEMY_DEFAULT_ROUND);
	}
	
	private void setRandPossibleDir(Map mapa){
		int[] ret = mapa.getPossibleWays(getSur());
		
		if(ret.length > 0)
			direction = ret[(int)(Math.random() * ret.length)];
		else 
			direction = -1;
	}

	public GVector2f getSur(){
		return position.div(Block.SIZE).toInt();
	}
	
	
	
	@Override
	public void update(float delta) {
		if(position.mod(Block.SIZE).isNull())
			setRandPossibleDir(parent.getLevel().getMap());
		
		if(direction == -1)
			return;
		
		position = position.add(Utils.getMoveFromDir(direction).mul(speed));
	}

	@Override
	public GVector2f getPosition() {
		return position;
	}
	
	@Override
	public GVector2f getSize() {
		return new GVector2f(Options.ENEMY_DEFAULT_WIDTH, Options.ENEMY_DEFAULT_HEIGHT);
	}

	public void fire() {
		if(direction >= 0 && (System.currentTimeMillis() - lastShot) >= cadence){
			parent.addBullet(position, direction, bulletSpeed, attack, Options.BULLET_DEFAULT_HEALT);
			lastShot = System.currentTimeMillis();
		}
	} 
}
