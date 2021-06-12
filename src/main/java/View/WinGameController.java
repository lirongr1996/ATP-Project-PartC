package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class WinGameController extends AView implements Initializable {
    private Media media;
    private MediaPlayer player;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            media = new Media(new File("resources/music/win.mp3").toURI().toString());
            player= new MediaPlayer(media);
            player.setVolume(0.1);
            player.play();
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
