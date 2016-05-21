/*
    jeffery R
    5-21-16
    Gives every sub-class a width and a height
    every GameElement is able to check for a collision with another gameElement
*/
package tanks;

import java.awt.geom.Area;

public abstract class GameElement extends DrawableElement 
{
    //generic information about the game element
    public int width, height;
    
    
    //geometry variable used for collisions
    public Area shape;
    
    
    /*
        checks for collision against another game element
        will only work if both shape variables are set
        depending on the object the shape are will be set differently

        returnes true if there was a collision, false otherwise
    */
    public boolean checkCollision(GameElement e)
    {
        //sets the areas of the two elements being compared
        this.setShape();
        e.setShape();
        //checks to see if there is an overlape in the two areas
        shape.intersect(e.shape);
    	return !(shape.isEmpty());
    }
    
    //method that ensures that every sub-class will set their shape accordingly
    public abstract void setShape();
    
}