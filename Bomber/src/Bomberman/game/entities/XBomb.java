package Bomberman.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import org.json.JSONObject;

import util.ResourceLoader;
import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.game.Game;
import Bomberman.game.level.Block;

public class XBomb extends XEntity{
	private long addedAt;
	private int time;
	private int range;
	private int demage;
	private boolean atom = true;
	private int step = 0;
	private int actDelay = 0;
	private Image image;
	private int[] dirRange;
	
	public XBomb(JSONObject object, Game parent) {
		this(object.getInt("time"),
			 new GVector2f(object.getString("position")),
			 object.getInt("range"),
			 parent,
			 object.getBoolean("atom"),
			 object.getInt("demage"),
			 new int[]{object.getInt("dirRange0"),
					   object.getInt("dirRange1"),
					   object.getInt("dirRange2"),
					   object.getInt("dirRange3")});
	}
	
	public XBomb(int time, GVector2f position, int range, Game parent, boolean atom) {
		this(time,
			 position,
			 range,
			 parent,
			 atom,
			 Options.BOMB_DEFAULT_DAMAGE,
			 new int[]{range,range,range,range});
	}
	
	public XBomb(int time, GVector2f position, int range, Game parent, boolean atom, int demage, int[] dirRange) {
		super(position, parent);
		this.dirRange = dirRange;
		this.demage = demage;
		this.range = range;
		this.time = time;
		this.atom = atom;
		this.image = ResourceLoader.loadTexture("bomb.png");
		addedAt = System.currentTimeMillis();
	}

	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("id", getID());
		result.put("alive", alive);
		result.put("position", position);
		
		result.put("addedAt", addedAt);
		result.put("time", time);
		result.put("range", range);
		result.put("demage", demage);
		result.put("atom", atom);
//		result.put("step", step);
//		result.put("actDelay", actDelay);
		result.put("dirRange0", dirRange[0]);
		result.put("dirRange1", dirRange[1]);
		result.put("dirRange2", dirRange[2]);
		result.put("dirRange3", dirRange[3]);
		
		return result.toString();
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
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = position.sub(getParent().getOffset());
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
	
	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE).toInt();
	}
	
	public void explode(){
		alive = false;
//		getParent().getConnection().bombExplode(this);
	}

	@Override
	public GVector2f getSize() {
		return new GVector2f(Options.BOMB_WIDTH, Options.BOMB_HEIGHT);
	}

	public int getRange() {
		return range;
	}

	public int getDemage() {
		return demage;
	}

}
