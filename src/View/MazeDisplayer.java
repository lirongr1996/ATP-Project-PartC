package View;
import algorithms.mazeGenerators.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    public void drawMaze ()
    {
        maze=MazeData.maze;
        drow();
    }

    private void drow() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRow();
            int cols = maze.getCol();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.RED);

            int [][]m=maze.getTwoDMaze();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(m[i][j] == 1){
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
        }
        else
        {

        }
    }
}
