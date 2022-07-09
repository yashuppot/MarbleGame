//SpriteSheet.java
//Yash Uppot
//Contains a SpriteSheet class that allows us to load SpriteSheets and grab sprites from them. Code is influenced by https://www.youtube.com/watch?v=jedilHUPO7c&t=97s

import java.awt.image.BufferedImage;

class SpriteSheet{
  
  private BufferedImage image; 
  
  public SpriteSheet(BufferedImage ss){ //Constructor, creates SpriteSheet from BufferedImage.
    this.image = ss;
  }
  
  
  public BufferedImage grabSprite(int col, int row, int width, int height){ //Grabs a sprite form the specified row and column, with specified height and width,
    BufferedImage img = image.getSubimage(col*height - height, row*width-width, width,height);
    return img;
  }
}