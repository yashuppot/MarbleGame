//Yash Uppot
//GravityFlip.java
/*Class that contains objects that flip Gravity, making the marble accelerate to the top of the screen. The GravityFlips have x coordinates, y coordinates, and have methods that allows us to check
 if the marble is contacting the GravityFlip. We can also add a hit check delay, so that the marble does not repeatedly flip the gravity as it's hitbox passes by the GravityFlip's hitbox.
*/

import java.util.*;
import java.awt.*;

import javax.swing.*;

class GravityFlip{
  //LOCATION SHAPE SIZE
  private int x;
  private int y;
  private int length;
  private int width;
  private Rectangle hitbox;
  
  //GRAVITY ITSELF
  private static double gravity = 1; 
  private static int hitCheckDelay;
  
  //DRAWING AND IMAGE RELATED
 private int counter;
 private static ArrayList<Image> gravityFrames = new ArrayList<Image>(); //List of frames for gravity flips.
 private static  Image GRAVITY1 = new ImageIcon("img/gravityframe1.png").getImage();
 private static  Image GRAVITY2 = new ImageIcon("img/gravityframe2.png").getImage();
 
 
   public static ArrayList<GravityFlip> flippers = new ArrayList<GravityFlip>();
  
  public GravityFlip(int xx, int yy){ //Creates a GravityFlip at (x,y)
    x=xx;
    y=yy;
    length = 25;
    width = 25;
    counter = 0;
      
    hitbox = new Rectangle (x-25,y-25,length+50,width+50);
  }
  
  //DRAWING RELATED
  public static void fillImages(){ //Fills in the necessary images to gravityFrames
    gravityFrames.add(GRAVITY1);
    gravityFrames.add(GRAVITY2);
  }
    
  public void draw(Graphics g){
    g.setColor(Color.BLACK);
    if(counter%50 < 10){ //Draws one frame 20 percent of the time and the other 80, to create a flashing effect.
      g.drawImage(gravityFrames.get(0),x,y,null);
    }
    else{
      g.drawImage(gravityFrames.get(1),x,y,null);
    }
    if(hitCheckDelay > 0){
      hitCheckDelay -= 1;
    }
      
    
    counter += 1;
  }
   
  
  //GETTERS SETTERS AND SMALL METHODS
  public Rectangle getHitbox(){ //Returns the hitbox
    return hitbox;
  }
  
  public int getDelay(){ //returns the hitCheckDelay
    return hitCheckDelay;
  }
  
  public void setDelay(int n){ //Sets the hitCheckDelay to n
    hitCheckDelay = n;
  }
      
  
  public static double getGravity(){ //Returns the value for gravity
    return gravity;
  }
  
  public static void setGravity(int g){ //Sets the gravity to g
    gravity = g;
  }
  
  public static void flipGravity(){ //Flips gravity
    gravity *= -1;
  }

}