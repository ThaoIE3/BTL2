package Bomberman.gui;

import glib.util.vector.GVector2f;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.util.HashMap;

import Bomberman.Options;
import Bomberman.core.XInteractable;

public abstract class Menu implements XInteractable{
	protected HashMap<String,GuiComponent> components = new HashMap<String,GuiComponent>();

	private Canvas canvas;
	
	public Menu(Canvas canvas){
		this.canvas = canvas;
	}
	
	protected void addButton(String val, String txt){
		GVector2f size = new GVector2f(Options.MENU_ITEM_WIDTH, Options.MENU_ITEM_HEIGHT);
		components.put(val, new Button(txt, size, Options.MENU_TEXT_SIZE, components.size() + 1, canvas));
	}
	
	protected void addSwitch(String val, String txt, boolean options){
		GVector2f size = new GVector2f(Options.MENU_ITEM_WIDTH, Options.MENU_ITEM_HEIGHT);
		components.put(val, new Switch(txt, size, Options.MENU_TEXT_SIZE, components.size() + 1, canvas, options));
	}
	
	@Override
	public void render(Graphics2D g2) {
		components.entrySet()
			   .stream()
			   .forEach(a -> a.getValue().render(g2));
	}
	
	@Override
	public void update(float delta) {
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().update(delta));
	}
	
	public void updateSize(){
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().updateSize());
	}
	
	public abstract void doAct(GVector2f click);
}
