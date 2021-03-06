//Yash Uppot
//Marble.java
//Marble class that allows us to create a marble, check where the user aims it, shoot it, detect collisions with walls and powerups, and keeps the marbles direction, velocity, x and y velocity
//components, and coordinates. 



import java.util.*;
import java.awt.*;
import java.awt.event.*;


class Marble{
  //BASIC SHAPE ATTRIBUTES
  private double x;
  private double y;
  private double homeX;
  private double homeY;
  private double checkX;
  private double checkY;
  public Rectangle hitbox = new Rectangle((int)x,(int)y,10,10);
  
  //SPEED AND TRAVEL STUFF
  private int xDirection;
  private int yDirection;
  private double potentialX;
  private double potentialY;
  private double vx;
  private double vy;
  private double travelAngle;
  private double travelVel;
  private int xVelocityZeroCounter;
  
  //LOGISTICAL STUFF
  private boolean hasShot;
  private boolean isAiming;
  private int balloonCount; 
  private double power;
  private int type;
  private int score;
  private static int lastScore;

  
 //SOUND
  private static final SoundEffect marbleHitWall= new SoundEffect("sound/marblehitwall1.wav");
  private static final SoundEffect marbleHitFlip= new SoundEffect("sound/marblehitflip1.wav");
  private static final SoundEffect bulletHitWall = new SoundEffect("sound/bullethitwall1.wav");
  private static final SoundEffect winLevel = new SoundEffect("sound/winlevel.wav");
    
    
 
  
  public static ArrayList<Marble> marbles = new ArrayList<Marble>();
  
 
  Image [] frames;
  
    
  

  
  
  public Marble(int xx, int yy, int typee){ //Creates a Marble at (xx, yy)
    x = xx;
    y = yy;
    homeX = x;
    homeY = y;
    checkX = homeX;
    checkY = homeY;
    type = typee;
      
      
    vx = 0;
    vy = 0;
    potentialX = 0;
    potentialY = 0;
    balloonCount = 0;
    hasShot = false;
    yDirection  = 0;
    xDirection = 0;
    power = 0;
    score = 1000;
 }
  
  
  
  
 //AIMING AND SHOOTING METHODS
 public void aim(Point mousePos, int currentPress){ //Adjusts the potential initial velocity of the Marble based on the position and distance of the mouse.
   if(currentPress == 1){
     isAiming = true;
     power=Math.min(power+0.2,20);
   }
   else{
     isAiming = false;
   }
   xDirection = getSign(mousePos.x - (int)x-10);
   yDirection = getSign(mousePos.y - (int)y-10);
   travelAngle = Math.atan2((mousePos.y - y-10),(mousePos.x - x-10));
   if(GravityFlip.getGravity() == 1){
     if(principleAngle((int)Math.toDegrees(travelAngle)) < 180){
       return;
     }
   }
     
   potentialX = Math.min(Math.abs(power*Math.cos(travelAngle)),Math.abs(20*Math.cos(travelAngle)))*xDirection;
   potentialY = Math.min(Math.abs(power*Math.sin(travelAngle)),Math.abs(14*Math.sin(travelAngle)))*yDirection;
 }
  
 public void shoot(MouseEvent e, int currentPress){ //Shoots the marble on right click MouseEvents.
   if(e == null){
     return;
   }
   if(e.getButton() == MouseEvent.BUTTON3){
     if(currentPress == -1){
       score -= 20;
       vx = potentialX;
       vy = potentialY;
       x += vx;
       y += vy;
       hitbox = new Rectangle((int)x+5,(int)y+5,15,15);
       GamePanel.setCurrentPress(0);
       
       
       hasShot = true;
     }
   }
     
 }
 
 
 //BALLOON RELATED METHODS
 public void removeBalloon(){ //Reduces balloonCount by 1
   balloonCount -= 1;
 }
 
 public void addBalloon(){ //Increases balloon count by 1
   balloonCount += 1;
 }
 
 public boolean hitBalloon(Balloon b){ //Checks if the Marble is contacting Balloon b, and is not already attached.
   for(int i = 0; i < 10; i++){
     if(b.getHitbox().contains(x+10+vx/10*i,y+10+vy/10*i)){
       return true;
     }
   }
   return false;

 }
 /////////////
 
 
 //GETTER METHODS
 public double getmarbleX(){ //returns the marbles x coordinate
   return vx+x;
 }
 
 public double getmarbleY(){ //returns the marbles y coordinate
   return vy+y;
 }
 
 public double getVx(){ //returns the marbles x velocity component
   return vx;
 }
 
 public double getVy(){ //returns the marbles y velocity component
   return vy;
 }
 
 public void increaseVx(){//Increases vx by 0.5
   vx += 0.5;
 }
 
 public void decreaseVx(){ //Decreases vx by 0.5
   vx -= 0.5;
 }
 
 public static int getLastScore(){
   return lastScore;
 }
 //////////////////////
 
 
 
 //TRAVAEL SPEED AND DIRECTION RELATED METHODS
 public void updateVel(){ //Updates the travelVel
   travelVel = Math.hypot(vx, vy);
 }
 
 public void newCheckPoint(){ //Sets the checkpoint x coordinate and check point y coordinate to the current x and y coordinates
   checkX = x;
   checkY = y;
 }
 
 public void updateAngle(){ //Updates the travelAngle
   travelAngle = Math.atan2(vy,vx);
 }
 
 
 public void updateComponents(){ //Updates the x and y velocity components
   vx = travelVel*Math.cos(travelAngle);
   vy = travelVel*Math.sin(travelAngle);
 }
 

 
  
 public void bounce(double[] angleList, int index){ //Bounces the marble off of the angle at angleList[index]. This angleList will come from the angles a Wall makes with the positive x axis.
  travelAngle = travelAngle + 2*(2*3.14-(travelAngle - Math.toRadians(angleList[index])));
  if(balloonCount == 0){
    travelVel *= 0.7;
  }
  else{
    vy *= 4;
    updateVel();
  }
  updateComponents();
 }
 
 ///////////////////////
  
   

 //MOVEMENT RELATED METHODS
 public void move(){ //Moves the Marble and updates the hitbox
   x += vx;
   y += vy;
   if(balloonCount == 0){
     hitbox = new Rectangle((int)x,(int)y,20,20);
   }
   else{
     hitbox = new Rectangle((int)x - 12, (int)y - 44,44,64);
   }
 }
 
 public void counterForces(){ //Applies the forces the counter the natural movement of the Marble (gravity, balloons, etc.)

   vx = vx*0.9999*Math.pow(0.9,balloonCount); //Reduces the x velocity (reduces faster the more balloons there are)
   
   if (balloonCount == 0){
     if(Math.abs( vy+0.5*GravityFlip.getGravity()) < 20){
       vy = vy+0.5*GravityFlip.getGravity();
     }
   }
   else{
     if(vy > -2){
       vy =Math.max(vy-1.5,-0.5);
     }
     else{
       vy = Math.min(vy+1.5,-0.5);
     }
       
   }
   
 }
 ////////////////////

 //HIT DETECTION METHODS
 public boolean hitGravityFlip(GravityFlip g){ //Detects if the marble is hitting or very close to a GravityFlip g.
   for(int i = 0; i < 10; i++){
     if(g.getHitbox().contains(x+10+vx/10*i,y+10+vy/10*i)){
       return true;
     }
   }
   return false;

 }
 
 public boolean hitBullet(Bullet b){//Detects if the Marble is hitting a Bullet b.
   return hitbox.intersects(b.getHitbox());
 }
 
 public boolean hitFlag(Flag f){//Detects if the Marble is hitting a Flag f.
   for(int i = 0; i < 10; i++){
     if(f.getHitbox().contains(x+10+vx/10*i,y+10+vy/10*i)){
       return true;
     }
   }
   return false;
 }
 
 public void tickVxZero(){ //Ticks the counter when vx and vy are small 
   if(Math.abs(vx) <  1 && Math.abs(vy) < 10 && balloonCount == 0){
     xVelocityZeroCounter+=1;
   }
   else{
     xVelocityZeroCounter  = 0; //Resets counter if vx or vy get too big
   }
 }
 
 public boolean isStationary(){//Checks if the ball is stationary
   if(xVelocityZeroCounter > 100){ //If vx and vy are small for 50 frames, the ball is likely stationary
     return true;
   }
   else{
     return false;
   }
 }
     
   
 
   
 
 public int hitWallSide(Wall w){//Returns an integer corresponding to which side of a Wall w the Marble is hitting.
   if(!w.getHitRegion().intersects(hitbox)){

     return -1;
   }
   
   else{
     Point preRotationCentre = Wall.rotate((int)(w.getCorners().get(0).getX()), (int)(w.getCorners().get(0).getY()),(int)x-3*(int)vx+10,(int)y-3*(int)vy+10,-w.getAngle()); 
     double nx = preRotationCentre.getX();
     double ny = preRotationCentre.getY();
     Point a = w.getCorners().get(0);
     Point b = w.getCorners().get(1);
     Point c = w.getCorners().get(2);
     Point d = w.getCorners().get(3);
     //return ((oppositeCorner.X - corner1.X) * (ballCenter.Y - corner1.Y) - (oppositeCorner.Y - corner1.Y) * (ballCenter.X - corner1.X)) > 0;
     if((a.getX() - c.getX())*(ny-c.getY()) -(a.getY()-c.getY())*(nx-c.getX())>0){ //If the preRotationCentre is above AC
       if((d.getX() - b.getX())*(ny-b.getY()) -(d.getY()-b.getY())*(nx-b.getX())>0){//If the preRotationCentre is above DB
         return 0; //Then the Marble must have hit the top side of the Wall
       }
       else{
         return 1; //Then the Marble must have hit the right side of the Wall
       }
     }
     else{
       if((d.getX() - b.getX())*(ny - b.getY()) -(d.getY()-b.getY())*(nx -b .getX())>0){
         return 3; //Then the Marble must have hit the left side of the Wall
       }
       else{
         return 2; //Then the Marble must have hit the bottom of the Wall.
       }
     }
   }
   //////////////
     
     
     
 }
 
 /////INTEGRAL KEY METHODS FOR FUNCTIONALITY
 
 public void draw(Graphics g){ //Draws the marble, with a trail behind it based on the speed of the Marble.
   if(isAiming){
     Graphics2D g2d = (Graphics2D)g;
     g2d.rotate(travelAngle,(int)x+10,(int)y+10);
     g2d.fillRect((int)x+10,(int)y+5,(int)power*5,10);
     g2d.fillPolygon(new int[] {(int)x + 10+ (int)power*5+10,(int)x + 10 + (int)power*5,(int)x + 10+ (int)power*5}, new int[]{(int)y+10,(int)y,(int)y + 20}, 3);
     g2d.rotate(-travelAngle,(int)x+10,(int)y+10);
   }
     
       
   if(type == 0){
     g.setColor(Color.BLACK);
   }
   else if(type == 1){
     g.setColor(Color.RED);
   }
   Font font = new Font ("AgencyFB", Font.BOLD, 60);
   g.setFont(font);
   g.setColor(Color.WHITE);
   g.drawString("SCORE: "+ score, 400,800);
   g.fillOval((int)(x), (int)(y),20,20);
   if(Math.max(Math.abs(vx),Math.abs(vy)) >= 10){
     for(int i= 0; i < 15; i++){
       g.fillOval((int)(x-vx/15*i), (int)(y-vy/15*i),20,20);
     }

   }
   else if(Math.max(Math.abs(vx),Math.abs(vy)) >= 5){
     for(int i= 0; i < 10; i++){
       g.fillOval((int)(x-vx/10*i), (int)(y-vy/10*i),20,20);
     }
   }
   else if(Math.max(Math.abs(vx),Math.abs(vy)) >= 2){
     for(int i= 0; i < 2; i++){
       g.fillOval((int)(x-vx/2*i), (int)(y-vy/2*i),20,20);
     }
   }

  }

   




  

   
   
 
 public void handleChanges(Point mousePos, MouseEvent mouseCurrent, int key, boolean [] keys, int currentPress){ //Handles all changes relating to the fields and class interactions of the Marble every frame.
   for(int i = 0; i < Bullet.bullets.size(); i++){ //Loop moves all bullets and checks if they hit the Marble or a Wall
     Bullet b = Bullet.bullets.get(i);
     b.move();
     if(hitBullet(b)){

       Bullet.bullets.remove(i);
       softClear();
     }
     for(Wall w: Wall.walls){
       if(b.hitWallSide(w) || !b.onScreen()){
         ScreenHandler.deadBullets.add(b);
         b.fullContact();
         Bullet.bullets.remove(i);
         break;
       }
     }
   }
   
   if(!hasShot){
     aim(mousePos, currentPress);
     shoot(mouseCurrent,currentPress);
   }
   else{
     move();
     counterForces();
     updateVel();
     updateAngle();
              

   

       for(Wall w: Wall.walls){ //Loop checks if the Marble is hitting any Wall, and if so either bounces off the wall or creates a new check point if stationary.
         if(w.getDelay() == 0){
           if(hitWallSide(w) >= 0){
                if(travelVel > 2){
               marbleHitWall.play();
             }
  
             if(w.getType() == Wall.BOUNCER){
               travelVel*=3;
             }
             if(isStationary()){
               if((hitWallSide(w) == 0 && GravityFlip.getGravity() == 1) || (hitWallSide(w) == 2 && GravityFlip.getGravity() == -1)){
                 while(true){
                   if(w.getHitRegion().intersects(hitbox)){
                     y -=1*GravityFlip.getGravity(); //Makes sure the ball is on the top of the Wall before making the check point.
                     hitbox = new Rectangle((int)x,(int)y,20,20);
                     
                   }
                   else{
                     break;
                     
                   }
                 }
                 newCheckPoint();
                 softClear();
               }
             }
             else{
               w.setDelay(3); //Makes sure the ball doesn't get "stuck" to the wall.
               bounce(w.getAngleList(), hitWallSide(w));
            
               updateComponents();
             }
           }
         }
         else{
           w.setDelay(w.getDelay()-1);
         }
       }
       for(GravityFlip f: GravityFlip.flippers){//Loop checks if the Marble hits nay GravityFlip and flips the gravity if so.
         if(f.getDelay() == 0){
           if(hitGravityFlip(f)){
             marbleHitFlip.play();
             f.setDelay(100);
             GravityFlip.flipGravity();
           }
         }
       }
       

      
         
     }
     for(Wall w: Wall.walls){ //Loops through each Wall and attempts to shoot.
       w.increaseCounter();
       if(w.isShooting()){
         bulletHitWall.play();
         w.shoot();
       }
       
     }
     for(int i = 0; i < Balloon.balloons.size(); i++){//Loop moves balloons, and checks if the user has detached from a balloon, or hit a balloon.
        Balloon b = Balloon.balloons.get(i);
        if(b.getCounter() > 200){
          b.fullPop();
          b.detach(this);
          Balloon.balloons.remove(i);
        }
         b.userKeys(key,keys,this);
         b.move(this);

        if(hitBalloon(b) && !b.getInert() && !b.getAttached()){
           addBalloon();
           x = b.getballoonX()+10;
           y = b.getballoonY()+44;
           travelVel*=0.7;
           updateComponents();
           b.attach(this);
         }
        
      }
      
      
     for(Flag fl: Flag.flags){//Loops through the flags, and if a flag is hit, completes the level.
       if(hitFlag(fl)){
         winLevel.play();
         lastScore = score;
         fl.fullComplete();
         //Marble.marbles.clear();
       }
     }
   
     tickVxZero(); 
   if(!onScreen()){ 
     softClear();
   }

   
 }
 
 public void clear(){ //Resets the Marble and game conditions.
   ScreenHandler.screenInitializer(GamePanel.getScreen());
    x = homeX;
    y = homeY;
    vx = 0;
    vy = 0;
    potentialX = 0;
    potentialY = 0;
    hasShot = false;
    yDirection  = 0;
    xDirection = 0;
    GravityFlip.setGravity(1);
    for(Balloon b: Balloon.balloons){
      b.detach(this);
      b.toInitial();
      b.resetInert();
    }

     
 }
 public void softClear(){ //Resets the marble and Game conditions to the last check point.
   x = checkX;
   y = checkY;
   vx = 0;
   vy = 0;
   potentialX = 0;
   potentialY = 0;
   yDirection  = 0;
   xDirection = 0;
   hasShot = false;
    for(Balloon b: Balloon.balloons){
      b.detach(this);
      //b.toInitial();
      b.resetInert();
    }
    xVelocityZeroCounter = 0;
    power = 0;
    
 }

   
   
 
 public boolean onScreen (){ //returns true if the Marble is on the screen.
   return(0<x && x<1200 && y<800 && 0<y);
 }
   
 
 
 private static int getSign(double x){ //returns the +- sign of a double.
   if(x<0){
     return -1;
   }
   else{
     return 1;
   }

 }
 
 private static int principleAngle(int angle){ //returns the principle angle of any given angle
   if(angle > 0){
     return angle%360;
   }
   return  angle + ((-angle/360)+1)*360;
   }
   
   
   
     
 
 
}
     