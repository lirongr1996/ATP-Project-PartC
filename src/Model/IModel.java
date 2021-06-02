package Model;

import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyEvent;

public interface IModel {
    public Object getGame();
    public void setGame(Object m);
    public void createGame(int row,int col, String type);
    public Object getPositionPlayer();
    public  Object setPositionPlayer(KeyEvent keyEvent);
}
