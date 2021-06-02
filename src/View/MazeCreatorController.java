package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class MazeCreatorController {
    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    public ChoiceBox choiseBox_type;
    public MyViewController control;
    private MyViewModel myViewModel;

    public void GenerateMaze(ActionEvent actionEvent) {
        int rows=0;
        int cols=0;
        String value;
        control=new MyViewController();
        Maze maze;
        try {
            rows = Integer.valueOf(textField_mazeRows.getText());
            cols = Integer.valueOf(textField_mazeCols.getText());
            if (rows<=1 || cols<=1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oh no , i cant be created :(" +System.lineSeparator()+"Please choose a size for me"
                        +System.lineSeparator()+"You must choose a positive number"+
                        System.lineSeparator()+"By the way zero is not positive number");
                alert.show();
            }
            else {
                value = (String) choiseBox_type.getValue();
                OutputStream output = new FileOutputStream("./resources/config.properties");
                Properties prop = new Properties();
                if (value.equals("Empty Maze"))
                    prop.setProperty("mazeGeneratingAlgorithm", "EmptyMazeGenerator");
                if (value.equals("Simple Maze"))
                    prop.setProperty("mazeGeneratingAlgorithm", "SimpleMazeGenerator");
                if (value.equals("Complex Maze"))
                    prop.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
                prop.setProperty("threadPoolSize", "3");
                prop.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
                prop.store(output, null);

                if (value == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Oh no , i cant be created :(" + System.lineSeparator() + "Please choose a type for me");
                    alert.show();
                } else {
                    myViewModel=MyViewModel.getInstance();
                    myViewModel.createGame(rows, cols, value);
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                }
            }
        }
        catch (Exception e)
        {
            value=(String) choiseBox_type.getValue();

            if (value==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oh no , i cant be created :(" +System.lineSeparator()+"Please choose a type and size for me");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oh no , i cant be created :(" + System.lineSeparator() + "Please choose a size for me");
                alert.show();
            }
        }

    }
}









