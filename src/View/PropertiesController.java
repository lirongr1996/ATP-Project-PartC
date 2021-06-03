package View;

import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

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

    StringProperty updateType=new SimpleStringProperty();
    StringProperty updateSolver=new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream input =new FileInputStream(("./resources/config.properties"));
            Properties prop = new Properties();
            prop.load(input);
            String type=prop.getProperty("mazeGeneratingAlgorithm");
            String solver=prop.getProperty("mazeSearchingAlgorithm");
            setUpdateSolver(solver);
            setUpdateType(type);
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no properties file.");
            alert.show();
        }


        mazeType.textProperty().bind(updateType);
        mazeSolver.textProperty().bind(updateSolver);
    }


    public void setUpdateType(String updateType) {
        this.updateType.set(updateType);
    }

    public void setUpdateSolver(String updateSolver) {
        this.updateSolver.set(updateSolver);
    }


    public StringProperty updateTypeProperty() {
        return updateType;
    }

    public StringProperty updateSolverProperty() {
        return updateSolver;
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel=viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
