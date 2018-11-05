package Bomberman.game.level;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;
import java.io.Serializable;

import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.CoreGame;
import Bomberman.game.Game;

public class Level implements XInteractable, Serializable{
	private static final long serialVersionUID = 1L;
	
	private Map map;
	private transient CoreGame parent;
	private int maxPlayers;
	private int defalutSpeed;
	
	public Level(CoreGame parent){
		this.parent = parent;
		defalutSpeed = Options.PLAYER_DEFAULT_SPEED;
		map = new Map(this);
		maxPlayers = 2;
	}
	
	@Override
	public void render(Graphics2D g2) {
		map.render(g2);
	}

	public GVector2f getNthPlayerDefaltPosition(int nth){
		GVector2f block = new GVector2f(Block.WIDTH, Block.HEIGHT);
		
		switch(nth){
			case 1:
				block = new GVector2f(Block.WIDTH, 0);
				return map.getNumberOfBlocks().mul(block).sub(block).add(new GVector2f(-Block.WIDTH, Block.HEIGHT));
			case 2:
				block = new GVector2f(0, Block.HEIGHT);
				return map.getNumberOfBlocks().mul(block).sub(block).add(new GVector2f(Block.WIDTH, -Block.HEIGHT));
			case 3:
				return map.getNumberOfBlocks().mul(block).sub(block);
			default:
				return block;
		}
	}
	
	public void cleanUp() {
		
	}
	
	public GVector2f getOffset(){
		return parent.getGame().getOffset();
	}

	public Map getMap() {
		return map;
	}

	public Game getParrent() {
		return parent.getGame();
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getDefalutSpeed() {
		return defalutSpeed;
	}

	public void setParent(CoreGame parent) {
		this.parent = parent;
		
	}

}
