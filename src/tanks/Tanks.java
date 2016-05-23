/*
    Set 3 programming project
    5-18-16
    *****************Tanks***************
    https://github.com/jrtechs/Tanks/wiki
*/

package tanks;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

public class Tanks 
{
    //fields
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Bullet> bullets;
    
    //constructor
    public Tanks()
    {
        
    }
    
    //creates a new instance of tanks to run
    public static void main(String[] arguments) 
    {
        Tanks runnable = new Tanks();
    }
    
    
    
    
    
    
    /*
        Sub-classes that require the fields in tanks
    */
    
    
    /*
         the player class which extends the living class
         a player has a turret and an int kills
         a player has a specialized move method
         a player has a update direction method which takes in a keyevent
    */
    private class Player extends Living
    {
        
    }
    
    
    /*
        A zombie extends Enemy
        a zombie has a specialized move method which 
        moves it twards the player
        
        the move method checks to see if it collides with the player
        if so it removes itself from the arraylist in tanks and deducts damage
    */
    private class Zombie extends Enemy
    {
        
    }
    
    
    
    
    /*
        a bullet moves at a specified angle
        if the bullet collides with a enemy it gives damage
        if the bullet goes off the screen it removes itself from the arraylist
    */
    private class Bullet extends RotationalElement
    {
        
        public Bullet(RotationalElement e)
        {
            x = e.x; //Change this coordinate to the player's location
            y = e.y; //Change this coordinate to the player's location
            direction = e.direction; //Change this coordinate to the player turret's angle
            speed = 10;
        }

        //Moving the bullet
        public void move()
        {
            super.move(-1);
            
            //Checks if the bullet goes off screen, if so... it get removed
            if(x < 0 || x > frame.getHeight())
            {
                bullets.remove(this); //Jeff does this work?
            }
            if (y < 0 || y > frame.getWidth())
            {
                bullets.remove(this); //"                  "
            }
        }
        
        
        
        
    }
    
    /*
        a turret is drawn ontop of the tank
        a turret can rotate and shoot bullets
    */
    private class Turret extends RotationalElement
    {
        
    }
    
    /*
    
    */
    private class Tank extends Enemy
    {
        
    }

}
