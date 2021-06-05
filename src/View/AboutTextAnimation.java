package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;
import static java.awt.Font.BOLD;

public class AboutTextAnimation extends Canvas {

    private double x=20;


    public void print()
    {
        double y=  getHeight();
        String str="First programmer: Liron Groer"+System.lineSeparator()+
                "Second programmer: Anna Kolkovsky"+System.lineSeparator()+
                ""+System.lineSeparator()+
                "The Generation Algorithms:"+System.lineSeparator()+
                "1. Empty Maze- maze without walls."+System.lineSeparator()+
                "2. Simple Maze- randomly choose where to place the walls."+System.lineSeparator()+
                "                In the end, create a path."+System.lineSeparator()+
                "3. Complex Maze- we use Prim Algorithm to create it."+System.lineSeparator()+
                ""+System.lineSeparator()+
                "The Solving Algorithms:"+System.lineSeparator()+
                "1. Breadth First Search- explores the node branch as far as possible before being forced to backtrack."+System.lineSeparator()+
                "                         The data structure used is a Queue"+System.lineSeparator()+
                "2. Depth First Search- Traverses by exploring as far as possible down each path before going back."+System.lineSeparator()+
                "                       The data structure used is a Stack"+System.lineSeparator()+
                "3. Best First Search- work exactly like the Breadth First Search but use a Priority Queue."+System.lineSeparator()+
                "                      So the one with the lowest distance is the next one that will be taken from the queue.";

      /*  Graphics g2d= getGraphics();
        //g2d.setFill(Color.RED);
        g2d.setColor(Color.RED);

        g2d.setFont(new Font("Bold",BOLD,25));

        //g2d.fillText(str,100,100);
        g2d.drawString(str,x,y);
        try{
            Thread.sleep(100);
            y-=20;
            if (y!=0)
                repaint();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

       */
        //try {
        //    while (y>0)
       //     {
                printString(20,str);
         //       y-=20;
         //       Thread.sleep(100);
          //  }
       // }catch (InterruptedException e) {
       //     e.printStackTrace();
      //  }

    }

    public void printString(double y,String str)
    {
        GraphicsContext g2d=getGraphicsContext2D();
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.setFill(Color.WHITE);
        g2d.fillText(str,x,y);
    }
}
