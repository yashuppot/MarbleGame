//Bullet.java
//Ysh Uppot
/*Bullet class allowing us to create Bullets. Contains methods to check if the Bullet is cotacting a Marble, or contacting a wall. If a abullet contacts the marble then the marble
 * is destroyed. If it hits a wall, the bullet is destoryed and an animation plays.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;
class Bullet{
  //LOCATION AND SHAPE
  private double x;
  private double y;
  private double vx;
  private double vy;
  private Rectangle hitbox;
  private static Image bulletImage = new ImageIcon("img/bullet.png").getImage();
  public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
  
  //CONTACT
  private boolean contactFlag; //Flag for when the bullet is contacting a wall so that ScreenHandler knows when to animate.
  private int contactX;
  private int contactY;
  private int contactCounter;
  
  public Bullet(double xx, double yy, double vxx, double vyy){ //Creats a bullet at xx,yy with velocity vxx and vyy
    x = xx;
    y = yy;
    vx = vxx;
    vy = vyy;
    hitbox = new Rectangle((int)x,(int)y,10,10);
  }
  public boolean onScreen (){ //Returns true if the bullet is on the screen.
   return(0<x && x<1200 && y<800 && 0<y);
 }
  public Rectangle getHitbox(){ //returns the Bullets hitbox
    return hitbox;
  }
  
  public int getContactX(){//Returns contactX
    return contactX;
  }
  
  public void setContactX(int x){//Sets contactX to x
    contactX = x;
  }
  
  public int getContactY(){//returns contactY
   return contactY;
  }
  
  public void setContactY(int y){//sets contactY to y
    contactY = y;
  }
  
  public void setContactCounter(int n){//sets ContactCounter to n
    contactCounter = n;
  }
  
  public int getContactCounter(){ //returns contactCounter
    return contactCounter;
  }
  
  public void setContactFlag(){//sets contactFlag to true
    contactFlag = true;
  }
  
  public void resetContactFlag(){ //Resets contactFlag to false
    contactFlag = false;
  }
  
  public boolean getContactFlag(){ //returns contactFlag
    return contactFlag;
  }
  
  public void fullContact(){ //Calls when bullet contacts a wall, sets contactFlag, contactCounter, contactX, and contactY.
    setContactFlag();
    setContactCounter(10);
    setContactX((int)x);
    setContactY((int)y);
    
  }
      
    
   
  
  public void move(){ //Moves the bullet
    x += vx;
    y += vy;
    hitbox = new Rectangle((int)x,(int)y,10,10);
  }
  
  public void draw(Graphics g){ //Draws the bullet
    g.drawImage(bulletImage,(int)x,(int)y,null);
  }
  
  public boolean hitWallSide(Wall w){ //Checks if the bullet is hitting a wall.
    return(w.getHitRegion().intersects(hitbox));
  }
  
  
         


    
}
    