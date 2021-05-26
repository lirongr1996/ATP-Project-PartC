package View;
import algorithms.mazeGenerators.*;

public class MazeData {
    public static Maze maze;

    public void createMaze (int row,int col, String type) throws Exception {
        IMazeGenerator mg;
        if (type.equals("Empty Maze"))
            mg = new EmptyMazeGenerator();
        else if (type.equals("Simple Maze"))
            mg = new SimpleMazeGenerator();
        else
            mg = new MyMazeGenerator();
        maze=mg.generate(row,col);
    }
}
