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
	public static final byte G_CLIENT_CONNECT = 1;	//client po�le serveru spr�vu o pripojen�	I_E
	public static final byte G_CLIENT_DISCONNECT = 2;	//client po�le serveru spr�vu o odpojen�	I_E
	public static final byte G_PLAYER_NEW_POSITION = 3;	//client po�le serveru spr�vu o novej poz�cii	I_X_Y_E
	public static final byte G_PLAYER_KEY_DOWN = 4;	//client po�le serveru spr�vu o stla�en� kl�vesy	I_K_E
	public static final byte G_PLAYER_INFO = 5;	//client po�le v�etky inform�cie o sebe	I_N_C_E
	
	public static final byte S_CLIENT_DISCONNECT = 6;	//server po�le clientom spr�vu o odpojen� clienta	I_E
	public static final byte S_PLAYER_NEW_POSITION = 7;	//server po�le clientom spr�vu o novej poz�ci clienta	I_X_Y_E
	public static final byte S_PLAYER_PUT_BOMBE = 8;	//server po�le clientom spr�vu o polo�en� bomby	I_X_Y_E
	public static final byte S_PLAYER_ADD_NEW_ = 9;	//server po�le clientom spr�vu o pripojen� nov�ho hr��a	I_N_C_X_Y_E
	public static final byte S_PLAYER_ACCESS_GRANTED_DANIED = 10;	//server povol� alebo zamietne clientovy pripojenie	S_X_Y_/serverove nastavenia/E
	public static final byte S_PLAYER_HIT = 11;	//server po�le clientovy spr�vu o zn�en� zdravia H_E
	public static final byte S_BOT_NEW_POSITION = 12;	//server po�le clientom spr�vu o pozicii bota	B_X_Y_E
	public static final byte S_BOT_KILLED = 13;	//server po�le clientom spr�vu o zabit� bota	B_E
	public static final byte S_BOT_HIT = 14;	//server po�le clientom spr�vu o zabit� bota	B_E
	public static final byte S_MAP_NAME = 15;	//server po�le clientovy spr�vu o aktualne hranej mape	M_E
	public static final byte S_MAP_CHANGE = 16;	//server po�le clientom spr�vu o zmene na mape	X_Y_F_E
}
