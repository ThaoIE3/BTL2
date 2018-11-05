package main2.server.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import main.both.core.KeyEvents;
import main2.server.MainBomberServer;


public class TempWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static KeyEvents keyboard = new KeyEvents();
	private static int width;
	private static int height;
	private Canvas canvas;
	private Game game;
	
	public TempWindow(Game game){
		this.game = game;
		
		setTitle(MainBomberServer.TITLE);
		Dimension screenSize;
		if(MainBomberServer.FULLSCREEN){
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setResizable(false);
			setExtendedState(Frame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
		else{
			screenSize = new Dimension(MainBomberServer.WIDTH,MainBomberServer.HEIGHT);
			setResizable(true);
		}
		
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		canvas = new Canvas();
		canvas.setSize(screenSize);
		canvas.addKeyListener(keyboard);
		add(canvas);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void render(){
		BufferStrategy buffer = canvas.getBufferStrategy();
		if(buffer==null){
			canvas.createBufferStrategy(3);
			return;
		}
		Graphics g = buffer.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		game.render(g2);
		g.dispose();
		buffer.show();
	}
	

	public static int width() {
		return width;
	}

	public static int height() {
		return height;
	}
}
