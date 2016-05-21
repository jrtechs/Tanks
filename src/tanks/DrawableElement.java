/*
5-21-16
jeffery R
super class for every object in DynamicBinding
allows for every object to have an image that can be drawn
every object will also have a (x,y) cordinate on the screen 
*/
package tanks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public abstract class DrawableElement
{
    //fields
    public String imageLocation;
    public int x,y;
    public BufferedImage img;
    
    //draws img onto the screen
    public void draw(Graphics g)
    {
        g.drawImage(img, x, y, null);
    }
    
    //loads the image from the disk into the Buffered image img
    public void loadImage()
    {
        img = null;
        try
        {
            img = ImageIO.read(getClass().getResourceAsStream(imageLocation));
            //img = ImageIO.read(new File(imageLoc));
        }
        catch(IOException e)
        {
            System.out.println(e.toString());
        }
    }
}
