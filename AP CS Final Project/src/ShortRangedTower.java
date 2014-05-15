import java.awt.Point;

public class ShortRangedTower extends Tower
{
	public int health = 1000;
	public int count = 0;
	public int wait = 10;
	public int level = 0;
	public Point location;
	public int width;
	public int height;
	
	public ShortRangedTower(Point pointIn, int widthIn, int heightIn) 
	{
		super(pointIn, widthIn, heightIn);
	}
	
	public Minion spawn()
	{
		count ++;
		if (count % (wait - level) == 0)
		{
			Minion minion = new ShortRangedMinion();
			return minion;
		}
		if (count == 100000000)
		{
			count = 0;
		}
	}
	
	public void draw()
	{
		
	}
}
