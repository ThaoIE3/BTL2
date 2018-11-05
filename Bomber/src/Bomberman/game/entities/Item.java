package Bomberman.game.entities;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import util.ResourceLoader;
import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Game;

public class Item implements XInteractable, XVisible{
	public final static int INCREASE_BOMBS = 0;
	public final static int INCREASE_HEALT = 1;
	public final static int INCREASE_SPEED = 2;
	public final static int INCREASE_RANGE = 3;
	private static HashMap<Integer, Image> images = new HashMap<Integer, Image>(); 
	
	static{
		images.put(INCREASE_HEALT, ResourceLoader.loadTexture("item_medikit.png"));
		images.put(INCREASE_SPEED, ResourceLoader.loadTexture("item_speed.png"));
		images.put(INCREASE_RANGE, ResourceLoader.loadTexture("item_plamen.png"));
		images.put(INCREASE_BOMBS, ResourceLoader.loadTexture("item_bomba.png"));
	}
	
	private Image image;
	private GVector2f position;
	private Game parent;
	private int type;
	
	//CONSTRUCTORS
	
	public Item(GVector2f position, int type, Game parent) {
		this.position = position;
		this.parent = parent;
		this.type = type;
		image = images.get(type);
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(parent.getOffset());
		g2.drawImage(image, pos.getXi(), pos.getYi(), Options.ITEM_DEFAULT_WIDTH, Options.ITEM_DEFAULT_HEIGHT, null);
	}

	//GETTERS

	public int getType() {return type;}
	@Override
	public GVector2f getPosition() {return position;}
	@Override
	public GVector2f getSize() {return new GVector2f(Options.ITEM_DEFAULT_WIDTH, Options.ITEM_DEFAULT_HEIGHT);}

}
