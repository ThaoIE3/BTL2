package Atomic.core;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import Atomic.component.Level;
import Atomic.component.Log;
import Atomic.map.Block;
import Atomic.map.Map;
import Atomic.object.GameObject;

public abstract class CoreGame {
	private boolean isRunning;
	private float frameTime;
	private Graphics2D g2;
	private Window window;
	private Canvas canvas;
	private Level level;
	public  Input input;
	private Log log;
	private ArrayList<GameObject> scene = new ArrayList<GameObject>();
	
	public abstract void init();
	
	//CREATORS
	
	protected void createWindow(int width, int height, String title){
		window = new Window(width, height, title);
		canvas = new Canvas();
		window.add(canvas);
		input = new Input();
		log = new Log(level);
		canvas.addKeyListener(input);
		canvas.addMouseListener(input);
		canvas.addMouseMotionListener(input);
	}
	
	protected void createLevel(){
		this.level = new Level(canvas);
		scene.add(level);
		log.setLevel(level);
	}
	
	//OTHERS
	
	public void start(float fps){
		frameTime = 1000/fps;
		isRunning = true;
		mainLoop();
	}
	
	private void input(float delta){
		for(GameObject g :scene){
			g.input(delta,input);
		}
	}
	
	private void update(float delta){
		for(GameObject g :scene){
			g.update(delta);
		}
	}
	
	private void prepare(){
		BufferStrategy buffer = canvas.getBufferStrategy();
		if(buffer==null){
			canvas.createBufferStrategy(3);
			return;
		}
		g2 = (Graphics2D)buffer.getDrawGraphics();
		
		render();
		
		g2.dispose();
		buffer.show();
	}
	
	private void render(){
		//g2.setColor(level.getBackgroundColor());
		//g2.fillRect(0, 0, window.getWidth(), window.getHeight());
		
		
//		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
//		g2.setComposite(ac);
		
		g2.drawImage(level.getBackgroundImage(), -level.getOffset().getXi(), -level.getOffset().getYi(), Map.NUM_X*Block.WIDTH, Map.NUM_Y*Block.HEIGHT, null);
		
		for(GameObject g :scene){
			g.render(g2);
		}
		if(level.get("showLogs"))
			log.render(g2);
	}
	
	private void mainLoop(){
		int frames = 0;
//		int ticks = 0;
		double lastTime;
		double startTime = System.currentTimeMillis();
		while(isRunning){
			lastTime = System.currentTimeMillis();
			while(System.currentTimeMillis()-lastTime <= frameTime){
//				ticks++;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {e.printStackTrace(); }
			}
			float cicleTime = (float)(System.currentTimeMillis()-lastTime) / frameTime;
			input(cicleTime);
			update(cicleTime);
			prepare();
			frames++;
			if(System.currentTimeMillis()-startTime >= 1000){
				//System.out.println("frames: "+frames+" ticks: "+ticks/*+" startTime: "+(System.currentTimeMillis()-startTime)*/);
				log.setFps(frames);
//				ticks = 0;
				frames = 0;
				startTime = System.currentTimeMillis();
			}
		}
	}
}
