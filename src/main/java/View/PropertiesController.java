package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesController extends AView implements Initializable {
    public Label mazeType;
    public Label mazeSolver;
    public Label numberThreads;
    public ChoiceBox choiseBox_type;
    public ChoiceBox choiseBox_solve;
    public TextField number;

    StringProperty updateType=new SimpleStringProperty();
    StringProperty updateSolver=new SimpleStringProperty();
    StringProperty updateThread=new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream input =new FileInputStream(("./resources/config.properties"));
            Properties prop = new Properties();
            prop.load(input);
            String type=prop.getProperty("mazeGeneratingAlgorithm");
            String solver=prop.getProperty("mazeSearchingAlgorithm");
            String thread=prop.getProperty("threadPoolSize");
            setUpdateSolver(solver);
            setUpdateType(type);
            setUpdateThread(thread);
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no properties file.");
            alert.show();
        }

        mazeType.textProperty().bind(updateType);
        mazeSolver.textProperty().bind(updateSolver);
        numberThreads.textProperty().bind(updateThread);
    }


    public void setUpdateType(String updateType) {
        this.updateType.set(updateType);
    }

    public void setUpdateSolver(String updateSolver) {
        this.updateSolver.set(updateSolver);
    }

    public void setUpdateThread(String updateThread) {
        this.updateThread.set(updateThread);
    }


    public StringProperty updateTypeProperty() {
        return updateType;
    }

    public StringProperty updateSolverProperty() {
        return updateSolver;
    }

    public StringProperty updateThreadProperty() {
        return updateThread;
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel=viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void Save(ActionEvent actionEvent) {
        String type=(String) choiseBox_type.getValue();
        String solve=(String) choiseBox_solve.getValue();
        String num=number.getText();

        if (type==null && solve==null && num==null)
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        if (type!=null)
            myViewModel.setGenerateAlgorithm(type);
        if (solve!=null)
            myViewModel.setSolveAlgorithm(solve);
        if(num!=null)
            myViewModel.setNumberOfThreads(num);
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }
}
