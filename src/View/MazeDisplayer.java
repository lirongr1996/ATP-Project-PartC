package View;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    private MyViewModel myViewModel;
    StringProperty imageFileNameWall=new SimpleStringProperty();
    StringProperty imageFileNamePlayer=new SimpleStringProperty();
    StringProperty imageFileNameGoal=new SimpleStringProperty();
    StringProperty imageFileNamePlayerWin=new SimpleStringProperty();
    private int playerRow;
    private int playerCol;
    private int goalRow;
    private int goalCol;

    public boolean setPlayerPosition(Object position) {
        this.playerRow = ((Position)position).getRowIndex();
        this.playerCol = ((Position)position).getColumnIndex();
        draw();
        if(playerRow==goalRow && playerCol==goalCol)
            return true;
        return false;
    }

    public void drawMaze ()
    {
        myViewModel=MyViewModel.getInstance();
        maze=(Maze) myViewModel.getGame();
        if (maze!=null) {
            goalRow = maze.getGoalPosition().getRowIndex();
            goalCol = maze.getGoalPosition().getColumnIndex();
        }
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRow();
            int cols = maze.getCol();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if (playerRow==goalRow && playerCol==goalCol)
            {
                drawPlayerWin(graphicsContext, cellHeight, cellWidth);
            }
            else {
                drawPlayer(graphicsContext, cellHeight, cellWidth);
                drawGoal(graphicsContext, cellHeight, cellWidth);
            }
        }
        else
        {

        }
    }

    private void drawPlayerWin(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = goalCol * cellWidth;
        double y = goalRow * cellHeight;

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayerWin()));
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no player image file.");
        }
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = goalCol * cellWidth;
        double y = goalRow * cellHeight;

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNameGoal()));
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no player image file.");
        }

    }


    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no player image file.");
        }

    }

    private void drawMazeWalls (GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols)
    {
        graphicsContext.setFill(Color.LIGHTGRAY);

        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            int [][]m=maze.getTwoDMaze();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(m[i][j] == 1){
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                    }
                    else
                    {
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no wall image file.");
        }

    }



    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }


    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public String imageFileNameGoalProperty() {
        return imageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public String getImageFileNamePlayerWin() {
        return imageFileNamePlayerWin.get();
    }

    public String imageFileNamePlayerWinProperty() {
        return imageFileNamePlayerWin.get();
    }

    public void setImageFileNamePlayerWin(String imageFileNamePlayerWin) {
        this.imageFileNamePlayerWin.set(imageFileNamePlayerWin);
    }
}
