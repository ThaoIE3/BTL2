package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import util.ResourceLoader;
import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Game;
import Bomberman.game.level.Block;
import glib.util.vector.GVector2f;

public class Bomb implements XInteractable, XVisible{
	private long addedAt;
	private int time;
	private GVector2f position;
	private int range;
	private boolean dead;
	private Game parent;
	private int demage;
	private boolean atom = true;
	private int step = 0;
	private int actDelay = 0;
	private Image image;
	private int[] dirRange;
	
	public Bomb(int time, GVector2f position, int range, Game parent) {
		this(time, position, range, parent, false);
	};
	
	
	public Bomb(int time, GVector2f position, int range, Game parent, boolean atom) {
		this.position = position;
		this.parent = parent;
		this.range = range;
		this.time = time;
		this.atom = atom;
		this.image = ResourceLoader.loadTexture("bomb.png");
		demage = Options.BOMB_DEFAULT_DAMAGE;
		addedAt = System.currentTimeMillis();
		dirRange = new int[]{range,range,range,range};
	}
	
	private void drawRange(Graphics2D g2, GVector2f pos){
		if(Options.BOMB_SHOW_RANGE){
			if(atom){
				g2.setColor(new Color(255, 0, 255, 100));
				
				GVector2f area = Block.SIZE.mul(range * 2 + 1);
				GVector2f areaPos = pos.sub(area.div(2)).add(Block.SIZE.div(2));
				g2.fillArc(areaPos.getXi(), areaPos.getYi(), area.getXi(), area.getYi(), 0, 360);
				
				g2.setColor(new Color(255, 0, 255, 255));
				g2.drawArc(areaPos.getXi(), areaPos.getYi(), area.getXi(), area.getYi(), 0, 360);
			}
			else{
				g2.setColor(new Color(255, 0, 255, 100));
				g2.fillRect(pos.getXi() - Block.WIDTH  * dirRange[3], 
						    pos.getYi(), 
						    Block.WIDTH  * dirRange[3] , 
						    Block.HEIGHT);
				g2.fillRect(pos.getXi() + Block.WIDTH, 
						    pos.getYi(), 
						    Block.WIDTH  * dirRange[1], 
						    Block.HEIGHT);
				
				g2.fillRect(pos.getXi(), 
					    	pos.getYi() - Block.HEIGHT * dirRange[0], 
					    	Block.WIDTH, 
					    	Block.HEIGHT * (dirRange[2] + dirRange[0] + 1));
			}
		}
	}
	
	public GVector2f getSur(){
		return position.div(Block.SIZE).toInt();
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(parent.getOffset());
		GVector2f pos2 = pos.add(new GVector2f(Options.BOMB_WIDTH, Options.BOMB_HEIGHT));
		
		drawRange(g2, pos);
		
		
		int xPos = (step%2)*image.getWidth(null)/2;
		g2.drawImage(image, pos.getXi(), pos.getYi(), pos2.getXi(), pos2.getYi(), xPos, 0, xPos + image.getWidth(null)/2, image.getHeight(null), null);

		checkTiming();
	}
	
	private void checkTiming(){
		if(actDelay == 0){
			actDelay = Options.BOMB_DEFAULT_DELAY;
			if(step == 2)
				step = 0;
			step++;
		}
		else
			actDelay--;
	}
	
	@Override
	public void update(float delta) {
		if(System.currentTimeMillis() - addedAt >= time)
			explode();
	}
	
	public void explode(){
		dead = true;
		parent.getConnection().bombExplode(this);
	}

	public GVector2f getPosition() {
		return position;
	}

	public boolean isDead() {
		return dead;
	}
	
	@Override
	public String toString() {
		return "position: " + getPosition() + " range: " + range;
	}

	public int getRange() {
		return range;
	}

	public boolean isAtom() {
		return atom;
	}

	public int getDemage() {
		return demage;
	}

	@Override
	public GVector2f getSize() {
		return new GVector2f(Options.BOMB_WIDTH, Options.BOMB_HEIGHT);//.add(Block.SIZE.mul(range * 2));
	}
}
