/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Futopart3.GUI;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author 184512
 */
public class MusicStuff {

    private Clip clip;
    private long songPosition;
    private boolean pause;

    public void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                audioInput.markSupported();
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                clip.loop(clip.LOOP_CONTINUOUSLY);

            } else {
                System.out.println("cant find file");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *A method to pause the music 
     * @return
     */
    public boolean pause() {
        long songPosition = clip.getMicrosecondPosition();
        clip.stop();
        return false;
    } 
    public boolean play(){
    clip.start();
    return true;
    }
 

    

}
