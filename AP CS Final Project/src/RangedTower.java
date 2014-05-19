import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class RangedTower extends Tower
{
	public int health = 1000;
	public int count = 0;
	public int wait = 10;
	public int level = 0;
	public Point location;
	public int width;
	public int height;
	
	public RangedTower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies) 
	{
		super(pointIn, widthIn, heightIn, enemies);	
	}
	
	public int removeHealth(int healthRemoved)
	{
		health -= healthRemoved;
		return health;
	}
	
	public Minion spawn()
	{
		count ++;
		if (count % (wait - level) == 0)
		{
			//TODO Implement RangedMinion. Using SplashMinion until then.
			Minion minion = new SplashMinion(new Point(location.x, location.y), Math.PI/2, getEnemies(), Color.CYAN); //Added by Akshay
			return minion;
		}
		if (count == 100000000)
		{
			count = 0;
		}
		return null;
	}
	
	public void draw(Graphics g)
	{
		
	}
}
