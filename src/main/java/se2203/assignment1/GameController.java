package se2203.assignment1;

/*
Piotr Nowak
04-Feb-2022
SE2203-Asn1
pnowak5@uwo.ca
*/

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Random;

public class GameController implements EventHandler<MouseEvent> {
    @FXML
    private Label label;
    @FXML
    private Button resetBtn, playBtn;
    @FXML
    private Slider headSizeSldr;
    @FXML
    private GridPane gameBoard;

    //track the number of hydra heads cut
    private int hydrasCutCount = 0;
    private int[][] boardState = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //18x18 array to store board state and
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},  //check for overlapping heads
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};


    @FXML
    protected void onResetButtonClick() {
        hydrasCutCount = 0; //reset hydra count to zero
        gameBoard.getChildren().clear(); //remove all existing hydra
        playBtn.setDisable(false); //enable play button
        headSizeSldr.setDisable(false); //enable slider
    }
    @FXML
    protected void onPlayButtonClick() {
        playBtn.setDisable(true); //disable play button
        headSizeSldr.setDisable(true); //disable slider
        Random rand = new Random();

        int sizeSelection = (int)headSizeSldr.getValue(); //get selected starting size
        int x = rand.nextInt(18), y = rand.nextInt(18); //determine starting location
        HydraHead newHead = makeNewHead(sizeSelection,x,y); //make initial head

        hydrasCutCount += 1; //increment hydra count
        boardState[x][y] = 1; //update board state

        GridPane.setConstraints(newHead,x,y);
        newHead.setOnMouseClicked(this); //set event handler
        gameBoard.getChildren().add(newHead); //add to scene
    }

    @Override
    public void handle(MouseEvent mouseEvent) { //when a hydra head is clicked

        Random rand = new Random();

        HydraHead clicked = (HydraHead) mouseEvent.getSource(); //get the hydra head that was clicked
        gameBoard.getChildren().remove(clicked); //remove it from the game
        boardState[clicked.getXPos()][clicked.getyPos()] = 0; //remove from board state

        if (clicked.getHead() == 1) //if it was a size 1, end here
        {
            if (gameBoard.getChildren().isEmpty()) //check win condition
            {
                Label win = new Label(String.format("You Win! - You have cut %d Hydra Heads", hydrasCutCount));
                win.setPrefWidth(650);
                win.setPrefHeight(40);
                GridPane.setConstraints(win, 8, 8);
                win.setFont(Font.loadFont("file:src/main/resources/se2203/assignment1/Blackcastlemf-BG5n.ttf", 22));
                win.setAlignment(Pos.CENTER);
                win.setTextFill(Color.RED);
                gameBoard.getChildren().add(win);
            }
            return;
        }

        int newHeads = rand.nextInt(2) + 2; //determine if 2 or 3 new heads are added
        hydrasCutCount += newHeads; //add to count

        //add new heads
        for (int i = 0; i < newHeads; i++)
        {
            int x, y; //get a new head location that is not occupied
            do {
                x = rand.nextInt(18);
                y = rand.nextInt(18);
            } while (boardState[x][y] == 1);

            boardState[x][y] = 1; //add to board state
            HydraHead newHead = makeNewHead(clicked.getHead() - 1, x, y); //make new head
            GridPane.setConstraints(newHead,x,y);
            newHead.setOnMouseClicked(this); //set event handler
            gameBoard.getChildren().add(newHead); //add to scene
        }
    }

    private HydraHead makeNewHead(int size, int x, int y)
    {
        switch (size) {
            case 1:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize1.png", 40, 40, false, false), 1, x, y);
            case 2:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize2.png", 40, 40, false, false), 2, x, y);
            case 3:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize3.png", 40, 40, false, false), 3, x, y);
            case 4:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize4.png", 40, 40, false, false), 4, x, y);
            case 5:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize5.png", 40, 40, false, false), 5, x, y);
            case 6:
                return new HydraHead(new Image("file:src/main/resources/se2203/assignment1/HeadSize6.png", 40, 40, false, false), 6, x, y);
            default:
                System.out.println("Error, invalid hydra head size");
                return null;
        }
    }
}