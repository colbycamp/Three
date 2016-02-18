package Main;

import javax.swing.JFrame;
import java.awt.Dimension;
import Level.*;
import Main.Menu.*;
import Main.Intro.*;

public class MAIN extends JFrame
{
   public final static int block = 32;
   public final static int width = 22;
   public final static int height = 17;
   public final static int totalWidth = width*block;
   public final static int totalHeight = height*block;
   public static int currentLevel = 1;
   private Level level = new Level(currentLevel,this);
   private Menu menu;

   public MAIN()
   {
      super("3");
      // setIconImage();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //add(new Intro(this));
      add(new Intro(this));
      getContentPane().setPreferredSize(new Dimension(totalWidth, totalHeight));
      setResizable(true);
      pack();
      setVisible(true);
   }

   public void changePanel(int choice)
   {
      getContentPane().removeAll();
      switch(choice)
      {
         case -1:
            menu = new Menu(this);
            getContentPane().add(menu);
            getRootPane().revalidate();
            repaint();
            menu.requestFocusInWindow();
            break;
         case 0:
            getContentPane().add(level);
            getRootPane().revalidate();
            repaint();
            level.requestFocusInWindow();
            break;
         case 1:
            //Credits if Game is completed
         case 2:
            //Load Game
            break;
         case 3:
            TypingGame tg = new TypingGame(currentLevel,this);
            getContentPane().add(tg);
            getRootPane().revalidate();
            repaint();
            tg.requestFocusInWindow();
            break;
         case 4:
            HelpDesk hd = new HelpDesk(currentLevel,this);
            getContentPane().add(hd);
            getRootPane().revalidate();
            repaint();
            hd.requestFocusInWindow();
            break;
         case 5:
            CyberSecurity cs = new CyberSecurity(currentLevel, this);
            getContentPane().add(cs);
            getRootPane().revalidate();
            repaint();
            cs.requestFocusInWindow();
            break;
      }
      getRootPane().revalidate();
   }

   public void changeLevel(int next)
   {
      currentLevel++;
      if(currentLevel < 7)
      {
         getContentPane().removeAll();
         level = new Level(currentLevel,this);
         getContentPane().add(level);
         getRootPane().revalidate();
         repaint();
         level.requestFocusInWindow();
      }
      else
      {
         //loads credits
      }
   }

   public static void main(String[]args)
   {
      new MAIN();
   }

}