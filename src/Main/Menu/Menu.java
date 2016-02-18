package Main.Menu;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Image;
import javax.imageio.ImageIO;

import Main.MAIN;

public class Menu extends JPanel implements KeyListener
{
   private MAIN main;
   private String [] choices = {"New Game","Credits","Load Game"};
   private boolean [] way = {true,false,false};
   private int selection = 0;
   private Image bg;

   public Menu(MAIN main) {
      this.main = main;
      addKeyListener(this);
      setFocusable(true);
      try 
      {
         bg = (Image)ImageIO.read(getClass().getResourceAsStream("/Resources/MMBACK.png"));
      
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   
   public void paint(Graphics g) {
   
      g.drawImage(bg, 0, 0, MAIN.totalWidth, MAIN.totalHeight, this);
      g.setFont(g.getFont().deriveFont(54f));
   
      for(int i = 0; i < choices.length; i++)
      {
         if(way[i])
            g.setColor(Color.GREEN);
         else
            g.setColor(Color.WHITE);
         g.drawString(choices[i],(MAIN.totalWidth/3),(MAIN.totalHeight/3)+((MAIN.totalHeight/9)*i)+(MAIN.totalHeight/9));
      }
   }
   
   public void keyTyped(KeyEvent e) {}
   
   public void keyPressed(KeyEvent e) {
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
            main.changePanel(0);
            break;
         case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;
      }
      
      selection = (selection+3)%3;
      
      way[selection] = true;
      
      repaint();
   }
   
   public void keyReleased(KeyEvent e) {}
}