package Futopart3.GUI;


import Futopart3.futoshiki2.Futoshiki;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author 184512
 */
public class FutoshikiController extends AnchorPane {
    private boolean on;
    private Futoshiki futo;
    boolean isDark = true;
    private FutoshikiBoard game;
    private BorderPane container;
    private int gridsize;
    private Button endgame;
    private Button newGame;
    private Button exit;
    private Button openGame;
    private Button clear;
    private Button saveGame;
    private Button solve;
    private Button check;
    private Button darkMode;
    private Button lightMode;
    private Button stopMusic;
    private VBox vbox;
    private HBox hbox;
    private TextField size;
    private MusicStuff musicObject;
    private String filepath;

    ComboBox<String> difficulty;
    Stage newWindow;

    /**
     * Futoshiki Controller constructor gets the game sound from the folder.
     * builds and positions the game within in the pane
     */
    public FutoshikiController() {
        super();
         filepath = "src\\audio\\Short_Meditation_Music_"
                 + "-_3_Minute_Relaxation_Calming-cI4ryatVkKw.wav";
         musicObject = new MusicStuff();
         on = true;
        musicObject.playMusic(filepath);
        this.container = new BorderPane();
        this.futo = new Futoshiki(3);
        Futoshiki temp = futo;
        do {
            temp.fillPuzzle(1, 1, 1);
        } while (!futo.isLegal());
        futo = temp;
        game = new FutoshikiBoard(futo, true);
        createMenu();
        makeProblemPane();
        setScene();
        container.setCenter(game.getBoard());
    }

    /**
     * openFile method opens the window to enable the user to open a file
     */
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        Window stage = null;
        fileChooser.setTitle("Open Game");
        fileChooser.showOpenDialog(stage);
    }

    /**
     * createButton Method - Creates a button in a default size and colour
     *
     * @param text- the name of the button
     * @return - the button object
     */
    public Button createButton(String text) {

        Button button = new Button(text);
        button.setPadding(new Insets(10, 10, 10, 10));
        setMinWidth(50);
        button.setStyle("-fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
        button.setAlignment(Pos.CENTER);

        return button;
    }

    /**
     * createMenu method- builds the vertical menu that is set to the right of
     * the game board
     */
    public void createMenu() {
        Label menuLabel = new Label("Menu");
        menuLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        vbox = new VBox(12);
        vbox.setMinSize(100, 100);
        solve = createButton("Solve");
        clear = createButton("Clear");
        saveGame = createButton("Save Game");
        newGame = createButton("New Game");
        check = createButton("Check");
        endgame = createButton("Give Up");
        exit = createButton("Exit");
        openGame = createButton("Open Game");
        menuLabel.minWidth(vbox.getWidth());
        vbox.getChildren().addAll(menuLabel, newGame, check, solve, openGame,
                clear, saveGame, endgame, exit);

        exit.setOnAction((ActionEvent event) -> {
            getButtonSound();
            System.exit(0);
        });
        newGame.setOnAction((ActionEvent newGame1) -> {
            getButtonSound();
            newPuzzle();
        });
        check.setOnAction((ActionEvent checkGame) -> {
            futo.CheckEmptySquare();
            getButtonSound();
            makeProblemPane();

        });
        solve.setOnAction((ActionEvent clearBoard) -> {
            getButtonSound();
            clearSquares();
            Futoshiki solved = futo;
            if (solved.solve(futo, 0, 0) == false) {
                                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Solved");
                alert.setHeaderText("This game cannot be solved.");
                alert.setContentText("Please press new game");
                alert.showAndWait();
            } else {
                futo = solved;
                setGame();
                container.setCenter(game);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Solved");
                alert.setHeaderText("This Game has been solved");
                alert.setContentText("This is the completed version of the game");
                alert.showAndWait();
            }

        });

        openGame.setOnAction((ActionEvent load) -> {
            getButtonSound();
            openFile();

        });
        saveGame.setOnAction((ActionEvent save) -> {
            getButtonSound();
        });

        endgame.setOnAction((Finish) -> {
            getButtonSound();
            if (futo.isPuzzleSolved() == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Given up ?");
                alert.setHeaderText("Commiseraions. maybe next time");
                alert.setContentText("The game is not complete and will now be solved");
                futo.solve(futo, 0, 0);
                alert.showAndWait();
            }
        });
        clear.setOnAction((ActionEvent clearBoard) -> {
            getButtonSound();
            clearSquares();
            setGame();
        });
        vbox.setAlignment(Pos.CENTER);
        container.setLeft(vbox);
    }

    /**
     * makeProblemPane method creates the right side of the container where all
     * potential problems with the game are listed
     */
    public void makeProblemPane() {
        VBox problem = new VBox();
        ListView<String> problems = new ListView<>();
        Label problemLabel = new Label("Problems");
        problemLabel.minWidth(problem.getWidth());
        problemLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        problems.getItems().addAll(futo.getProblems());
        problem.getChildren().addAll(problemLabel, problems);
        problem.setStyle("-fx-border-color: #010101; -fx-border-width: 1px;");
        if (futo.getProblems().isEmpty() && futo.isPuzzleSolved()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Complete");
            alert.setHeaderText("Congrats!");
            alert.setContentText("The game has been Completed and is legal !!!");

            alert.showAndWait();
        }
        container.setRight(problem);
    }

    /**
     * newPuzzle Method creates a second window that gets the user input for the
     * new futoshiki puzzle
     */
    public void newPuzzle() {
        Button go = createButton("create");
        Button choice = new Button();

        Label gameSize = new Label("Please select the size of the new game:  ");
        Label difficultyLabel = new Label("Please select the difficulty of the game:  ");
        HBox layout = new HBox();
        VBox list = new VBox();
        HBox layout2 = new HBox();
        difficulty = new ComboBox<>();
        difficulty.getItems().addAll("Easy", "Hard");
        difficulty.getSelectionModel().selectFirst();

        TextField size = new TextField();

        size.setMaxWidth(40);
        layout.getChildren().addAll(gameSize, size);
        layout2.getChildren().addAll(difficultyLabel, difficulty);
        TilePane secondaryLayout = new TilePane();
        secondaryLayout.setPrefColumns(2);
        list.getChildren().addAll(layout, layout2);
        list.getChildren().add(go);
        secondaryLayout.getChildren().add(list);

        go.setOnAction(e -> {
            try {// if is number
                gridsize = Integer.valueOf(size.getText());
                getButtonSound();
            } catch (NumberFormatException NotNumber) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("New Game Error");
                alert.setHeaderText("Unknown Value");
                alert.setContentText("Please Enter a numeric value\n"
                        + "the Grid will be set to default");

                alert.showAndWait();
                gridsize = 5;
            }
            generateLegalPuzzle(difficulty.getValue());

            container.getChildren().clear();

            createMenu();
            makeProblemPane();
            setScene();
            container.setCenter(setGame());
            newWindow.close();

        });
        Scene secondScene = new Scene(secondaryLayout, 230, 200);

        // New window (Stage)
        newWindow = new Stage();

        newWindow.setAlwaysOnTop(true);
        newWindow.setMinWidth(400);
        newWindow.setMaxWidth(400);
        newWindow.setTitle("New Game");
        newWindow.setScene(secondScene);

        newWindow.show();

    }

    /**
     * Creates a legal Puzzle for the new game
     *
     * @param type - the type of game from the comboBox
     */
    public void generateLegalPuzzle(String type) {
        futo = new Futoshiki(gridsize);
        Random random = new Random();
//         boolean isLegal = newgame.isLegal();

        switch (type) {
            case "Easy":
                do {
                    futo = new Futoshiki(gridsize);
                    futo.fillPuzzle(random.nextInt(gridsize),
                            random.nextInt(gridsize), random.nextInt(gridsize));
                } while (!futo.isLegal());

                break;

            default:
                do {
                    futo = new Futoshiki(gridsize);
                    futo.fillPuzzle(random.nextInt(gridsize) / 4,
                            random.nextInt(gridsize), random.nextInt(gridsize));
                } while (!futo.isLegal());

                break;
        }

    }

    /**
     * setGame method passes the new game object in order to get a new game
     * board
     *
     * @return the futoshikiboard object
     */
    public Pane setGame() {

        return game = new FutoshikiBoard(futo, true);
    }

    /**
     * getContainer method
     *
     * @return returns the container containing the game
     */
    public BorderPane getContainer() {
        return container;
    }

    public Pane getPuzzle() {
        container.getChildren().clear();
        container.setCenter(game);
        container.autosize();

        return game.getBoard();
    }

    public FutoshikiBoard getBoard() {

        return game;
    }

    /**
     * resets the current squares back to 0. if they are editable then builds
     * the new GUI object
     */
    public void clearSquares() {
        for (int i = 0; i < futo.gridSize; i++) {
            for (int j = 0; j < futo.gridSize; j++) {
                if (!futo.getSquare(i, j).isEmpty() && futo.getSquare(i, j).getIsEditable()) {
                    futo.setSquare(i, j, 0);
                    setGame();
                    container.setCenter(game);

                }

            }

        }

    }

    /**
     * setScene method- allows the user to choose between dark and light mode
     */
    public void setScene() {

        isDark = true;
        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        darkMode = createButton("Dark Mode");
        lightMode = createButton("Light Mode");
        stopMusic = createButton("Stop Music");
        hBox.getChildren().addAll(darkMode, lightMode,stopMusic);
        lightMode.setOnAction((lightmode) -> {
            getButtonSound();
            isDark = true;
            game = new FutoshikiBoard(futo, true);
            container.setCenter(game);
            container.setStyle("-fx-background-color: #FFFFFF;");

        });
        darkMode.setOnAction((darkmode) -> {
            getButtonSound();
            isDark = false;
            game = new FutoshikiBoard(futo, false);
            container.setStyle("-fx-background-color: #808080;");
            container.setCenter(game);

        });
        stopMusic.setOnAction((darkmode) -> {
            getButtonSound();
            if (on == true) {
               musicObject.pause(); 
               on = false;
              
            }
            else {
            musicObject.play();
            on = true;
            }
            

        });
        container.setBottom(hBox);

    }

    /**
     * getButtonSound gets the default sound for each menu button
     */
    public void getButtonSound() {

        String filepath = "\\src\\audio\\Portal_button_p"
                + "ress-uZwtzcZ1i9M.wav";
        ButtonMenuSound musicObject = new ButtonMenuSound();
        musicObject.playMusic(filepath);
    }

}
