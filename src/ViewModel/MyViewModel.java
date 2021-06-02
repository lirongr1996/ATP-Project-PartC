package ViewModel;


import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyEvent;

public class MyViewModel {
    private static MyViewModel instance=null;
    private IModel game;

    private MyViewModel()
    {
        game=new MyModel();
    }

    public static MyViewModel getInstance()
    {
        if (instance==null)
            instance=new MyViewModel();
        return instance;
    }

    public void LoadGame(Object m)
    {
        game.setGame(m);
    }

    public void createGame(int rows,int  cols,String value)
    {
        game.createGame(rows, cols, value);
    }

    public Object getGame()
    {
        return game.getGame();
    }

    public  Object getPositionPlayer(){
        return game.getPositionPlayer();
    }

    public Object setPositionPlayer(KeyEvent keyEvent)
    {
        return game.setPositionPlayer(keyEvent);
    }
}
