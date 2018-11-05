package Bomberman.game;

import java.awt.Graphics2D;

import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.entities.Item;
import Bomberman.game.entities.XVisible;
import Bomberman.game.level.Block;
import Bomberman.game.other.SpritesAnimation;
import glib.util.vector.GVector2f;

public class Player implements XInteractable, XVisible{
	protected GVector2f position;
	private String name;
	protected int speed;
	private int healt;
	private int range;
	private String image;
	private boolean moving;
	private boolean atom = true;
	private int direction = 2;
	protected Game parent;
	protected GVector2f offset;

	public GVector2f getOffset() {
		return parent.getOffset();
	}
	
	//CONSTRUCTORS
	
	public Player(Game parent, GVector2f position) {
		this(parent, position, Options.PLAYER_DEFAULT_NAME, Options.PLAYER_DEFAULT_SPEED);
	}
	
	public Player(Game parent, GVector2f position, String name, int speed) {
		this(parent, position, name, speed, Options.PLAYER_DEFAULT_HEALT, "player" + ((int)(Math.random() * 3) + 1) + ".png");
	}
	
	public Player(Game parent, GVector2f position, String name, int speed, int healt, String image) {
		this.position = position;
		this.parent = parent;
		this.speed = speed;
		this.healt = healt;
		this.name = name;
		range =  Options.PLAYER_DEFAULT_RANGE;
		setImage(image);
	}
	
	public GVector2f getSur(){
		return position.add(new GVector2f(Options.PLAYER_WIDTH, Options.PLAYER_HEIGHT).div(2)).div(Block.SIZE).toInt();
	}

	//OTHERS
	
	public void move(GVector2f move){
		position = position.add(move.mul(speed));
	}
	
	public void eatItemType(int type){
		switch(type){
			case Item.INCREASE_HEALT:
				if(healt < Options.PLAYER_MAX_HEALT)
					healt++;
				break;
			case Item.INCREASE_SPEED:
				if(speed < Options.PLAYER_MAX_SPEED)
					speed++;
				break;
			case Item.INCREASE_RANGE:
				if(range < Options.PLAYER_MAX_RANGE)
					range++;
				break;
		}
	}
	
	//OVERRIDES
	
	@Override
	public void render(Graphics2D g2) {
//		System.out.println("pos: " + positiON);
//		SYSTEM.OUT.PRINTLN("PAR: " + PARENT);
//		SYSTEM.OUT.PRINTLN("ZOOM: " + PARENT.GETZOOM());
//		SYSTEM.OUT.PRINTLN("OFF: " + GETOFFSET());
		if(parent == null || position == null)
			return;
		
		GVector2f pos = position.mul(parent.getZoom()).sub(getOffset());

		GVector2f size = new GVector2f(Options.PLAYER_WIDTH, Options.PLAYER_HEIGHT);
		
		SpritesAnimation.drawPlayer(pos, size.mul(parent.getZoom()), g2, getDirection(), getImage(), isMoving());
	}
	
	//GETTERS

	public GVector2f getPosition() {
		return position;
	}
	
	public int getHealt() {
		return healt;
	}
	
	public String getName() {
		return name;
	}

	public boolean isMoving() {
		return moving;
	}

	//SETTERS

	public void addDemmage(int demmage){
		healt += demmage;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setPosition(GVector2f position) {
		this.position = position;
	}

	public String getImage() {
		return image;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public int getSpeed() {
		return speed;
	}

	public void setOffset(GVector2f offset) {
		this.offset = offset;
	}

	
	public void setImage(String image) {
		this.image = image;
		SpritesAnimation.setSprite(image, 5, 5, 4, 6);
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRange() {
		return range;
	}

	@Override
	public GVector2f getSize() {
		return new GVector2f(Options.PLAYER_WIDTH, Options.PLAYER_HEIGHT);
	}

	public boolean isAtom() {
		return atom;
	}
}
