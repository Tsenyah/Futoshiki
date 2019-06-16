/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Launcher;


import Futopart3.GUI.FutoshikiController;
import Futopart3.GUI.FutoshikiController;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author tajs21
 */
public class FutoshikiGuiLauncher extends Application {

     
    AnchorPane container;
    private Scene scene;
     FutoshikiController newGame;




    


    @Override
    public void start(Stage primaryStage
    ) {

 
      
        this.newGame = new FutoshikiController();
     
        container = new AnchorPane();
 
        container.getChildren().add(newGame.getContainer()  );
        container.setStyle("fx-border-color: #010101; -fx-border-width: 5px;");

        container.autosize();
        Scene scene = new Scene(container);
        primaryStage.sizeToScene();     
        primaryStage.setTitle("Futoshiki");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
      
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
