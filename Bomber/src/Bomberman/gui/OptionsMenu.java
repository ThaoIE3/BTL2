package Bomberman.gui;

import glib.util.vector.GVector2f;

import java.awt.Canvas;

public class OptionsMenu extends Menu{
	private MainMenu mainMenu;
	public OptionsMenu(Canvas canvas, MainMenu mainMenu) {
		super(canvas);
		this.mainMenu = mainMenu;
		addSwitch("sound", "Zvuky", true);
		addButton("back", "Back to main menu");
	}

	@Override
	public void doAct(GVector2f click) {
		if(components.get("back").isClickIn(click))
			mainMenu.setActMenu(MainMenu.MAIN_MENU);
		components.get("sound").isClickIn(click);
	}

}
