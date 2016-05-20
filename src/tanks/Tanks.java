package tanks;

/**
 * 
 * Set 3 class project
 * 5-19-16
 * 
 */
public class Tanks 
{
    //constructor
    public Tanks()
    {
        //this is a test by bryce
        
    }
    private class Player extends RotationalElement
    {
        
    }
    private abstract class Enemy extends RotationalElement
    {
        //this is a test by nick
    }
    private class Zombie extends Enemy
    {
        
    }
    private class Bullet extends RotationalElement
    {
        
    }
    
    //creates a new instance of tanks to run
    public static void main(String[] arguments) 
    {
        Tanks runnable = new Tanks();
    }
}
