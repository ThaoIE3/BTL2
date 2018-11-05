package main.both.core;

import java.awt.Graphics2D;

import main.both.components.Map;
import main.both.components.MyPlayer;

public class Game {
	public MyPlayer player;
	public Map mapa;
	
	private GameObject root = null;
	
	public void init(){};
	
	public void update(float delta){
		getRoot().updateAll(delta);
	};
	
	public void input(float delta){
		getRoot().inputAll(delta);
	};

	public void render(Graphics2D g2){
		getRoot().renderAll(g2);
	};
	
	public void addObject(GameObject object){
		getRoot().addChild(object);
	}
	
	public GameObject getRoot(){
		if(root == null){
			root = new GameObject();
		}
		return root;
	}

	public void close() {
		
	}
}
