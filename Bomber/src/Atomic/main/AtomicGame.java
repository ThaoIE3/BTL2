package Atomic.main;

import Atomic.core.CoreGame;

public class AtomicGame extends CoreGame{
	public void init() {
		createWindow(800, 600, "AtomicBomber");
		createLevel();
	}

}
