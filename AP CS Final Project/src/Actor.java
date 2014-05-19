import java.awt.Point;

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
}