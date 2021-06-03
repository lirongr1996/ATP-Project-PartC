package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class HomePageController extends AView implements Initializable {
    public ImageView imageView;
    public AnchorPane anchor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/forest.jpg"));
           // imageView.setFitHeight(0);
            //imageView.setFitWidth(0);

            imageView.fitHeightProperty().bind(anchor.heightProperty());
            imageView.fitWidthProperty().bind(anchor.widthProperty());
            imageView.setImage(image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void start(ActionEvent actionEvent) {
        myViewModel.LoadGame(null);
        try {
            moveFXML("MazeCreator.fxml");
            moveFXML("MyView.fxml");
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("not working");
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
