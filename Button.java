//Button.java
//Yash Uppot
/*Contains Button class to create Buttons anywhere on the screen with desired length and wdith. Contains methods to check if a button is pressed or hovered, and to get the screen
 * modes of the button.
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class Button{
  //LOCATION SHAPE SIZE
  private int x;
  private int y;
  private int length;
  private int width;
  private int mode;
  private Rectangle hitbox;
  private Image buttonImage;

  
  //HOVER STATUS
  private boolean hovering;
  
  //IMAGE RELATED
  public static ArrayList<Button> buttons = new ArrayList<Button>();
  public static ArrayList<Image> buttonTypes = new ArrayList<Image>();
  public static final Image BUTTON1 = new ImageIcon("img/button1.png").getImage();
  public static final Image BUTTON2 = new ImageIcon("img/button2.png").getImage();
  public static final Image BUTTON3 = new ImageIcon("img/button3.png").getImage();
  public static final Image BUTTON4 = new ImageIcon("img/button4.png").getImage();
  
  
  public Button(int xx, int yy, int lengthh, int widthh, int modee, int index){ //Creates a button at (xx,yy) with desired length, width, screen mode, and image index.
    x = xx;
    y = yy;
    length = lengthh;
    width = widthh;
    mode = modee;
    hitbox = new Rectangle(x,y,length,width);
    buttonImage = buttonTypes.get(index);
    hovering = false;
    
  }
  
  public static void fillImages(){//Fills the necessary images to buttonTypes
    buttonTypes.add(BUTTON1);
    buttonTypes.add(BUTTON2);
    buttonTypes.add(BUTTON3);
    buttonTypes.add(BUTTON4);
  }
    
  
  public boolean checkPressed(MouseEvent e){ //Checks if a button is pressed by a certain MouseEvent e.
    if (hitbox.contains(new Point(e.getX(), e.getY()))){
      return true;
    }
    else{
      return false;
    }
  }
  
  public int getMode(){ //Returns the buttons screen mode.
    return mode;
  }
  
  public void updateHovering(Point mousePos){ //Updates the status of hoveirng.
    hovering = hitbox.contains(mousePos);
  }
    
    
  
  

    
    
    
    
    
    public void draw(Graphics g){ //Draws the button to the screen with an outline if hovering
      g.setColor(Color.RED);
      g.drawImage(buttonImage,x,y,null);
      g.setColor(Color.GREEN);
      if(hovering){
        g.drawRect(x,y,length,width);
      }
      
      
    }
        
      
}