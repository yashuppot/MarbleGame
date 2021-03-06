//Yash Uppot
//Wall.java
/*A Wall class which allows us to create walls for the marble to bounce off of. We can change the length, width, position, and angle of rotation of each wall. The class also contains useful information about the wall
  such as a list of hitboxes for each side, and a list of "hitShapes" for the rotated walls. It also contains a list of angles, since different sides make different angles with the positive x axis
  of our game window. We also keep the unrotated hitbox so that we can more accurately check for collisions around corners, when more than one side of the wall is contacting the marble.
*/


import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
class Wall{
  //BASIC SHAPE ATTRIBUTES
  private int x;
  private int y;
  private int length;
  private int width;
  private double angle;
  private int type;
  private int hitCheckDelay;
  
 public static final int BASIC = 0;
 public static final int LASER = 1;
 public static final int BOUNCER = 2;
 private int counter;
  
  
  //STUFF  THAT HAS TO DO WITH ROTATION
  private AffineTransform tx;
  private Rectangle hitbox;
  private Area hitRegion;
  private Shape hitShape; //Shape object keeping the region which the Wall takes up post rotation.
  private double[] angleList; //List of angles that the rootated hitboxes create with the positive x axis.
  private Rectangle unrotatedHitbox; //Rectangle representing the region that the hitbox takes up pre rotation.
  private ArrayList<Point> unrotatedCorners;
  
  public static ArrayList<Wall> walls = new ArrayList<Wall>(); //List of walls currently on the screen.

 public static ArrayList<Image> wallTypes = new ArrayList<Image>(); //List of images that can make a wall.
 public static final Image WALL1 = new ImageIcon("img/wall1.png").getImage();
 public static final Image WALL2 = new ImageIcon("img/wall2.png").getImage();
 public static final Image WALL3 = new ImageIcon("img/wall3.png").getImage();
 public static final Image WALL4 = new ImageIcon("img/wall4.png").getImage();
 public static final Image WALL5 = new ImageIcon("img/wall5.png").getImage();
 public static final Image WALL6 = new ImageIcon("img/wall6.png").getImage();
 public static final Image WALL7 = new ImageIcon("img/wall7.png").getImage();
 public static final Image WALL8 = new ImageIcon("img/wall8.png").getImage();
 public static final Image WALL9 = new ImageIcon("img/wall9.png").getImage();
  public static final Image WALL10 = new ImageIcon("img/wall10.png").getImage();
  public static final Image WALL11 = new ImageIcon("img/wall11.png").getImage();
  public static final Image WALL12 = new ImageIcon("img/wall12.png").getImage();
 public static final Image WALL13 = new ImageIcon("img/wall13.png").getImage();
 public static final Image WALL14 = new ImageIcon("img/wall14.png").getImage();
 public static final Image WALL15 = new ImageIcon("img/wall15.png").getImage();
 public static final Image WALL16 = new ImageIcon("img/wall16.png").getImage();
 



 
    


  public Wall(int xx,int yy,int lengthh,int widthh,double anglee, int typee){//Creates a wall at xx,yy with desired length, width, clockwise angle of rotation, and desired image.
    //CREATING BASIC SHAPE ATTRIBUTES
    x = xx;
    y = yy;
    length = lengthh;
    width = widthh;
    angle = anglee;
    type = typee;
    
    //CREATING THE NECESSARY ROTATION 
    tx = new AffineTransform();
    tx.rotate(Math.toRadians(angle),x,y);
    
    unrotatedHitbox = new Rectangle(x,y,length,width);
    hitbox = new Rectangle(x,y,length,width);
    
    //ANGLE RELATED
    angleList = new double[4];
    angleList[0] = angle;
    angleList[1] = 90+angle;
    angleList[2] = angle;
    angleList[3] = 90+angle;
      
    //LIST OF UNROTATED CORNERS FOR CHECKING MARBLE CENTRE AGAINTS
    unrotatedCorners = new ArrayList<Point>();
    unrotatedCorners.add(new Point(x,y));
    unrotatedCorners.add(new Point(x+length,y));
    unrotatedCorners.add(new Point(x + length, y+width));
    unrotatedCorners.add(new Point(x,y+width));
    
    
      
   //HITBOXES
   hitShape = tx.createTransformedShape(unrotatedHitbox);
   hitRegion = new Area(hitShape);
   
   //IMAGE
  }
  
  public static void fillImages(){
    wallTypes.add(WALL1);
    wallTypes.add(WALL2);
    wallTypes.add(WALL3);
    wallTypes.add(WALL4);
    wallTypes.add(WALL5);
    wallTypes.add(WALL6);
    wallTypes.add(WALL7);
    wallTypes.add(WALL8);
    wallTypes.add(WALL9);
    wallTypes.add(WALL10);
    wallTypes.add(WALL11);
    wallTypes.add(WALL12);
    wallTypes.add(WALL13);
    wallTypes.add(WALL14);
    wallTypes.add(WALL15);
    wallTypes.add(WALL16);
  }
  


  
 //////GETTTER METHODS
  public int getType(){//returns type
    return type;
  }
  
  public void increaseCounter(){//Increases counter by 1.
    counter += 1;
  }
  public Rectangle getunrotatedHitbox(){//Returns the undrotated hitbox
    return unrotatedHitbox;
  }
  
  public int getDelay(){ //retursn the hitCheckDelay
    return hitCheckDelay;
  }
  
  public void setDelay(int n){ //Sets the hitCheckDelay
    hitCheckDelay = n;
  }
    
  
  public ArrayList<Point> getCorners(){ //Returns list of corner prerotation
    return unrotatedCorners;
  }
    
  public double getAngle(){//Returns the angle with positive x axis (clockwise).
    return angle;
  }
  
  public Point getRotationalCentre(){//Returns the point about which the Wall is rotated.
    return new Point((int)x,(int)y);
  }
  public Area getHitRegion(){//returns the hitRegion
    return hitRegion;
  }
  
  
    public Shape getHitshape(){ //Returns the list of hitShapes
    return hitShape;
  }
  
  
  public double[] getAngleList(){//Returns the list of angles
    return angleList;
  }
  
  
  ////GENERAL METHODS
  public static Point rotate(int anchorx, int anchory, int rx, int ry, double angle){ //Rotates a pair of coordinates (rx,ry) about a pair (anchorx,anchory) by a desired angle
   double newx =  anchorx+(rx - anchorx)*Math.cos(Math.toRadians((int)angle)) - (ry-anchory)*Math.sin(Math.toRadians((int)angle));
   double newy =  anchory+ (ry - anchory)*Math.cos(Math.toRadians((int)angle)) + (rx - anchorx)*Math.sin(Math.toRadians((int)angle));
  
   return new Point((int)newx,(int)newy);
 }
  public boolean isShooting(){
    return (counter % 80 == 50 && type == Wall.LASER);
  }
    
  
  public void shoot(){ //Shoots a bullet if a wall is of type LASER, from the middle of the top side of the wall.
    Point shotLocation = rotate(x, y, x+length/2, y+width+11, angle);
    Bullet.bullets.add(new Bullet(shotLocation.getX(),shotLocation.getY(),Math.cos(Math.toRadians(angle+90))*5, Math.sin(Math.toRadians(angle+90))*5));
  }

    

  
   
   
 
  
}
  
    