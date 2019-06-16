/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Futopart3.GUI;

import Futopart3.futoshiki2.Constraint;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author 184512
 */
public class FutoshikiConstraintGUI extends Label {

    private Constraint c;

    /**
     * Futoshiki Constraint class that creates a label depending on if the gid is in dark or light mode
     * @param c - constraint 
     * @param isDark- board colour     
     */
    public FutoshikiConstraintGUI(Constraint c, boolean isDark) {
        super(c.getSymbol());
        this.c = c;
        setAlignment(Pos.CENTER);
        setPrefSize(50, 50);
        setFont(Font.font("Verdana",FontWeight.BOLD,20));
        if(isDark){
            setTextFill(Color.BLACK);
        }
        else{
        setTextFill(Color.WHITESMOKE);
        }

    }

}
