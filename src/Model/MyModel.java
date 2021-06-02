package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MyModel implements IModel{
    private Maze maze;
    private Position player;

    public void createGame(int row,int col, String type) {
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
        CommunicateWithServer_MazeGenerating(row,col);
        mazeGeneratingServer.stop();
    }

    @Override
    public Object getPositionPlayer() {
        return player;
    }

    @Override
    public Object setPositionPlayer(KeyEvent keyEvent) {
        int row=player.getRowIndex();
        int col=player.getColumnIndex();
        int [][]m=maze.getTwoDMaze();
        try {
            switch (keyEvent.getCode()) {
                case DIGIT1:
                    row+=1;
                    col-=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT2:
                    row+=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT3:
                    row+=1;
                    col+=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT4:
                    col-=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT6:
                    col+=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT7:
                    row-=1;
                    col-=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT8:
                    row-=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                case DIGIT9:
                    row-=1;
                    col+=1;
                    if (checkBound(row,col)&& !checkWall(row,col))
                        player=new Position(row,col);
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Can't move the character.");
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can't move the character.");
        }

        return player;
    }

    private boolean checkBound(int row,int col)
    {
        if (row==-1||col==-1||row==maze.getRow()|| col==maze.getCol())
        {

            return false;
        }
        return true;
    }

    private boolean checkWall(int row,int col)
    {
        if (maze.getTwoDMaze()[row][col]==1)
        {

            return true;
        }
        return false;
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

    @Override
    public Object getGame() {
        return maze;
    }

    @Override
    public void setGame(Object m) {
        this.maze=(Maze) m;
        if (m!=null)
            this.player=maze.getStartPosition();
    }
}
