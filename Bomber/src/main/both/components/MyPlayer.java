package main.both.components;

import main.MainBomber;
import main.both.Constants;
import main.both.core.utils.Logs;
import main.both.core.utils.Vector2f;

public class MyPlayer extends Player{
	//defaultné klávesy 
	private static int keyUp = Constants.DEFAULT_UP_KEY;
	private static int keyDown= Constants.DEFAULT_DOWN_KEY;
	private static int keyRight = Constants.DEFAULT_RIGHT_KEY;
	private static int keyLeft = Constants.DEFAULT_LEFT_KEY;
	
	public boolean moved = false;
	
	public MyPlayer(String name){
		Logs.write("Vytvoril sa hráè "+name);
		this.name = name;
		this.speed = Constants.DEFAULT_SPEED;
		this.bombs = Constants.DEFAULT_NUMBER_OF_BOMBS;
		this.dosah = Constants.DEFAULT_EXPLOSION_RANGE;
		position = new Vector2f(0, 0);
		offset = new Vector2f(0 - MainBomber.WIDTH / 2, 0 - MainBomber.HEIGHT / 2);
	}
	
	public void update(float delta){
		//nastaví posun
		offset.setX(position.getX() - MainBomber.WIDTH / 2);
		offset.setY(position.getY() - MainBomber.HEIGHT / 2);

		//skontroluje posun
		if(offset.getX() < 0){
			offset.setX(0);
        }
        if(offset.getX() > (Map.getNumX() * Map.block) - MainBomber.WIDTH){
        	offset.setX((Map.getNumX() * Map.block) - MainBomber.WIDTH);
        }
        if(offset.getY() < 0){
			offset.setY(0);
        }	        
        if(offset.getY() > (Map.getNumY() * Map.block) - MainBomber.HEIGHT){
        	offset.setY((Map.getNumY() * Map.block) - MainBomber.HEIGHT); 
        }
        
        //skontroluje pozíciu
        if(position.getX() < 0){
        	position.setX(0);
        }
        if(position.getY() < 0){
        	position.setY(0);
        }
        if(position.getX() + Map.block > Map.getNumX() * Map.block){
        	position.setX(Map.getNumX() * Map.block - Map.block);
        }
        if(position.getY() + Map.block > Map.getNumY() * Map.block){
        	position.setY(Map.getNumY() * Map.block - Map.block);
        }
	}
	
	public void input(float delta){
		delta = 1;
		//posunie hráèa
		if(this.keys[0]){
			position.addToY(-speed * delta);
			moved = true;
		}
		if(this.keys[1]){
			position.addToX(-speed * delta);
			moved = true;
		}
		if(this.keys[2]){
			position.addToY(speed * delta);
			moved = true;
		}
		if(this.keys[3]){
			position.addToX(speed * delta);
			moved = true;
		}
	}

	public void addBomb() {
		//addBomb
	}
}
