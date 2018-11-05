package Bomberman.game;

import java.awt.Graphics2D;

import Bomberman.Options;
import Bomberman.core.CoreEngine;
import Bomberman.game.multiplayer.Communicable;
import Bomberman.game.multiplayer.GameClient;
import Bomberman.game.multiplayer.GameServer;
import Bomberman.gui.MainMenu;

public class CoreGame extends CoreEngine{
	public static final int MAIN_MENU = 0;
	public static final int RUNNING = 1;
	
	protected int gameIs =  MAIN_MENU;
	
	private MainMenu mainMenu = new MainMenu(this);
	private Game game;
	private GameServer server;
	private GameClient client;
	
	//CONSTRUCTORS
	
	public CoreGame(){
		super(Options.FPS, Options.UPS, Options.RENDER_TIME);
	}
	
	//OVERRIDES
	
	@Override
	protected void render(Graphics2D g2) {
		clearScreen(g2);
		
		switch(gameIs){
			case MAIN_MENU:
				mainMenu.render(g2);
				break;
			case RUNNING:
				if(game != null)
					game.render(g2);
				break;
		}
	}
	
	@Override
	protected void update(float delta) {
		if(gameIs == MAIN_MENU)
			mainMenu.update(delta);
		
		if(game != null)
			game.update(delta);
		
	}
	
	@Override
	protected void input() {
		switch(gameIs){
			case MAIN_MENU:
				mainMenu.input();
				break;
			case RUNNING:
				if(game != null)
					game.input();
				break;
		}
	}
	
	@Override
	protected void init() {
		getInput().addMenu(mainMenu);
	}
	
	@Override
	public void onResize() {
		mainMenu.updateSize();
	}
	
	//OTHERS
	
	private void clearScreen(Graphics2D g2){
		g2.setColor(Options.BACKGROUND_COLOR);
		g2.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());
	}
	
	public int gameIs() {
		return gameIs;
	}

	//MENU_ACTIONS

	public void startNewGame(){
		if(gameIs == RUNNING)
			return;
		
		stopGame();

		server = new GameServer(this);
		game = new Game(this);
		
		gameIs = RUNNING;
	}
	
	public void joinGame(){
		stopGame();
		
		client = new GameClient(this);
		game = new Game(this);
		
		do{
			game.checkReady();
		}while(!game.isReady());
		
		getConnection().sendImage();
		
		gameIs = RUNNING;
	}
	
	public void continueGame(){
		if(game == null || gameIs == RUNNING)
			return;
		gameIs = RUNNING;
	}
	
	public void pausedGame(){
		gameIs = MAIN_MENU;
		mainMenu.setGameLaunched(true);
	}
	
	public void stopGame(){
		if(game != null)
			game.cleanUp();
		
		if(client != null)
			client.cleanUp();
		
		if(server != null)
			server.cleanUp();
		
		mainMenu.setGameLaunched(false);
		game = null;
	}

	public void exitGame() {
		
		stopGame();
		
		mainMenu.cleanUp();
		stop();
		cleanUp();
		System.exit(1);
	}

	//SETTERS

	public void setGame(Game game) {
		this.game = game;
	}

	//GETTERS
	

	public boolean isLaunched(){
		return game != null;
	}
	
	public Communicable getConnection(){
		if(server != null && client == null)
			return server;
		
		if(server == null && client != null)
			return client;
		return null;
	}
	
	public Game getGame() {
		return game;
	}

	public GameServer getServer() {
		return server;
	}

}
