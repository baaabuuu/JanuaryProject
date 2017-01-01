package gameCode;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * A resource managers for the sprites we use,
 * Habla habla espanol?
 */
public class SpriteStorage {
	/** The single instance of this class */
	private static SpriteStorage single = new SpriteStorage();
	
	/**
	 * Get the single instance of this class in order to
	 * reduce data duplication = potentially less space used
	 */
	public static SpriteStorage get() {
		return single;
	}
	
	/** The cached sprite map, from reference to sprite instance */
	@SuppressWarnings("rawtypes")
	private HashMap sprites = new HashMap();
	
	/**	 * returns a sprite instance containing an accelerated image. */
	@SuppressWarnings("unchecked")
	public Sprite getSprite(String ref) {
		/** checks whether there is a sprite in our cache, if not it gets a new*/
		if (sprites.get(ref) != null) {
			return (Sprite) sprites.get(ref);
		}
		BufferedImage sourceImage = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(ref);
			if (url == null) {
				fail("Can't find ref: "+ref);
			}
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			fail("Failed to load: "+ref);
		}
		/** creates our accelerated image using the proper size and stores teh sprite in our buffer*/
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);

		image.getGraphics().drawImage(sourceImage,0,0,null);
		
		Sprite sprite = new Sprite(image);
		sprites.put(ref,sprite);
		return sprite;
	}
	
	/**In case of errors this is printed, we also get it to exit our program. */
	private void fail(String message) {
		System.err.println(message);
		//exits the program
		System.exit(0);
	}
}