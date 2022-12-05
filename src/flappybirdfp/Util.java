/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybirdfp;

/**
 *
 * @author dimas
 */
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Util {

    private static HashMap<String, Image> cache = new HashMap<String, Image>();

    public static Image loadImage(String path) {
        Image image = null;

		if (cache.containsKey(path)) {
			return cache.get(path);
		}

		try {
			image = ImageIO.read(new File(path));

			if (!cache.containsKey(path)) {
				cache.put(path, image);
			}
		} 
		catch (IOException e) {
		    e.printStackTrace();
        }

		return image;
	}
}
