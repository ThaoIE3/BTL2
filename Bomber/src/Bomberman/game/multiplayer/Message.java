package Bomberman.game.multiplayer;

import org.json.JSONObject;

public class Message {
	private final static String KEY_WORD_TYPE = "type";
	private final static String KEY_WORD_CONTENT = "content";
	
	public final static int TS_PLAYER_NAME 		= 0;
	public final static int FS_PLAYER_POSITION  = 1;
	public final static int FS_ACTUAL_LEVEL 	= 2;
	public final static int FS_NEW_PLAYER 		= 3;
	public final static int TS_PLAYER_MOVE 		= 4;
	public final static int FS_PLAYER_NEW_POS 	= 4;
	public final static int FS_EVERITHING_SEND 	= 5;
	public final static int TS_PLAYER_IMAGE 	= 6;	//hráè pošle serveru obrázok
	public final static int FS_PLAYER_IMAGE 	= 7;	//server pošle všdetkým hráèom obrázok
	public final static int TS_ADD_BOMB 		= 8;
	public final static int FS_AFFECTED_BLOCKS  = 9;
	public final static int FS_PLAYER_WAS_HIT   = 10;
	public final static int FS_PLAYER_NAME   	= 11;
	public final static int FS_ADD_EXPLOSION   	= 12;
	public final static int FS_ADD_ITEM		   	= 13;
	public final static int FS_PLAYER_EAT_ITEM	= 14;
	public final static int TS_PLAYER_EAT_ITEM	= 15;
	
	private int type;
	private String content;
	
	public Message(String data){
		JSONObject result = new JSONObject(data);
		this.type = result.getInt(KEY_WORD_TYPE);
		this.content = result.getString(KEY_WORD_CONTENT); 
	}
	
	public Message(int type, String content){
		this.type = type;
		this.content = content;
	}
	
	public Message(int type, JSONObject content){
		this.type = type;
		this.content = content.toString();
	}
	
	public int getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		JSONObject result = new JSONObject();
		result.put(KEY_WORD_TYPE, type);
		result.put(KEY_WORD_CONTENT, content);
		return result.toString();
	}
}
