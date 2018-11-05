package Bomberman.game.multiplayer;


import glib.util.vector.GVector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Bomberman.Options;
import Bomberman.game.CoreGame;
import Bomberman.game.Game;
import Bomberman.game.Player;
import Bomberman.game.entities.Bomb;
import Bomberman.game.level.Block;
import Bomberman.game.level.Level;
import Bomberman.game.level.Map;
import Bomberman.game.multiplayer.core.Server;

public class GameServer extends Server implements Communicable{
//	private HashMap<String, Player> players = new HashMap<String, Player>();
	private Level actLevel;
	
	//CONSTRUCTORS
	public GameServer(CoreGame parent){
		super(parent);
		actLevel = new Level(parent);
	}
	
	//OTHERS
	
	protected void establishConnection(String name, ClientPlayer player){
		actLevel.getParrent().addPlayer(name, player);
		
		player.write(actLevel);
		player.setPosition(actLevel.getNthPlayerDefaltPosition(getNumberOfClients()));
		
		player.write(Message.FS_PLAYER_POSITION + " " + player.getPosition());
		
		doForAllClients(a -> {player.write(Message.FS_NEW_PLAYER         + " " + 
		                                   a.getValue().getName()       + " " + 
		                                   a.getValue().getImage()  	+ " " + 
		                                   a.getValue().getDirection()  + " " + 
		                                   a.getValue().getHealt() 		+ " " + 
		                                   a.getValue().getPosition() 	+ " " + 
		                                   a.getValue().getSpeed());
		});
		
		Player me = actLevel.getParrent().getMyPlayer();
		
		player.write(Message.FS_NEW_PLAYER   + " " + 
		             me.getName() 			+ " " + 
				     me.getImage() 			+ " " + 
		             me.getDirection() 		+ " " + 
				     me.getHealt() 			+ " " + 
		             me.getPosition() 		+ " " + 
				     me.getSpeed());
		
		player.write(Message.FS_EVERITHING_SEND + " ");
	}
	
	@Override
	public boolean canConnect() {
		return getNumberOfClients() < actLevel.getMaxPlayers();
	}
	
	@Override
	public HashMap<String, Player> getPlayers() {
		HashMap<String, Player> result = new HashMap<String, Player>();
		
		doForAllClients(a -> result.put(a.getKey(), (Player)a.getValue()));
		return result;
	}

	@Override
	public void playerMove(GVector2f move, int direction) {
		Player p = actLevel.getParrent().getMyPlayer();
		write(Message.FS_PLAYER_NEW_POS + " " + p.getName() + " " + move + " " + direction);
	}

	@Override
	public void sendImage() {
		
	}

	@Override
	public void putBomb(GVector2f position) {
		Player player = actLevel.getParrent().getMyPlayer();
		actLevel.getParrent().addBomb(Options.BOMB_DEFAULT_TIME, 
									  position, 
									  player.getRange(),
									  Options.BOMB_DEFAULT_TIME);
		write(Message.TS_ADD_BOMB + " " + position + " " + Options.BOMB_DEFAULT_TIME + " " + player.getRange());
	}

	private void detonateAnother(String a){
		Bomb b = actLevel.getParrent().getBomb(new GVector2f(a).mul(Block.SIZE).toString());
		
		if(b != null && !b.isDead())
			b.explode();
	}
	
	@Override
	public void bombExplode(Bomb bomb) {
		Map mapa = actLevel.getMap();
		Game game = actLevel.getParrent();
		GVector2f bombSur = bomb.getSur();
		ArrayList<String> affectedBlocks = mapa.getBlockAroundPoint(bombSur, bomb.getRange());
		
		write(Message.FS_ADD_EXPLOSION + " " +
		      bomb.getPosition() 	  + " " +
			  "explosion1.png" 		  + " " +
		      new GVector2f(5, 5));
		
		
		if(!bomb.isAtom())
			affectedBlocks = affectedBlocks.stream()
										   .filter(a -> bombSur.atLeastOneSame(new GVector2f(a)))
										   .collect(Collectors.toCollection(ArrayList<String>::new));
		
		affectedBlocks.stream()
//					  .peek(a -> detonateAnother(a))
				 	  .forEach(a -> {
				 		  int num = (int)(Math.random() * 4);
				 		  game.addItem(new GVector2f(a), num);
				 		  write(Message.FS_ADD_ITEM + " " + a + " " + num);
				 	  });
		mapa.hitBlocks(affectedBlocks, bomb.getDemage());
		
		StringBuilder msg = new StringBuilder(Message.FS_AFFECTED_BLOCKS + " ");
		affectedBlocks.stream()
		              .forEach(a -> msg.append(a + "-" + mapa.getBlock(a).getType() 
		            		                     + "-" + mapa.getBlock(a).getHealt() + " "));
		
		
		game.addExplosion(bomb.getPosition(), 
						  "explosion1.png", 
						  new GVector2f(5,5), 
						  Options.EXPLOSION_DEFAULT_OFFSET,
						  Options.EXPLOSION_DEFAULT_DELAY);
		
		
		game.getPlayers().entrySet()
		                 .stream()
		                 .filter(a -> isPlayerHitted(a.getValue(), bomb))
		                 .forEach(a -> getClient(a.getKey()).write(Message.FS_PLAYER_WAS_HIT + " " + bomb.getDemage()));
		
		
		if(isPlayerHitted(game.getMyPlayer(), bomb))
			game.getMyPlayer().addDemmage(-bomb.getDemage());
		
		write(msg.toString());
	}

	@Override
	protected void proccesMsg(String msg, Entry<String, ClientPlayer> temp){
		ClientPlayer value = temp.getValue();
		String key = temp.getKey();

		String[] strings = msg.split(" ");
		if(msg.startsWith(Message.TS_PLAYER_NAME + " ")){
			String name = strings[1];
			write(Message.FS_PLAYER_NAME + " " + key + " " + name);
			renameClient(key, msg.split(" ")[1]);
			actLevel.getParrent().renamePlayer(key, name);
			
		}
		else if(msg.startsWith(Message.TS_PLAYER_EAT_ITEM + " ")){
			actLevel.getParrent().removeItem(strings[2]);
			write(Message.FS_PLAYER_EAT_ITEM + " " + temp.getValue().getName() + " " + strings[1] + " " + strings[2]);
		}
		else if(msg.startsWith(Message.TS_PLAYER_IMAGE + " ")){
			value.setImage(msg.split(" ")[1]);
			write(Message.FS_PLAYER_IMAGE + " " + value.getName() + " " + value.getImage());
		}
		else if(msg.startsWith(Message.TS_PLAYER_MOVE + " ")){
			GVector2f move = new GVector2f(strings[1]);
			value.setPosition(move);
			value.setDirection(Integer.parseInt(strings[2]));
			write(Message.FS_PLAYER_NEW_POS + " " + value.getName() + " " + move + " " + value.getDirection());
		}
		else if(msg.startsWith(Message.TS_ADD_BOMB + " ")){
			actLevel.getParrent().addBomb(Integer.parseInt(strings[2]), 
										  new GVector2f(strings[1]), 
										  Integer.parseInt(strings[3]),
										  Options.BOMB_DEFAULT_TIME);
			write(msg);
		}
	}
	
	public void eatItem(GVector2f sur, int type){
		actLevel.getParrent().removeItem(sur.toString());
		write(Message.FS_PLAYER_EAT_ITEM + " " + actLevel.getParrent().getMyPlayer().getName() + " " + type + " " + sur);
	}
	
	//GETTERS

	public Level getLevel(){
		return actLevel;
	}

	@Override
	public int getNumberPlayersInGame() {
		return getNumberOfClients() + 1;
	}
	
	@Override
	public boolean isReady() {
		return true;
	}

	private boolean isPlayerHitted(Player player, Bomb bomb){
		boolean inRange = player.getSur().dist(bomb.getPosition().div(40).toInt()) <= bomb.getRange();
		if(bomb.isAtom())
			return inRange;
		else
			return inRange && player.getSur().atLeastOneSame(bomb.getSur());
			
	}

	@Override
	public GVector2f getMyPosition() {
		return new GVector2f(40, 40);
	}
		
}
