package Bomberman.game.other;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;

import util.ResourceLoader;

public class SpritesAnimation {
	private static HashMap<String, SpritesAnimation> animations = new HashMap<String, SpritesAnimation>(); 
	
	private Image image;
	private int numberOfImages;
	private int numberOfSteps;
	private int step;
	private int delay;
	private int actDelay;
	private GVector2f imageSize;
	
	public SpritesAnimation(String name, int numX, int numY,int positions, int delay) {
		this.image = ResourceLoader.loadTexture(name);
		this.delay = delay;
		numberOfImages = numX * numY;
		actDelay = delay;
		step = 0;
		numberOfSteps = numberOfImages / positions ;
		imageSize = new GVector2f(image.getWidth(null) / numX, image.getHeight(null) / numY);
	}
	
	public static void setSprite(String name,  int numX, int numY, int positions, int delay){
		animations.put(name, new SpritesAnimation(name, numX, numY, positions, delay));
	}

	
	public static void drawPlayer(GVector2f position, GVector2f size, Graphics2D g2, int type, String name, boolean isRunning){
		SpritesAnimation sprite = animations.get(name);
		
		if(sprite == null)
			return;
		
		GVector2f position2 = position.add(size);
		int sourceX = sprite.step * sprite.imageSize.getXi();
		int sourceY = type * sprite.imageSize.getXi();
		
		if(!isRunning)
			sourceX = 0;
		
		g2.drawImage(sprite.image, 
					 position.getXi(), position.getYi(), 
					 position2.getXi(), position2.getYi(),
				     sourceX, sourceY, 
				     sourceX + sprite.imageSize.getXi(), sourceY + sprite.imageSize.getYi(),
				     null);
		
		checkTiming(sprite);
	}
	
	private static void checkTiming(SpritesAnimation sprite){
		if(sprite.actDelay == 0){
			sprite.actDelay = sprite.delay;
			
			sprite.step++;
			if(sprite.step + 1 == sprite.numberOfSteps)
				sprite.step = 0;
		}
		else
			sprite.actDelay--;
	}
	
}
