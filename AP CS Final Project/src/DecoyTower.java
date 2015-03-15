import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/***
By: Josh, Julian, Akshay, Ian
Description: A low-cost tower which spawns DecoyMinions.
**/
public class DecoyTower extends Tower
{
	public int health = 1000;
	public int count = 0;
	public int wait = 100;
	public int level = 0;
	public Color color = new Color(225, 255, 255);
	
	public DecoyTower(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBase, List<Tower> enemyTowers) 
	{
		super(pointIn, widthIn, heightIn, enemies, enemyBase, enemyTowers);
		setMaxHealth(health);
	}

	public int getHealth()
	{
		return health;
	}
	
	public void removeHealth(int healthRemoved)
	{
		health -= healthRemoved;		
		 if (health <= 0)
        {
            die();
            
        }							
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
		if (count % (wait - level) == 1)
		{
			Minion minion = new DecoyMinion(new Point(location), Math.PI, getEnemies(), enemyBase, enemyTowers, color);
			return minion;
		}
		return null;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
	    g.fillRect(location.x - width/2, location.y - height/2, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(location.x - width/2, location.y - height/2, width, height);
        g.setColor(new Color(124, 252, 0));
        g.fillRect(location.x - 50, location.y - 60, (int)(.1*1000), 10);
        g.setColor(new Color(62, 180, 137));
        g.fillRect(location.x - 50, location.y - 60, (int)(.1*health), 10);
        g.setColor(Color.BLACK);
        g.drawRect(location.x - 50, location.y - 60, (int)(.1*1000), 10);
	    g.setColor(color);
		g.fillRect(location.x - width/2, location.y - height/2, width, height);
		if (getEnemies() == World.enemyMinions)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.drawRect(location.x - width/2, location.y - height/2, width, height);
	}
}
