package util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceLoader {
	private static HashMap<String, Image> loadedTextures = new HashMap<String, Image>();
	private static InputStream load(String fileName){
		InputStream input = ResourceLoader.class.getResourceAsStream(fileName);
		if(input == null){
			input = ResourceLoader.class.getResourceAsStream("/"+fileName);
		}
		return input;
	}
	
	public static Image loadTexture(String fileName){
		if(loadedTextures.containsKey(fileName))
			return loadedTextures.get(fileName);
		else{
			try {
				loadedTextures.put(fileName, ImageIO.read(load("texture/"+fileName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loadedTextures.get(fileName);
		}
	}
}
