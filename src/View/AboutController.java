package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class AboutController extends AView implements Initializable {
public AboutTextAnimation textPrint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textPrint.print();
    }

    @Override
    public void setViewModel(MyViewModel viewModel) {
        myViewModel=viewModel;
        myViewModel.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
