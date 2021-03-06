import java.awt.Point;

/***
By: Josh, Julian, Akshay, Ian
Description: Represents any entity that can appear on the playing field.
**/
public abstract class Actor 
{
	Point loc;
	public Actor(Point _loc)
	{
		loc = _loc;
	}
	public Point getLocation()
	{
		return loc;
	}

	abstract public int getMaxHealth();

	abstract public int getHealth();
	
	abstract public void removeHealth(int amount);
	
	abstract public void die();
	
	
}