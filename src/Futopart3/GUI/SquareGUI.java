
package Futopart3.GUI;

import Futopart3.futoshiki2.Futoshiki;
import Futopart3.futoshiki2.FutoshikiSquare;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;


/**
 *
 * @author 184512
 */
public class SquareGUI extends Button {

    FutoshikiSquare s;
    int x, y;
    private final int size;

    /**
     * SquareGui is a class that extends a button.
     * @param s - the futoshiki square in question.
     * @param isEdit - gets id the tile is editable or not 
     * @param puzzle - gets the current futoshiki board 
     */
    public SquareGUI(FutoshikiSquare s, int size,boolean isEdit,Futoshiki puzzle)
    
    
    {
        super(String.valueOf(s.getValue()));
        this.s = s;
        this.size = size;
        this.x = s.getRow();
        this.y = s.getColumn();
        setPrefSize(50, 50);
        setStyle("-fx-border-color: #010101; -fx-border-width: 1px;");
        if (s.getValue()== 0 ) {
            setText(String.valueOf(" "));
        }
        
        
            setOnAction((ActionEvent event) -> {
                String filepath = "\\src\\audio\\Button Click-SoundBible.com-1931397433.wav";
       ButtonMenuSound musicObject = new ButtonMenuSound();
        musicObject.playMusic(filepath);
                if (s.getValue()< size) {
                    s.setValue(s.getValue()+ 1);
                    
                    setText(String.valueOf(s.getValue()));
        musicObject.playMusic(filepath);
                } else {
                    try{ 
                        s.setValue(0);
                    setText(String.valueOf(" "));
                   
                }catch(NumberFormatException 
                        exception){
                        System.out.println("This Square has been set to 0");
                }
                }
                puzzle.setSquare(x, y,Integer.parseInt(getText()));
            });
        }

    
}
