package View;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
public AboutTextAnimation textPrint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textPrint.print();
    }
}
