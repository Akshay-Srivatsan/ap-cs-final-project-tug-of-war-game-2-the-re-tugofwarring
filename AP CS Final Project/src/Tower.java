
import java.awt.Point;

public abstract class Tower
{
	public int health;
	public int count;
	public int wait;
	public int level;
	public Point location;
	public int width;
	public int height;
	
	public Tower(Point pointIn, int widthIn, int heightIn)	
	{
		location = pointIn;
		width = widthIn;
		height = heightIn;
	}
	
	public int removeHealth()
	{
		
	}
	
	public Minion spawn()
	{
		
	}
	
	public void draw()
	{
		
	}
}