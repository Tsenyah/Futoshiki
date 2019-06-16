
package Futopart3.GUI;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author 184512
 */
public class ButtonMenuSound {
    
    void playMusic(String musicLocation){
       try { 
           File musicPath = new File(musicLocation);
           if (musicPath.exists()) {
               AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
               audioInput.markSupported();
               Clip clip = AudioSystem.getClip();
               clip.open(audioInput);
               clip.start();      
           }
           else{
               System.out.println("cant find file");
           }
           
       }catch(Exception ex)
       {
           ex.printStackTrace();
       }
       
    
    }

    
}
