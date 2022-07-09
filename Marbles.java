//Marbles.java
//Yash Uppot
/*Class containing the GamePanel and the Frame for the game. The game is called Marbles. The objective of the game is to get your Marble to the flag as quickly as possible. Each time
 * you shoot the marble by left clicking and holding to charge up the shot, your score decreases. There are also obstacles like Walls and Lasers that block your path to the Flag. There
 * are also helpful powerups like Ballons, bounce pads, and Gravity flips that help you maneuver to the Flag.
 * Balloons can be moved with arrow keys, and released with w.
 * Horizontal walls often serve as stepping stones to help reach the Flag.
 * There are 5 levels which get progressively harder.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Marbles extends JFrame{ //Class that creates the frame for the game, and sets up it's properties.
 GamePanel game = new GamePanel();
  
    public Marbles() {
  super("MARBLES");
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  add(game);
  pack();  // set the size of my Frame exactly big enough to hold the contents
  setVisible(true);
    }
    
    public static void main(String[] arguments){
  Marbles frame = new Marbles();  
  }
}


  



///GAME PANEL
class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener{

 private static int screen;
 private int key; //Keeps current key press code
 private boolean []keys = new boolean[200]; //List of keys
 private MouseEvent mouseCurrent = null; //Keeps the current mouse event after every frame.
 private static int currentPress; //Keeps the current mousepress (ie rightclick leftclick etc.)
 private Point mousePos = null; //Keeps the current mouse position after every frame.
 private int count;
 
 
 javax.swing.Timer timer; 

 
//Marble that the player controlls
 




 
  
  


 public static boolean mouseInFrame = false;
 
 
 public GamePanel(){
   //Filling all static Image ArrayLists.
   Wall.fillImages();
   ScreenHandler.fillImages();
   GravityFlip.fillImages();
   Flag.fillImages();
   Balloon.fillImages();
   Button.fillImages();
    
    

    
 
  
 
 //Setting up the screen.
  screen = 0;
  ScreenHandler.screenInitializer(screen);
  ScreenHandler.initContact();
  ScreenHandler.initPop();
  ScreenHandler.initComplete();
  int DIMX = 1200;
  int DIMY = 800;
  setPreferredSize(new Dimension(DIMX, DIMY));
  timer = new javax.swing.Timer(2, this);
  timer.start();
  setFocusable(true);
  requestFocus();
  addKeyListener(this);
  addMouseListener(this);
 }

 public static void setCurrentPress(int n){
   currentPress = n;

 }

 public static int getScreen(){
   return screen;
 }
 
 @Override
 public void actionPerformed(ActionEvent e){
   if(mousePos != null){
     for(Marble m: Marble.marbles){
       m.handleChanges(mousePos, mouseCurrent,key,keys,currentPress); //Handles changes relating to the Marble every frame.
     }
     for(Button bt: Button.buttons){
       bt.updateHovering(mousePos); //Checks if any buttons are being hovered every frame.
     }


   }
  if(mouseInFrame){ //MOUSE STUF
   Point mouse = MouseInfo.getPointerInfo().getLocation();
   Point offset = getLocationOnScreen();
   mousePos = new Point((int)(mouse.getX() - offset.getX()),(int)(mouse.getY() - offset.getY()));


  }
  if(mouseCurrent != null){
    for(Button bt: Button.buttons){//Loop checks for button presses if there is a current MouseEvent.  
      if(bt.checkPressed(mouseCurrent)){
        screen = bt.getMode();
        ScreenHandler.screenInitializer(screen);
        break;
      }
      
    }
  }
   for(Marble m: Marble.marbles){
    m.move();
  }
   

  if(Flag.isComplete()){ //Loop checks if the level is completed every frame.
    screen = -2;
    ScreenHandler.screenInitializer(GamePanel.screen);
    Flag.reset();
  }
  
   
    
    
  repaint();
  
  //Resetting the key and mouse actions after each frame.
  key = -1; 
  mouseCurrent = null;


 }
 
 @Override
 public void keyReleased(KeyEvent ke){ //Calls when a key is released and updates the key, aswell as key list keys.
  key = ke.getKeyCode();
  keys[key] = false;
 } 
 
 @Override
 public void keyPressed(KeyEvent ke){  //Calls when a key is pressed and updates the key, aswell as key list keys.
  key = ke.getKeyCode();
  keys[key] = true;
 }
 
 @Override
 public void keyTyped(KeyEvent ke){  //Calls when a key is typed and updates the key, aswell as key list keys.
 }
 @Override
 public void mouseClicked(MouseEvent e){  //Calls when mouse is clicked, updates mouseCurrent.
   mouseCurrent = e;
 }

 @Override
 public void mouseEntered(MouseEvent e){ //Calls when mouse enteres screen, updates mouseCurrent.
   mouseInFrame = true;
   mouseCurrent = e;
 }

 @Override
 public void mouseExited(MouseEvent e){  //Calls mouse exits screen, updates mouseCurrent,
   mouseInFrame = false;
   mouseCurrent = e;
 }

 @Override
 ///MOUSE PRESSED
 public void mousePressed(MouseEvent e){ //Calls when mouse is pressed, updates mouseCurrent
   currentPress = 1;
   mouseCurrent = e;
 }
///
 @Override
 public void mouseReleased(MouseEvent e){ //Calls when mouse is released, updates mouseCurrent
   currentPress = -1;
   mouseCurrent = e;
 }
 
 public void mouseDragged(MouseEvent e){ //Calls when mouse is dragged, updates mouseCurrent
  mouseCurrent = e;
 }
 
 public void mouseMoved(MouseEvent e){ //Calls when mouse is moved, updates mouseCurrent
   mouseCurrent = e;
 }
 
 

 @Override
 public void paint(Graphics g){ //Draws all changes to the screen
   g.drawImage(ScreenHandler.getBack(),0,0,this);
   
   ScreenHandler.initialDrawer(g,screen); //Draws the current screens background and other images and the Marble.
 
  //Drawing all the objects on the screen
   for(GravityFlip f: GravityFlip.flippers){
     f.draw(g);
   }
 
   for(Balloon b: Balloon.balloons){
     if(Marble.marbles.size() > 0){
       b.draw(g,Marble.marbles.get(0));
     }
   }
   for(Button bt: Button.buttons){
     bt.draw(g);
   }
   for(Flag fl: Flag.flags){
     fl.draw(g);
   }
   for(Bullet b: Bullet.bullets){
     b.draw(g);
   }


 }
}