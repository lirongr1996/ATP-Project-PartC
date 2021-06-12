package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoadMazeController extends AView implements Initializable {
    public TreeView treeView_files;
    private MyViewModel myViewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> rootItem = new TreeItem<String> ("MazeFiles");
        rootItem.setExpanded(true);
        File f=new File ("./resources/MazeFile");
        File[] files = f.listFiles();
        for (int i=0;i<files.length;i++)
        {
            if (files[i].getName().equals("program.txt"))
                continue;
            rootItem.getChildren().add(new TreeItem<String>(files[i].getName()));
        }
        treeView_files.setRoot(rootItem);
    }

    public void OpenFile(ActionEvent actionEvent) {
        try {
            TreeItem<String> select= (TreeItem<String>) treeView_files.getSelectionModel().getSelectedItem();

            if (select==null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please choose a file.");
                alert.show();
            }
            else
            {
                File f=new File ("./resources/MazeFile");
                File[] files = f.listFiles();
                int i=0;
                for (i=0;i<files.length;i++)
                {
                    if (files[i].getName().equals(select.getValue()))
                        break;
                }
                ObjectInputStream intObject=new ObjectInputStream(new FileInputStream(files[i]));
                myViewModel.LoadGame(intObject.readObject());
                myViewModel.setSolution(intObject.readObject());
                myViewModel.setCharacter((String) intObject.readObject());

/*
                OutputStream output = new FileOutputStream("./resources/config.properties");
                Properties prop = new Properties();
                prop.setProperty("threadPoolSize", "3");
                prop.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
                prop.setProperty("mazeGeneratingAlgorithm", type);
                prop.store(output, null);
*/
                ((Node)actionEvent.getSource()).getScene().getWindow().hide();
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("   ");
            alert.show();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        myViewModel=viewModel;
    }
}
