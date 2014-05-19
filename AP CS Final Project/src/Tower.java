import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public abstract class Tower
{
	public int health;
	public int count;
	public int wait;
	public int level;
	public Point location;
	public int width;
	public int height;
	public List<Minion> enemyList;
	
	public Tower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, List<Tower> enemyTowers)	
	{
		location = pointIn;
		width = widthIn;
		height = heightIn;
		enemyList = enemies;
	}
	
	public abstract int removeHealth(int healthRemoved);
	
	public abstract Minion spawn();
	
	public void draw(Graphics g)
	{
		
	}
	
	public List<Minion> getEnemies()
	{
		return enemyList;
	}
}