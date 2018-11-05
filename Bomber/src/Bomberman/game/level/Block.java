package Bomberman.game.level;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import java.util.HashMap;

import util.ResourceLoader;
import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.entities.XVisible;

public class Block implements XInteractable, Serializable, XVisible{
	private static final long serialVersionUID = 1L;

	public final static int GRASS 	= 100;
	public final static int WOOD 	= 101;
	public final static int IRON 	= 102;
	public final static int FORREST = 103;
	public final static int WATER 	= 104;
	public final static int TILE 	= 105;
	public final static int ROCK 	= 106;
	
	private static HashMap<Integer, Image> images = new HashMap<Integer, Image>();
	
	static{
		images.put(WOOD, ResourceLoader.loadTexture("block_wood_64.jpg"));
		images.put(GRASS, ResourceLoader.loadTexture("block_grass_64.jpg"));
		images.put(IRON, ResourceLoader.loadTexture("block_iron_64.jpg"));
	}
	
	
	public final static int NOTHING = 0;
	public final static int DESTRUCTABLE = 1;
	public final static int INDESTRUCTIBLE = 2;
	
	public final static int WIDTH = Options.BLOCK_DEFAULT_WIDTH;
	public final static int HEIGHT = Options.BLOCK_DEFAULT_HEIGHT;
	public final static GVector2f SIZE = new GVector2f(40, 40);

	private GVector2f position;
	private int type;	
	private int healt;
	private Map parent;
	
	//CONSTRUCTORS
	
	public Block(GVector2f position, int type, Map parent) {
		this(position, type, parent, Options.BLOCK_DEFAULT_HEALT);
	}
	
	public Block(GVector2f position, int type, Map parent, int healt) {
		this.position = position;
		this.parent = parent;
		this.healt = healt;
		this.type = type;
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		if(type == NOTHING)
			return;
		
		GVector2f pos = position.mul(SIZE).sub(parent.getOffset());
		
		g2.drawImage(images.get(type + 100), pos.getXi() , pos.getYi(), WIDTH, HEIGHT, null);
	}

	@Override
	public String toString() {
		return "position: " + getPosition() + " type: " + getType() + " healt: " + getHealt();
	}
	
	public boolean hit(int demage){
		healt -= demage;
		return healt <= 0;
	}
	
	//SETTERS
	
	public void setHealt(int healt) {this.healt = healt;}

	//GETTERS
	
	public GVector2f getPosition(){
		float zoom = parent.getParent().getParrent().getZoom();
		GVector2f finalSize = SIZE.mul(zoom);
		return  position.mul(finalSize);
	}

	@Override
	public GVector2f getSize() {return SIZE;}
	public int getType() {return type;}
	public int getHealt() {return healt;}
	public void setType(int type) {this.type = type;}

}
