package Bomberman.game;

import glib.util.vector.GVector2f;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import util.ResourceLoader;
import Bomberman.Options;
import Bomberman.core.Input;
import Bomberman.core.XInteractable;
import Bomberman.game.entities.Bomb;
import Bomberman.game.entities.Bullet;
import Bomberman.game.entities.Enemy;
import Bomberman.game.entities.Explosion;
import Bomberman.game.entities.Item;
import Bomberman.game.entities.XBullet;
import Bomberman.game.entities.XEnemy;
import Bomberman.game.entities.XExplosion;
import Bomberman.game.entities.XItem;
import Bomberman.game.entities.XVisible;
import Bomberman.game.level.Block;
import Bomberman.game.level.Level;
import Bomberman.game.multiplayer.Communicable;
import Bomberman.game.other.PlayerInfo;
import Bomberman.gui.Logger;

public class Game implements XInteractable, MouseWheelListener{
	private Logger logger;
	private CoreGame parent;
	private Level level;
	private MyPlayer myPlayer;
	private HashMap<String, Bomb> bombs = new HashMap<String, Bomb>();
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private HashMap<String, XItem> items = new HashMap<String, XItem>();
	private ArrayList<XExplosion> explosions = new ArrayList<XExplosion>();
	private ArrayList<XEnemy> enemies = new ArrayList<XEnemy>();
	private ArrayList<XBullet> bullets = new ArrayList<XBullet>();
	private Communicable connection;
	private float zoom = 1f;
	private PlayerInfo info;
	private boolean ready;
	
	//CONSTRUCTORS
	
	public Game(CoreGame parent){
		this.parent = parent;
		connection = parent.getConnection();
		do{
			level = connection.getLevel();
		}while(level == null);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		logger = new Logger(parent.getCanvas().getWidth() + 10,0);
//		logger.show();
		logger.addText("Logger uspešne vytvorený");
		
		myPlayer = new MyPlayer(connection.getMyPosition(), parent.getCanvas(), this);
		info = new PlayerInfo(myPlayer);
		
		getCanvas().addMouseWheelListener(this);
		
//		parent.getConnection().sendMyImage();
	}
	
	public void renamePlayer(String oldName, String newName){
		Player tmp = players.get(oldName);
		tmp.setName(newName);
		players.remove(oldName);
		players.put(newName, tmp);
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
		level.render(g2);
		myPlayer.render(g2);
		
//		connection.getPlayers()
//		          .entrySet()
//		          .forEach(a -> a.getValue().render(g2));
		new HashMap<String, Player>(players).entrySet()
											.parallelStream()
											.map(a -> a.getValue())
											.filter(this::isVisible)
											.forEach(a -> a.render(g2));
		
		new HashMap<String, Bomb>(bombs).entrySet()
		                                .parallelStream()
		                                .map(a -> a.getValue())
		                                .filter(this::isVisible)
		                                .forEach(a -> a.render(g2));
		
		new ArrayList<XExplosion>(explosions).stream()
											 .filter(this::isVisible)
				  							 .forEach(a -> a.render(g2));

		new ArrayList<XBullet>(bullets).stream()
						 			   .filter(this::isVisible)
									   .forEach(a -> a.render(g2));
		
		new ArrayList<XEnemy>(enemies).stream()
									  .filter(this::isVisible)	
									  .forEach(a -> a.render(g2));
		
		new HashMap<String, XItem>(items).entrySet()
									     .parallelStream()
									 	 .map(a -> a.getValue())
										 .filter(this::isVisible)
										 .forEach(a -> a.render(g2));
		
		
		info.render(g2);
	}
	
	@Override
	public void update(float delta) {
		myPlayer.update(delta);
		
		new HashMap<String, Bomb>(bombs).entrySet()
				 						.parallelStream()
				 						.peek(a -> a.getValue().update(delta))
				 						.filter(a -> a.getValue().isDead())
				 						.forEach(a -> bombs.remove(a.getKey()));
		
		explosions.removeAll(explosions.parallelStream()
									   .filter(a -> !a.isAlive())
									   .collect(Collectors.toCollection(ArrayList<XExplosion>::new)));
		
		enemies.parallelStream()
			   .forEach(a -> a.update(delta));
		
		bullets = bullets.parallelStream()
						 .peek(a -> a.update(delta))
						 .filter(a -> a.isAlive())
						 .collect(Collectors.toCollection(ArrayList<XBullet>::new));
		
		new ArrayList<XExplosion>(explosions).parallelStream()
											 .forEach(a -> a.update(delta));
	}
	
	
	@Override
	public void input() {
		if(Input.isKeyDown(Input.KEY_ESCAPE))
			parent.pausedGame();
		
		int maxEnemies = 1;
		
		if(Input.isKeyDown(Input.KEY_F))
			for(int i=0 ; i < maxEnemies ; i++)
					enemies.add(new XEnemy(level.getMap().getRandomEmptyBlock().getPosition(),this));
		
		if(Input.isKeyDown(Input.KEY_E))
			for(XEnemy e : enemies)
				e.fire();
		
		myPlayer.input();
	}
	
	//OTHERS
	
	public void changeZoom(float value){
		if(!Options.ALLOW_ZOOMING)
			return;
		
		zoom += value;
		if(level.getMap().getNumberOfBlocks().getX() * Block.WIDTH * zoom < parent.getCanvas().getWidth()){
			zoom -= value; 
			return;
		}
		
		if(zoom > 5)
			zoom -= value;
	}

	public void playerMove(){
		connection.playerMove(getMyPlayer().getPosition(), getMyPlayer().getDirection());
//		getMyPlayer().clearTotalMove();
	}

	public void checkReady(){
		ready = connection.isReady();
	}

	public boolean isVisible(XVisible b){
		return !(b.getPosition().getX() + Block.WIDTH  * zoom < getOffset().getX()    || 
				 b.getPosition().getY() + Block.HEIGHT * zoom < getOffset().getY()    || 
				   getOffset().getX() + getCanvas().getWidth()  < b.getPosition().getX()   ||
				   getOffset().getY() + getCanvas().getHeight() < b.getPosition().getY());
	}
	
	public void cleanUp() {
		level.cleanUp();
	}
	
//	public void changePlayerName(String oldName, String newName){
//		
//	}
	
	public void removeItem(String key){
		items.remove(key);
	}
	
	//GETTERS

	public float getZoom() {return zoom;}
	public Level getLevel() {return level;}
	public boolean isReady() {return ready;}
	public Logger getLogger(){return logger;}
	public MyPlayer getMyPlayer() {return myPlayer;}
	public Canvas getCanvas(){return parent.getCanvas();}
	public XItem getItem(String key){return items.get(key);}
	public Bomb getBomb(String key){return bombs.get(key);}
	public Communicable getConnection() {return connection;}
	public GVector2f getOffset() {return myPlayer.getOffset();}
	public HashMap<String, Player> getPlayers() {return players;};
	public boolean hasBomb(String key){return bombs.containsKey(key);}
	public boolean hasItem(String key){return items.containsKey(key);}
	
	//SETTERS
	
	public void setReady(boolean ready) {this.ready = ready;}

	//ADDERS

	public void addBullet(GVector2f position, int direction, int speed, int demage, int healt){
		bullets.add(new XBullet(position, this, direction,  speed, healt,  demage));
	}
	
	public void addExplosion(GVector2f position, String name, GVector2f num, int offset, int delay){
		explosions.add(new XExplosion(position, this, name, new GVector2f(5,5), offset, delay));
	}
	
	public void addPlayer(String name, Player player) {
		players.put(name, player);
	}
	
	public void addItem(GVector2f position, int type){
		String sur = position.toString();
		
		if(!items.containsKey(sur)){
			if(getLevel().getMap().getBlock(position.getXi(), position.getYi()).getType() != Block.NOTHING)
				items.put(sur, new XItem(position.mul(Block.SIZE), type, this));
		}
		else
			items.remove(sur);
	}

	public void addBomb(int bombDefaultTime, GVector2f position, int range, int time) {
		bombs.put(position.toString(), new Bomb(time, position, range, this));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		changeZoom((float)arg0.getWheelRotation() / 10);
		
	}
}
