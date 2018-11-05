package main.game.core;

import main.game.Bomberman;
import main.both.core.Game;
import main.both.core.utils.Time;
import main.both.core.utils.Window;
import main.both.rendering.RenderEngine;

public class CoreEngine {
	private double frameTime;
	private boolean isRunning;
	private Game game;
	private RenderEngine renderer;;
	private Window window;
	
	public CoreEngine(int framerate, Bomberman bomberman) {
		isRunning=false;
		this.game = bomberman;
		frameTime = 1/(double)framerate;
		renderer = new RenderEngine(game.getRoot());
		createWindow();
	}

	public void createWindow() {
		window = new Window(game);
	}
	
	public void start() {
		if(!isRunning)
			run();
	}

	private void run() {
		isRunning = true;
		int frames = 0;
		double frameCounter = 0;
		
		game.init();
		double lastTime = Time.GetTime();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;
			double startTime = Time.GetTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			while(unprocessedTime > frameTime){
				render = true;
				unprocessedTime -= frameTime;
				
				//pause/play

				game.input((float) frameTime);
				game.update((float) frameTime);
				if(game.player.moved)
					((Bomberman)game).updateClient();

				if(frameCounter >= 1.0){
//					Logs.write(String.valueOf(frames));
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render){
				window.update(renderer);
				frames++;
			}
			else{
				try{
					Thread.sleep(1);
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
}
