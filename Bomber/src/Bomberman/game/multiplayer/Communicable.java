package Bomberman.game.multiplayer;

import java.util.HashMap;

import glib.util.vector.GVector2f;
import Bomberman.game.Player;
import Bomberman.game.entities.Bomb;
import Bomberman.game.entities.XBomb;
import Bomberman.game.level.Level;

public interface Communicable {
	//FROM CLIENT TO SERVER
	public void playerMove(GVector2f move, int direction);
	public void sendImage();
	public void putBomb(GVector2f position);
	public boolean isReady();
	public void eatItem(GVector2f sur, int type);
	public void bombExplode(Bomb bomb);

	public Level getLevel();
	public int getNumberPlayersInGame();
	public GVector2f getMyPosition();
	
	public default HashMap<String, Player> getPlayers(){
		return new HashMap<String, Player>();
	};
}
