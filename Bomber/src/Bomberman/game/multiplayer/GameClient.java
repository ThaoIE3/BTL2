package Bomberman.game.multiplayer;

import java.util.HashMap;

import glib.util.vector.GVector2f;
import Bomberman.Options;
import Bomberman.game.CoreGame;
import Bomberman.game.Player;
import Bomberman.game.entities.Bomb;
import Bomberman.game.level.Level;
import Bomberman.game.level.Map;
import Bomberman.game.multiplayer.core.Client;
import Bomberman.game.multiplayer.core.Server;

public class GameClient extends Client implements Communicable{
	private CoreGame parent;
	private Level actLevel;
	private GVector2f startingPlayerPosition;
	private boolean ready;

	//CONSTRUCTORS
	
	public GameClient(CoreGame parent) {
		this.parent = parent;
	}

	//OTHERS
	
	@Override
	public int getNumberPlayersInGame() {
		return players.size() + 1;
	}
	
	public void proccesObject(Object o){
		if(o instanceof Level){
			actLevel = (Level)o;
			actLevel.setParent(parent);
		}
	}
	
	@Override
	public void proccesMsg(String msg) {
		String[] message = msg.split(" ");
		if(msg.startsWith(Message.FS_PLAYER_POSITION + " ")){
			String s = message[1];
			startingPlayerPosition = new GVector2f(s);
		}
		else if(msg.startsWith(Message.FS_ADD_EXPLOSION + " ")){
			actLevel.getParrent().addExplosion(new GVector2f(message[1]), 
											   message[2], 
											   new GVector2f(message[3]), 
											   Options.EXPLOSION_DEFAULT_OFFSET,
											   Options.EXPLOSION_DEFAULT_DELAY);
		}
		else if(msg.startsWith(Message.FS_PLAYER_EAT_ITEM + " ")){
			Player player = actLevel.getParrent().getPlayers().get(message[1]);
			if(player == null)
				player = actLevel.getParrent().getMyPlayer();
			player.eatItemType(Integer.parseInt(message[2]));
			
			actLevel.getParrent().removeItem(message[3]);
		}
		else if(msg.startsWith(Message.FS_ADD_ITEM + " ")){
			actLevel.getParrent().addItem(new GVector2f(message[1]), 
										  Integer.parseInt(message[2]));
		}
		else if(msg.startsWith(Message.FS_PLAYER_WAS_HIT + " ")){
			
			actLevel.getParrent().getMyPlayer().addDemmage(-Integer.parseInt(message[1]));
		}
		else if(msg.startsWith(Message.FS_AFFECTED_BLOCKS + " ")){
			Map mapa = actLevel.getMap();
			for(int i=1 ; i<message.length ; i++){
				String[] s = message[i].split("-");
				if(s[2].isEmpty() || s[1].isEmpty())
					continue;
				mapa.getBlock(s[0]).setHealt(Integer.valueOf(s[2]));
				mapa.getBlock(s[0]).setType(Integer.valueOf(s[1]));
			}
		}
		else if(msg.startsWith(Message.FS_NEW_PLAYER + " ")){
			String[] res = msg.replace("  ", " ").split(" ");
			players.put(res[1],new Player(actLevel.getParrent(), 
										  new GVector2f(res[5]), 
										  res[1], 
										  Integer.parseInt(res[6]), 
										  Integer.parseInt(res[4]), 
										  res[2]));
			
			System.out.println("hr��: " + res[1] + " m� obr�zok: " + players.get(res[1]).getImage());
			
			while(actLevel == null || actLevel.getParrent() == null || players.get(res[1]) == null){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			actLevel.getParrent().addPlayer(res[1], players.get(res[1]));
		}
		else if(msg.startsWith(Message.FS_EVERITHING_SEND + " ")){
			ready = true;
		}
		else if(msg.startsWith(Message.TS_PLAYER_NAME + " ")){
			Player temp = players.get(message[1]);
			players.remove(message[1]);
			temp.setName(message[2]);
			players.put(message[2], temp);
			System.out.println("playerovy sa zmenilo meno");
		}
		else if(msg.startsWith(Message.FS_PLAYER_IMAGE + " ")){
			String name = actLevel.getParrent().getMyPlayer().getName();
			
			if(name == message[1])
				return;
			players.get(name).setImage(message[2]);
			System.out.println("obr�zok nastaven�");
		}
		else if(msg.startsWith(Message.FS_PLAYER_NEW_POS + " ")){
			Player player = players.get(message[1]);
			
			if(player != null){
				player.setPosition(new GVector2f(message[2]));
				player.setDirection(Integer.parseInt(message[3]));
			}
		}
		else if(msg.startsWith(Message.TS_ADD_BOMB + " ")){
			actLevel.getParrent().addBomb(Integer.parseInt(message[2]), 
										  new GVector2f(message[1]), 
										  Integer.parseInt(message[3]),
										  Options.BOMB_DEFAULT_TIME);
		}
	}

	@Override
	public void playerMove(GVector2f move, int direction) {
		write(Message.TS_PLAYER_MOVE + " " + move + " " + direction);
	}

	@Override
	public void sendImage() {
		write(Message.TS_PLAYER_IMAGE + " " + actLevel.getParrent().getMyPlayer().getImage());
	}

	@Override
	public void putBomb(GVector2f position) {
		Player player = actLevel.getParrent().getMyPlayer();
		write(Message.TS_ADD_BOMB + " " + position + " " + Options.BOMB_DEFAULT_TIME + " " + player.getRange());
	}

	@Override
	public void bombExplode(Bomb bomb) {
		return;
	}

	//GETTERS
	
	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public Level getLevel() {
		return actLevel;
	}

	@Override
	public GVector2f getMyPosition() {
		return startingPlayerPosition;
	}
	
	@Override
	public HashMap<String, Player> getPlayers() {
		return players;
	}

	
	@Override
	public void eatItem(GVector2f sur, int type) {
		actLevel.getParrent().removeItem(sur.toString());
		write(Message.TS_PLAYER_EAT_ITEM + " " + type + " " + sur);
	}
	
}
