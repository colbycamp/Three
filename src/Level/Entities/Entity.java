package Level.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;

import Main.MAIN;
import Level.*;

public class Entity extends BackImage{

   private int X,Y;

   public Entity(int x, int y, int id) {
      super(id);
      X = x;
      Y = y;
   }
   
   public int getXLocation() {
   
      return X;
   
   }
   
   public int getYLocation() {
   
      return Y;
   }
   
     public int getGridX() {
   
      return (X/MAIN.block);
   
   }
   
   public int getGridY() {
   
      return (Y/MAIN.block);
   }
   
   public void up(Level l) {
      
      if(l.imageB[(Y/MAIN.block)-1][X/MAIN.block].getID() < 100)
         for(int i = 0; i < l.entities.length; i++)
            {
            if(l.entities[i].getYLocation() == Y-MAIN.block && l.entities[i].getXLocation() == X)
               break;
            if(i == l.entities.length-1)
               Y-= MAIN.block;
            }
   }
   
   public void down(Level l) {
   
      if(l.imageB[(Y/MAIN.block)+1][X/MAIN.block].getID() < 100)
         for(int i = 0; i < l.entities.length; i++)
            {
            if(l.entities[i].getYLocation() == Y+MAIN.block && l.entities[i].getXLocation() == X)
               break;
            if(i == l.entities.length-1)
               Y+= MAIN.block;
            }           
   }
   
   public void left(Level l) {
      
      if(l.imageB[Y/MAIN.block][(X/MAIN.block)-1].getID() < 100)
         for(int i = 0; i < l.entities.length; i++)
            {
            if(l.entities[i].getYLocation() == Y && l.entities[i].getXLocation()+MAIN.block == X)
               break;
            if(i == l.entities.length-1)
               X-= MAIN.block;
            }            
   }
   
   public void right(Level l) {
   
      if(l.imageB[Y/MAIN.block][(X/MAIN.block)+1].getID() < 100)
         for(int i = 0; i < l.entities.length; i++)
            {
            if(l.entities[i].getYLocation() == Y && l.entities[i].getXLocation()-MAIN.block == X)
               break;
            if(i == l.entities.length-1)
               X+= MAIN.block;
            }     
   }
   
   public int checkForActionSides(Level l) {
      return 0;
   }
}