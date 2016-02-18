package Level.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.Random;

import Main.MAIN;
import Level.*;

public class SmartTestificate extends Testificate
{      
   private HelpDesk hd;
   //0 = not waiting, 1 = waiting, 2 = leaving
   private int waiting = 0;
   //-1 = still has questions #>1 go to that door!!!!!
   private int answeredQuestion = -1;
   
   public SmartTestificate(int x, int y, int id, HelpDesk hd) 
   {
      super(x,y,id,hd);
      this.hd = hd;
      Thread th = new Thread(this);
      th.start();
   }
   
   public void goTo(int x, int y)
   {
      int tempX = getGridX();
      int tempY = getGridY();
      try 
      {
         if(waiting == 1)
         {
            if(tempY > y)
            {
               while (tempY != y)
               {
                  up(l);
                  Thread.sleep(100);
                  tempY = getGridY();
               }
            }
            else if(tempY < y)
            {
               while (tempY != y)
               {
                  down(l);
                  Thread.sleep(100);
                  tempY = getGridY();
               }
            }
         }
         else if(waiting == 2) {
            if(tempX > x)
            {
               while (tempX != x)
               {
                  left(l);
                  Thread.sleep(100);
                  tempX = getGridX();
               }
            }
            else if(tempX < x)
            {
               while (tempX != x)
               {
                  right(l);
                  Thread.sleep(100);
                  tempX = getGridX();
               }
            }
          }
      } 
      catch(InterruptedException e) 
      {
         System.out.println("SOMETHING MESSED UP.");
         e.printStackTrace();
      }
   }
      
   public void run() 
   {
      while(true) 
      {
         try 
         {
            if(waiting == 0) {
            
            Random r = new Random();
            int time = r.nextInt(5000)+1000;
            int direction = r.nextInt(4);
            Thread.sleep(time);
            move(direction);
            time = r.nextInt(5000)+1000;
            Thread.sleep(time);
            moveBack(direction);
            
            Random togWalk = new Random();
            
            if(togWalk.nextInt(15) == 5) {
               hd.upDateLine(false,this);
               waiting = 1;
               }
            }
            
            else if(waiting == 1)
            {
               Thread.sleep(500);
               goTo(8,4+hd.getPlaceInLine(this));
               if(answeredQuestion > 0)
                  waiting = 3;
            }
            
            else {
               Thread.sleep(500);
               goTo(hd.getDoorX(answeredQuestion),MAIN.height);
               hd.removeMe(this);
            }
            
         } 
         catch(InterruptedException e) 
         {
            System.out.println("SOMETHING MESSED UP.");
            e.printStackTrace();
         }
      }
   }
}