//SoundEffect.java
//Yash Uppot
//Contains SoundEffect class that allows us to play, loop, stop, and load music.
import java.io.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
class SoundEffect{
    private Clip c;
    public SoundEffect(String filename){
        setClip(filename);
    }
    public void setClip(String filename){ //Loads and opens the audio file.
        try{
            File f = new File(filename);
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
        } catch(Exception e){ System.out.println("error"); }
    }
    public void play(){ //Plays the audio
        c.setFramePosition(0);
        c.start();
    }
    public void stop(){ //Stops the audio
        c.stop();
    }
    
    public void playLoop(){ //Plays the audio in a continous loop.
       c.setFramePosition(0);
       c.start();
       c.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
       