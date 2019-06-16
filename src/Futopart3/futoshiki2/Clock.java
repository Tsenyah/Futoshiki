/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Futopart3.futoshiki2;

/**
 *
 * @author tajs21
 */
public class Clock extends Thread {
   
   static private int seconds, minutes, hours;
    private boolean reset;
    String time;

    public Clock() throws InterruptedException{ for (int i = 0; i < 60; i++) {
            this.sleep(1000);
            this.seconds = this.seconds+1;
            System.out.println( getSeconds());
        }
    }

    
        
       
    
    public int getSeconds(){
    
    return this.seconds;
    }

}
