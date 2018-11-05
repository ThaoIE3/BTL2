package Atomic.component;

import glib.data.Scene;
import glib.util.GColor;
import glib.util.vector.GVector2f;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import util.ResourceLoader;
import Atomic.core.Input;
import Atomic.enity.Bomb;
import Atomic.enity.Enemy;
import Atomic.enity.Player;
import Atomic.enity.weapon.Weapon;
import Atomic.map.Block;
import Atomic.map.Map;
import Atomic.object.GameObject;
import Atomic.particles.Particle;

public class Level extends GameObject{
	private GVector2f offset;
	private Color backgroundColor;
	private Image backgroundImage;
	private Map map;
	private Player player;
	private Canvas canvas;
	private ArrayList<Enemy> enemies;
	private ArrayList<Weapon> weapons;
	private ArrayList<Explosion> explosions;
	private ArrayList<Bomb> bombs;
	private ArrayList<Particle> particles;
	
	private Scene<GameObject> scene;
	
	private long drawableEnemies;
	private long drawableWeapons;
	private long drawableExplosions;
	private long drawableBombs;
	private long drawableParticles;
	private HashMap<String, Boolean> options;
	
	
	//CONSTRUCTORS
	
	public Level(Canvas canvas){
		this.canvas = canvas;
		
		offset = new GVector2f(0 - canvas.getWidth() / 2, 0 - canvas.getHeight() / 2);

		init();
		
		for(int i=0 ; i<20 ; i++){
			enemies.add(new Enemy(this));
		}
	}

	//OTHERS
	
	public void init(){
		enemies = new ArrayList<Enemy>();
		bombs = new ArrayList<Bomb>();
		weapons = new ArrayList<Weapon>();
		explosions = new ArrayList<Explosion>();
		particles = new ArrayList<Particle>();
		
		backgroundColor = GColor.RED;
		backgroundImage = ResourceLoader.loadTexture("stadion.jpg");
		
		options = new HashMap<String, Boolean>();
		options.put("showParticles", false);
		options.put("showLogs", true);
		
		player = new Player(this);
		map = new Map(this);
		new Explosion();
	}
	
	//ADDERS
	
	public void addWeapon(Weapon b){
		weapons.add(b);
	}

	public void addBomb(Player player) {
		GVector2f pos = player.getPosition().add(new GVector2f(Player.WIDTH/2, Player.HEIGHT/2));
		pos = getMap().get(pos).getPosition();
		
		GVector2f direction = new GVector2f();
		
		if(player.isSkejtboard()){
			switch(player.getDirection()){
				case 0:
					direction = new GVector2f(0,-4);
					break;
				case 1:
					direction = new GVector2f(4,0);
					break;
				case 2:
					direction = new GVector2f(0,4);
					break;
				case 3:
					direction = new GVector2f(-4,0);
					break;
			}
		}
		
		Bomb b = new Bomb(pos, this, map.calcBombDist(pos, player), player.getDamage(),player.isNano(), direction);
		if(!bombs.contains(b))
			bombs.add(b);
	}

	public void addExplosion(Explosion explosion) {
		explosions.add(explosion);
		
	}

	public void addParticle(Particle particle){
		particles.add(particle);
	}
	
	//OVERRIDES
	
	public void render(Graphics2D g2){
		map.render(g2);
		
		
		drawableBombs = bombs.stream()
							 .filter(this::isVisible)
							 .peek(a -> a.render(g2))
							 .count();
		
		
		drawableEnemies = enemies.stream()
								 .filter(this::isVisible)
								 .peek(a -> a.render(g2))
								 .count();
		
		player.render(g2);
		
		
		drawableWeapons = weapons.stream()
				 				 .filter(this::isVisible)
				 				 .peek(a -> a.render(g2))
				 				 .count();
		
		drawableParticles = particles.stream()
				 					 .filter(this::isVisible)
				 					 .peek(a -> a.render(g2))
				 					 .count();
		
		drawableExplosions = explosions.stream()
				 					   .filter(this::isVisible)
				 					   .peek(a -> a.render(g2))
				 					   .count();
	}
	
	public void input(float delta, Input input){
		player.input(delta, input);
	}
	
	public void update(float delta){
		map.update(delta);
		player.update(delta);
		
		enemies.removeAll(enemies.stream()
								 .peek(a->a.update(delta))
								 .filter(a->a.isDead())
								 .collect(Collectors.toList()));
		
		bombs.removeAll(bombs.stream()
				 			 .peek(a->a.update(delta))
				 			 .filter(a->a.isDead())
				 			 .collect(Collectors.toList()));
		
		weapons.removeAll(weapons.stream()
				 				 .peek(a->a.update(delta))
				 				 .filter(a->a.isDead())
				 				 .collect(Collectors.toList()));
		
//		weapons = weapons.parallelStream()
//						 .filter(a -> !a.isDead())
//						 .peek(a->a.update(delta))
//						 .collect(Collectors.toCollection(ArrayList<Weapon>::new));
		
		explosions.removeAll(explosions.stream()
				 					   .peek(a->a.update(delta))
				 					   .filter(a->a.isDead())
				 					   .collect(Collectors.toList()));
		
		particles.removeAll(particles.stream()
				 					 .peek(a->a.update(delta))
				 					 .filter(a->a.isDead())
				 					 .collect(Collectors.toList()));
	}
	
	//GETTERS
	
	public boolean isVisible(GameObject o){
		if(o.getPosition().getX() + Block.WIDTH < offset.getX() || offset.getX()+canvas.getWidth()<o.getPosition().getX() ||
		   o.getPosition().getY() + Block.HEIGHT < offset.getY() || offset.getY()+canvas.getHeight()<o.getPosition().getY())
			return false;
		return true;
	}
	
	public boolean get(String s){
		if(!options.containsKey(s))
			return false;
		return options.get(s);
	}
	
	public String getNumOfEnemies(){
		return drawableEnemies+" / "+enemies.size();
	}
	
	public String getNumOfWeapons(){
		return drawableWeapons+" / "+weapons.size();
	}
	
	public String getNumOfExplosions(){
		return drawableExplosions+" / "+explosions.size();
	}
	
	public String getNumOfBombs(){
		return drawableBombs+" / "+bombs.size();
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Map getMap() {
		return map;
	}

	public GVector2f getOffset() {
		return offset;
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public Player getPlayer() {
		return player;
	}
	
	public String getNumOfParticles(){
		return drawableParticles+" / "+particles.size();
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	//SETTERS

	public void setOffset(GVector2f offset) {
		this.offset = offset;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
