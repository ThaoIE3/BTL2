package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import org.json.JSONObject;

import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.game.Game;
import Bomberman.game.Utils;
import Bomberman.game.level.Block;

public class XBullet extends XEntity{
	private static GVector2f size = new GVector2f(Options.BULLET_DEFAULT_WIDTH  - 2 * Options.BULLET_DEFAULT_OFFSET, 
			  									  Options.BULLET_DEFAULT_HEIGHT - 2 * Options.BULLET_DEFAULT_OFFSET);
	private int direction;
	private int speed;
	private int healt;
	private int demage;
	private Color color;
	
	public XBullet(GVector2f position, Game parent, int direction, int speed, int healt, int demage){
		super(position, parent);
		this.direction = direction;
		this.demage = demage;
		this.speed = speed;
		this.healt = healt;
	}
	public XBullet(JSONObject object, Game parent) {
		this(new GVector2f(object.getString("position")),
			 parent,
			 object.getInt("direction"),
			 object.getInt("speed"),
			 object.getInt("healt"),
			 object.getInt("demage"));
	}

	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset()).add(Options.BULLET_DEFAULT_OFFSET);
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.BULLET_DEFAULT_ROUND, Options.BULLET_DEFAULT_ROUND);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.BULLET_DEFAULT_ROUND, Options.BULLET_DEFAULT_ROUND);
	}
	
	@Override
	public void update(float delta) {
		Block block = getParent().getLevel().getMap().getBlockOnPosition(getPosition().add(Block.SIZE.div(2)));
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
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("id", getID());
		result.put("alive", alive);
		result.put("position", position);
		
		result.put("direction", direction);
		result.put("speed", speed);
		result.put("demage", demage);
		result.put("healt", healt);
		
		return result.toString();
	}

	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE).toInt();
	}

	@Override
	public GVector2f getSize() {
		return size;
	}

}
