package Level;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Color;
import java.util.Random;
import java.awt.Image;
import java.util.ArrayList;

import Main.MAIN;

public class CyberSecurity extends JPanel implements Runnable, KeyListener
{
   private int lvl;
   private boolean started;
   public static boolean wait = false;
   public static int some = 10000;
   public static int lives = 5;
   public static int score = 0;
   private boolean right,left;
   public static boolean[][] invaders = new boolean[4][15];
   private boolean win,lose;
   public static int playerX = 150;
   private int cyberblock = 48;
   private int offx = 10;
   private int offy = 10;
   private Image i1,i2,i3;
   private Random r = new Random();
   public static ArrayList<Shot> shots = new ArrayList();
   public static ArrayList<Shot> invadersshots = new ArrayList();
   private MAIN main;
   
   
   public CyberSecurity(int l, MAIN main)
   {
      lvl = l;         
      i1 = getImage("1.png");
      i2 = getImage("2.png");
      i3 = getImage("3.png");
      addKeyListener(this);
      setFocusable(true);
      wait = false;
      some = 10000;
      lives = 5;
      score = 0;
      playerX = 150;
      
      this.main = main;
      
      Thread t = new Thread(this);
         t.start();
   }
   
   public void paint(Graphics g)
   {
      if (!started)
      {
         g.drawImage(getImage("Controls.png"),0,0,this);
      }
      else{
      g.drawImage(getImage("Background"+lvl%3+".png"),0,0,this);
      if(!win&&!lose){
      drawInvaders(g);
      drawPlayer(g);
      drawShots(g);}
      else if(win)
      {
         g.setColor(Color.white);
         g.setFont(g.getFont().deriveFont(48f));
         g.drawString("WINNER",getWidth()/2-100,getHeight()/2);
      }
      else if(lose)
      {
         g.setColor(Color.white);
         g.setFont(g.getFont().deriveFont(48f));
         g.drawString("GAME OVER",getWidth()/2-200,getHeight()/2);
      }
      g.setColor(Color.white);
      g.setFont(g.getFont().deriveFont(28f));
      g.drawString("Lives: "+lives,20,getHeight()-20);
      g.drawString("Score: "+score,getWidth()-500,getHeight()-20);}
   }
   
   public void drawShots(Graphics g)
   {
      for(int x = 0; x<shots.size();x++)
      {
         Shot s = shots.get(x);
         g.drawImage(s.image(),s.getX(),s.getY(),this);
      }
      for(int y = 0; y<invadersshots.size();y++)
      {
         Shot p = invadersshots.get(y);
         g.drawImage(p.image(),p.getX(),p.getY(),this);
      }
   }
   
   public Image image()
   {
      if(lvl%3 == 0)
         return i1;
      if(lvl%3 == 1)
         return i2;
      if(lvl%3 == 2)
         return i3;
      return null;
   }
   
   public Image getImage(String filename)
   {
      try
      {
         return (Image)ImageIO.read(getClass().getResourceAsStream("/Resources/Images/CS/" + filename));
      }catch(Exception e){
         e.printStackTrace();
         return null;
      }
   }
   
   public void drawInvaders(Graphics g)
   {
      for(int x = 0; x < invaders[0].length; x++)
      {
         for(int y = 0; y < invaders.length; y++)
         {
            if(!invaders[y][x])
               g.drawImage(image(),offx+(x*cyberblock),offy+y*cyberblock,this);
         }
      }
   }
   
   public void drawPlayer(Graphics g)
   {
      g.drawImage(getImage("Player.png"),playerX,17*32-50,this);
   }
   
   public void keyReleased(KeyEvent e)
   {
      if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         right = false;
      if(e.getKeyCode() == KeyEvent.VK_LEFT)
         left = false;
      
   }
   public void keyPressed(KeyEvent e)
   {
      if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         right = true;
      if(e.getKeyCode() == KeyEvent.VK_LEFT)
         left = true;
      if(e.getKeyCode() == KeyEvent.VK_SPACE&&!started){
         invaders = new boolean[4][15];
         started = true;}
      if(e.getKeyCode() == KeyEvent.VK_SPACE&&started)
      {
         
         if(shots.size()<5)
            shots.add(new Shot(playerX,false));
      }
   }
   
   public void keyTyped(KeyEvent e){
      }
   
   public void run()
   {
      while(lives>0&&score!=1620)
      {
         try
         {
            
            if(left)
            {
               if(playerX>0)
                  playerX-=10;
               else
                  playerX = 0;
            }
            if(right)
            {
               if(playerX<22*32-16)
                  playerX+=10;
               else
                  playerX = 22*32-16;
            }
            if(some<=0)
            {
               some = 10000;
               int g = r.nextInt(3);
               if(g==0)
                  invadersshots.add(new Shot(playerX+50,true));
                  if (g==2)
                     invadersshots.add(new Shot(playerX-50,true));
                  else
                     invadersshots.add(new Shot(playerX,true));
            }
            if(wait)
            {
               Thread.sleep(1000);
               wait = false;
            }
            some -= (lvl+1)*100;
            Thread.sleep(50);
            repaint();
         }catch(Exception e)
         {
            e.printStackTrace();
         }
      }
      if (score == 1620)
      {
         win = true;
         Level.challenges[2] = true;
      }
      else
      {
         lose = true;
         Level.challenges[2] = false;
      }
      repaint();
      try
      {
         Thread.sleep(2000);
      }catch(Exception e){
         e.printStackTrace();
      }
      main.changePanel(0);
   }
}