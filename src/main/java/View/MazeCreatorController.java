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
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

public class MazeCreatorController extends AView {
    public TextField textField_mazeRows;
    public TextField textField_mazeCols;
    public ChoiceBox choiseBox_type;
    public MyViewController control;
    private MyViewModel myViewModel;

    public void GenerateMaze(ActionEvent actionEvent) {
        int rows=0;
        int cols=0;
        String character;
        control=new MyViewController();
        try {
            rows = Integer.valueOf(textField_mazeRows.getText());
            cols = Integer.valueOf(textField_mazeCols.getText());
            character = (String) choiseBox_type.getValue();

            if (rows<=1 || cols<=1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oh no , i cant be created :(" +System.lineSeparator()+"Please choose a size for me"
                        +System.lineSeparator()+"You must choose a positive number"+
                        System.lineSeparator()+"By the way zero is not positive number");
                alert.show();
            }
            else {
                myViewModel.createGame(rows, cols, character);
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            }
        }
        catch (Exception e)
        {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oh no , i cant be created :(" + System.lineSeparator() + "Please choose a size for me");
                alert.show();
        }

    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        myViewModel=viewModel;
        this.myViewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}









