package Bomberman.game.entities;

import org.json.JSONObject;

import Bomberman.core.XInteractable;
import Bomberman.game.Game;
import Bomberman.game.other.IdGenerator;
import glib.util.vector.GVector2f;

public abstract class XEntity implements XVisible, XInteractable{
	private int id;
	private Game parent;
	protected GVector2f position;
	protected boolean alive;
	
	
	public XEntity(JSONObject json, Game parent){
		this.parent = parent;
		id = json.getInt("id");
		alive = json.getBoolean("alive");
		position = new GVector2f(json.getString("position"));
		
	}
	
	public XEntity(GVector2f position, Game parent){
		this.position = position;
		this.parent = parent;
		alive = true;
		id = IdGenerator.getId();
	}
	
	public abstract String toJSON();
	
	public abstract GVector2f getSur();
	
	public int getID(){
		return id;
	}
	
	@Override
	public GVector2f getPosition() {
		return position;
	}

	@Override
	public abstract GVector2f getSize();

	public boolean isAlive() {
		return alive;
	}

	public Game getParent() {
		return parent;
	}

}
