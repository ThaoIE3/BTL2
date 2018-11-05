package main2.server.core;

public class Vector2f {
	private float x;
	private float y;
	
	public Vector2f(){
		this(0,0);
	}
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getLength(){
		return (float)Math.sqrt(x * x + y * y);
	}

	public float max(){
		return Math.max(x, y);
	}
	
	public float min(){
		return Math.min(x, y);
	}
	
	public Vector2f add(Vector2f r){
		return new Vector2f(x + r.getX(), y + r.getY());
	}
	
	public Vector2f add(float r){
		return new Vector2f(x + r, y + r);
	}
	
	public Vector2f sub(Vector2f r){
		return new Vector2f(x - r.getX(), y - r.getY());
	}
	
	public Vector2f sub(float r){
		return new Vector2f(x - r, y - r);
	}
	
	public Vector2f mul(Vector2f r){
		return new Vector2f(x * r.getX(), y * r.getY());
	}
	
	public Vector2f mul(float r){
		return new Vector2f(x * r, y * r);
	}
	
	public Vector2f div(Vector2f r){
		return new Vector2f(x / r.getX(), y / r.getY());
	}
	
	public Vector2f div(float r){
		return new Vector2f(x / r, y / r);
	}
	
	public Vector2f Abs(){
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public float getX() {return x;}
	public float getY() {return y;}
	public int getXi() {return (int)x;}
	public int getYi() {return (int)y;}
	
	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}
	
	public String toString(){
		return "(" + x + " " + y + ")";
	}
	
	public boolean equals(Vector2f r){
		return x == r.getX() && y == r.getY();
	}
}
