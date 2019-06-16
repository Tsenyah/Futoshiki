package Futopart3.futoshiki2;

import com.sun.prism.image.Coords;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is the "model answer" to the second part of the Futoshiki
 * assignment. It includes extra classes for Constraints and Futoshiki Squares.
 * There will be other equally good (or even better) solutions, as well as lots
 * of worse ones!
 *
 * Documentation is not complete, as complete documentation is required for part
 * 3 of the assignment.
 *
 * @author Ian Wakeman
 * @version 1.0
 */
public class Futoshiki implements java.io.Serializable {

    private static final long SerialVersionUID = 1L;
    //constant to specify size of puzzle
    public static final int DEFAULTGRIDSIZE = 5;
    public int gridSize;

    private final FutoshikiSquare[][] squares;
    private final Constraint[][] rowConstraints;
    private final Constraint[][] columnConstraints;

    public Futoshiki(int gridSize) {
        this.gridSize = gridSize;
        squares = new FutoshikiSquare[gridSize][gridSize];
        rowConstraints = new Constraint[gridSize][gridSize - 1];
        columnConstraints = new Constraint[gridSize][gridSize - 1];

        //set up initial grid of empty squares
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                squares[row][column] = new FutoshikiSquare(row, column);
                squares[row][column].setEditable(true);
            }
        }

        //set up initial row constraints (no constraints)
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize - 1; column++) {
                rowConstraints[row][column] = new EmptyConstraint(squares[row][column], squares[row][column + 1]);
            }
        }

        //set up initial column constraints (no constraints)
        for (int column = 0; column < gridSize; column++) {
            for (int row = 0; row < gridSize - 1; row++) {
                columnConstraints[column][row] = new EmptyConstraint(squares[row][column], squares[row + 1][column]);
            }
        }
    }

    public Futoshiki() {
        this(DEFAULTGRIDSIZE);
    }

    public void setSquare(int row, int column, int val) {
        squares[row][column].setValue(val);
    }

    public void setRowConstraint(int row, int col, String relation) {
        if (relation.equals("<")) {
            rowConstraints[row][col] = new LessThanConstraint(rowConstraints[row][col]);
        } else if (relation.equals(">")) {
            rowConstraints[row][col] = new GreaterThanConstraint(rowConstraints[row][col]);
        }
    }

    public void setColumnConstraint(int col, int row, String relation) {
        if (relation.equals("<")) {
            columnConstraints[col][row] = new LessThanConstraint(columnConstraints[col][row]);
        } else if (relation.equals(">")) {
            columnConstraints[col][row] = new GreaterThanConstraint(columnConstraints[col][row]);
        }
    }

    public FutoshikiSquare getSquare(int row, int col) {
        return squares[row][col];
    }

    /**
     * ******************************************************************
     * Fills a Futoshiki puzzle with the randomized values and constraints. This
     * is not guaranteed to be legal
     *
     * @param numValues How many values to enter
     * @param numHorizontal How many horizontal constraints
     * @param numVertical How many vertical constraints
     * ******************************************************************
     */
    public void fillPuzzle(int numValues, int numHorizontal, int numVertical) {
        Random rand = new Random();
        // There is repetition of code here, but removing the repetition
        // requires some functional manipulation which we haven't covered yet 
        int countValues = 0;
        while (countValues < numValues) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            if (squares[x][y].isEmpty()) {
                squares[x][y].setValue(rand.nextInt(gridSize) + 1);
                squares[x][y].setEditable(false);
                
                countValues++;
            }
        }
        int countHorizontal = 0;
        while (countHorizontal < numHorizontal) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize - 1);
            if (rowConstraints[x][y] instanceof EmptyConstraint) {
                rowConstraints[x][y] = rand.nextInt(2) > 0 ? new GreaterThanConstraint(rowConstraints[x][y])
                        : new LessThanConstraint(rowConstraints[x][y]);
                countHorizontal++;
            }
        }
        int countVertical = 0;
        while (countVertical < numVertical) {
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize - 1);
            if (columnConstraints[x][y] instanceof EmptyConstraint) {
                columnConstraints[x][y] = rand.nextInt(2) > 0
                        ? new GreaterThanConstraint(columnConstraints[x][y])
                        : new LessThanConstraint(columnConstraints[x][y]);
                countVertical++;
            }
        }

    }

    /**
     * ******************************************************************
     * Returns a String in Ascii for pretty printing
     *
     * @return The string representing the puzzle
     * ******************************************************************
     */
    public String displayString() {
        String s = "";
        for (int row = 0; row < gridSize - 1; row++) {
            s += drawRow(row);
            s += drawColumnConstraints(row);
        }
        s += drawRow(gridSize - 1);
        return s;
    }

    /**
     * Whether the puzzle is in a legal state
     *
     * @return true if ok
     */
    public boolean isLegal() {
        boolean result = validValues();
        for (int row = 0; row < gridSize; row++) {
            result &= checkRow(row);
        }
        for (int col = 0; col < gridSize; col++) {
            result &= checkColumn(col);
        }
        return result;
    }

    /**
     *
     * @return A list of problems on this puzzle
     */
    public List<String> getProblems() {
        List<String> result = validValuesString();
        for (int row = 0; row < gridSize; row++) {
            result.addAll(rowProblems(row));
        }
        for (int col = 0; col < gridSize; col++) {
            result.addAll(columnProblems(col));
        }
        return result;
    }

    private String printTopBottom() {
        String s = "";
        for (int col = 0; col < gridSize; col++) {
            s += "---";
            if (col < (gridSize - 1)) {
                s += " ";
            }
        }
        return s + "\n";
    }

    private String drawColumnConstraints(int row) {
        String s = " ";
        for (int col = 0; col < gridSize; col++) {
            s += columnConstraints[col][row].getSymbol() + " ";
            if (col < (gridSize - 1)) {
                s += "  ";
            }
        }

        return s + "\n";
    }

    private String drawRow(int row) {
        String s = printTopBottom();
        for (int col = 0; col < gridSize; col++) {
            s += "|" + squares[row][col].getSymbol() + "|";
            if (col < gridSize - 1) {
                s += rowConstraints[row][col].getSymbol();
            }
        }
        return s + "\n" + printTopBottom();
    }

    /**
     * *********************************************************************
     * Lots of paired methods (row/column) or (boolean/String result) below
     * **********************************************************************
     */
    private boolean checkRowDuplicates(int row) {
        boolean[] found = new boolean[gridSize + 1]; //1 longer as numbers go up to gridSize
        for (int col = 0; col < gridSize; col++) {
            if (!squares[row][col].isEmpty()) {
                int val = squares[row][col].getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        return false;
                    } else {
                        found[val] = true;
                    }
                }
            }
        }
        return true;
    }

    private List<String> rowDuplicateProblems(int row) {
        ArrayList<String> result = new ArrayList();
        boolean[] found = new boolean[gridSize + 1]; //1 longer as numbers go up to gridSize
        for (int col = 0; col < gridSize; col++) {
            if (!squares[row][col].isEmpty()) {
                int val = squares[row][col].getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        result.add("Duplicate entry " + val + " on row " + row + 1);
                    } else {
                        found[val] = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean checkColumnDuplicates(int col) {
        boolean[] found = new boolean[gridSize + 1]; //1 longer as numbers go up to gridSize
        for (int row = 0; row < gridSize; row++) {
            if (!squares[row][col].isEmpty()) {
                int val = squares[row][col].getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        return false;
                    } else {
                        found[val] = true;
                    }
                }
            }
        }
        return true;
    }

    private List<String> columnDuplicateProblems(int col) {
        ArrayList<String> result = new ArrayList();
        boolean[] found = new boolean[gridSize + 1]; //1 longer as numbers go up to gridSize
        for (int row = 0; row < gridSize; row++) {
            if (!squares[row][col].isEmpty()) {
                int val = squares[row][col].getValue();
                if (!isIllegalValue(val)) {
                    if (found[val]) {
                        result.add("Duplicate entry " + val + " on column " + col + 1);
                    } else {
                        found[val] = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean checkRowConstraints(int row) {
        for (int col = 0; col < gridSize - 1; col++) {
            Constraint constraint = rowConstraints[row][col];
            if (!constraint.isLegal()) {
                return false;
            }
        }
        return true;
    }

    private List<String> rowConstraintProblems(int row) {
        ArrayList<String> result = new ArrayList();
        for (int col = 0; col < gridSize - 1; col++) {
            Constraint constraint = rowConstraints[row][col];
            if (!constraint.isLegal()) {
                result.add("Row constraint violated at " + row + 1 + " "
                        + col + 1 + " and " + row + 1 + " " + (col + 2));
            }
        }
        return result;
    }

    private boolean checkColumnConstraints(int col) {
        for (int row = 0; row < gridSize - 1; row++) {
            Constraint constraint = columnConstraints[col][row];
            if (!constraint.isLegal()) {
                return false;
            }
        }
        return true;
    }

    private List<String> columnConstraintProblems(int col) {
        ArrayList<String> result = new ArrayList();
        for (int row = 0; row < gridSize - 1; row++) {
            Constraint constraint = columnConstraints[col][row];
            if (!constraint.isLegal()) {
                result.add("Column constraint violated at " + row + 1 + " "
                        + col + 1 + " and " + (row + 2) + " " + col + 1);
            }
        }
        return result;
    }

    private boolean checkRow(int row) {
        return checkRowDuplicates(row) && checkRowConstraints(row);
    }

    private List<String> rowProblems(int row) {
        List<String> s = rowDuplicateProblems(row);
        s.addAll(rowConstraintProblems(row));
        return s;
    }

    private boolean checkColumn(int col) {
        return checkColumnDuplicates(col) && checkColumnConstraints(col);
    }

    private List<String> columnProblems(int col) {
        List<String> s = columnDuplicateProblems(col);
        s.addAll(columnConstraintProblems(col));
        return s;
    }

    private boolean validValues() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (!squares[row][col].isEmpty()) {
                    int value = squares[row][col].getValue();
                    if (isIllegalValue(value)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isIllegalValue(int val) {
        return (val < 1) || (val > gridSize);
    }

    private List<String> validValuesString() {
        ArrayList<String> result = new ArrayList();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (!squares[row][col].isEmpty()) {
                    int value = squares[row][col].getValue();
                    if (isIllegalValue(value)) {
                        result.add("Illegal value " + value + " at " + row + 1 + " " + col + 1);
                    }
                }
            }
        }
        return result;
    }

    public Constraint getRowConstraint(int row, int col) {
        return rowConstraints[row][col];
    }

    public Constraint getColumnConstraint(int row, int col) {
        return columnConstraints[row][col];
    }

    public void CheckEmptySquare() {
        boolean empty = false;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (squares[i][j].isEmpty()) {
                    empty = true;
                        break;
                }
            }}
            if (empty) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Check Error");
                alert.setHeaderText("Unfinished Game");
                alert.setContentText("Not all Squares have been filled");
                alert.showAndWait();

            }
    }
    

/**
     * this solves the current futoshiki puzzle 
     * @param game futoshiki
     * @param row int
     * @param column int
     * @return boolean
     */
   public boolean solve(Futoshiki game, int row, int column){
      Futoshiki solvedGame = game;
      
       if(isLegal() == false ){
           squares[row][column].setValue(0);
           return false;
          
        }else{
           if(isPuzzleSolved()== true && isLegal() == true){
               return true;
           }else{
                for(int i = 0; i < gridSize; i++){
                        for(int j = 0; j < gridSize; j++){
                            boolean Legaltest = isLegal();
                        
                               if(solvedGame.getSquare(i, j).getValue()!= squares[i][j].getValue()||  squares[i][j].getValue()== 0 )  {
                                   
                               int k = 0;
                                    while(k < gridSize){
                                    squares[i][j].setValue(k +1);
                                     Legaltest = solve(solvedGame, i, j);
                                     k++;
                                    if(Legaltest == false && isLegal() == false){
                                        squares[row][column].setValue(0);
                                      return false;
                                      
                                    }else if(Legaltest == true && isPuzzleSolved()== true){
                                        return true;
                                    }
                                    
                                    }
                                    if(Legaltest == false){
                                        squares[row][column].setValue(0);
                                        return Legaltest;
                                    }else{
                                        Legaltest = isPuzzleSolved();
                                        if(Legaltest == true){
                                            return Legaltest;
                                        }
                                    }
                                }
                               if(Legaltest == false){
                                   squares[row][column].setValue(0);
                                   return Legaltest;
                                   
                               }else{
                                   Legaltest = isPuzzleSolved();
                                   if(Legaltest == true){
                                       return Legaltest;
                                   }
                                         
                               }
                        }
                }
           }
       }

       return validValues();
   } 

    /**
     *
     * @return the size if the grid
     */
    public int getBoardSize() {
        return gridSize;
    }
        public boolean isPuzzleSolved() {

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (squares[i][j].isEmpty()) {
                    return false;
                }

            }
        }
        return isLegal();
    }
}
