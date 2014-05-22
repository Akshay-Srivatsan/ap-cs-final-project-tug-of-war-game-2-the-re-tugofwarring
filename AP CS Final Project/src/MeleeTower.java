import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class MeleeTower extends Tower
{
	public int health = 1000;
	public int count = 0;
	public int wait = 10;
	public int level = 0;
	public Point location;
	public int width;
	public int height;
	public Color color = new Color(225, 255, 0);
	
	public MeleeTower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBase, List<Tower> enemyTowers) 
	{
		super(pointIn, widthIn, heightIn, enemies, enemyBase, enemyTowers);
	}
	
	public void removeHealth(int healthRemoved)
	{
		health -= healthRemoved;
	}
	
	public void die()
	{
	    World.killActor(this);
	}
	
	public Minion spawn()
	{
		count ++;
		if (count == 100000000)
		{
			count = 0;
		}
		if (count % (wait - level) == 0)
		{
			Minion minion = new MeleeMinion(new Point(location.x, location.y), Math.PI/2, getEnemies(), enemyBase, enemyTowers, color);
			return minion;
		}
		return null;
	}
	
	public int getHealth()
	{
	    return health;
	}
	
	public void draw(Graphics g)
	{
	    g.setColor(color);
		g.fillRect(location.x, location.y, width, height);
	}
}
