package Atomic.core;

import glib.util.vector.GVector2f;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;


public class Input implements KeyListener, MouseListener, MouseMotionListener{
	private HashMap<Integer,Boolean> keys = new HashMap<Integer,Boolean>();
	private HashMap<Integer,Boolean> mouses = new HashMap<Integer,Boolean>();
	private GVector2f mousePos = new GVector2f();
	
	
	
	public final static int KEY_W = 87;
	public final static int KEY_A = 65;
	public final static int KEY_S = 83;
	public final static int KEY_D = 68;
	public final static int KEY_Q = 81;
	public final static int KEY_E = 69;
	public final static int KEY_F = 70;
	public final static int KEY_UP = 38;
	public final static int KEY_LEFT = 37;
	public final static int KEY_DOWN = 40;
	public final static int KEY_RIGHT = 39;
	public final static int KEY_LCONTROL = 17;
	public final static int KEY_ENTER = 10;
	public final static int KEY_ESCAPE = 27;
	public final static int KEY_LALT = 18;
	public final static int KEY_LSHIFT = 16;
	public final static int KEY_SPACE = 32;
	
	public final static int BUTTON_LEFT = 1;
	public final static int BUTTON_RIGHT = 3;
	
	public boolean isKeyDown(int key){
		if(keys.containsKey(key)){
			return keys.get(key);
		}
		return false;
	}
	public boolean isMouseDown(int key){
		if(mouses.containsKey(key)){
			return mouses.get(key);
		}
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys.put(e.getKeyCode(), true);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.put(e.getKeyCode(), false);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos = new GVector2f(e.getX(),e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = new GVector2f(e.getX(),e.getY());
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouses.put(e.getButton(), true);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouses.put(e.getButton(), false);
		
	}
	public GVector2f getMousePos() {
		return mousePos;
	}
}
