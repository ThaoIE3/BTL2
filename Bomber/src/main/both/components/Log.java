package main.both.components;

import java.awt.Color;
import java.awt.Graphics2D;

import main.both.core.Game;

public class Log extends GameComponent{
	//poèet pripojených hráèov
	private Game game;
	
	public Log(Game game){
		this.game = game;
	}
	
	public void update(float delta){
		
	}
	
	public void render(Graphics2D g2){
		g2.setColor(new Color(0,0,0,155));
		g2.fillRect(0, 0, 60, 20);
		
		g2.setColor(Color.WHITE);
		//g2.drawString("hráèov: "+((Bomberman)game).pocetHracov, 0, 10);
	};
}
