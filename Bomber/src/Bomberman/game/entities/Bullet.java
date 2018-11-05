package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Game;
import Bomberman.game.Utils;
import Bomberman.game.level.Block;

public class Bullet implements XInteractable, XVisible{
	private static GVector2f size = new GVector2f(Options.BULLET_DEFAULT_WIDTH  - 2 * Options.BULLET_DEFAULT_OFFSET, 
			  									  Options.BULLET_DEFAULT_HEIGHT - 2 * Options.BULLET_DEFAULT_OFFSET);
	private GVector2f position;
	private int direction;
	private int speed;
	private Game parent;
	private int healt;
	private int demage;
	private boolean alive;
	private Color color;

	public Bullet(GVector2f position, int direction, int speed, int demage, Game parent) {
		this.direction = direction;
		this.position = position;
		this.demage = demage;
		this.parent = parent;
		this.speed = speed;
		healt = 1;
		alive = true;
		color = Color.yellow;
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(parent.getOffset()).add(Options.BULLET_DEFAULT_OFFSET);
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.BULLET_DEFAULT_ROUND, Options.BULLET_DEFAULT_ROUND);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.BULLET_DEFAULT_ROUND, Options.BULLET_DEFAULT_ROUND);
	}
	
	@Override
	public void update(float delta) {
		Block block = parent.getLevel().getMap().getBlockOnPosition(getPosition().add(Block.SIZE.div(2)));
		if(block != null && block.getType() != Block.NOTHING){
			healt--;
			block.hit(demage);
		}
		
		if(healt <= 0)
			alive = false;
		
		
		if(alive)
			position = position.add(Utils.getMoveFromDir(direction).mul(speed));
		
	}
	
	@Override
	public GVector2f getPosition() {
		return position;
	}

	@Override
	public GVector2f getSize() {
		return size;
	}

	public boolean isAlive() {
		return alive;
	}

}
