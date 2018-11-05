package main2.server;

import java.util.ArrayList;

import main2.server.component.Map;
import main2.server.component.Player;
import main2.server.core.Game;

public class Bomberman extends Game{
	public ArrayList<Player> players;
	public Map mapa;
	
	public Bomberman(){
		players = new ArrayList<Player>();
	}
}
