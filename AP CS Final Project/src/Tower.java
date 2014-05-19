
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
	public Point enemyBase;
	public List<Tower> enemyTowers;
	
	public Tower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBaseIn, List<Tower> enemyTowersIn)	
	{
		location = pointIn;
		width = widthIn;
		height = heightIn;
		enemyList = enemies;
		enemyBase = enemyBaseIn;
		enemyTowers = enemyTowersIn;
	}
	
	public int removeHealth() //Modified by Akshay
	{
		health = health - 1;
		return health;
	}
	
	public Minion spawn()
	{
		//TODO Shouldn't this be an abstract method? -- Akshay
		return null;
	}
	
	public void draw(Graphics g)
	{
		
	}
	
	public List<Minion> getEnemies() //Added by Akshay
	{
		return enemyList;
	}
}