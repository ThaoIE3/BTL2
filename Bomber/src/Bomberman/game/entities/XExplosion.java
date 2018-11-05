package Bomberman.game.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.geom.AffineTransform;

import org.json.JSONObject;

import util.ResourceLoader;
import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.game.Game;
import Bomberman.game.level.Block;

public class XExplosion extends XEntity{
	private Image image;
	private GVector2f num;
	private int offset;
	GVector2f size;
	private int delay;
	private int actDelay;
	private int frame;
	private int numOfImages;
	private GVector2f imageSize;
	private int angle;
	private String imageName;

	public XExplosion(JSONObject object, Game parent){
		this(new GVector2f(object.getString("position")),
			 parent,
			 object.getString("imageName"),
			 new GVector2f(object.getString("num")),
			 object.getInt("offset"),
			 object.getInt("delay"));
	}
	
	public XExplosion(GVector2f position, Game parent, String imageName, GVector2f num, int offset, int delay) {
		super(position, parent);
		this.num = num;
		this.offset = offset;
		this.imageName = imageName;
		this.delay = actDelay = delay;
		this.size = new GVector2f(Options.EXPLOSION_DEFAULT_WIDTH, Options.EXPLOSION_DEFAULT_HEIGHT).add(offset * 2);
		this.image = ResourceLoader.loadTexture(imageName);
		imageSize = new GVector2f(image.getWidth(null), image.getHeight(null)).div(num);
		numOfImages = num.getXi() * num.getYi();
		frame = 0;
		angle = (int)(Math.random() * 360);
	}

	@Override
	public void update(float delta) {
		if(actDelay == 0){
			frame++;
			actDelay = delay;
		}
		else
			actDelay--;
	}
	@Override
	
	public void render(Graphics2D g2){
		if(!alive)
			return;
		
		if(frame == numOfImages)
			alive = false;
		
		GVector2f pos = new GVector2f(frame % num.getX(), frame / num.getY()).toInt().mul(imageSize);
		GVector2f pos2 = pos.add(imageSize);
		
		GVector2f tempPos = position.sub(getParent().getOffset()); 
		
		tempPos = tempPos.add(size.div(2));
		AffineTransform saveAT = g2.getTransform();
		g2.translate(tempPos.getXi(), tempPos.getYi());
		g2.rotate(angle);
		g2.drawImage(image, 
				-Options.EXPLOSION_DEFAULT_WIDTH, -Options.EXPLOSION_DEFAULT_HEIGHT,
				Options.EXPLOSION_DEFAULT_WIDTH, Options.EXPLOSION_DEFAULT_HEIGHT, 
				pos.getXi(), pos.getYi(), 
				pos2.getXi(), pos2.getYi(), 
				null);
		g2.setTransform(saveAT);
		
	}
	
	
	
	@Override
	public String toJSON() {
		JSONObject result = new JSONObject();
		
		result.put("id", getID());
		result.put("alive", alive);
		result.put("position", position);
		
		result.put("num", num);
		result.put("offset", offset);
		result.put("delay", delay);
		result.put("imageName", imageName);
		
		return result.toString();
	}

	@Override
	public GVector2f getSur() {
		return position.div(Block.SIZE.mul(getParent().getZoom())).toInt();
	}

	@Override
	public GVector2f getSize() {
		return size;
	}

}
