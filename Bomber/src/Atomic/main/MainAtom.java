package Atomic.main;

import Atomic.core.CoreGame;

public class MainAtom {
	public static void main(String[] args) {
		CoreGame game = new AtomicGame();
		game.init();
		game.start(60);
	}
}
