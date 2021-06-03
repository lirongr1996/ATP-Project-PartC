package ViewModel;


import Model.IModel;
import Model.MovementDirection;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel game;

    public MyViewModel(IModel game) {
        this.game = game;
        this.game.assignObserver(this);
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

    public void setPositionPlayer(KeyEvent keyEvent)
    {
        MovementDirection d;
        switch (keyEvent.getCode()) {
            case DIGIT1:
                d = MovementDirection.DL;
                break;
            case DIGIT2:
                d = MovementDirection.DOWN;
                break;
            case DIGIT3:
                d = MovementDirection.DR;
                break;
            case DIGIT4:
                d = MovementDirection.LEFT;
                break;
            case DIGIT6:
                d = MovementDirection.RIGHT;
                break;
            case DIGIT7:
                d = MovementDirection.UL;
                break;
            case DIGIT8:
                d = MovementDirection.UP;
                break;
            case DIGIT9:
                d = MovementDirection.UR;
                break;
            case NUMPAD1:
                d = MovementDirection.DL;
                break;
            case NUMPAD2:
                d = MovementDirection.DOWN;
                break;
            case NUMPAD3:
                d = MovementDirection.DR;
                break;
            case NUMPAD4:
                d = MovementDirection.LEFT;
                break;
            case NUMPAD6:
                d = MovementDirection.RIGHT;
                break;
            case NUMPAD7:
                d = MovementDirection.UL;
                break;
            case NUMPAD8:
                d = MovementDirection.UP;
                break;
            case NUMPAD9:
                d = MovementDirection.UR;
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Can't move the character.");
                return;
        }
        game.setPositionPlayer(d);
    }

    public Object getSolution ()
    {
        return game.getSolutionPath();
    }

    public  void setSolution(Object p)
    {
        game.setSolutionPath(p);
    }

    public void solveGame()
    {
        game.SolveGame();
    }

    public void setPositionPlayer(MouseEvent mouseEvent)
    {
        MovementDirection d=MovementDirection.DL;
        int row=((Position)game.getPositionPlayer()).getRowIndex();
        int col=((Position)game.getPositionPlayer()).getColumnIndex();
        int [][]m=((Maze)game.getGame()).getTwoDMaze();
        row+=mouseEvent.getX();
        col+=mouseEvent.getY();

        game.setPositionPlayer(d);
    }

    public void setStratPR(double positionH) {
        game.setRow(positionH);
    }

    public void setstratPC(double positionW) {
        game.setCol(positionW);
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

}
