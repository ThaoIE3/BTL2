package Atomic.enity;

import glib.util.GColor;
import glib.util.vector.GVector2f;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import Atomic.component.Level;
import Atomic.core.Input;
import Atomic.enity.weapon.Bullet;
import Atomic.enity.weapon.Rocket;
import Atomic.map.Block;
import Atomic.map.Map;
import Atomic.object.GameObject;

public class Player extends GameObject{
	public final static int WIDTH = 30;
	public final static int HEIGHT = 30;
	
	private Color color;
	private Level level;
	private float speed;
	private int range;
	private int bombs;
	private int accularity;
	private int damage;
	private int direction;
	
	private static int upKey = Input.KEY_W;
	private static int downKey = Input.KEY_S;
	private static int rightKey = Input.KEY_D;
	private static int leftKey = Input.KEY_A;
	
	private double lastShot = System.currentTimeMillis();
	
//	private boolean atomBomb;
//	private boolean fireBomb;
	private boolean nano;
	private boolean skejtboard;
//	private boolean kicking;
//	private boolean shield;
//	private boolean ghost;
	
	//CONSTRUCTORS
	
	public Player(Level level){
		super(new GVector2f (80,80));
		this.level = level;
		color = GColor.BLACK;
		init();
	}
	
	//OTHERS
	
	private void init(){
		speed = 5;
		range = 1;
		bombs = 1;
		damage = 100;
		accularity = 20;
		direction = 2;
	}
	
	//OVERRIDES
	
	public void input(float delta,Input input){
		if(input.isKeyDown(upKey)){
			GVector2f newPos = getPosition().add(new GVector2f(0,-speed*delta));
			if(level.getMap().isCollision(newPos) ||
			  (getPosition().getX()%Block.WIDTH != 0 && (getPosition().getX()+WIDTH)%Block.WIDTH != 0 && level.getMap().isCollision(newPos.add(new GVector2f(WIDTH,0))))){
				getPosition().setY(level.getMap().get(newPos).getPosition().getY()+Block.HEIGHT);
			}
			else
				setPosition(newPos);
			direction = 0;
		}
		if(input.isKeyDown(downKey)){ //s
			GVector2f newPos = getPosition().add(new GVector2f(0,speed*delta));
			if(level.getMap().isCollision(newPos.add(new GVector2f(0,HEIGHT))) ||
			  ((getPosition().getX()+WIDTH)%Block.WIDTH != 0 && level.getMap().isCollision(newPos.add(new GVector2f(WIDTH,HEIGHT))))){
				getPosition().setY(level.getMap().get(newPos.add(new GVector2f(0,HEIGHT))).getPosition().getY()-HEIGHT);
			}
			else
				setPosition(newPos);
			direction = 2;
		}
		if(input.isKeyDown(leftKey)){ //a
			GVector2f newPos = getPosition().add(new GVector2f(-speed*delta,0));
			if(level.getMap().isCollision(newPos) ||
			  (getPosition().getY()%Block.HEIGHT != 0 && (getPosition().getY()+HEIGHT)%Block.HEIGHT != 0 && level.getMap().isCollision(newPos.add(new GVector2f(0,HEIGHT))))){
				getPosition().setX(level.getMap().get(newPos).getPosition().getX()+Block.WIDTH);
			}
			else
				setPosition(newPos);
			direction = 3;
		}
		if(input.isKeyDown(rightKey)){ //d
			GVector2f newPos = getPosition().add(new GVector2f(speed*delta, 0));
			if(level.getMap().isCollision(newPos.add(new GVector2f(WIDTH,0))) ||
			  ((getPosition().getY()+HEIGHT)%Block.HEIGHT != 0 && level.getMap().isCollision(newPos.add(new GVector2f(WIDTH,HEIGHT))))){
				getPosition().setX(level.getMap().get(newPos.add(new GVector2f(WIDTH,0))).getPosition().getX()-WIDTH);
			}
			else
				setPosition(newPos);
			direction = 1;
		}
		
		if(input.isKeyDown(Input.KEY_LCONTROL)){ //d
			level.addBomb(this);
		}
		
		
		if(input.isMouseDown(1) && System.currentTimeMillis() - lastShot > Bullet.CADENCE){
			
			GVector2f realMousePos = input.getMousePos().add(level.getOffset());
			GVector2f pos = getPosition().add(new GVector2f(WIDTH/2, HEIGHT/2));
			level.addWeapon(new Bullet(level, realMousePos.sub(pos).Normalized(),accularity,damage/20));
			lastShot = System.currentTimeMillis();
		}
		
		if(input.isMouseDown(3) && System.currentTimeMillis() - lastShot > Rocket.CADENCE){
			GVector2f realMousePos = input.getMousePos().add(level.getOffset());
			GVector2f pos = getPosition().add(new GVector2f(WIDTH/2, HEIGHT/2));
			level.addWeapon(new Rocket(level, realMousePos.sub(pos).Normalized(),accularity,damage));
			lastShot = System.currentTimeMillis();
		}
	}
	
	public void update(float delta){
		level.getOffset().setX(getPosition().getX() - level.getCanvas().getWidth() / 2);
		level.getOffset().setY(getPosition().getY() - level.getCanvas().getHeight() / 2);

		//skontroluje posun
		if(level.getOffset().getX() < 0){
			level.getOffset().setX(0);
        }
		
        if(level.getOffset().getX() > (Map.NUM_X * Block.WIDTH) - level.getCanvas().getWidth()){
        	level.getOffset().setX((Map.NUM_X * Block.WIDTH) - level.getCanvas().getWidth());
        }
        
        if(level.getOffset().getY() < 0){
        	level.getOffset().setY(0);
        }
        
        if(level.getOffset().getY() > (Map.NUM_Y * Block.HEIGHT) - level.getCanvas().getHeight()){
        	level.getOffset().setY((Map.NUM_Y * Block.HEIGHT) - level.getCanvas().getHeight()); 
        }
        
      //skontroluje pozíciu
        if(getPosition().getX() < 0){
        	getPosition().setX(0);
        }
        if(getPosition().getY() < 0){
        	getPosition().setY(0);
        }
        if(getPosition().getX() + Block.WIDTH > Map.NUM_X * Block.WIDTH){
        	getPosition().setX(Map.NUM_X * Block.WIDTH - Block.WIDTH);
        }
        if(getPosition().getY() + Block.HEIGHT > Map.NUM_Y * Block.HEIGHT){
        	getPosition().setY(Map.NUM_Y * Block.HEIGHT - Block.HEIGHT);
        }
	}
	
	
	public void render(Graphics2D g2){
		GVector2f pos = getPosition().sub(level.getOffset());
		
		g2.setColor(color);
		g2.fill3DRect(pos.getXi(), pos.getYi(), WIDTH, HEIGHT, true);
		
	}
	
	//GETTERA
	
	
	//GETTERS

	public int getRange() {
		return range;
	}

	
	public int getDamage() {
		return damage;
	}

	public boolean isNano() {
		return nano;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isSkejtboard() {
		return skejtboard;
	}
	
}

