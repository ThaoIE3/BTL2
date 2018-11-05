package Bomberman.gui;

import java.awt.Graphics2D;

import Bomberman.game.CoreGame;
import glib.util.vector.GVector2f;

public final class MainMenu extends Menu{
	public final static int MAIN_MENU = 0;
	public final static int OPTIONS = 1;
	public final static int OPTIONS_NEW_GAME = 2;
	public final static int OPTIONS_JOIN_GAME = 3;
	
	
	
	CoreGame parent;
	private int actMenu;
	private Menu OptionsMenu;
	
	public MainMenu(CoreGame parent) {
		super(parent.getCanvas());
		this.parent = parent;
		
		
		addButton("newGame", "Nová hra");
		addButton("continue", "Pokraèova");
		addButton("stopGame", "Stopnú hru");
		addButton("connectToGame", "Pripoji sa k hre");
		addButton("options", "Nastavenia");
		addButton("exit", "Koniec");
		setGameLaunched(false);
		
		OptionsMenu = new OptionsMenu(parent.getCanvas(), this); 
		
		actMenu = MAIN_MENU;
	}

	@Override
	public void doAct(GVector2f click) {
		if(parent.gameIs() != CoreGame.MAIN_MENU)
			return;
		if(actMenu == MAIN_MENU){
			if(components.get("newGame").isClickIn(click))
				parent.startNewGame();
			else if(components.get("continue").isClickIn(click))
				parent.continueGame();
			else if(components.get("stopGame").isClickIn(click))
				parent.stopGame();
			else if(components.get("connectToGame").isClickIn(click))
				parent.joinGame();
			else if(components.get("options").isClickIn(click))
				actMenu = OPTIONS;
			else if(components.get("exit").isClickIn(click))
				parent.exitGame();
		}
		else if(actMenu == OPTIONS)
			OptionsMenu.doAct(click);
		
	}
	
	@Override
	public void update(float delta) {
		if(actMenu == MAIN_MENU)
			components.entrySet()
				   .stream()
				   .forEach(a -> a.getValue().update(delta));
		else if(actMenu == OPTIONS)
			OptionsMenu.update(delta);
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(actMenu == MAIN_MENU)
			components.entrySet()
				   .stream()
				   .forEach(a -> a.getValue().render(g2));
		else if(actMenu == OPTIONS)
			OptionsMenu.render(g2);
	}
	
	public void setGameLaunched(boolean value){
		components.get("continue").setActive(value);
		components.get("stopGame").setActive(value);
	}

	public void cleanUp() {
		components.entrySet().forEach(a -> a.getValue().cleanUp());
		components.clear();
	}

	public void updateSize(){
		components.entrySet()
				  .stream()
				  .forEach(a -> a.getValue().updateSize());
		
		OptionsMenu.updateSize();
	}
	
	public void setActMenu(int actMenu) {
		this.actMenu = actMenu;
	}
}
