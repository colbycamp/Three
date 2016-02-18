package Level.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.Random;

import Main.MAIN;
import Level.*;

public class Testificate extends Entity implements Runnable
{
   public Level l;
   public static int lineNum;
   
   public Testificate(int x, int y, int id, Level l) 
   {
      super(x,y,id);
      Thread th = new Thread(this);
      th.start();
   }
   
   //TESTIFICATE DOES NOT MOVE
   
   public void move(int direction) 
   {
      switch(direction) 
      {
         case 0:
            up(l);
            break;
         case 1:
            down(l);
            break;
         case 2:
            left(l);
            break;
         case 3:
            right(l);
            break;
      }
   }
   
   public void moveBack(int direction) 
   {
      switch(direction) 
      {
         case 0:
            down(l);
            break;
         case 1:
            up(l);
            break;
         case 2:
            right(l);
            break;
         case 3:
            left(l);
            break;
      }
   }
      
   public void run() 
   {
      while(true) 
      {
         try 
         {
            Random r = new Random();
            int time = r.nextInt(5000)+1000;
            int direction = r.nextInt(4);
            Thread.sleep(time);
            move(direction);
            time = r.nextInt(5000)+1000;
            Thread.sleep(time);
            moveBack(direction);
         } 
         catch(InterruptedException e) 
         {
            System.out.println("SOMETHING MESSED UP.");
            e.printStackTrace();
         }
      }
   }
}