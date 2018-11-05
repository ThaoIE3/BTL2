package Bomberman.core;

import java.awt.Graphics2D;

public interface XInteractable {
	public default void render(Graphics2D g2){};
	public default void input(){};
	public default void update(float delta){};
	public default void cleanUp(){};
}
