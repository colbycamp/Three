package Level;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.Image;

public class Shot implements Runnable
{
   int xloc;
   int yloc;
   Image img;
   boolean hit;
   boolean invader,invaderhit;
   public Shot(int x,boolean s)
   {
      xloc = x;
      invader = s;
      if(!invader)
         yloc = 17*32-50-28;
      else
         yloc = 0;
      img = getImage("/Resources/Images/CS/Shot.png");
      Thread t = new Thread(this);
         t.start();
   }
   public Image image()
   {
      return img;
   }
   public int getX()
   {
      return xloc;
   }
   public int getY()
   {
      return yloc+40;
   }
   public Image getImage(String filename)
   {
      try
      {
         return (Image)ImageIO.read(getClass().getResourceAsStream(filename));
      }catch(Exception e){
         e.printStackTrace();
         return null;
      }
   }
   public void processHit()
   {
      if(yloc/27<4)
      {
         if(!CyberSecurity.invaders[yloc/28][xloc/47])
         {
            hit = true;
            CyberSecurity.score+=27;
            CyberSecurity.invaders[yloc/28][xloc/47] = true;
            CyberSecurity.shots.remove(this);
         }
      }
      if(yloc<0)
      {
         hit = true;
         CyberSecurity.shots.remove(this);
      }
   }
   public void processInvaderHit()
   {
      if(yloc/27>16)
      {  
         
         if(xloc >= CyberSecurity.playerX && xloc <= CyberSecurity.playerX+16)
         {
            CyberSecurity.lives--;
            CyberSecurity.some = 10000;
            CyberSecurity.wait=true;
            CyberSecurity.invadersshots = new ArrayList();
            CyberSecurity.playerX = 150;
         }
         invaderhit = true;
         
         CyberSecurity.invadersshots.remove(this);
      }
   }
   public void run()
   {
      while(true)
      {
         try{
            if(invader&&!invaderhit)
            {
               yloc+=30;
               processInvaderHit();
            }
            if(!invader&&!hit)
            {
               yloc-=20;
               processHit();
            }
            Thread.sleep(100);
         }catch(Exception e){
               e.printStackTrace();
         }
      }
   }
}