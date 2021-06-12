package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer {
    protected static MyViewModel myViewModel;

    public void moveFXML (String name) throws IOException {
       FXMLLoader fxmlLoader=new FXMLLoader(getClass().getClassLoader().getResource("View/"+name));
       Parent root = fxmlLoader.load();
       Scene homeS=new Scene(root);
       Stage stage=new Stage();
       stage.hide();
       stage.setScene(homeS);
       AView newView=fxmlLoader.getController();
       newView.setViewModel(myViewModel);
       stage.showAndWait();
    }
}
