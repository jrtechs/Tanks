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
        //this is a test push by Matt
        //this is a push by Mike R
    }
    private class Player extends RotationalElement
    {
        //This is a test by emily
    }
    private abstract class Enemy extends RotationalElement
    {
        
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
