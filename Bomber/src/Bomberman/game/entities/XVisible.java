package Bomberman.game.entities;

import glib.util.vector.GVector2f;

public interface XVisible {
	public GVector2f getPosition();
	public GVector2f getSize();
	
//	public default boolean isCollision(Visible object){
//		return false;
//	}
}
