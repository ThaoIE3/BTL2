package Atomic.object;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;

import Atomic.core.Input;

public abstract class GameObject {
	private GVector2f position;
	
	private boolean dead;
	
	public GameObject(){
		this.position = new GVector2f();
	}
	
	public GameObject(GVector2f position){
		this.position = position;
	}

	public void update(float delta){
		
	}
	
	public void render(Graphics2D g2){
		
	}
	
	public void input(float delta, Input input){
		
	}
	
	//GETTERS
	
	public GVector2f getPosition() {
		return position;
	}
	
	public boolean isDead() {
		return dead;
	}

	//SETTERS
	
	public void setPosition(GVector2f position) {
		this.position = position;
	}
	
	public void setDead(boolean dead){
		this.dead = dead;
	}
}
