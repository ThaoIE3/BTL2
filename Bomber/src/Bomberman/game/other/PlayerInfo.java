package Bomberman.game.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Bomberman.Options;
import Bomberman.core.XInteractable;
import Bomberman.game.Player;

public class PlayerInfo implements XInteractable{
	private Player player;
	
	public PlayerInfo(Player player) {
		super();
		this.player = player;
	}
	
	@Override
	public void render(Graphics2D g2) {
		if(!Options.INFO_RENDER)
			return;
		
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, Options.INFO_WIDTH, Options.INFO_HEIGHT);
		g2.setColor(new Color(0,0,0,255));
		g2.drawRect(0, 0, Options.INFO_WIDTH, Options.INFO_HEIGHT);
		
		g2.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, Options.INFO_TEXT_SIZE));
		
		g2.setColor(Color.WHITE);
		
		g2.drawString("Healt: " + player.getHealt(), Options.INFO_OFFSET, Options.INFO_TEXT_SIZE * 1);
		g2.drawString("Speed: " + player.getSpeed(), Options.INFO_OFFSET, Options.INFO_TEXT_SIZE * 2);
		g2.drawString("Range: " + player.getRange(), Options.INFO_OFFSET, Options.INFO_TEXT_SIZE * 3);
//		g2.drawString("Healt: " + player.getHealt(), Options.INFO_OFFSET, Options.INFO_TEXT_SIZE * 4);
		
	}
}
