package Bomberman.gui;

import glib.util.vector.GVector2f;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Bomberman.Options;

public class Switch extends GuiComponent{
	private Color bgInactiveColor = Color.LIGHT_GRAY;
	private Color bgColor = Color.GREEN;
	private Color bgHoverColor = Color.DARK_GRAY;
	private Color textColor = Color.BLACK;
	
	private Color trueColor = Color.BLUE;
	private Color falseColor = Color.RED;
	
	private boolean value;
	private int viewWidth = 100;
	
	public Switch(String text,  GVector2f size, int textSize, int nthButton, Canvas canvas, boolean value) {
		this.text = text;
		this.size = size;
		this.textSize = textSize;
		this.canvas = canvas;
		this.value = value;
		setActive(true);
		offset = new GVector2f(0,20);
		pos = new GVector2f((Options.WINDOW_DEFAULT_WIDTH - size.getXi()) / 2, (nthButton * (size.getYi() + offset.getYi())) + Options.MENU_VERTICAL_OFFSET);
		textPos = pos.add(new GVector2f(((size.getXi() - (text.length()/2 * textSize))/2), (size.getYi() - textSize)/2 + textSize/4*3));
	}
	
	public void updateSize(){
		pos.setX(((canvas.getWidth() - size.getXi()) / 2));
		textPos.setX(pos.getX() + ((size.getXi() - (text.length()/2 * textSize))/2));
	}
	
	@Override
	protected void clickIn() {
		value = !value;
	}
	
	@Override
	public void render(Graphics2D g2) {

		g2.setColor(value?trueColor:falseColor);
		g2.fillRoundRect(pos.getXi() + size.getXi() - viewWidth, pos.getYi(), viewWidth, size.getYi(), round,round);
		
		if(isActive())
			g2.setColor(hover ? bgHoverColor : bgColor);
		else
			g2.setColor(bgInactiveColor);
		
		
		g2.fillRoundRect(pos.getXi(), pos.getYi(), size.getXi() - viewWidth, size.getYi(), round,round);
		g2.fillRect(pos.getXi() + size.getXi() - viewWidth - round, pos.getYi(), round * 2, size.getYi());
		
		
		
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(borderThickness));
		g2.drawRoundRect(pos.getXi(), pos.getYi(), size.getXi(), size.getYi(), 20,20);
		
		g2.setColor(textColor);
		g2.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, textSize));
		g2.drawString(text, textPos.getXi(), textPos.getYi());
	}
	
}
