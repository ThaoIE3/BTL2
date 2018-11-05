package Atomic.core;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Window(int width, int height, String title){
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setResizable(true);
		setVisible(true);
	}
	
}
