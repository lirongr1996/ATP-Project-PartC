package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class WinGameController extends AView implements Initializable {
    public MediaView mediaView;
    public ImageView imageView;
    private Media media;
    private MediaPlayer player;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //media = new Media(new File("./resources/Images/MediocreObeseHoverfly-size_restricted.gif").toURI().toString());
            //player = new MediaPlayer(media);
            //player.setAutoPlay(true);
            //mediaView.setMediaPlayer(player);
            Image i = new Image(getClass().getResource("MediocreObeseHoverfly-size_restricted.gif").toString());
            imageView.setImage(i);
        }catch (Exception e)
        {
            e.printStackTrace();
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
