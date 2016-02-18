package Level.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;

import Main.MAIN;
import Level.*;

public class Player extends Entity{

   public Player(int x, int y, int id) {
      super(x,y,id);
   }
   
   public void up(Level l) {
      
      super.up(l);
      checkForDoor(l);
   }
   
   public void down(Level l) {
   
      super.down(l);
      checkForDoor(l);   
   }
   
   public void left(Level l) {
      
      super.left(l);
      checkForDoor(l);
   }
   
   public void right(Level l) {
   
      super.right(l);
      checkForDoor(l);
   }
   
   public void checkForDoor(Level l) {
      int id = l.imageB[getGridY()][getGridX()].getID();
   
      if(id > 62 && id < 73 && l.playedCS && l.playedTG)
         l.nextLevel();
   }
   
   public int checkForActionSides(Level l) {
      int upID = 0;
      int downID = 0;
      int leftID = 0;
      int rightID = 0;
      
      if(getGridY() != 0)
         upID = l.imageB[getGridY()-1][getGridX()].getID();
      if(getGridY() != l.imageB.length-1)
         downID = l.imageB[getGridY()+1][getGridX()].getID();
      if(getGridX() != 0)
         leftID = l.imageB[getGridY()][getGridX()-1].getID();
      if(getGridX() != l.imageB[0].length-1)
         rightID = l.imageB[getGridY()][getGridX()+1].getID();
         
      for(int i = 1; i < l.entities.length; i++)
         {
         if(getGridY() == l.entities[i].getGridY()-1 && getGridX() == l.entities[i].getGridX())
            return 1;
         if(getGridY() == l.entities[i].getGridY()+1 && getGridX() == l.entities[i].getGridX())
            return 1;
         if(getGridY() == l.entities[i].getGridY() && getGridX() == l.entities[i].getGridX()-1)
            return 1;
         if(getGridY() == l.entities[i].getGridY() && getGridX() == l.entities[i].getGridX()+1)
            return 1;
         }
         
      if(upID == 300 || upID == 301 || upID == 302) { 
         return upID;
      }
      
      if(downID == 300 || downID == 301 || downID == 302) {
         return downID;
      }
      
      if(leftID == 300 || leftID == 301 || leftID == 302) {
         return leftID;
      }
      
      if(rightID == 300 || rightID == 301 || rightID == 302) {
         return rightID;
      }
   return 0;
   }
}