package Bomberman.gui;

import java.awt.Canvas;

import glib.math.GColision;
import glib.util.vector.GVector2f;
import Bomberman.core.Input;
import Bomberman.core.XInteractable;

public abstract class GuiComponent implements XInteractable{
	protected GVector2f offset;
	protected GVector2f textPos;
	protected int borderThickness = 5;
	protected String text;
	protected int textSize;
	protected GVector2f pos;
	protected GVector2f size;
	protected int round = 20;
	
	protected boolean hover;
	private boolean active;

	protected Canvas canvas;
	
	protected void clickIn(){
		
	};
	
	public boolean isClickIn(GVector2f click){
		boolean result = GColision.pointRectCollision(pos, size, click);
		
		if(result)
			clickIn();
		
		return result; 
	}
	
	@Override
	public void update(float delta) {
		if(active)
			hover = GColision.pointRectCollision(pos, size, Input.getMousePosition());
	}
	
	//dorobiù
	public void cleanUp() {
	}

	public abstract void updateSize();
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
}
