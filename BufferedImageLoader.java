//BufferedImageLoader.java
//Yash Uppot
//Contains a BufferedImageLoader class that loads Buffered Images. Code influenced by https://www.youtube.com/watch?v=jedilHUPO7c&t=97s
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;



public class BufferedImageLoader {
  private BufferedImage image;
  
  public BufferedImage loadImage(String path) throws IOException{ //Loads the image at the filepath given.
    image = ImageIO.read(getClass().getResource(path));
    return image;
  }
}
  