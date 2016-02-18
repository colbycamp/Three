package Level;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;

import Main.MAIN;
import Level.Entities.*;

/*need to add question thing!!!!!!!!!!!!!!!
 /if the question is right have it count for something
 /Basically need to make method that counts and one that decides if they win
 /when displaying question make sure to say where it comes from
*/
public class HelpDesk extends Level 
{
   private int block = MAIN.block;
   private int levelID;
   private MAIN main;
   private boolean up, down, left, right, action, quit;
   private ArrayList<Entity> line = new ArrayList<Entity>();
   private ArrayList<Questions> qandas = new ArrayList<Questions>();
   private int rightQuestions = 0;
   private int totalQuestions = 0;
   private boolean nextQuestion = true;
   private ArrayList<String> c = new ArrayList<String>();
   private Questions current;
   private boolean gameOver = false;
      
   public HelpDesk(int ln, MAIN main) 
   {
      super(ln+100, main);
      loadQuestions(ln);
   }
   
   public void paint(Graphics g)
   {  
      super.paint(g);
      if(gameOver == false)
      {
         drawQuestions(g);
      }
      else
      {
         if(rightQuestions > 8)
            g.drawString("Winner",14*MAIN.block,10*MAIN.block);
         else
            g.drawString("Loser",14*MAIN.block,10*MAIN.block);
         main.changePanel(0);
      }
   }
   
   private void drawQuestions(Graphics g)
   { 
      Font f = new Font("IrisUPC",Font.BOLD,30);
      g.setColor(Color.blue);
      g.setFont(f);
         if(nextQuestion)
         {
            Random r = new Random();
            current = qandas.get(r.nextInt(qandas.size()));
         }
         
         int resize = 100;
         if(current.question.length() > 16)
         {
            g.drawString(current.question.substring(0,25),14*MAIN.block,10*MAIN.block);
            if(current.question.length() > 25)
               g.drawString(current.question.substring(25,50),14*MAIN.block,11*MAIN.block);
            if(current.question.length() > 50)
               g.drawString(current.question.substring(50,current.question.length()),14*MAIN.block,12*MAIN.block);
            else
               g.drawString(current.question.substring(36,current.question.length()),14*MAIN.block,11*MAIN.block);
         }
         else
            g.drawString(current.question,14*MAIN.block,10*MAIN.block);
         
         f.deriveFont(20);
         g.setFont(f);
         
         if(nextQuestion)
         {
            for(int i = 0; i < c.size(); i++)
               c.remove(i);
            c = current.randomAnswer();
         }
      
         for(int a = 0; a < c.size(); a++)
            g.drawString(a + ": " + c.get(a),14*MAIN.block,((10*MAIN.block)+(resize + (a*35))));
      nextQuestion = false;
   }
   
   public void loadLevel(int ln) {
      super.loadLevel(ln);
      
   }
   
   public void loadEntities(int ln) {
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
               entities[i] = new SmartTestificate(Integer.parseInt(tokens[0])*MAIN.block,Integer.parseInt(tokens[1])*MAIN.block,Integer.parseInt(tokens[2]),this);
         }
      }
      catch(IOException e) {
         System.out.println("****LEVEL " + ln + " NOT FOUND****");
         e.printStackTrace();
      }
   }
   //delete entry = true than it will delete the smartTestificate
   //entitiy in the array
   //if entry = false than it will add entity
   public void upDateLine(boolean deleteEntry,SmartTestificate t)
   {
      if(deleteEntry)
         line.remove(0);
      else
         line.add(t);
   }
   
   public void loadQuestions(int ln)
   {
      try{
         InputStream a = getClass().getResourceAsStream("/Resources/Images/HD/21 Questions.txt");
         BufferedReader b = new BufferedReader(new InputStreamReader(a));
         //AOQ = amount of questions, it is in the file right before the
         //entity data is entered
         //Each new line in the file = a NEW ENTITY
         int AOQ = Integer.parseInt(b.readLine());
         for(int i = 0; i < AOQ; i++) {
            String line = b.readLine();
            String [] tokens = line.split(",");
            qandas.add(new Questions(tokens[0],tokens[1],tokens[2],tokens[3],tokens[4]));
         }
      }
      catch(IOException e) {
         System.out.println("****LEVEL " + ln + " NOT FOUND****");
         e.printStackTrace();
      }
   }
   
   public int getPlaceInLine(SmartTestificate t)
   {
      if(line.size() > entities.length)
         line.remove(line.size()-1);
      return line.indexOf(t);
   }
   
   public int getDoorX(int i)
   {
      switch(i)
      {
         case 1:
            return 4;
         case 2:
            return 7;
         case 3:
            return 10;
         case 4:      
            return 13;
         default:
            return 4;
      }
   }
   
   public void removeMe(SmartTestificate t)
   {
      for(int i = 0; i < entities.length; i++)
         if(entities[i] == t)
            entities[i] = null;
   }
   
   public void keyTyped(KeyEvent e)
   {
      if(e.getKeyCode() == KeyEvent.VK_0)
         {
         if(0 == c.indexOf(current.answerFour))
            rightQuestions++;
         qandas.remove(current);
         }
      if(e.getKeyCode() == KeyEvent.VK_1)
         {
         if(1 == c.indexOf(current.answerFour))
            rightQuestions++;
         qandas.remove(current);
         }
      if(e.getKeyCode() == KeyEvent.VK_2)
         {
         if(2 == c.indexOf(current.answerFour))
            rightQuestions++;
         qandas.remove(current);
         }
      if(e.getKeyCode() == KeyEvent.VK_3)
         {
         if(3 == c.indexOf(current.answerFour))
            rightQuestions++;
         qandas.remove(current);
         }
      totalQuestions++;
      nextQuestion = true;
      gameOver = true;
      repaint();
   }
   
   private class Questions
   {
      public String question = "";
      public String answerOne = "";
      public String answerTwo = "";
      public String answerThree = "";
      public String answerFour = "";
      private ArrayList<String> list = new ArrayList<String>();
      
      public Questions(String q,String a1,String a2,String a3, String a4)
      {
         question = q;
         answerOne = a1;
         answerTwo = a2;
         answerThree = a3;
         answerFour = a4;
         list.add(answerOne);
         list.add(answerTwo);
         list.add(answerThree);
         list.add(answerFour);
      }
      
      public ArrayList<String> randomAnswer()
      {
         ArrayList<String> random = new ArrayList<String>();
         while(random.size() < 4)
         {
            Random r = new Random();
            random.add(list.get(r.nextInt(3)));
         }
         return random;
      }
   }
}