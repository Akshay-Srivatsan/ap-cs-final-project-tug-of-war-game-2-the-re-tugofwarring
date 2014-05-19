import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
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
	ArrayList<Tower> eTowers;
	
	public MeleeTower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, List<Tower> enemyTowers) 
	{
		eTowers = enemyTowers;
		super(pointIn, widthIn, heightIn, enemies, enemyTowers);
	}
	
	public int removeHealth(int healthRemoved)
	{
		health -= healthRemoved;
		return health;
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
			Minion minion = new MeleeMinion(new Point(location.x, location.y), Math.PI/2, getEnemies(), color, towers);
			return minion;
		}
		return null;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(location.x, location.y, width, height);
	}
}
