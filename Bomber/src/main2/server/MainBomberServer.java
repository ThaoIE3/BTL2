package main2.server;

import main2.server.core.CoreEngine;

public class MainBomberServer {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int FPS = 60;
	public static final boolean FULLSCREEN = false;
	public static final String TITLE = "Bomberman";
	
	public static void main(String[] args) {
		CoreEngine game = new CoreEngine(FPS,new Bomberman());
		game.start();

	}

}
