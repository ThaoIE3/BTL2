package main.both.rendering;

import java.awt.Color;
import java.awt.Graphics2D;

import main.both.core.GameObject;
import main.both.core.utils.Window;

public class RenderEngine {
	private GameObject root;
	
	public RenderEngine(GameObject root){
		this.root = root;
	}
	
	public void render(Graphics2D g2){
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, Window.width(), Window.height());
		
		root.renderAll(g2);
	}
}
