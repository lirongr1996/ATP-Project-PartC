package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class HelpController extends AView implements Initializable {
    public ImageView imageLomus;
    public ImageView imageVoldemort;
    public ImageView imageHarry;

    @Override
    public void setViewModel(MyViewModel viewModel) {
        this.myViewModel=viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResourceAsStream("/images/lomus.jpg"));
        imageLomus.setImage(image);
        Image image1 = new Image(getClass().getResourceAsStream("/images/voldemort.jpg"));
        imageVoldemort.setImage(image1);
        Image image2 = new Image(getClass().getResourceAsStream("/images/harryPotter.jpg"));
        imageHarry.setImage(image2);
    }
}
