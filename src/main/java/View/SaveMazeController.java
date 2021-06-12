package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

public class SaveMazeController extends AView {

    public TextField textField_name;
    private MyViewModel myViewModel;


    public void SaveFile(ActionEvent actionEvent) {
        String name=textField_name.getText();

        try {
            File f=new File ("./resources/MazeFile");
            File[] files = f.listFiles();
            int i=0;
            for (i=0;i< files.length;i++)
            {
                if (files[i].getName().equals(name))
                {
                    break;
                }
            }
            if (i==files.length)
            {
                FileOutputStream newFile=new FileOutputStream("./resources/MazeFile/"+name);
                ObjectOutputStream outObject = new ObjectOutputStream(newFile);
                outObject.writeObject(myViewModel.getGame());
                InputStream input =new FileInputStream(("./resources/config.properties"));
                Properties prop = new Properties();
                prop.load(input);
                //String type=prop.getProperty("mazeGeneratingAlgorithm");
              //  outObject.writeObject(type);
                outObject.writeObject(myViewModel.getSolution());
                outObject.writeObject(myViewModel.getCharacter());
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This name already exist."+System.lineSeparator()+"Please enter a new name for the file.");
                alert.show();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("   ");
            alert.show();
        }
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel=viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
