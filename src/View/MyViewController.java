package View;

import ViewModel.MyViewModel;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class MyViewController implements IView, Initializable {
    public MazeDisplayer mazeDisplayer;
    private MyViewModel myViewModel;
    private Media media;
    private MediaPlayer player;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            media = new Media(getClass().getResource("/music/Harry Potter Theme Song.mp3").toURI().toString());
            player= new MediaPlayer(media);
            player.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CreateMaze(ActionEvent actionEvent) {
        myViewModel=MyViewModel.getInstance();
        myViewModel.LoadGame(null);
        try {
            Parent homeP= FXMLLoader.load((getClass().getResource("MazeCreator.fxml")));
            Scene homeS=new Scene(homeP);
            Stage stage=new Stage();
            stage.hide();
            stage.setScene(homeS);
            stage.showAndWait();
            if(myViewModel.getGame()!=null) {
                player.stop();
                media = new Media(getClass().getResource("/music/Harry's Wondrous World.mp3").toURI().toString());
                player = new MediaPlayer(media);
                player.setVolume(0.1);
                player.play();
            }
            mazeDisplayer.drawMaze();
            mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
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
                    Parent homeP= FXMLLoader.load((getClass().getResource("About.fxml")));
                    Scene homeS=new Scene(homeP);
                    Stage stage=new Stage();
                    stage.hide();
                    stage.setScene(homeS);
                    stage.showAndWait();
                }
                catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("not working");
                    alert.show();
                }
            }


    public void SaveMaze(ActionEvent actionEvent) {
        myViewModel=MyViewModel.getInstance();
        if (myViewModel.getGame()==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no maze to save");
            alert.show();
        }
        else {
            try {
                Parent homeP = FXMLLoader.load((getClass().getResource("SaveMaze.fxml")));
                Scene homeS = new Scene(homeP);
                Stage stage = new Stage();
                stage.hide();
                stage.setScene(homeS);
                stage.showAndWait();
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
                myViewModel=MyViewModel.getInstance();
                Parent homeP = FXMLLoader.load((getClass().getResource("LoadMaze.fxml")));
                Scene homeS = new Scene(homeP);
                Stage stage = new Stage();
                stage.hide();
                stage.setScene(homeS);
                stage.showAndWait();
                if (myViewModel.getGame()==null) {
                    player.stop();
                    media = new Media(getClass().getResource("/music/Harry's Wondrous World.mp3").toURI().toString());
                    player = new MediaPlayer(media);
                    player.setVolume(0.1);
                    player.play();
                }
                mazeDisplayer.drawMaze();
                mazeDisplayer.setPlayerPosition(myViewModel.getPositionPlayer());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("not working");
                alert.showAndWait();
            }
        }
    }

    public void ShowProperties(ActionEvent actionEvent) {
        myViewModel=MyViewModel.getInstance();
        if (myViewModel.getGame()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No properties available.");
            alert.showAndWait();
        }
        else {
            try {
                Parent homeP = FXMLLoader.load((getClass().getResource("Properties.fxml")));
                Scene homeS = new Scene(homeP);
                Stage stage = new Stage();
                stage.hide();
                stage.setScene(homeS);
                stage.showAndWait();
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
        myViewModel=MyViewModel.getInstance();
        boolean win=mazeDisplayer.setPlayerPosition(myViewModel.setPositionPlayer(keyEvent));
        keyEvent.consume();
        try {
            if (win)
            {
                player.stop();
                Parent homeP = FXMLLoader.load((getClass().getResource("WinGame.fxml")));
                Scene homeS = new Scene(homeP);
                Stage stage = new Stage();
                stage.hide();
                stage.setScene(homeS);
                stage.showAndWait();
                myViewModel.LoadGame(null);
                mazeDisplayer.drawMaze();
                media = new Media(getClass().getResource("/music/Harry Potter Theme Song.mp3").toURI().toString());
                player= new MediaPlayer(media);
                player.play();
            }
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.showAndWait();
        }

    }

    public void ChangeSound(ActionEvent actionEvent) {
        try {
            Parent homeP = FXMLLoader.load((getClass().getResource("Properties.fxml")));
            Scene homeS = new Scene(homeP);
            Stage stage = new Stage();
            stage.hide();
            stage.setScene(homeS);
            stage.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
            alert.showAndWait();
        }
    }
}
