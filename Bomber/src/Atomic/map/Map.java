package Atomic.map;

import glib.util.vector.GVector2f;

import java.awt.Graphics2D;

import Atomic.component.Level;
import Atomic.enity.Bomb;
import Atomic.enity.Player;
import Atomic.object.GameObject;

public class Map extends GameObject{
	public final static int NUM_X = 60;
	public final static int NUM_Y = 40;
	private Block[][] mapa;
	private Level level;
	private int destructible;
	private int drawable;
	
	//CONSTRUCTORS
	
	public Map(Level level){
		this.level = level;
		createMap();
	}
	
	//CREATORS
	
	public void createMap(){
		mapa = new Block[NUM_X][NUM_Y];
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				mapa[i][j] = new Block(new GVector2f(Block.WIDTH*i, Block.HEIGHT*j), (int)(Math.random()*1.3f),level);
			}
		}
	}
	
	//OVERRIDES
	
	public void render(Graphics2D g2){
		int res = 0;
		int res2 = 0;
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				if(mapa[i][j].getType()==0){
					continue;
				}
				GVector2f p = mapa[i][j].getPosition();
				GVector2f o = level.getOffset();
				//podmienka na vykreslovanie len viditelnych blokov
				if(p.getX() + Block.WIDTH < o.getX() || o.getX()+level.getCanvas().getWidth()<p.getX() ||
				   p.getY() + Block.HEIGHT < o.getY() || o.getY()+level.getCanvas().getHeight()<p.getY())
					continue;
				
				mapa[i][j].render(g2);
				res2++;
//				if(mapa[i][j].getType()>0)
//					res++;
			}
		}
		drawable = res2;
		destructible = res;
	}
	
	//OTHERS
	
	public boolean isCollision(GVector2f p){
		GVector2f pos = p.div(new GVector2f(Block.WIDTH, Block.HEIGHT));
		if(exist(pos.getXi(),pos.getYi()) && mapa[pos.getXi()][pos.getYi()].getType()==0){
			return false;
		}
		return true;
	}

	private boolean exist(int i, int j){
		return i>=0 && j>=0 && i<NUM_X && j<NUM_Y;
	}
	
	public int[] calcBombDist(GVector2f from, Player player){ 
		GVector2f sur = getSur(from);
		int v0, v1, v2, v3;
		v0 = v1 = v2 = v3 = -1;
		if(!player.isNano()){
			for(int i=1 ; i<=player.getRange() ; i++){
				if(v0 < 0 && exist(sur.getXi(), sur.getYi()-i) &&  mapa[sur.getXi()][sur.getYi()-i].getType()>0)
					v0 = i - 1 + Bomb.OVERDRAW_BLOCK;
				
				if(v1 < 0 && exist(sur.getXi()+i, sur.getYi()) && mapa[sur.getXi()+i][sur.getYi()].getType()>0)
					v1 = i - 1 + Bomb.OVERDRAW_BLOCK;
				
				if(v2 < 0 && exist(sur.getXi(), sur.getYi()+i) && mapa[sur.getXi()][sur.getYi()+i].getType()>0)
					v2 = i - 1 + Bomb.OVERDRAW_BLOCK;
				
				if(v3 < 0 && exist(sur.getXi()-i, sur.getYi()) && mapa[sur.getXi()-i][sur.getYi()].getType()>0)
					v3 = i - 1 + Bomb.OVERDRAW_BLOCK;
			}
		}
		if(v0 < 0)
			v0 = player.getRange();
		if(v1 < 0)
			v1 = player.getRange();
		if(v2 < 0)
			v2 = player.getRange();
		if(v3 < 0)
			v3 = player.getRange();
		return new int[]{v0, v1, v2, v3};
		
	}

	//GETTERS
	
	public GVector2f getSur(GVector2f v){
		return get(v).getPosition().div(new GVector2f(Block.WIDTH, Block.HEIGHT));
	}
	
	public Block get(GVector2f p){	//vráti blok na mape podla suradnic
		GVector2f playerPos = p.div(new GVector2f(Block.WIDTH, Block.HEIGHT));
		if(!exist(playerPos.getXi(),playerPos.getYi()))
			return new Block(new GVector2f(),0,level);
		return mapa[playerPos.getXi()][playerPos.getYi()];
	}
	
	public int getDestructible() {
		return destructible;
	}

	public int getDrawable() {
		return drawable;
	}
}
