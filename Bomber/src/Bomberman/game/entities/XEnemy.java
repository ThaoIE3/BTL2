package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import org.json.JSONObject;

import Bomberman.Options;
import Bomberman.game.Game;
import Bomberman.game.Utils;
import Bomberman.game.level.Block;
import Bomberman.game.level.Map;
import glib.util.vector.GVector2f;

public class XEnemy extends XEntity{
	private static GVector2f size = new GVector2f(Options.ENEMY_DEFAULT_WIDTH  - 2 * Options.ENEMY_DEFAULT_OFFSET, 
			  									  Options.ENEMY_DEFAULT_HEIGHT - 2 * Options.ENEMY_DEFAULT_OFFSET);;
	private int direction;
	private int speed;
	private int bulletSpeed;
	private int cadence;
	private long lastShot;
	private int attack;
	private int healt;
	
	
	private Color color;
	
	public XEnemy(JSONObject object, Game parent){
		this(new GVector2f(object.getString("position")), 
			 parent,
			 object.getInt("speed"),
			 object.getInt("bulletSpeed"),
			 object.getInt("cadence"),
			 object.getInt("attack"),
			 object.getLong("lastShot"),
			 object.getInt("direction"),
			 object.getInt("halt")
		);
	}
	
	public XEnemy(GVector2f position, Game parent) {
		this(position, 
			 parent, 
			 Options.ENEMY_DEFAULT_SPEED, 
			 Options.ENEMY_DEFAULT_BULLET_SPEED, 
			 Options.ENEMY_DEFAULT_CADENCE, 
			 Options.ENEMY_DEFAULT_ATTACK,
			 Options.ENEMY_DEFAULT_HEALT);
	}
	
	public XEnemy(GVector2f position, Game parent, int speed, int bulletSpeed, int cadence, int attack, int healt){
		this(position,
			 parent, 
			 speed, 
			 bulletSpeed, 
			 cadence, 
			 attack,
			 System.currentTimeMillis() - cadence, 
			 -2,
			 healt);
	}
	
	public XEnemy(GVector2f position, Game parent, int speed, int bulletSpeed, int cadence, int attack, long lastShot, int direction, int healt){
		super(position, parent);
		
		this.bulletSpeed = bulletSpeed;
		this.direction = direction;
		this.lastShot = lastShot;
		this.position = position;
		this.cadence = cadence;
		this.attack = attack;
		this.speed = speed;
		this.healt = healt;
		
		color = Color.CYAN;
		
		if(direction == -2)
			this.direction = getRandPossibleDir(parent.getLevel().getMap());
	}

	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("id", getID());
		result.put("alive", alive);
		result.put("position", position);
		
		result.put("direction", direction);
		result.put("speed", speed);
		result.put("bulletSpeed", bulletSpeed);
		result.put("cadence", cadence);
		result.put("lastShot", lastShot);
		result.put("attack", attack);
		
		return result.toString();
	}

	private int getRandPossibleDir(Map mapa){
		int[] ret = mapa.getPossibleWays(getSur());
		
		int dir = -1;
		
		if(ret.length > 0)
			dir = ret[(int)(Math.random() * ret.length)];
		
		return dir;
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset()).add(Options.ENEMY_DEFAULT_OFFSET);
		
		
		g2.setColor(color);
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.ENEMY_DEFAULT_ROUND, Options.ENEMY_DEFAULT_ROUND);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), Options.ENEMY_DEFAULT_ROUND, Options.ENEMY_DEFAULT_ROUND);
	}
	
	//dorobi
	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE.mul(getParent().getZoom())).toInt();
	}
	
	//dorobi
	@Override
	public GVector2f getSize() {
		return Block.SIZE;
	}
	
	@Override
	public void update(float delta) {
		if(position.mod(Block.SIZE).isNull())
			direction = getRandPossibleDir(getParent().getLevel().getMap());
		
		if(direction == -1)
			return;
		
		position = position.add(Utils.getMoveFromDir(direction).mul(speed));
	}
	
	public void fire() {
		if(direction >= 0 && (System.currentTimeMillis() - lastShot) >= cadence){
			getParent().addBullet(position, direction, bulletSpeed, attack, Options.BULLET_DEFAULT_HEALT);
			lastShot = System.currentTimeMillis();
		}
	} 
}
