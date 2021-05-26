package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        System.out.println(e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("not working");
        alert.show();
    }

    }


    public void ExitFromTheProgram(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();

    }

}
