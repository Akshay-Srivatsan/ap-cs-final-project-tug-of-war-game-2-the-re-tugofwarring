import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class SplashTower extends Tower
{
	public int health = 1000;
	public int count = 0;
	public int wait = 10;
	public int level = 0;
	public Point location;
	public int width;
	public int height;
	
	public SplashTower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBase, List<Tower> enemyTowers) 
	{
		super(pointIn, widthIn, heightIn, enemies, enemyBase, enemyTowers);
	}
	
	public int removeHealth(int healthRemoved)
	{
		health -= healthRemoved;
		return health;
	}
	
	public Minion spawn()
	{
		count++;
		if (count >= 100000000)
		{
			count = 0;
		}
		if (count % (wait - level) == 0)
		{
			Minion minion = new SplashMinion(new Point(location.x, location.y), Math.PI/2, getEnemies(), enemyBase, enemyTowers, Color.BLUE); //Added by Akshay
			return minion;
		}
		return null;
	}
	
	public void draw(Graphics g)
	{
		
	}

}
