package Level;

import java.awt.Image;
import javax.imageio.ImageIO;

public class BackImage {

   private Image IMAGE;
   private int ID;

   public BackImage(int id) {
      ID = id;   
      loadImage();
   }
   
   private void loadImage()  {
      try {
      // Images made by paint are .png we can change later
         IMAGE = (Image)ImageIO.read(getClass().getResourceAsStream("/Resources/Images/" + ID + ".png"));
      
      } 
      catch(Exception e) {
      
         e.printStackTrace();
         
      }
   }
   
   public Image getImage() {
   
      return IMAGE;
   
   }
   
   public int getID() {
      
      return ID;
   }
}