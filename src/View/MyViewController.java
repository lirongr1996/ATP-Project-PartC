package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;


public class MyViewController implements IView {
    public MazeDisplayer mazeDisplayer;

    public void CreateMaze(ActionEvent actionEvent) {
        MazeData.maze=null;
        try {
            Parent homeP= FXMLLoader.load((getClass().getResource("MazeCreator.fxml")));
            Scene homeS=new Scene(homeP);
            Stage stage=new Stage();
            stage.hide();
            stage.setScene(homeS);
            stage.showAndWait();
            mazeDisplayer.drawMaze();
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
        if (MazeData.maze==null)
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
                Parent homeP = FXMLLoader.load((getClass().getResource("LoadMaze.fxml")));
                Scene homeS = new Scene(homeP);
                Stage stage = new Stage();
                stage.hide();
                stage.setScene(homeS);
                stage.showAndWait();
                mazeDisplayer.drawMaze();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("not working");
                alert.showAndWait();
                mazeDisplayer.drawMaze();
            }
        }
    }
}
