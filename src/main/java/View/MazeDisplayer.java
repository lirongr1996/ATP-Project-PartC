package View;
import algorithms.mazeGenerators.*;
import algorithms.search.AState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    StringProperty imageFileNameWall=new SimpleStringProperty();
    StringProperty imageFileNamePlayer=new SimpleStringProperty();
    StringProperty imageFileNameGoal=new SimpleStringProperty();
    StringProperty imageFileNamePlayerWin=new SimpleStringProperty();
    StringProperty imageFileNameSolution=new SimpleStringProperty();
    StringProperty imageFileNameHomePage=new SimpleStringProperty();
    StringProperty imageFileNameDementor=new SimpleStringProperty();
    StringProperty imageFileNameDumbledore=new SimpleStringProperty();
    StringProperty imageFileHermione=new SimpleStringProperty();
    private int playerRow;
    private int playerCol;
    private int goalRow;
    private int goalCol;
    private boolean isSolution=false;
    private boolean isWall=false;
    private ArrayList<AState> mazeSolution;
    private int wallRow;
    private int wallCol;
    private String character;

    public void isSolutionTrue()
    {
        isSolution=true;
    }
    public void isSolutionFalse()
    {
        isSolution=false;
    }


    public void setPlayerPosition(Object position) {
        this.playerRow = ((Position)position).getRowIndex();
        this.playerCol = ((Position)position).getColumnIndex();
        draw();
    }

    public void setWallTrue(int[] wall)
    {
        wallRow=wall[0];
        wallCol=wall[1];
        isWall=true;
    }
    public boolean isWall()
    {
        if (isWall==true)
            return true;
        return false;
    }
    public void setWallFalse()
    {
        isWall=false;
    }

    public double[] getGoalPosition()
    {
        double[] arr= new double[4];
        arr[0]= getHeight()/maze.getRow()*goalRow;
        arr[1]= getWidth()/maze.getCol()*goalCol;
        arr[2]=getHeight()/maze.getRow()+arr[0];
        arr[3]=getWidth()/maze.getCol()+arr[1];
        return  arr;
    }

    public double[] getPosition (Object position)
    {
        double[] arr= new double[4];
        arr[0]= getHeight()/maze.getRow()*((Position)position).getRowIndex();
        arr[1]= getWidth()/maze.getCol()*((Position)position).getColumnIndex();
        arr[2]=getHeight()/maze.getRow()+arr[0];
        arr[3]=getWidth()/maze.getCol()+arr[1];
        return arr;
    }


    public void drawMaze (Object game, String c)
    {
        maze=(Maze) game;
        if (maze!=null) {
            goalRow = maze.getGoalPosition().getRowIndex();
            goalCol = maze.getGoalPosition().getColumnIndex();
        }
        character=c;
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
                if (isSolution==true)
                    drawPath(graphicsContext, cellHeight, cellWidth);
                drawPlayer(graphicsContext, cellHeight, cellWidth);
                drawGoal(graphicsContext, cellHeight, cellWidth);
                if (isWall==true)
                    drawPlayerWall(graphicsContext, cellHeight, cellWidth);
            }
        }
    }

    public void drawHomePage()
    {
        Image homeImage = null;
        try {
            GraphicsContext graphicsContext = getGraphicsContext2D();
            homeImage = new Image(new FileInputStream(getImageFileNameHomePage()));
            graphicsContext.drawImage(homeImage,0,0,getWidth(),getHeight());
        }
        catch (Exception e)
        {
        }
    }
    public void setSolution(Object s)
    {
        mazeSolution=((ArrayList<AState>)s);
    }

    private void drawPath(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        Image pathImage = null;
        try {
            pathImage=new Image(new FileInputStream(getImageFileNameSolution()));
            int row,col;
            for (int i=0;i<mazeSolution.size();i++)
            {
                String str=mazeSolution.get(i).toString();
                String r=str.substring(1,str.indexOf(','));
                String c=str.substring(str.indexOf(',')+1,str.length()-1);
                row=Integer.parseInt(r);
                col=Integer.parseInt(c);
                double x = col * cellWidth;
                double y = row * cellHeight;
                graphicsContext.drawImage(pathImage, x, y, cellWidth, cellHeight);
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no path image file.");
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

    private void drawPlayerWall(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = wallCol * cellWidth;
        double y = wallRow * cellHeight;

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNameDementor()));
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
            if (character.equals("Harry Potter"))
                playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
            else if (character.equals("Dumbledore"))
                playerImage = new Image(new FileInputStream(getImageFileNameDumbledore()));
            else
                playerImage = new Image(new FileInputStream(getImageFileNameHermione()));
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

    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public String imageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }

    public String getImageFileNameHomePage() {
        return imageFileNameHomePage.get();
    }

    public String imageFileNameHomePage() {
        return imageFileNameHomePage.get();
    }

    public void setImageFileNameHomePage(String imageFileNameHomePage) {
        this.imageFileNameHomePage.set(imageFileNameHomePage);
    }

    public String getImageFileNameDementor() {
        return imageFileNameDementor.get();
    }

    public String imageFileNameDementor() {
        return imageFileNameDementor.get();
    }

    public void setImageFileNameDementor(String imageFileNameDementor) {
        this.imageFileNameDementor.set(imageFileNameDementor);
    }

    public String getImageFileNameDumbledore() {
        return imageFileNameDumbledore.get();
    }

    public String imageFileNameDumbledore() {
        return imageFileNameDumbledore.get();
    }

    public void setImageFileNameDumbledore(String imageFileNameDumbledore) {
        this.imageFileNameDumbledore.set(imageFileNameDumbledore);
    }



    public String getImageFileNameHermione() {
        return imageFileHermione.get();
    }

    public String imageFileNameHermione() {
        return imageFileHermione.get();
    }

    public void setImageFileNameHermione(String imageFileHermione) {
        this.imageFileHermione.set(imageFileHermione);
    }
}
