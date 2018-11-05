package main2.server.multiplayer;

public interface Connectable {
	/*
	 * I - ID
	 * E - \n
	 * X - SUR_X
	 * Y - SUR_Y
	 * N - NICK
	 * C - COLOR
	 * S - STATUS
	 * B - BOT_ID
	 * M - MAP_NAME
	 * H - HEALT
	 * F - FIELD
	 * K - KEY
	 */
	public static final byte G_CLIENT_CONNECT = 1;	//client pošle serveru správu o pripojení	I_E
	public static final byte G_CLIENT_DISCONNECT = 2;	//client pošle serveru správu o odpojení	I_E
	public static final byte G_PLAYER_NEW_POSITION = 3;	//client pošle serveru správu o novej pozícii	I_X_Y_E
	public static final byte G_PLAYER_KEY_DOWN = 4;	//client pošle serveru správu o stlaèení klávesy	I_K_E
	public static final byte G_PLAYER_INFO = 5;	//client pošle všetky informácie o sebe	I_N_C_E
	
	public static final byte S_CLIENT_DISCONNECT = 6;	//server pošle clientom správu o odpojení clienta	I_E
	public static final byte S_PLAYER_NEW_POSITION = 7;	//server pošle clientom správu o novej pozíci clienta	I_X_Y_E
	public static final byte S_PLAYER_PUT_BOMBE = 8;	//server pošle clientom správu o položení bomby	I_X_Y_E
	public static final byte S_PLAYER_ADD_NEW_ = 9;	//server pošle clientom správu o pripojení nového hráèa	I_N_C_X_Y_E
	public static final byte S_PLAYER_ACCESS_GRANTED_DANIED = 10;	//server povolý alebo zamietne clientovy pripojenie	S_X_Y_/serverove nastavenia/E
	public static final byte S_PLAYER_HIT = 11;	//server pošle clientovy správu o znížení zdravia H_E
	public static final byte S_BOT_NEW_POSITION = 12;	//server pošle clientom správu o pozicii bota	B_X_Y_E
	public static final byte S_BOT_KILLED = 13;	//server pošle clientom správu o zabití bota	B_E
	public static final byte S_BOT_HIT = 14;	//server pošle clientom správu o zabití bota	B_E
	public static final byte S_MAP_NAME = 15;	//server pošle clientovy správu o aktualne hranej mape	M_E
	public static final byte S_MAP_CHANGE = 16;	//server pošle clientom správu o zmene na mape	X_Y_F_E
}
