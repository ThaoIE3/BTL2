package Bomberman.game.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import org.json.JSONObject;

import util.ResourceLoader;
import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.game.Game;
import Bomberman.game.level.Block;

public class XItem extends XEntity{
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
	private int type;
	private Image image;
	
	public XItem(JSONObject object, Game parent){
		this(new GVector2f(object.getString("position")), 
		     object.getInt("type"),
		     parent);
	};
	
	public XItem(GVector2f position,int type, Game parent) {
		super(position, parent);
		this.type = type;
		image = images.get(type);
	}

	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("id", getID());
		result.put("alive", alive);
		result.put("position", position);
		
		result.put("type", type);
		
		return result.toString();
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset());
		g2.drawImage(image, pos.getXi(), pos.getYi(), Options.ITEM_DEFAULT_WIDTH, Options.ITEM_DEFAULT_HEIGHT, null);
	}
	
	public int getType() {return type;}

	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE.mul(getParent().getZoom())).toInt();
	}

	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}

}
