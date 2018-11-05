package main.both.core.utils;

public class Vector2f {
	private float x,y;
	
	public Vector2f(){
		this(0,0);
	};
	
	public Vector2f(float x, float y){
		this.x=x;
		this.y=y;
	};
	
	public Vector2f(Vector2f v){
		this.x = v.getX();
		this.y = v.getY();
	};
	
	public float dot(Vector2f v){
		//create dot product
        return x * v.getX() + y * v.getY();
    };
    
	public float getLength(){
		return (float)Math.sqrt(this.x*this.x+this.y*this.y);
	};
	
	public float max(){
		return Math.max(this.x,this.y);
	};
	
	public float min(){
		return Math.min(this.x,this.y);
	};
	
	public void normalize(){
		float dlzka=this.getLength();
		this.x/=dlzka;
		this.y/=dlzka;
	}
	
	public void rotate(float angle){
		float rad=(float)Math.toRadians(angle);

		float cos=(float)Math.cos(rad);
		float sin=(float)Math.sin(rad);
		this.x = (x * cos - y * sin);
		this.y = (x * sin + y * cos);
	}
	
	public float dist(Vector2f v){
		//return distance between 2 point
		float dx = x - v.x;
		float dy = y - v.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
	
	public void negate(){
		this.x *= -1;
		this.y *= -1;
	}
	
	public float angleBetween(Vector2f v) {
		float dotProduct = dot(v);
		float angle = (float)Math.acos(dotProduct);
		return angle;
	}
	
	public void add(Vector2f v){
		this.x += v.getX();
		this.y += v.getY();
	};
	
	public void add(float num){
		this.x += num;
		this.y += num;
	};
	
	public void sub(Vector2f v){
		this.x -= v.getX();
		this.y -= v.getY();
	};
	
	public void sub(float num){
		this.x -= num;
		this.y -= num;
	}
	
	public void mul(Vector2f v){
		this.x *= v.getX();
		this.y *= v.getY();
	};
	
	public void mul(float num){
		this.x *= num;
		this.y *= num;
	}
	
	public void div (Vector2f v){
		this.x /= v.getX();
		this.y /= v.getY();
	};
	
	public void div (float num){
		this.x /= num;
		this.y /= num;
	};
	
	public void abs(){
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	};
	
	public void addToY(float y){
		this.y += y;
	};
	
	public void addToX(float x){
		this.x += x;
	};
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	};
	
	public void set(Vector2f a){
		set(a.getX(), a.getY());
	};
	
	public String toString(){
		return "["+this.x+"x"+this.y+"]";
	}
	
	public static Vector2f interpolateLinear(float scale, Vector2f startValue, Vector2f endValue, Vector2f store) {
        if (store == null)
            store = new Vector2f();
        store.setX(interpolateLinear(scale, startValue.getX(), endValue.getX()));
        store.setY(interpolateLinear(scale, startValue.getY(), endValue.getY()));
        return store;
    }
	
	public static Vector2f interpolateLinear(float scale, Vector2f startValue, Vector2f endValue) {
        return interpolateLinear(scale, startValue, endValue, null);
    }
	
	public static float interpolateLinear(float scale, float startValue, float endValue) {
        if (startValue == endValue)
            return startValue;
        
        if (scale <= 0f)
            return startValue;
        
        if (scale >= 1f)
            return endValue;
        
        return ((1f - scale) * startValue) + (scale * endValue);
    }
	
	public boolean isInRect(float x, float y, float w, float h){
		if(this.x > x && this.x < x + w && this.y > y && this.y < y + h)
			return true;
		return false;
	};
	
	public boolean isInCircle(float x, float y, float r){
		double dist;
		dist = (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);
		dist = Math.sqrt(dist);
		if(dist < r)
			return true;
		return false;
	}
	
	public Vector2f getInstatnce(){
		return new Vector2f(this);
	}
	
	public boolean equals(Vector2f v){
		return x == v.getX() && y == v.getY();
	};
}
