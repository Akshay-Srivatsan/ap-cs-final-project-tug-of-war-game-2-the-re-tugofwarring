import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
* Represents any static object which can spawn Minions.
* 
* By: Akshay Srivatsan, Ian Loftis, Josh Campbell, Julian Christensen
**/
public abstract class Tower extends Actor
{
	public int health;
	public int count;
	public int wait;
	public int level;
	public Point location;
	public int width;
	public int height;
	public List<Minion> enemyList;
	public List<Tower> enemyTowers;
	public Point enemyBase;
	public int maxHealth;
	
	public Tower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBaseIn, List<Tower> enemyTowersIn)	
	{
	    super(pointIn);
		location = pointIn;
		width = widthIn;
		height = heightIn;
		enemyList = enemies;
		enemyBase = enemyBaseIn;
		enemyTowers = enemyTowersIn;
	}

	public void setMaxHealth(int mh)
	{
		maxHealth = mh;
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public abstract void removeHealth(int healthRemoved);
	
	public abstract Minion spawn();
	
	public abstract void draw(Graphics g);
	
	public abstract int getHealth();
	
	public List<Minion> getEnemies()
	{
		return enemyList;
	}
}