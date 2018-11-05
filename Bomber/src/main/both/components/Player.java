package main.both.components;

import java.awt.Color;
import java.awt.Graphics2D;

import main.both.core.utils.Vector2f;

public class Player extends GameComponent{
	protected String name;
	protected int dosah;
	protected int bombs;
	protected int demage;
	protected int speed;
	protected Vector2f position;
	public static Vector2f offset;
	public boolean[] keys = new boolean[4];
	protected boolean isMoving = false;
	protected Color color = Color.DARK_GRAY;
	
	public void render(Graphics2D g2){
		if(position==null)
			return;
		g2.setColor(color);
		g2.fillRect((int)(position.getX()-offset.getX()),(int)(position.getY()-offset.getY()),Map.block,Map.block);
	}
	
	//getters and seeters
	public float getX() {
		return position.getX();
	}

	public float getY() {
		return position.getY();
	}
	
	public Vector2f getPosition(){
		return position;
	}

	public int getDosah() {
		return dosah;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setName(String name) {
		this.name = name;
	}
}
