//ScreenHandler.java
//Yash Uppot
//Contains ScreenHandler class that handles animations, drawing image files to the screen, and creating objects for each new screen.


import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class ScreenHandler{
  
   private static Image back;
   private static ArrayList<Image> random = new ArrayList<Image>(); //List of random assorted images
   private static final Image BOOTH = new ImageIcon("img/booth.png").getImage();
   private static final Image CHOOSE = new ImageIcon("img/choose.png").getImage();
  private static final Image CONGRATS = new ImageIcon("img/congratulations.png").getImage();
   private static final Image SCORE = new ImageIcon("img/score.png").getImage();
   private static final Image ARROW = new ImageIcon("img/arrow.png").getImage();
  
  private static ArrayList<Image> backdrops = new ArrayList<Image>(); //List of backdrops for levels
  private static final Image BACK1 = new ImageIcon("img/black'.jpg").getImage(); 
  private static final Image BACK2 = new ImageIcon("img/level1backdrop.png").getImage();
  private static final Image BACK3 = new ImageIcon("img/level2backdrop.jpg").getImage();
  private static final Image BACK4 = new ImageIcon("img/level3backdrop.jpg").getImage();
  private static final  Image BACK5 = new ImageIcon("img/level4backdrop.jpg").getImage();
  private static final Image BACK6 = new ImageIcon("img/congratulationsbackdrop.jpg").getImage();
  private static final Image BACK7 = new ImageIcon("img/level5backdrop.jpg").getImage();
 
private static SoundEffect TITLEBACKGROUND = new SoundEffect("sound/titlebackground.wav");
private static SoundEffect LEVEL1BACKGROUND = new SoundEffect("sound/level1background.wav");
private static SoundEffect LEVEL2BACKGROUND = new SoundEffect("sound/level2background.wav");
private static SoundEffect LEVEL3BACKGROUND = new SoundEffect("sound/level3background.wav");
private static SoundEffect LEVEL4BACKGROUND = new SoundEffect("sound/level4background.wav");
private static SoundEffect LEVEL5BACKGROUND = new SoundEffect("sound/level5background.wav");
private static ArrayList<SoundEffect> soundList = new ArrayList<SoundEffect>(); //List of SounfEffects.
 
 
  private static BufferedImage currentPopSpritesFrame;
  private static BufferedImage popSprites; //Sprite sheet for balloon pop animation.
  private static int popSpritesCounter = 13;
  
  private static BufferedImage currentContactSpritesFrame;
  private static BufferedImage contactSprites; //Sprite sheet for bullet fire animation.
  public static ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
  
  private static BufferedImage currentCompleteSpritesFrame;
  private static BufferedImage completeSprites; //Sprite sheet for level complete animation
  private static int completeSpritesCounter = 59;
 
 
 public static void fillImages(){ //Fills all images and sounds to the ArrayLists
   backdrops.add(BACK1);
   backdrops.add(BACK2);
   backdrops.add(BACK3);
   backdrops.add(BACK4);
   backdrops.add(BACK5);
   backdrops.add(BACK6);
   backdrops.add(BACK7);
   
   random.add(BOOTH);
   random.add(CHOOSE);
   random.add(CONGRATS);
   random.add(SCORE);
   random.add(ARROW);
   
   soundList.add(TITLEBACKGROUND);
   soundList.add(LEVEL1BACKGROUND);
   soundList.add(LEVEL2BACKGROUND);
   soundList.add(LEVEL3BACKGROUND);
   soundList.add(LEVEL4BACKGROUND);
   soundList.add(LEVEL5BACKGROUND);
 }
 public static Image getBack(){
   return back;
 }
 public static void clear(){ //Resets the screen and clears all objects.
   Flag.resetCompleteFlag();
   Flag.reset();
   for(SoundEffect s: soundList){
     s.stop();
   }
   GravityFlip.setGravity(1);
   Bullet.bullets.clear();
   Balloon.balloons.clear();
   Flag.flags.clear();
   Marble.marbles.clear();
   Wall.walls.clear();
   GravityFlip.flippers.clear();
   Button.buttons.clear();
 }
 
 public static void initPop(){ //Initialiszes the popSprites BufferedImage.
    BufferedImageLoader loader = new BufferedImageLoader();
    try{
      popSprites  = loader.loadImage("img/popsprites.png");
    }
    catch(IOException e){
      e.printStackTrace();
    }
 }
 
 public static void playPopSprites(Graphics g){//Plays the popSprites animation when a ballon has popped
   
    if(Balloon.getPopFlag()){
      if(popSpritesCounter >1){
        SpriteSheet ss = new SpriteSheet(popSprites);
        currentPopSpritesFrame = ss.grabSprite(popSpritesCounter,1,64,64);
        g.drawImage(currentPopSpritesFrame,Balloon.getPopX(), Balloon.getPopY(),null); //Drawing the current frame
        popSpritesCounter -= 1; //Changing the index to that of the next frame.
      }
      else{
        Balloon.resetPopFlag();
        popSpritesCounter = 13;
      }
    }
  }
 
 public static void initComplete(){ //Initialized the completeSprites BufferedImage
    BufferedImageLoader loader = new BufferedImageLoader();
    try{
      completeSprites  = loader.loadImage("img/completesprites.png");
    }
    catch(IOException e){
      e.printStackTrace();
    }
 }
   
 
  public static void playCompleteSprites(Graphics g){ //Plays the completeSprites animation when a level is complete.
   
    if(Flag.getCompleteFlag()){
      if(completeSpritesCounter >1){
        SpriteSheet ss = new SpriteSheet(completeSprites);
        currentCompleteSpritesFrame = ss.grabSprite(completeSpritesCounter,1,128,128);
        g.drawImage(currentCompleteSpritesFrame,Flag.getCompleteX(), Flag.getCompleteY(),null); //Drawing the current frame.
        completeSpritesCounter -= 1; //Changing the index to that of the next frame.
      }
      else{
        Flag.finish();
        Flag.resetCompleteFlag();
        completeSpritesCounter = 59;
      }
    }
  }
 
  public static void initContact(){ //Initializes the contactSprites BufferedImage 
      BufferedImageLoader loader = new BufferedImageLoader();
    try{
      contactSprites = loader.loadImage("img/contactsprites.png");
    }
    catch(IOException e){
      e.printStackTrace();
     }
  }
    
  public static void playContactSprites(Graphics g, int i){ //Plays the contactSprites animation when a bullet at position i in an ArrayList is fired.
    Bullet b = deadBullets.get(i);
   
  
     if(b.getContactFlag()){
      if(b.getContactCounter() >1){
        SpriteSheet ss = new SpriteSheet(contactSprites);
        currentContactSpritesFrame = ss.grabSprite(b.getContactCounter(),1,64,64);
        g.drawImage(currentContactSpritesFrame,b.getContactX(), b.getContactY(),null); //Draws the current frame
        b.setContactCounter(b.getContactCounter()-1); //Changes the index to that of the next frame.
      }
      else{
        b.resetContactFlag();
        b.setContactCounter(10);
        deadBullets.remove(i);
      }
    }
  }
 
 
   
 
  public static void screenInitializer(int screen){ //Initializes all objects on the screen for each screen.
    if(screen == -2){
       clear();
       //Reset and Home buttons for win screen.
       Button.buttons.add(new Button(1000,5,64,64,1,2)); 
       Button.buttons.add(new Button(1080,5,64,64,0,3));
    }
      
    else if(screen == -1){
      clear();
      //Level select buttons for level select screen.
      for(int i = 0; i < 5; i++){
        Button.buttons.add(new Button(23 + 256*i,300, 128, 128, i+1,1));
      }
      
    }
                          
        
    else if (screen == 0){
      clear();
      //Level select button.
      Button.buttons.add(new Button(476,461,300,80,-1,0));
      
      //Starts background music.
      TITLEBACKGROUND.playLoop();
      
      
    }
    else if(screen == 1){ //Creates all the objects for the level 1 layout
      clear();
      LEVEL1BACKGROUND.playLoop();
     
       Wall man =  new Wall(200,500,800,50,0,Wall.BASIC);
       Wall dababy = new Wall(300,100,200,25,1,Wall.BASIC);
    
       Wall.walls.add(man);
       Wall.walls.add(dababy);
       
     
       Button.buttons.add(new Button(1000,5,64,64,1,2));
       Button.buttons.add(new Button(1080,5,64,64,0,3));

       Flag flag = new Flag(340,34,0);
       Flag.flags.add(flag);
       
        Marble player = new Marble(923,480,1); 
        Marble.marbles.add(player);
    }
    else if(screen == 2){ //Creates all the objects for the level 2 layout
      clear();
      LEVEL2BACKGROUND.playLoop();
      

      Wall.walls.add(new Wall(0,700,1200,100,0,Wall.BASIC));
      Wall.walls.add(new Wall(922,500,25,75,20,Wall.BOUNCER));
      Wall.walls.add(new Wall(750,300,25,175,0,Wall.BASIC));
      Wall.walls.add(new Wall(1000,200,25,175,0,Wall.BASIC));
      Wall.walls.add(new Wall(850,400,200,25,0,Wall.BASIC));
      Wall.walls.add(new Wall(400,100,600,25,0,Wall.BASIC));
     
      GravityFlip.flippers.add(new GravityFlip(800,200));
      
       Marble player = new Marble(622,680,1); 
       Marble.marbles.add(player);
       
       Button.buttons.add(new Button(1000,5,64,64,2,2));
       Button.buttons.add(new Button(1080,5,64,64,0,3));
       
       Flag flag = new Flag(400,128,1);
       Flag.flags.add(flag);
    }
    else if(screen == 3){ //Creates all the objects for the level 3 layout
      clear();
      LEVEL3BACKGROUND.playLoop();
      Wall.walls.add(new Wall(650,650,100,25,0,Wall.BASIC));
      
      for(int i = 0; i < 5; i++){
        Wall.walls.add(new Wall(150+225*i,175-4*i,100,25,0,Wall.BASIC));
      }
      for(int i = 0; i < 5; i++){
        Wall.walls.add(new Wall(250+200*i,100,100,25,0,Wall.BASIC));
      }
      for(int i = 0; i < 3; i++){
        Wall.walls.add(new Wall(170 + 250*i,20,100,25,0,Wall.BASIC));
      }
      
      Balloon.balloons.add(new Balloon(320,300));
      
       Button.buttons.add(new Button(1000,5,64,64,3,2));
       
       Button.buttons.add(new Button(1080,5,64,64,0,3));
      
       Marble player = new Marble(700,630,1); 
       Marble.marbles.add(player);
       
       Flag.flags.add(new Flag(700,36,0));
       

      
    }
    else if(screen == 4){//Creates all the objects for the level 4 layout
      clear();
      LEVEL4BACKGROUND.playLoop();
      
      Wall.walls.add(new Wall(0,710,1200,80,0,Wall.BASIC));
      Wall.walls.add(new Wall(100,400,100,100,-100,Wall.LASER));
      Wall.walls.add(new Wall(800,300,200,75,-80,Wall.BASIC));
      Wall.walls.add(new Wall(540,410,100,300,0,Wall.BASIC));
      Wall.walls.add(new Wall(400,125,250,30,0,Wall.BASIC));
      Wall.walls.add(new Wall(0,10,1000,50,0,Wall.BASIC));
      Wall.walls.add(new Wall(900,200,200,50,0,Wall.BASIC));
      
      Flag.flags.add(new Flag(920, 136, 0));
      
       Button.buttons.add(new Button(1000,5,64,64,4,2));
       Button.buttons.add(new Button(1080,5,64,64,0,3));
       
       GravityFlip.flippers.add(new GravityFlip(200,200));

       Marble player = new Marble(650,690,1);
       Marble.marbles.add(player);
       
   
    }
    else if(screen == 5){//Creates all the objects for the level 5 layout.
      clear();
      LEVEL5BACKGROUND.playLoop();
      Button.buttons.add(new Button(1000,5,64,64,5,2));
      Button.buttons.add(new Button(1080,5,64,64,0,3));
      
      Marble player = new Marble(650,690,1);
      Marble.marbles.add(player);
      
      Wall.walls.add(new Wall(0,710,1200,110,0,Wall.BASIC));
      Wall.walls.add(new Wall(700,210,100,500,0,Wall.BASIC));
      Wall.walls.add(new Wall(100,600,100,25,0,Wall.BASIC));
      Wall.walls.add(new Wall(850,190,300,25,0,Wall.LASER));
      Wall.walls.add(new Wall(840,390,300,25,0,Wall.BASIC));
      Wall.walls.add(new Wall(910,610,50,100,0,Wall.BASIC));
      
      GravityFlip.flippers.add(new GravityFlip(820,310));
      GravityFlip.flippers.add(new GravityFlip(1020,310));
      
      Flag.flags.add(new Flag(1000,646,0));
      
      Balloon.balloons.add(new Balloon(400,220));
      
      
      
    }
    
       
       
  }
      public static void initialDrawer(Graphics g,int screen){
        if(screen == -2){ //Draws all image files for the win screen
          back = backdrops.get(5);
          g.drawImage(random.get(2),50,200,null);
          g.drawImage(random.get(3),100,400,null);
          Font font = new Font ("AgencyFB", Font.BOLD, 100);
          g.setFont(font);
          g.setColor(Color.BLACK);
          g.drawString(Marble.getLastScore()+"",500,600);
          
          
          
        }
        if(screen == -1){ //Draws all images for the level select screen.
          g.drawImage(random.get(1),100,100,null);
          Font font = new Font ("AgencyFB", Font.BOLD, 30);
          g.setFont(font);
          g.setColor(Color.WHITE);
          for(int i = 0; i < 5; i++){
            g.drawString("LEVEL " + (i+1), 255*i+30,500);
          }
          
        }
        
        
        else if(screen==0){ //Draws all images for the home screen
          Image marbleText = new ImageIcon("img/marble_text.png").getImage();
          g.drawImage(marbleText,330,180,null);
          back = backdrops.get(0);
          Font font = new Font ("AgencyFB", Font.BOLD, 10);
          g.setFont(font);
          g.setColor(Color.WHITE);
          g.drawString("1. USE RIGHT CLICK TO AIM AND CHARGE YOUR SHOT, RELEASE IT TO FIRE",400,600);
          g.drawString("2. USE THE ARROW KEYS TO CONTROLL BALLOON MOVEMENT, USE W TO RELEASE BALLOONS",400,630);
          g.drawString("3. TRY TO REACH THE FLAG WITH AS FEW SHOTS AS POSSIBLE",400,660);
            
          

        }
        else if(screen ==1){ //Draws all images for level 1
          g.drawImage(random.get(0), 900,436,null);
          
          for(Marble m: Marble.marbles){
            m.draw(g);
          }
          
          g.drawImage(Wall.wallTypes.get(0),200,500,null);
          g.drawImage(Wall.wallTypes.get(1),300,100,null);
          
          back = backdrops.get(1);
          playPopSprites(g);
          playCompleteSprites(g);
        }
        else if(screen ==2){ //Draws all images for level 2

          back = backdrops.get(2);
          g.drawImage(random.get(0), 600,636,null);
          g.drawImage(random.get(4),952,500,null);
          
          for(Marble m: Marble.marbles){ 
            m.draw(g);
          }
          g.drawImage(Wall.wallTypes.get(2),895,500,null);
          g.drawImage(Wall.wallTypes.get(3),750,300,null);
          g.drawImage(Wall.wallTypes.get(4),850,400,null);
          g.drawImage(Wall.wallTypes.get(5),400,100,null);
          g.drawImage(Wall.wallTypes.get(3),1000,200,null);
      
          playPopSprites(g);
          playCompleteSprites(g);
        }
        else if(screen == 3){ //Draws all images for level 3
          back = backdrops.get(3);
          
          g.drawImage(random.get(0), 678,590,null);
          
          for(Marble m: Marble.marbles){
            m.draw(g);
          }
          
          g.drawImage(Wall.wallTypes.get(6),650,640,null);
          
          for(int i = 0; i < 5; i++){
            g.drawImage(Wall.wallTypes.get(6),150+225*i,165-4*i,null);
          }
          for(int i = 0; i < 5; i++){
            g.drawImage(Wall.wallTypes.get(6),250+200*i,90,null);
          }
          for(int i = 0; i < 3; i++){
            g.drawImage(Wall.wallTypes.get(6),170 + 250*i,10,null);
          }
          
          playPopSprites(g);
          playCompleteSprites(g);
        }
        else if(screen == 4){//Draws all images for level 4
          back = backdrops.get(4);
          
          for(Marble m: Marble.marbles){
            m.draw(g);
          }
          
          g.drawImage(Wall.wallTypes.get(7),80,285,null);
          g.drawImage(Wall.wallTypes.get(8),540,410,null);
          g.drawImage(Wall.wallTypes.get(9),400,125,null);
          g.drawImage(Wall.wallTypes.get(10),800,102,null);
          g.drawImage(Wall.wallTypes.get(11),0,10,null);
          g.drawImage(Wall.wallTypes.get(12),900,200,null);
            
          playPopSprites(g);
          for(int i = 0; i < deadBullets.size(); i++){
            playContactSprites(g,i);
          }
           playCompleteSprites(g);
        }
        else if(screen == 5){//Draws all images for level 5
          back = backdrops.get(6);
          
          
          for(Marble m: Marble.marbles){
            m.draw(g);
          }
          
          g.drawImage(Wall.wallTypes.get(6),100,590,null);
          g.drawImage(Wall.wallTypes.get(13),700,210,null);
          g.drawImage(Wall.wallTypes.get(14),850,190,null);
          g.drawImage(Wall.wallTypes.get(14),840,390,null);
          g.drawImage(Wall.wallTypes.get(15),910,610,null);
          
          playPopSprites(g);
          playCompleteSprites(g);
           for(int i = 0; i < deadBullets.size(); i++){
            playContactSprites(g,i);
          }
          
     
          
          
          
        
         
         
       
          
                         
        }
      }
        
   
  }
      