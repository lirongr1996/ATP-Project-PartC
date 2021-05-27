package View;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;

public class SaveMazeController {

    public TextField textField_name;


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
                outObject.writeObject(MazeData.maze);
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
}
