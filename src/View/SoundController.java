package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.util.Observable;
import java.util.Observer;

public class SoundController extends AView {

    public Slider slider_volume;
    public CheckBox checkBox_mute;

    public void MuteVolume(ActionEvent actionEvent) {
        if (checkBox_mute.isSelected())
        {

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
