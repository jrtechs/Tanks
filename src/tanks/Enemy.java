/*
    super class for any enemy in the game
    a enemy can spawn at a random location along the border of the map
    a enemy can also calculate the distance and angle to the player
    5-23-16
*/
package tanks;

import javax.swing.JFrame;


public class Enemy extends Living
{
    public Enemy()
    {
        super();        
    }
    
    
   /*
    enemy spawns at a random location on boarder of map. Random number 1-4 
    to determine which edge of map enemy spawns. Random number code to
    determine how far down or how far over player spawns on given edge.
    */
    public void spawn(JFrame frame)
    {
        int temp=(int)(Math.random()*(4)) + 1;
        if(temp==1)
        {
            y=0;
            x=(int)(Math.random()*(frame.getWidth()+1));
        }
        else if(temp==2)
        {
            y=(int)(Math.random()*(frame.getHeight()+1));
            x=frame.getWidth();
        }
        else if(temp==3)
        {
            y=frame.getHeight();
            x=(int)(Math.random()*(frame.getWidth()+1));
        }
        else
        {
            x=0;
            y=(int)(Math.random()*(frame.getHeight()+1));
        }
    }
    
    
    /*
    Used right triangle algebra to determine distance from x,y of enemy 
    to x,y of player.
    */
    public double distToPlayer(RotationalElement p)
    {
        double d, a, b;
        a=Math.abs(x-p.x);
        b=Math.abs(y-p.y);
        d=Math.sqrt(a*a+b*b);
        return(d);
    }
    
    
    /*
    Found lengths of imaginary triangle between player and enemy. 
    Then used inverse trig to determine angle.
    */
    public double angleToPlayer(RotationalElement p)
    {
        double a, b, ang;
        a=(x-p.x); //doesnt have to me absolute value since using atan2
        b=(y-p.y);
        ang=Math.atan2(b,a); //atan2 prevents divisible by zero errors
        ang = Math.toDegrees(ang);
        return(ang);
    }
}
