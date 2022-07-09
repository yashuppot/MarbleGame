//Yash Uppot
//Flag.java
/*Flag class that allows us to create flags which will end the level if the Marble is able to reach them. They have an x coordinate, y coordinate, and type corresponding to their orientation.
 * The orientation determines the location of the flag's hitbox. The class contains methods to change the level's completion status, and reset it when a new level starts.
*/


import java.util.*;
import java.awt.*;
import javax.swing.*;
class Flag{
  //LOCATION SHAPE SIZE
  private int x;
  private int y;
  private int length;
  private int width;
  private Rectangle hitbox;
  private Image flagImage;
  private int type;
  
  //LEVEL COMPLETION
  private static boolean completeFlag = false;
  private static int completeX;
  private static int completeY;
  private static boolean levelComplete = false;
  
  //IMAGE AND DRAWING RELATED
  public static ArrayList<Flag> flags = new ArrayList<Flag>();
  public static ArrayList<Image> flagTypes = new ArrayList<Image>();
  public static final Image FLAG1 = new ImageIcon("img/flag1.png").getImage();
  public static final Image FLAG2 = new ImageIcon("img/flag2.png").getImage();
    

  public Flag(int xx, int yy, int index){ //Creates a flag at (xx,yy)
    x = xx;
    y = yy;
    length = 40;
    width = 20;
    type = index;
    if(type == 1){
      hitbox = new Rectangle(x,y,length,width);
    }
    else{
      hitbox = new Rectangle(x,y+40,length,width);
    }
      
    flagImage = flagTypes.get(index);
  
  }
  
  //GETTERS SETTERS AND QUICK METHODS
  public static void fillImages(){//Fills the necessary images into flagTypes
    flagTypes.add(FLAG1);
    flagTypes.add(FLAG2);
  }
  public static void setCompleteFlag(){ //Updates the completeFlag to true
    completeFlag = true;
  }
  public static void resetCompleteFlag(){ //Updates the completeFlag to false
    completeFlag = false;
  }
    
  public Rectangle getHitbox(){ //Returns the hitbox
    return hitbox;
  }
  
  public static void finish(){//Finishes the level
    levelComplete = true;
  }
  
  public static boolean isComplete(){//Checks if the level is finished
    return levelComplete;
  }
  public void fullComplete(){ //Calls when a level is complete, updates the completFlag and completX and completeY
    setCompleteFlag();
    setCompleteX(x-32);
    setCompleteY(y-32);
  }
      
  
  public static void reset(){ //Resets the completion status of the level
    levelComplete = false;
  }
  public static int getCompleteX(){ //returns completeX
    return completeX;
  }
  public static int getCompleteY(){//returns completeY
    return completeY;
  }
  public static void setCompleteX(int x){ //sets completeX to x
    completeX = x;
  }
  public static void setCompleteY(int y){ //sets completeY to y
    completeY = y;
  }
  
    
    
    
  public static boolean getCompleteFlag(){ //returns completeFlag
    return completeFlag;
  }

    

  //DRAWING
  public void draw(Graphics g){ //Draws the Flag to the screen.
    g.drawImage(flagImage,x,y,null);
  }
}