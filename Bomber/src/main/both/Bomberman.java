package main.both;

import java.util.ArrayList;

import main.both.components.Log;
import main.both.components.Map;
import main.both.components.MyPlayer;
import main.both.core.Game;
import main.both.core.GameObject;
import main.both.core.utils.Window;
import main.both.multiplayer.OtherPlayer;
import main.both.multiplayer.Server;

public class Bomberman extends Game{
	private Server server;
	public ArrayList<OtherPlayer> players = new ArrayList<OtherPlayer>();
	
	public int pocetHracov = 0;
	
	public Bomberman(){
		server = new Server(this);
	}
	
	public void init(){
		this.mapa = new Map("mapa1",player);
		player = new MyPlayer("Gabriel");
		
		Window.keyboard.addPlayer(player);
		//GameObject mapa = new GameObject().addComponent(new Map(30,30,p));
		GameObject mapa = new GameObject().addComponent(this.mapa);
		addObject(mapa);
		addObject(new GameObject().addComponent(player));
		
		addObject(new GameObject().addComponent(new Log(this)));
	}
	
	public void createPlayer(){
		
	}

	public void updateClients() {
		server.write(Server.PLAYER_POSITION+" "+player.getName()+" "+player.getPosition().getX()+" "+player.getPosition().getY());
		player.moved = false;
	}
	
	public void close(){
		server.close();
	}
}
