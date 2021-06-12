package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private Position player;
    private ArrayList<AState> mazeSolutionSteps;
    private double [] x;
    private double [] y;
    private int[]wall;
    private final Logger LOG = LogManager.getLogger();
    private String character;

    public MyModel() {
        try {
            OutputStream output = new FileOutputStream("./resources/config.properties");
            Properties prop = new Properties();
            prop.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
            prop.setProperty("threadPoolSize", "3");
            prop.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
            prop.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGenerateAlgorithm(String generate)
    {
        try {
            InputStream input =new FileInputStream(("./resources/config.properties"));
            Properties prop = new Properties();
            prop.load(input);
            OutputStream output = new FileOutputStream("./resources/config.properties");
            Properties newProp = new Properties();
            if (generate.equals("Empty Maze"))
                newProp.setProperty("mazeGeneratingAlgorithm", "EmptyMazeGenerator");
            if (generate.equals("Simple Maze"))
                newProp.setProperty("mazeGeneratingAlgorithm", "SimpleMazeGenerator");
            if (generate.equals("Complex Maze"))
                newProp.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
            newProp.setProperty("threadPoolSize", prop.getProperty("threadPoolSize"));
            newProp.setProperty("mazeSearchingAlgorithm", prop.getProperty("mazeSearchingAlgorithm"));
            newProp.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSolveAlgorithm(String solve)
    {
        try {
            InputStream input =new FileInputStream(("./resources/config.properties"));
            Properties prop = new Properties();
            prop.load(input);
            OutputStream output = new FileOutputStream("./resources/config.properties");
            Properties newProp = new Properties();
            newProp.setProperty("mazeGeneratingAlgorithm", prop.getProperty("mazeGeneratingAlgorithm"));
            newProp.setProperty("threadPoolSize", prop.getProperty("mazeSearchingAlgorithm"));
            if (solve.equals("Breadth First Search"))
                newProp.setProperty("mazeSearchingAlgorithm", "BreadthFirstSearch");
            if (solve.equals("Depth First Search"))
                newProp.setProperty("mazeSearchingAlgorithm", "DepthFirstSearch");
            if (solve.equals("Best First Search"))
                newProp.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
            newProp.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNumberOfThreads(String number)
    {
        try {
            InputStream input =new FileInputStream(("./resources/config.properties"));
            Properties prop = new Properties();
            prop.load(input);
            OutputStream output = new FileOutputStream("./resources/config.properties");
            Properties newProp = new Properties();
            newProp.setProperty("mazeGeneratingAlgorithm", prop.getProperty("mazeGeneratingAlgorithm"));
            newProp.setProperty("threadPoolSize", number);
            newProp.setProperty("mazeSearchingAlgorithm", prop.getProperty("mazeSearchingAlgorithm"));
            newProp.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double [] getX() {
        return x;
    }

    @Override
    public int[] getWall() {
        return wall;
    }

    public void createGame(int row, int col, String c) {
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        CommunicateWithServer_MazeGenerating(row,col);
        mazeGeneratingServer.stop();
        if (c==null)
            this.character="Harry Potter";
        else
            this.character=c;
        setChanged();
        notifyObservers("game created");
    }

    @Override
    public Object getPositionPlayer() {
        return player;
    }

    @Override
    public void setPositionPlayer(MovementDirection direction) {
        int row=player.getRowIndex();
        int col=player.getColumnIndex();
        int [][]m=maze.getTwoDMaze();
        try {
            switch (direction) {
                case DL:
                    row+=1;
                    col-=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case DOWN:
                    row+=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case DR:
                    row+=1;
                    col+=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case LEFT:
                    col-=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case RIGHT:
                    col+=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case UL:
                    row-=1;
                    col-=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case UP:
                    row-=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
                case UR:
                    row-=1;
                    col+=1;
                    if (checkBound(row,col)) {
                        player = new Position(row, col);
                        setChanged();
                        if (row==maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
                            notifyObservers("win");
                        else
                            notifyObservers("player move");
                    }
                    break;
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can't move the character.");
        }
    }

    @Override
    public Object getSolutionPath() {
        return mazeSolutionSteps;
    }

    @Override
    public void SolveGame() {
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        CommunicateWithServer_SolveSearchProblem();
        solveSearchProblemServer.stop();


        setChanged();
        notifyObservers("solve game");
    }

    @Override
    public void setSolutionPath(Object p) {
        mazeSolutionSteps=(ArrayList<AState>)p;
    }

    private boolean checkBound(int row,int col)
    {
        if (row==-1||col==-1||row==maze.getRow()|| col==maze.getCol())
        {
            return false;
        }
        if (maze.getTwoDMaze()[row][col]==1)
        {
            wall=new int[2];
            wall[0]=row;
            wall[1]=col;
            setChanged();
            notifyObservers("wall");
            return false;
        }
        return true;
    }



    private void CommunicateWithServer_MazeGenerating(int row,int col) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*col+24 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze
                        maze = new Maze(decompressedMaze);
                        player=maze.getStartPosition();
                        maze.getTwoDMaze()[player.getRowIndex()][player.getColumnIndex()]=0;
                        InputStream input =new FileInputStream(("./resources/config.properties"));
                        Properties prop = new Properties();
                        prop.load(input);
                        LOG.info("The client "+InetAddress.getLocalHost()+" ask for maze size ["+row+"X"+col+"]. The maze generator is "+prop.getProperty("mazeGeneratingAlgorithm"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        mazeSolutionSteps = mazeSolution.getSolutionPath();
                        InputStream input =new FileInputStream(("./resources/config.properties"));
                        Properties prop = new Properties();
                        prop.load(input);
                        LOG.info("The client "+InetAddress.getLocalHost()+" ask for solver. The searching algorithm is "+prop.getProperty("mazeSearchingAlgorithm")+". The solution have "+mazeSolutionSteps.size()+" steps.");
                    } catch (Exception e) { e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
    }

    @Override
    public Object getGame() {
        return maze;
    }

    @Override
    public void setGame(Object m) {
        this.maze=(Maze) m;
        if (m!=null) {
            this.player = maze.getStartPosition();
        }
    }


    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public double[] getGoalPosition() {
        return y;
    }

    @Override
    public String getCharacter() {
        return character;
    }

    @Override
    public void setCharacter(String c) {
        character=c;
        setChanged();
        notifyObservers("game loaded");
    }

    @Override
    public void setRow(double [] position) {
        x=position;
    }

    @Override
    public void setCol(double [] positionW) {
        y=positionW;
    }
}
