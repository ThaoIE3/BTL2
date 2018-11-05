package Bomberman.game;

import java.awt.Canvas;
import java.util.HashMap;

import Bomberman.Options;
import Bomberman.core.Input;
import Bomberman.game.level.Block;
import glib.util.vector.GVector2f;

public class MyPlayer extends Player{
	private GVector2f move = new GVector2f();
	private GVector2f totalMove = new GVector2f();
	private Canvas canvas;
	private HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>(); 
	
	public MyPlayer(GVector2f position, Canvas canvas, Game parent) {
		super(parent, position, Options.PLAYER_DEFAULT_NAME, parent.getLevel().getDefalutSpeed());
		
		setOffset(new GVector2f(0 - canvas.getWidth() / 2, 0 - canvas.getHeight() / 2));
		
		this.canvas = canvas;
		this.parent = parent;
		move = new GVector2f();

		
		keys.put(Input.KEY_W, false);
		keys.put(Input.KEY_A, false);
		keys.put(Input.KEY_S, false);
		keys.put(Input.KEY_D, false);
		
		
	}
	
	@Override
	public void input(){
		if(Input.isKeyDown(Input.KEY_SPACE))
			parent.changeZoom(0.1f);
		
		if(Input.isKeyDown(Input.KEY_LSHIFT))
			parent.changeZoom(-0.1f);
		

		if(Input.isKeyDown(Input.KEY_LCONTROL)){
			GVector2f pos = getSur().mul(Block.SIZE);
			
			if(!parent.hasBomb(pos.toString()))
				parent.getConnection().putBomb(pos);
		}
		
		
		move = new GVector2f();
		
		if(!keys.get(Input.KEY_W) && Input.isKeyDown(Input.KEY_W))
			setDirection(2);
		keys.put(Input.KEY_W, Input.isKeyDown(Input.KEY_W));
		
		
		if(!keys.get(Input.KEY_S) && Input.isKeyDown(Input.KEY_S))
			setDirection(3);
		keys.put(Input.KEY_S, Input.isKeyDown(Input.KEY_S));

		
		if(!keys.get(Input.KEY_A) && Input.isKeyDown(Input.KEY_A))
			setDirection(0);
		keys.put(Input.KEY_A, Input.isKeyDown(Input.KEY_A));
		
		
		if(!keys.get(Input.KEY_D) && Input.isKeyDown(Input.KEY_D))
			setDirection(1);
		keys.put(Input.KEY_D, Input.isKeyDown(Input.KEY_D));
			
		
		if(keys.get(Input.KEY_W))
			move.addToY(-1);
		if(keys.get(Input.KEY_S))
			move.addToY(1);
		if(keys.get(Input.KEY_A))
			move.addToX(-1);
		if(keys.get(Input.KEY_D))
			move.addToX(1);
		
		
		setMoving(!move.isNull());
		
		if(!move.isNull())
			parent.playerMove();
		
		if(move.getX() < 0 && move.getY() == 0)
			setDirection(0);
		else if(move.getX() > 0 && move.getY() == 0)
			setDirection(1);
		else if(move.getX() == 0 && move.getY() < 0)
			setDirection(2);
		else if(move.getX() == 0 && move.getY() > 0)
			setDirection(3);
	}
	
	public void update(float delta){
		if(position == null)
			return;

		totalMove = totalMove.add(move);
		
		position.addToX(move.getX() * speed * delta);
		if(isInBlock())
			position.addToX(-move.getX() * speed * delta);
		
		position.addToY(move.getY() * speed * delta);
		if(isInBlock())
			position.addToY(-move.getY() * speed * delta);
		
		checkBorders();
		checkOffset();
		
		if(parent.hasItem(getSur().toString())){
			int type = parent.getItem(getSur().toString()).getType();
			
			eatItemType(type);
			parent.getConnection().eatItem(getSur(), type);
		}
	}
	
	private boolean isInBlock(){
		float topOffset = 20;
		float bottomOffset = 30;
		float rightOffset = 11;
		float leftOffset = 9;
		
		GVector2f t = position.add(new GVector2f(Options.PLAYER_WIDTH, Options.PLAYER_HEIGHT - topOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f b = position.add(new GVector2f(Options.PLAYER_WIDTH, Options.PLAYER_HEIGHT + bottomOffset).div(2)).div(Block.SIZE).toInt();
		GVector2f r = position.add(new GVector2f(Options.PLAYER_WIDTH - rightOffset, Options.PLAYER_HEIGHT).div(2)).div(Block.SIZE).toInt();
		GVector2f l = position.add(new GVector2f(Options.PLAYER_WIDTH + leftOffset, Options.PLAYER_HEIGHT).div(2)).div(Block.SIZE).toInt();
		
		try{
			return parent.getLevel().getMap().getBlock(t.getXi(), t.getYi()).getType() != Block.NOTHING ||
				   parent.getLevel().getMap().getBlock(b.getXi(), b.getYi()).getType() != Block.NOTHING ||
				   parent.getLevel().getMap().getBlock(r.getXi(), r.getYi()).getType() != Block.NOTHING ||
				   parent.getLevel().getMap().getBlock(l.getXi(), l.getYi()).getType() != Block.NOTHING;
		}catch(NullPointerException e){
			return true;
		}
	}
	
	public void clearTotalMove(){
		totalMove = new GVector2f(); 
	}
	
	public void checkOffset(){
		
		getOffset().setX(getPosition().getX() * parent.getZoom() - canvas.getWidth() / 2);
		getOffset().setY(getPosition().getY() * parent.getZoom() - canvas.getHeight() / 2);


		GVector2f nums = parent.getLevel().getMap().getNumberOfBlocks();
		
		//skontroluje posun
		if(getOffset().getX() < 0)
			getOffset().setX(0);
        
        if(getOffset().getX() > (nums.getX() * Block.WIDTH * parent.getZoom()) - canvas.getWidth())
        	getOffset().setX((nums.getX()* Block.WIDTH * parent.getZoom()) - canvas.getWidth());
        
        if(getOffset().getY() < 0)
        	getOffset().setY(0);
        
        if(getOffset().getY() > (nums.getY() * Block.HEIGHT * parent.getZoom()) - canvas.getHeight())
        	getOffset().setY((nums.getY() * Block.HEIGHT * parent.getZoom()) - canvas.getHeight()); 
        
	}
	
	private void checkBorders(){
		if(position.getX() * parent.getZoom() < 0)
			position.setX(0);
        
        if(position.getY() * parent.getZoom() < 0)
        	position.setY(0);
        
        while(parent == null || parent.getLevel() == null || parent.getLevel().getMap() == null)
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        
        GVector2f nums = parent.getLevel().getMap().getNumberOfBlocks();
        
        if(position.getX() * parent.getZoom() + Options.PLAYER_WIDTH * parent.getZoom() > nums.getX() * Block.WIDTH * parent.getZoom())
        	position.setX((nums.getX() * Block.WIDTH * parent.getZoom() - Options.PLAYER_WIDTH * parent.getZoom()) / parent.getZoom());
        
        if(position.getY() * parent.getZoom() + Options.PLAYER_HEIGHT * parent.getZoom() > nums.getY() * Block.HEIGHT * parent.getZoom())
        	position.setY((nums.getY() * Block.HEIGHT * parent.getZoom() - Options.PLAYER_HEIGHT * parent.getZoom())  / parent.getZoom());
	}

	@Override
	public GVector2f getOffset() {return offset;}
	public GVector2f getTotalMove() {return totalMove;}
}
