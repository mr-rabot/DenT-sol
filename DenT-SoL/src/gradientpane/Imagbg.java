package gradientpane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Imagbg
  extends JPanel
{
  private boolean tile;
  
  public Imagbg() {}
  
  public void paintComponent(Graphics g)
  {
    String cd = System.getProperty("user.dir");
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(cd + "/ImpFiles/Images/bg.jpg"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    super.paintComponent(g);
    if (tile) {
      int iw = image.getWidth(this);
      int ih = image.getHeight(this);
      if ((iw > 0) && (ih > 0)) {
        for (int x = 0; x < getWidth(); x += iw) {
          for (int y = 0; y < getHeight(); y += ih) {
            g.drawImage(image, x, y, iw, ih, this);
          }
        }
      }
    } else {
      g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
  }
}
