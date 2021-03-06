//Balloon.java
//Yash Uppot
/*Contains a Balloon class that lets us create Balloons that float the user upwards. After a time limit they pop, and they can be released at any time with the w key. They also
 * can be moved horizontally with the arrow keys. Contains methods to get the position, attach and detach to marbles, and check for user input
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Balloon{
  //LOCATION AND SHAPE
  private double x;
  private double y;
  private double initialX;
  private double initialY;
  private int length;
  private int width;
  private Rectangle hitbox;
    
    
 //ATTACHMENT STATUS
  private int counter; //Counter for poppiing time limit 
  private boolean attached;
  private boolean inert;
  
  private static boolean popFlag = false; //Flag for if the balloon is poped so that ScreeHandler knows when to animate.
  private static int popX;
  private static int popY;
  
  
  public static ArrayList<Balloon> balloons = new ArrayList<Balloon>();
  
  
  public static ArrayList<Image> balloonTypes = new ArrayList<Image>(); 
  public static final Image BALLOON1 = new ImageIcon("img/balloon1.png").getImage();
  

  
  
  public Balloon(int xx, int yy){//Creates a balloon at (xx,yy)
    x=xx;
    y=yy;
    initialX = xx;
    initialY = yy;
    length = 64;
    width = 64;
    attached = false;
    hitbox = new Rectangle ((int)(x),(int)(y),length,width);
    inert = false;
  }
  
  public static void fillImages(){//Fills balloonTypes ArrayList with necessary images
    balloonTypes.add(BALLOON1);
  }
  public void draw(Graphics g, Marble m){ //Draws the balloon
    for(int i = 0; i < 3; i++){
      g.drawImage(balloonTypes.get(0),(int)x,(int)y,null);
    }
  }
  
  public void move(Marble m){ //Moves the ballon, if attached it will move with the marble.
    if(attached){
      x = m.getmarbleX()-22;
      y = m.getmarbleY()-44;
      counter+=1;
    }
  }
  
  public int getCounter(){//returns the counter
    return counter;
  }
  
  public void makeInert(){//Makes the ballon inert, meaning we don't check if it is contacting the marble anymore.
    inert = true;
  }
   
  public double getballoonX(){//Returns x
    return x;
  }
  public double getballoonY(){//returns y
    return y;
  }
  
  public static void resetPopFlag(){//Resets the popFlag to false
    popFlag = false;
  }
  public static boolean getPopFlag(){//Returns the PopFlag
    return popFlag;
  }
  public static void setPopFlag(){//Sets the popFlag tp true
    popFlag = true;
  }
  
  public static int getPopX(){//Returns popX
    return popX;
  }
  
  public static int getPopY(){ //returns popY
    return popY;
  }
  
  public static void setPopX(int x){//Sets popX to x
    popX = x;
  }
  
  public static void setPopY(int y){//Sets popY to y
    popY = y;
  }
  
     

  
  public Rectangle getHitbox(){//Returns the hitbox
    return hitbox;
  }
  
  public void attach(Marble m){//Attaches the ballon to a Marble m.
    attached = true;
  }
  
  public void userKeys(int key, boolean[] keys, Marble m){//Checks the users keystrokes and responds accordingly
    if( attached){
      if(key ==  KeyEvent.VK_W){
        if(keys[key] == false){
           inert = true; //Makes inert once the marble is detached from the balloon to stop reattachment
           detach(m);
        }
   
      }
   
      else if(key == 39){
        m.increaseVx(); //Increases marbles x velocity when right arrow key pressed
      }
      else if(key == 37){
        m.decreaseVx(); //Decreases marbles x velocity when left arrow key pressed
      }
    }         
  }
  
  public boolean getInert(){ //Returns inert
    return inert;
  }
  
  public void resetInert(){ //Resets inert to false
    inert = false;
  }
  
  public void fullPop(){ //Calls when the ballon pops, sets popFlag, sets popY, sets popX, and makes the Balloon inert.
     Balloon.setPopFlag();
     Balloon.setPopY((int)this.y);
     Balloon.setPopX((int)this.x);
     makeInert();
  }
     
    
  
  public void detach(Marble m){ //Detaches the Balloon from the Marble.
    if(attached){
      m.removeBalloon(); //Decreases Marbles balloon count if the Balloon was attached when detach(m) was called
    }
    attached = false; 
    hitbox = new Rectangle((int)x,(int)y,length,width);
 
    
  }
  
  public void toInitial(){ //Returns balloon to it's initial position.
    x = initialX;
    y = initialY;
    counter = 0;
  }
  
  public boolean getAttached(){// returns attached
    return attached;
  }
}