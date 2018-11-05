package Bomberman.core;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import Bomberman.Options;

public class Window extends JFrame implements ComponentListener{
	private static final long serialVersionUID = 1L;
	private CoreEngine parent;

	public Window(CoreEngine parent){
		this.parent = parent;
		setTitle(Options.WINDOW_TITLE);
		setResizable(true);
		setSize(Options.WINDOW_DEFAULT_WIDTH, Options.WINDOW_DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		addComponentListener(this);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		parent.onResize();
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
