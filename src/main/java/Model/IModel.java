package Model;

import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Observer;

public interface IModel {
    public Object getGame();
    public void setGame(Object m);
    public void createGame(int row,int col, String c);
    public Object getPositionPlayer();
    public  void setPositionPlayer(MovementDirection direction);
    public Object getSolutionPath ();
    public void SolveGame();
    public void setSolutionPath(Object p);
    public void assignObserver(Observer o);
    public double[] getGoalPosition();
    public String getCharacter();
    public void setCharacter(String c);

    void setRow(double[] positionH);
    void setCol(double [] positionW);
    public double []getX();
    public int[] getWall();
    public void setGenerateAlgorithm(String generate);
    public void setSolveAlgorithm(String solve);
    public void setNumberOfThreads(String number);
}
