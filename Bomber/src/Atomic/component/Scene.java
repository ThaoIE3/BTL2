package Atomic.component;

import java.util.ArrayList;
import java.util.List;

import Atomic.enity.Enemy;
import Atomic.enity.weapon.Weapon;
import Atomic.object.GameObject;

public class Scene {
	private ArrayList<GameObject> scene = new ArrayList<GameObject>();

	public void add(GameObject g) {
		scene.add(g);
	}
	
	public void remove(GameObject g){
		scene.remove(g);
	}
	
	public void removeAll(List<GameObject> g){
		scene.removeAll(g);
	}
	
	public int getSize(){
		return scene.size();
	}
	
	public ArrayList<GameObject> getScene(){
		return new ArrayList<GameObject>(scene);
	}
	
	public boolean contains(GameObject g){
		return scene.contains(g);
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> get(T object){
		ArrayList<T> result = new ArrayList<T>();
		scene.stream()
			 .filter(a -> a.getClass().isAssignableFrom(object.getClass()))
			 .forEach(a -> result.add((T)a));
		return result;
	}
	
	public ArrayList<GameObject> getObjects(){
		return new ArrayList<GameObject>(scene);
	}
	

//	public ArrayList<GameObject> getOthers(){
//		ArrayList<GameObject> result = new ArrayList<GameObject>();
//		scene.stream().filter(a -> !(a instanceof GameObject) &&
//								   !(a instanceof Water) &&
//								   !(a instanceof ParticleEmmiter) &&
//								   !(a instanceof Hud)).forEach(a -> result.add((GameComponent)a));
//		return result;
//	}
}
