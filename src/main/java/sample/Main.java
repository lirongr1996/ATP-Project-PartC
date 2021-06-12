package sample;

import Model.IModel;
import Model.MyModel;
import View.HomePageController;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getClassLoader().getResource("View/MyView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Maze Game");
        primaryStage.setScene(new Scene(root, 700, 440));


        IModel model=new MyModel();
        MyViewModel viewModel=new MyViewModel(model);
        MyViewController view=fxmlLoader.getController();
        view.setViewModel(viewModel);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
