package Bomberman.core;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public abstract class CoreEngine {
	private static boolean RENDER_TIME;
	private static int FPS = 60;
	private static int UPS = 60;
	private boolean running;
	
	private Window window;
	private Input input = new Input();
	private Canvas canvas = new Canvas();
	private Graphics2D g2;
	
	public CoreEngine(){
		this(60, 60, true);
	}
	
	public CoreEngine(int fps, int ups, boolean renderTime){
		RENDER_TIME = renderTime;
		FPS = fps;
		UPS = ups;
	}
	
	public void run(){
		running = true;
		defaultInit();
		mainLoop();
	}
	
	protected void cleanUp() {
		window.dispose();
		window.removeAll();
		Input.cleanUp();
		
	}

	public void stop(){
		running = false;
	}
	
	private void mainLoop(){
		long initialTime = System.nanoTime();
		final double timeU = 1000000000 / UPS;
		final double timeF = 1000000000 / FPS;
		double deltaU = 0, deltaF = 0;
		int frames = 0, ticks = 0;
		long timer = System.currentTimeMillis();

		    while (running) {
		        long currentTime = System.nanoTime();
		        deltaU += (currentTime - initialTime) / timeU;
		        deltaF += (currentTime - initialTime) / timeF;
		        initialTime = currentTime;

		        if (deltaU >= 1) {
		            defaultInput();
		            //System.out.println(deltaU);
		            defaultUpdate((float)Math.min(deltaU, 2));
		            ticks++;
		            deltaU--;
		        }

		        if (deltaF >= 1) {
		            defaultRender();
		            frames++;
		            deltaF--;
		        }

		        if (System.currentTimeMillis() - timer > 1000) {
		            if (RENDER_TIME)
		                System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
		            
		            frames = 0;
		            ticks = 0;
		            timer += 1000;
		        }
		    }
	}
	
	//DEFAULT MAIN METHODS
	
	private void defaultInit(){
		window = new Window(this);
		window.add(canvas);
		
		canvas.addMouseListener(input);
		canvas.addKeyListener(input);
		canvas.addMouseMotionListener(input);
		init();
	};
	
	private void defaultInput(){
		input();
	}
	
	private void defaultUpdate(float delta){
		update(delta);
	}
	
	private void defaultRender(){
		BufferStrategy buffer = canvas.getBufferStrategy();
		if(buffer==null){
			canvas.createBufferStrategy(3);
			return;
		}
		g2 = (Graphics2D)buffer.getDrawGraphics();
		
		
		//BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
		//Graphics2D g = (Graphics2D)image.getGraphics();
		
		render(g2);
		
		//image = PostFX.bloomEffect(image);
		
		
		//g2.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		
		g2.dispose();
		buffer.show();
	}
	
	//MAIN METHODS
	
	

	protected void init(){
		
	};
	
	protected void input(){
		
	}
	
	protected void update(float delta){
		
	}
	
	protected void render(Graphics2D g2){
		
	}

	public Window getWindow() {
		return window;
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	public Input getInput() {
		return input;
	}

	
	public void onResize() {}
}
