import java.awt.Point;


public abstract class Minion
{
	private Point location;
	private double direction;
	private int health;
	
	public Minion()
	{
		location = new Point(-1,-1);
		direction = -1;
		health = -1;
	}
	
	public Minion(Point _loc, double _dir, int _health)
	{
		location = _loc;
		direction = _dir;
		health = _health;
	}
	
	public void setLocation(Point _location)
	{
		location = _location;
	}
	
	public void setDirection(double _direction)
	{
		direction = _direction;
	}
	
	public void setHealth(int _health)
	{
		health = _health;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public double getDirection()
	{
		return direction;
	}
	
	public abstract void draw();
	
}
