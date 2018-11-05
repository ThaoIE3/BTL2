package Bomberman.game.multiplayer;

import glib.util.vector.GVector2f;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import Bomberman.game.Game;
import Bomberman.game.Player;

public class ClientPlayer extends Player{
	private Socket socket;
	private ObjectInputStream objectReader;
	private ObjectOutputStream objectWritter;

	public ClientPlayer(Game parent, Socket socket, String name) {
		super(parent, new GVector2f(), name, 1);
		
		this.socket = socket;
		try {
			objectWritter = new ObjectOutputStream(socket.getOutputStream());
			objectWritter.flush();
			objectReader = new ObjectInputStream(socket.getInputStream());
			System.out.println("nov˝ hr·Ë ˙speöne nadviazal spojenie");
		} catch (IOException e) {
			System.out.println("nepodarilo sa vytvoriù reader alebo writter pri hr·Ëovi menom " + getName()); 
		}
	}

	public Object read(){
		try {
			Object o = objectReader.readObject();
			return o;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cleanUp() {
		try {
			socket.close();

			objectReader.close();
			objectWritter.close();
		} catch (IOException e) {
			System.out.println("nepodarilo sa zmazaù hr·Ëa menom " + getName());
		}
	}

	public void write(Serializable o){
		try {
			objectWritter.writeObject(o);
			objectWritter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
