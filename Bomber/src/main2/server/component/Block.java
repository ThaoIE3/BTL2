package main2.server.component;

import java.awt.Color;
import java.awt.Graphics2D;

import main2.server.core.Vector2f;

public class Block {
	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	
	private Vector2f position;
	private int healt;
	private int type;
	
	public void render(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.fillRect(WIDTH * position.getXi(), HEIGHT * position.getYi(), WIDTH, HEIGHT);
	}
}
