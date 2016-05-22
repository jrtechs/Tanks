/*
    jeffery R
    5-21-16
    super class for any object that moves at an angle
 */

package tanks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class RotationalElement extends GameElement
{
    //0-360 angle which determines which direction the object is facing
    public double direction;
    
    //integer which defines how fast a object will move
    public int speed;
    
    //convertes degrease to radians
    public double degToRad()
    {
        return direction * (Math.PI/180);
    }
    
    //converts radians to degrees
    public double radToDeg()
    {
        return direction * (180/ Math.PI);
    }
    
    /*
        moves the object's x and y according to its degrees
        the multiplier is used to move at a specific speed
        a positive multiplier(1) moves the object backwards
        a negative multiplier(-1) moves the object forwards
    */
    public void move(int multiplier)
    {
        y += Math.sin(degToRad()) * multiplier * speed;
        x += Math.cos(degToRad()) * multiplier * speed;
    }
    
    /*
        Draws the object at a specified angle rotated about the center
    */
    @Override
    public void draw(Graphics g)
    {
        //creates the object to rotate the image
        AffineTransform tf = AffineTransform.getTranslateInstance(x, y);
        
        //rotates the image around the center
        tf.rotate(degToRad(), width/2, height/2);
        
        //draws the rotated image on the panel
        ((Graphics2D)(g)).drawImage(img, tf, null);
    }
    
    /*
        Sets the area variable shape in GameElement to be used in collision code
    */
    @Override
    public void setShape() 
    {
        AffineTransform rotate = AffineTransform.getRotateInstance(degToRad(), x, y);
        
        shape = new Area(rotate.createTransformedShape(new Rectangle(x,y,width, height)));
    }
}
