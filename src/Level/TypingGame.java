package Level;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.awt.event.*;

import Main.MAIN;

public class TypingGame extends JPanel implements KeyListener
{
   private String[] sentences;
   private int sentenceCount = 8;
   private int amountOfsentences = 0;
   private Image background;
   private Font font;
   private int blackboardX = 250;
   private int blackboardY = 180;
   private int difficulty;
   private String currentSentence = "";
   private int points = 0;
   private boolean bonus = false;
   private boolean spam = false;
   private Thread animationThread;
   private Thread wordThread;
   private boolean started = false;
   private boolean gameOver = false;
   private Image typingInstructions;
   private MAIN main;
   
   public TypingGame(int currentLevel, MAIN main)
   {
      addKeyListener(this);
      setFocusable(true);
      this.main = main;
      
      populateFields();
      getWords();
      calculateDifficulty(currentLevel);
      startWordThread();
   }
   
   public void paint(Graphics g)
   {
      g.setFont(font);
      g.setColor(Color.WHITE);
      drawBackground(g);
      if(gameOver == false)
      {
         drawWords(g);
      }
      else
      {
         if(amountOfsentences > 3)
            g.drawString("Winner",blackboardX-30,blackboardY);
         else
            g.drawString("Loser",blackboardX-30,blackboardY);
         main.changePanel(0);
      }
   }
   
   private void populateFields()
   {
      try {
         InputStream in = getClass().getResourceAsStream("/Resources/Images/TG/Originals.txt");
         BufferedReader info = new BufferedReader(new InputStreamReader(in));
         
         sentences = new String[Integer.parseInt(info.readLine())];
         
         for(int a = 0; a < sentences.length; a++)
            sentences[a] = info.readLine();
      } 
      catch(IOException e) {
         e.printStackTrace();
      }
      
      try {
         background = (Image)ImageIO.read(getClass().getResourceAsStream("/Resources/Images/TG/TypingBackground.png"));
      }
      catch(IOException e){
         e.printStackTrace();
      }
      
      try {
         typingInstructions = (Image)ImageIO.read(getClass().getResourceAsStream("/Resources/Images/TG/TypingInstructions.png"));
      }
      catch(IOException e){
         e.printStackTrace();
      }
      
      try {
         font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resources/Images/TG/SqueakyChalkSound.ttf"));
         font = font.deriveFont(20f);
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }
   
   private void drawBackground(Graphics g)
   {
         if (!started)
            g.drawImage(typingInstructions,0,0,this);
         else
            g.drawImage(background,0,0,this);
   }
   
   private void drawWords(Graphics g)
   {  
      
      String [] full = currentSentence.split(" ");
      int count = 0;
      String firstLine = "";
      String secondLine = "";
      
      for(int i = 0; i < full.length; i++)
      {
         count += full[i].length()+1;
         if(count < 33)
            firstLine += full[i] + " ";
         else if(count < 60)
            secondLine += full[i] + " "; 
      }
      g.drawString(firstLine,blackboardX-30,blackboardY);
      g.drawString(secondLine,blackboardX-30,blackboardY+65);
      System.out.println(points);
      g.drawString(Integer.toString(points),blackboardX, 50);
   }
   
   private void calculateDifficulty(int currentLV)
   {
      difficulty = Math.abs(currentLV-7);
   }
   
   private void getWords()
   {
      Random r = new Random();
      int a = r.nextInt(sentences.length);
      currentSentence = sentences[a];
      repaint();
   }
   
   private void calculatePoint(boolean correctLetter, boolean endOfTime) {
      
      if(currentSentence.length() == 0 && bonus == true) {
      
         points += Math.abs(difficulty-7)+1;
         amountOfsentences++;
         bonus = false;
      }
      
      else if(correctLetter)
      {
         points += Math.abs(difficulty-7)+1;
      }
      
      else if(currentSentence.length() != 0 && !correctLetter)
         points -= ((Math.abs(difficulty-7)/4)+1);
         
      else if(spam == true) {
         points -= ((Math.abs(difficulty-7)/4)+1);
         spam = true;
      }
         
      if(endOfTime)
         points -= ((Math.abs(difficulty-7)/4)+1)*(currentSentence.length()/4);
   }
      
   public void keyTyped(KeyEvent e) {}
   
   public void keyPressed(KeyEvent e) {
   
      if(e.getKeyCode() == KeyEvent.VK_SPACE)
         started = true;
   }
   
   public void keyReleased(KeyEvent e) {
   
      if(currentSentence.length() > 0 && e.getKeyChar() == currentSentence.charAt(0))
      {
         currentSentence = currentSentence.substring(1);
         calculatePoint(true, false);
      }
      else
         calculatePoint(false, false);
      repaint();
   }
   
   public void startWordThread()
   {
      wordThread = new Thread(){
         public void run()
         {
            while(true && gameOver == false)
            {
               try
               {
                  bonus = true;
                  if(difficulty > 3)
                     wordThread.sleep((difficulty*5)*1000);
                  else
                     wordThread.sleep((difficulty*6)*1000);
                  sentenceCount--;
                  bonus = false;
                  spam = false;
                  calculatePoint(false,true);
                  if(sentenceCount == 0)
                  {
                     repaint();
                     gameOver = true;
                  }
                  getWords();
               }
               catch(Exception e){
                  e.printStackTrace();
               }
            }
         }
      };
      wordThread.start();
   }
}