package main.both.core;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.both.components.GameComponent;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	
	public GameObject(){
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
	}
	
	public void addChild(GameObject child){
		children.add(child);
	}
	
	public ArrayList<GameObject> getAllAttached(){
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child:children)
			result.addAll(child.getAllAttached());
		
		result.add(this);
		return result;
	}
	
	public GameObject addComponent(GameComponent component){
		component.setParent(this);
		components.add(component);
		return this;
	}
	
	public void inputAll(float delta){
		input(delta);
		for(GameObject child:children){
			child.inputAll(delta);
		}
	}
	
	public void updateAll(float delta){
		update(delta);
		for(GameObject child:children){
			child.updateAll(delta);
		}
	}

	public void renderAll(Graphics2D g2){
		render(g2);
		for(GameObject child:children){
			child.renderAll(g2);
		}
	}

	public void input(float delta){
		for(GameComponent component:components){
			component.input(delta);
		}
	}
	
	public void update(float delta){
		for(GameComponent component:components){
			component.update(delta);
		}
	}
	
	public void render(Graphics2D g2){
		for(GameComponent component:components){
			component.render(g2);
		}
	}
}
