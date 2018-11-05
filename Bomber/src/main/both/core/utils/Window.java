package main.both.core.utils;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import main.MainBomber;
import main.both.core.Game;
import main.both.core.KeyEvents;
import main.both.rendering.RenderEngine;

public class Window extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static KeyEvents keyboard = new KeyEvents();
	private static int width;
	private static int height;
	private Canvas canvas;
	
	public Window(Game game){
		setTitle(MainBomber.TITLE);
		Dimension screenSize;
		if(MainBomber.FULLSCREEN){
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setResizable(false);
			setExtendedState(Frame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
		else{
			screenSize = new Dimension(MainBomber.WIDTH,MainBomber.HEIGHT);
			setResizable(true);
		}
		
		addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e){
		    	game.close();
		    }
		});
		
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
	
	public void update(RenderEngine game){
		BufferStrategy buffer = canvas.getBufferStrategy();
		if(buffer==null){
			canvas.createBufferStrategy(3);
			return;
		}
		Graphics g = buffer.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
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
