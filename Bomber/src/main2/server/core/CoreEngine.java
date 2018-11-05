package main2.server.core;

import main2.server.Bomberman;


/*
 *	robí èasové blbostièky
 */
public class CoreEngine {
	private double frameTime;
	private boolean isRunning;
	private Game game;
	private TempWindow window;
	
	public CoreEngine(int framerate, Bomberman bomberman) {
		isRunning=false;
		this.game = bomberman;
		frameTime = 1/(double)framerate;
		window = new TempWindow(game);
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
		double lastTime = System.currentTimeMillis();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;
			double startTime = System.currentTimeMillis();
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

				if(frameCounter >= 1.0){
//					Logs.write(String.valueOf(frames));
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render){
				window.render();
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
