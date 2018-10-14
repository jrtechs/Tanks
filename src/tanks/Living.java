/*
    super class for any object that is alive
    has health, and a boolean isAlive
    every living element can take damage from another Rotational Element
    determines damage by using instanceof
*/
package tanks;


public class Living extends RotationalElement
{
    public int health;
    public boolean isAlive;
    
    public Living()
    {
        health=0;
        isAlive=true;
    }
    public boolean getAlive()
    {
        return isAlive;
    }
    /*
    when method is called,takes health away from living object, 
    if health is less than 0, is alive = false
    
    */
    public void takeDamage()
    {
        health-=10;
        if(health<=0) {
            isAlive=false;
        }
    }
}
