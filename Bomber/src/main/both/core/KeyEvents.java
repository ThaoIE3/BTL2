package main.both.core;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.both.components.MyPlayer;


public class KeyEvents implements KeyListener {

	private MyPlayer player;
	@Override
	public void keyPressed(KeyEvent arg0) {
//		System.out.println(arg0.getKeyCode());
		switch(arg0.getKeyCode()){
			case 38:
				player.keys[0] = true;
				break;
			case 37:
				player.keys[1] = true;
				break;
			case 40:
				player.keys[2] = true;
				break;
			case 39:
				player.keys[3] = true;
				break;
		}
	};

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()){
			case 38:
				player.keys[0] = false;
				break;
			case 37:
				player.keys[1] = false;
				break;
			case 40:
				player.keys[2] = false;
				break;
			case 39:
				player.keys[3] = false;
				break;
		}
	};

	@Override
	public void keyTyped(KeyEvent arg0) {
	};
	
	public void addPlayer(MyPlayer kto){
		this.player = kto;
	}

}
