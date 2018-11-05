package Atomic.particles;

import glib.util.vector.GVector2f;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import util.ResourceLoader;
import Atomic.component.Level;
import Atomic.object.GameObject;

public class Particle extends GameObject{
	public final static Image defaultImage = ResourceLoader.loadTexture("smoke.png");
	private Image image;
	private GVector2f size;
	private Level level;
	private int life;
	private double angle;
	private float rotSpeed;
	private float offset = 0;
	private float fading = 0.001f; 
	private float opacity;
	
	//CONSTRUCTORS
	
	public Particle(GVector2f position, GVector2f size, Image image, Level level, int maxLife , float rotSpeed, float opacity, float fadding){
		super(position);
		this.image = image;
		this.level = level;
		this.size = size;
		life = maxLife;
		angle = Math.random()*360;
		this.opacity = opacity;
	}
	
	//OVERRIDES
	
	public void render(Graphics2D g2){
		AffineTransform originalTransform = g2.getTransform();
		
		GVector2f pos = getPosition().sub(level.getOffset()).add(size.div(2));
		if(opacity <= 0){
			setDead(true);
			return;
		}
		g2.rotate(Math.toRadians(angle), pos.getXi(), pos.getYi());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(image, pos.getXi()-(int)offset, pos.getYi()-(int)offset, size.getXi()+(int)(offset*2), size.getYi()+(int)(offset*2), null);
		g2.setTransform(originalTransform);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	public void update(float delta){
		life--;
		offset += 0.3f;
		if(life <= 0 )
			setDead(true);
		angle += rotSpeed;
		opacity -= fading;
	}
}
