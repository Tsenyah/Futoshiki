/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Futopart3.GUI;

import Futopart3.futoshiki2.Futoshiki;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author 184512
 */
public class FutoshikiBoard extends Pane{

    private  Pane pane;
    private  Futoshiki puzzle;
    private  SquareGUI[][] grid;
    private  int gridsize;

    /**
     *FutoshikiBoard class extends the regular pane. used for building the game board
     * @param futo - futoshiki puzzle in question   
     * @param isDark - boolean for the dark or light mode 
     */
    public FutoshikiBoard(Futoshiki futo,boolean isDark) {
        this.puzzle = futo;
        
        this.gridsize = ((puzzle.gridSize * 2) - 1);
        GridPane gridLayout = new GridPane();

        gridLayout.setHgap(10); 
        gridLayout.setVgap(10); 
        gridLayout.setPadding(new Insets(10, 10, 10, 10));

        grid = new SquareGUI[gridsize][gridsize];
        Pane root = new Pane();
        int rowCounter = 0;
        for (int i = 0; i < puzzle.gridSize; i++) {
            int colCounter = 0;
            for (int j = 0; j < puzzle.gridSize; j++) {
                SquareGUI square;
                if ( puzzle.getSquare(i, j).getIsEditable()) {
                    square = new SquareGUI(puzzle.getSquare(i, j), futo.gridSize, true, puzzle);
                    grid[i][j] = square;

                } else {
                    square = new SquareGUI(puzzle.getSquare(i, j), futo.gridSize, false,puzzle);
                    grid[i][j] = square;
                    square.setDisable(true);

                }

                gridLayout.add(square, colCounter, rowCounter);
                colCounter += 2;
            }
            rowCounter += 2;
        }
        rowCounter = 0;
        for (int i = 0; i < puzzle.gridSize; i++) {
            int colCounter = 1;
            for (int j = 0; j < puzzle.gridSize - 1; j++) {

                gridLayout.add(new FutoshikiConstraintGUI(puzzle.getRowConstraint(i, j), isDark), colCounter, rowCounter);
                colCounter += 2;
            }
            rowCounter += 2;
        }
        
        rowCounter = 1;
        for (int i = 0; i < puzzle.gridSize - 1; i++) {
            int colCounter = 0;
            for (int j = 0; j < puzzle.gridSize; j++) {
                gridLayout.add(new FutoshikiConstraintGUI(puzzle.getColumnConstraint(j  , i),isDark), colCounter, rowCounter);
                colCounter += 2;
            }
            rowCounter += 2;

        }
        setStyle("fx-border-color: #010101; -fx-border-width: 5px;");
        getChildren().add(gridLayout);
        
        
    }

    public Pane getBoard() {

        return this;
    }
    public SquareGUI[][] getGrid()
    {
    return grid;
    }
    
    

  

 

}

