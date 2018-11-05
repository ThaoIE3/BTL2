package Bomberman.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger {
	private JFrame frame;
	private JTextArea text;
	
	public Logger(){
		this(0,0);
	}
	
	public Logger(int x, int y){
		frame = new JFrame();
		frame.setLocation(x, y);
		frame.setSize(800, 600);
		frame.setTitle("Bomberman Logger");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(new JScrollPane(text = new JTextArea(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	}
	
	public void show(){
		frame.setVisible(true);
	}
	
	public void hide(){
		frame.setVisible(false);
	}
	
	public void addText(String msg){
		text.append(msg + "\n");
	}
}
