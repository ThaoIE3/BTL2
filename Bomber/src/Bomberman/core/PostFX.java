package Bomberman.core;

import glib.util.GColor;
import glib.util.vector.GVector3f;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class PostFX {
	public static BufferedImage bloomEffect(BufferedImage image) {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		
		for(int i=0 ; i<image.getWidth() ; i++){
			for(int j=0 ; j<image.getHeight() ; j++){
				result.setRGB(i, j, image.getRGB(i, j));
			}
		}
		
		return result;
	}
	
	public static BufferedImage greyScaleEffect(BufferedImage image){
			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		
		for(int i=0 ; i<image.getWidth() ; i++){
			for(int j=0 ; j<image.getHeight() ; j++){
				
				Color color = new Color(image.getRGB(i, j));
				int finalColor = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				result.setRGB(i, j, new Color(finalColor, finalColor, finalColor).getRGB());
			}
		}
		
		return result;
	}
	
	private static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	
	public static BufferedImage blurEffect(BufferedImage image, float value){
		BufferedImage result = deepCopy(image);
		
		for(int i=0 ; i<image.getWidth() ; i++){
			for(int j=0 ; j<image.getHeight() ; j++){
				if(i==0 || j==0 || i+1 == image.getWidth() || j+1 == image.getHeight())
					continue;
				GColor c;
				GVector3f color = new GVector3f();
				c = new GColor(result.getRGB(i+1, j+1));
				color = color.add(new GVector3f(c.getRed(), c.getGreen(), c.getBlue()));
				c = new GColor(result.getRGB(i-1, j+1));
				color = color.add(new GVector3f(c.getRed(), c.getGreen(), c.getBlue()));
				c = new GColor(result.getRGB(i+1, j-1));
				color = color.add(new GVector3f(c.getRed(), c.getGreen(), c.getBlue()));
				c = new GColor(result.getRGB(i-1, j-1));
				color = color.add(new GVector3f(c.getRed(), c.getGreen(), c.getBlue()));
				
				color = color.div(4);
				
				image.setRGB(i, j, new GColor(color.getXi(), color.getYi(), color.getZi()).getRGB());
			}
		}
		return result;
	}
}
