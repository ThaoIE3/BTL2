package main.both.components;

import java.awt.Graphics2D;

import main.both.core.GameObject;

public class GameComponent {
	GameObject parent;
	
	public void input(float delta){};
	
	public void update(float delta){};
	
	public void render(Graphics2D g2){};
	
	public void setParent(GameObject parent){
		this.parent = parent;
	}
}
