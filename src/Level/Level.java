package Level;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Thread;
import java.util.Arrays;
import java.awt.Color;

import Main.MAIN;
import Level.Entities.*;

public class Level extends JPanel implements KeyListener, Runnable {

   //2D array for all background images
   public static BackImage[][] imageB = new BackImage[MAIN.height][MAIN.width];
   //1D array for all foreground 
   public static Entity[] entities;
   public static boolean [] challenges = new boolean[3];
   private int block = MAIN.block;
   private int levelID;
   private MAIN main;
   private boolean up, down, left, right, action, quit;
   private boolean loadMiniGame = false;
   private boolean spaceCheck = false;

   public boolean playedCS = false;
   public boolean playedHD = false;
   public boolean playedTG = false;
   
   private String [] choices = {"Cyber Security","Help Desk","Typing Game"};
   private boolean [] way = {true,false,false};
   private int selection = 0;
   
   public Level(int ln, MAIN main) {
      levelID = ln;
      this.main = main;
      loadLevel(ln);
      
      addKeyListener(this);
      setFocusable(true);
      
      Thread thread = new Thread(this);
      thread.start();
   }
   
   public void paint(Graphics g) {
      populatePanel(g);
   }
   
   public void populatePanel(Graphics g) {
   
      //double for-loop draws all of the background images
      for(int x = 0; x < imageB[0].length; x++) {
      
         for(int y = 0; y < imageB.length; y++){
         
            g.drawImage(imageB[y][x].getImage(),x*block,y*block,this);
         }
      }
      //single for-loop draws all of the foreground entities
      for(int i = 0; i < entities.length; i++)
         g.drawImage(entities[i].getImage(),entities[i].getXLocation(),entities[i].getYLocation(),this);
         
      if(loadMiniGame)
      {
         g.setColor(new Color(0, 0, 0, 175));
         g.fillRect(0, 0,  MAIN.totalWidth, MAIN.totalHeight);
         g.setColor(Color.BLACK);
         g.setFont(g.getFont().deriveFont(54f));
      
         for(int i = 0; i < choices.length; i++)
         {
            if(way[i])
               g.setColor(Color.GREEN);
            else
               g.setColor(Color.WHITE);
            g.drawString(choices[i],(MAIN.totalWidth/3),(MAIN.totalHeight/3)+((MAIN.totalHeight/9)*i)+(MAIN.totalHeight/9));
            
         }
         
         g.setColor(Color.BLUE);
         if(playedCS && selection == 0)
         {
            g.drawString("Cant Play That!",(MAIN.totalWidth/3),(MAIN.totalHeight/3)- 60+(MAIN.totalHeight/9));
            spaceCheck = false;
         } 
         if(playedHD && selection == 1)
         {
            g.drawString("Cant Play That!",(MAIN.totalWidth/3),(MAIN.totalHeight/3)- 60+(MAIN.totalHeight/9));
            spaceCheck = false;
         }      
         if(playedTG && selection == 2)
         {
            g.drawString("Cant Play That!",(MAIN.totalWidth/3),(MAIN.totalHeight/3)- 60+(MAIN.totalHeight/9));
            spaceCheck = false;
         }      
      }
      
   }
   
   public void loadLevel(int ln) 
   {
      loadBackground(ln);
      loadEntities(ln);
   }
   
   public void loadBackground(int ln)
   {
      try 
      {
         InputStream in = getClass().getResourceAsStream("/Resources/LevelMaps/" + ln + ".txt");
                           
         BufferedReader info = new BufferedReader(new InputStreamReader(in));
                           
         for(int a = 0; a < MAIN.height; a++) 
         {
            String line = info.readLine();
            String[] tokens = line.split(",");
            
            for(int b = 0; b < MAIN.width; b++) 
            {
               imageB[a][b] = new BackImage(Integer.parseInt(tokens[b]));
            }
         }
      }
      catch(IOException e) {
         System.out.println("****LEVEL " + ln + " NOT FOUND****");
         e.printStackTrace();
      }
   }
   
   public void loadEntities(int ln)
   {
      try{
         InputStream in = getClass().getResourceAsStream("/Resources/Entities/" + ln + ".txt");
         BufferedReader info = new BufferedReader(new InputStreamReader(in));
         //AOE = amount of entities, it is in the file right before the
         //entity data is entered
         //Each new line in the file = a NEW ENTITY
         int AOE = Integer.parseInt(info.readLine());
         entities = new Entity[AOE];
         for(int i = 0; i < AOE; i++) {
            String line = info.readLine();
            String[] tokens = line.split(",");
            if(i == 0)
               entities[i] = new Player(Integer.parseInt(tokens[0])*MAIN.block,Integer.parseInt(tokens[1])*MAIN.block,Integer.parseInt(tokens[2]));
            else
               entities[i] = new Testificate(Integer.parseInt(tokens[0])*MAIN.block,Integer.parseInt(tokens[1])*MAIN.block,Integer.parseInt(tokens[2]),this);
         }
      } 
      catch(IOException e) {
         System.out.println("****LEVEL " + ln + " NOT FOUND****");
         e.printStackTrace();
      }
   }
   
   public void nextLevel() {
      up = down = left = right = action = quit = false;
      if(!Arrays.toString(challenges).contains("true"));
      main.changeLevel(levelID+1);
   }
   
   public void keyTyped(KeyEvent e){}  
   
   public void keyPressed(KeyEvent e)
   {
      if(!loadMiniGame)
      {
         switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
               up = true;
               break;
            case KeyEvent.VK_DOWN:
               down = true;
               break;
            case KeyEvent.VK_LEFT:
               left = true;
               break;
            case KeyEvent.VK_RIGHT:
               right = true;
               break;
            case KeyEvent.VK_ESCAPE:
               quit = true;
               break;
            case KeyEvent.VK_SPACE:
               if(entities[0].checkForActionSides(this) == 1)
                  loadMiniGame = true;
               break;
         }
      }
      else
      {
         way[selection] = false;
         switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
               selection--;
               break;
            case KeyEvent.VK_DOWN:
               selection++;
               break;
            case KeyEvent.VK_LEFT:
               selection--;
               break;
            case KeyEvent.VK_RIGHT:
               selection++;
               break;
            case KeyEvent.VK_SPACE:
               if(selection == 0 && !playedCS)
               {
                  main.changePanel(5);
                  playedCS = true;
               }
               else if(selection == 0 && playedCS)
                  spaceCheck = true;
               
               if(selection == 1 && !playedHD)
               {
                  main.changePanel(4);
                  playedHD = true;
               }
               else if(selection == 1 && playedHD)
                  spaceCheck = true;
               
               if(selection == 2 && !playedTG)
               {
                  main.changePanel(3);
                  playedTG = true;
               }
               
               else if(selection == 2 &&playedTG)
                  spaceCheck = true;
               break;
            case KeyEvent.VK_ESCAPE:
               loadMiniGame = false;
               break;
         }
      }
      
      selection = (selection+3)%3;
      
      way[selection] = true;
      
      repaint();
      
      if(e.getKeyCode() == KeyEvent.VK_SPACE)
      {
         int i = entities[0].checkForActionSides(this); 
         System.out.println(i);
      }
   }
   
   public void keyReleased(KeyEvent e) {
      switch(e.getKeyCode()) {
         case KeyEvent.VK_UP:
            up = false;
            break;
         case KeyEvent.VK_DOWN:
            down = false;
            break;
         case KeyEvent.VK_LEFT:
            left = false;
            break;
         case KeyEvent.VK_RIGHT:
            right = false;
            break;
         case KeyEvent.VK_SPACE:
            action = false;
            break;
      }
   }
   
   public void run() {
      while(true) {
         try {
            if(up && entities[0].getYLocation() > 0) {
               entities[0].up(this);
            }
            if(down && entities[0].getYLocation() < MAIN.totalHeight-MAIN.block) {
               entities[0].down(this);
            }
            if(left && entities[0].getXLocation() > 0) {
               entities[0].left(this);
            }
            if(right && entities[0].getXLocation() < MAIN.totalWidth-MAIN.block) {
               entities[0].right(this);
            }
            if(quit) {
               //save all files
               System.exit(0);
            }
            Thread.sleep(100);
            repaint();
         } 
         catch(InterruptedException e) {
            System.out.println("THREAD IN LEVELS INTERRUPTED");
            e.printStackTrace();
         }
      }
   }
}