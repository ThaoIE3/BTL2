package Bomberman.game.entities;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Game;


public class Explosion implements XInteractable, XVisible{
	private Image image;
	private int numOfImages;
	private GVector2f position;
	private GVector2f imageSize;
	private GVector2f num;
	GVector2f size;
	private int frame;
	private int delay;
	private int actDelay;
	private int angle;
	private boolean dead;
	private Game parent;
	
	public Explosion(GVector2f position, Image image, GVector2f num, int offset, int delay, Game parent){
		this.image = image;
		this.numOfImages = num.getXi() * num.getYi();
		this.position = position.sub(offset);
		this.num = num;
		this.size = new GVector2f(Options.EXPLOSION_DEFAULT_WIDTH, Options.EXPLOSION_DEFAULT_HEIGHT).add(offset * 2);
		this.parent = parent;
		actDelay = this.delay = delay;
		imageSize = new GVector2f(image.getWidth(null), image.getHeight(null)).div(num);
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
	
	public void render(Graphics2D g2){
		if(dead)
			return;
		
		if(frame == numOfImages)
			dead = true;
		
		GVector2f pos = new GVector2f(frame % num.getX(), frame / num.getY()).toInt().mul(imageSize);
		GVector2f pos2 = pos.add(imageSize);
		
		GVector2f tempPos = position.sub(parent.getOffset()); 
		
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


	public boolean isDead() {return dead;}
	@Override
	public GVector2f getPosition() {return position;}
	@Override
	public GVector2f getSize() {return new GVector2f(Options.EXPLOSION_DEFAULT_WIDTH, Options.EXPLOSION_DEFAULT_HEIGHT);}
}
