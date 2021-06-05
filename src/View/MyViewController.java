package View;

import ViewModel.MyViewModel;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Set;


public class MyViewController extends  AView implements Initializable {
    public MazeDisplayer mazeDisplayer;
    public MenuItem menuItem_solve;
    public Pane pane;
    public AnchorPane aPane;
    public ScrollPane scrollPane;
    public ImageView imageView;
    private Media media;
    private MediaPlayer player;
    private Media mWall;
    private MediaPlayer pWall;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pane.prefWidthProperty().bind(scrollPane.widthProperty());
            pane.prefHeightProperty().bind((scrollPane.heightProperty()));
            mazeDisplayer.widthProperty().bind(pane.widthProperty());
            mazeDisplayer.heightProperty().bind(pane.heightProperty());

            aPane.heightProperty().addListener(e->{
                if (myViewModel!=null)
                    mazeDisplayer.drawMaze(myViewModel.getGame());
            });
            aPane.widthProperty().addListener(e->{
                if (myViewModel!=null)
                    mazeDisplayer.drawMaze(myViewModel.getGame());
            });

            menuItem_solve.setDisable(true);
            media = new Media(getClass().getResource("/music/Harry Potter Theme Song.mp3").toURI().toString());
            player= new MediaPlayer(media);
            player.play();

            mazeDisplayer.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (myViewModel!=null)
                        myViewModel.setPositionPlayer(event);
                }
            });


            pane.setOnScroll(new EventHandler<ScrollEvent>() {
                @Override
                public void handle(ScrollEvent event) {
                    double zoomFactor = 1.05;
                    double deltaY = event.getDeltaY();

                    if (deltaY < 0){
                        zoomFactor = 0.95;
                    }
                    pane.setScaleX(pane.getScaleX() * zoomFactor);
                    pane.setScaleY(pane.getScaleY() * zoomFactor);
                    event.consume();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void CreateMaze(ActionEvent actionEvent) {
        myViewModel.LoadGame(null);
        try {
            moveFXML("MazeCreator.fxml");
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.show();
        }
    }


    public void ExitFromTheProgram(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void Info(ActionEvent event) {
        try {
            moveFXML("About.fxml");
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.show();
        }
    }


    public void SaveMaze(ActionEvent actionEvent) {
        if (myViewModel.getGame()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no maze to save");
            alert.show();
        }
        else {
            try {
                moveFXML("SaveMaze.fxml");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("not working");
                alert.showAndWait();
            }
        }
    }

    public void LoadMaze(ActionEvent actionEvent) {
        File f=new File ("./resources/MazeFile");
        File[] files = f.listFiles();
        if (files.length==1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("There are none existing files.");
            alert.showAndWait();
        }
        else {
            try {
                moveFXML("LoadMaze.fxml");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("not working");
                alert.showAndWait();
            }
        }
    }

    public void ShowProperties(ActionEvent actionEvent) {
        if (myViewModel.getGame()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No properties available.");
            alert.showAndWait();
        }
        else {
            try {
                moveFXML("Properties.fxml");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("not working");
                alert.showAndWait();
            }
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void keyPressed(KeyEvent keyEvent) {
        myViewModel.setPositionPlayer(keyEvent);
        keyEvent.consume();
    }


    public void ShowSolution(ActionEvent actionEvent) {
        mazeDisplayer.isSolutionTrue();
        myViewModel.solveGame();
    }


    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel=viewModel;
        this.myViewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        String change=(String) arg;
        switch (change)
        {
            case "game created":
                MazeCreated();
                break;
            case "player move":
                PlayerMoved();
                break;
            case "solve game":
                solveGame();
                break;
            case "game loaded":
                LoadGame();
                break;
            case "wall":
                try {
                    player.pause();
                    mWall = new Media(getClass().getResource("/music/wall.mp3").toURI().toString());
                    pWall = new MediaPlayer(mWall);
                    pWall.play();
                    player.play();
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The music not working");
                    alert.showAndWait();
                }
                break;
            case "win":
                PlayerWin();
                break;
        }
    }


    private void PlayerWin() {
        try {
            mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
            player.stop();
            moveFXML("WinGame.fxml");

            myViewModel.LoadGame(null);
            mazeDisplayer.drawHomePage();
            menuItem_solve.setDisable(true);
            mazeDisplayer.isSolutionFalse();
            media = new Media(getClass().getResource("/music/Harry Potter Theme Song.mp3").toURI().toString());
            player= new MediaPlayer(media);
            player.play();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.showAndWait();
        }
    }

    private void LoadGame() {
        try {
            if (myViewModel.getGame()!=null) {
                player.stop();
                media = new Media(getClass().getResource("/music/Harry's Wondrous World.mp3").toURI().toString());
                player = new MediaPlayer(media);
                player.setVolume(0.1);
                player.play();
            }
            mazeDisplayer.drawMaze(myViewModel.getGame());
            menuItem_solve.setDisable(false);
            mazeDisplayer.isSolutionFalse();
            mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
            myViewModel.setStratPR(mazeDisplayer.getPosition(myViewModel.getPositionPlayer()));
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not found the sound");
            alert.showAndWait();
        }
    }

    private void solveGame() {
        mazeDisplayer.setSolution(myViewModel.getSolution());
        mazeDisplayer.drawMaze(myViewModel.getGame());
    }

    private void PlayerMoved() {
        mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
        myViewModel.setStratPR(mazeDisplayer.getPosition(myViewModel.getPositionPlayer()));
    }

    private void MazeCreated() {
        try {
            if(myViewModel.getGame()!=null) {
                player.stop();
                media = new Media(getClass().getResource("/music/Harry's Wondrous World.mp3").toURI().toString());
                player = new MediaPlayer(media);
                mazeDisplayer.isSolutionFalse();
                player.setVolume(0.1);
                player.play();
            }
            mazeDisplayer.drawMaze(myViewModel.getGame());
            menuItem_solve.setDisable(false);
            mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
            myViewModel.setStratPR(mazeDisplayer.getPosition(myViewModel.getPositionPlayer()));
            myViewModel.setGoalPostion(mazeDisplayer.getGoalPosition());
        }
        catch ( Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not found the sound");
            alert.showAndWait();
        }
    }

    public void Help(ActionEvent actionEvent) {
        try {
            moveFXML("Help.fxml");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.showAndWait();
        }
    }
}
