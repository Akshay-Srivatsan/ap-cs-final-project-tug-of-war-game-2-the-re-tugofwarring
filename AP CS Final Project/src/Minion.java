import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 * The Minion class represents any game element with a location, direction, and
 * health. 
 * 
 * @author Akshay
 *
 */
public abstract class Minion extends Actor
{
	private Point location;
	private double direction;
	private List<Minion> enemyList;
	private Actor target = null;
	private Color color;
	private List<Tower> et;
	private Point eb;
	private int maxHealth;

	public Minion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> enemyTowers, Color color)
	{
		super(loc);
		location = loc;
		direction = dir;
		enemyList = enemies;
		et = enemyTowers;
		setColor(color);
		eb = enemyBase;
	}

	public void setMaxHealth(int mh)
	{
		maxHealth = mh;
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public void setLocation(Point _location)
	{
		location = _location;
	}

	public Point getEnemyBase()
	{
		return eb;
	}
	
	public List<Tower> getEnemyTowers()
	{
		return et;
	}

	public void setDirection(double _direction)
	{
		direction = _direction;
	}

	public abstract void setHealth(int _health);

	public abstract void setSpeed(int speed);

	public void setColor(Color _color)
	{
		color = _color;
	}

	public Color getColor()
	{
		return color;
	}

	public Point getLocation()
	{
		return location;
	}

	public abstract int getHealth();

	public double getDirection()
	{
		return direction;
	}

	public abstract int getSpeed();

	public void removeHealth(int amount)
	{
		setHealth(getHealth() - 1);
		if (getHealth() <= 0)
		{
			die();
			//return true;
		}
		//return false;
	}

	public Minion findMinion()
	{
		Minion m;
		if (enemyList != null && enemyList.size() != 0)
			m = enemyList.get(0);
		else
			return null;
		for (Minion i : enemyList)
		{
			double distance = location.distance(i.location);
			if (distance < location.distance(m.location))
			{
				m = i;
			}
		}
		return m;
	}

	public void setTarget(Actor m)
	{
		target = m;
	}

	public Actor getTarget()
	{
		return target;
	}

	public abstract int getRange();

	public int getSize()
	{
		return 5;
	}

	public List<Minion> getEnemies()
	{
		return enemyList;
	}

	public abstract void attack();

	public void draw(Graphics g)
	{
		Color old = g.getColor();
		g.setColor(new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), (int)(getHealth()/(float)maxHealth*255)));
		int s = getSize();
		g.fillOval(location.x - s, location.y - s, s*2, s*2);
		if (getEnemies() == World.enemyMinions)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.drawOval(location.x - s, location.y - s, s*2, s*2);
		
		g.drawString(String.valueOf(getHealth()), location.x, location.y-10);



		if (target != null && World.SHOW_TARGETS)
		{
			g.drawLine(getLocation().x, getLocation().y, target.getLocation().x, target.getLocation().y);
		}


		int r = getRange();
		g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 32));
		g.fillOval(location.x - r, location.y - r, r*2, r*2);

		g.setColor(old);
	}

	public abstract void die();

	public abstract int getDamage();

	public void act()
	{
		move();
	}

	public double directionTo(Point point)
	{
		double dir = 0;
		Point center = getLocation();
		int pointX = point.x;
		int pointY = point.y;
		int centerX = center.x;
		int centerY = center.y;
		double adjustForQuadrant = 0;
    	if(pointX >= centerX && pointY >= centerY)
    	{
    	    adjustForQuadrant = 0 * (Math.PI / 180);
    	}
    	else if(pointX >= centerX && pointY <= centerY)
    	{
    	    adjustForQuadrant = 360 * (Math.PI / 180);
    	}
    	else if(pointX <= centerX && pointY <= centerY)
    	{
    	    adjustForQuadrant = 180 * (Math.PI / 180);
    	}
    	else
    	{
    	    adjustForQuadrant = 180 * (Math.PI / 180);
    	}
		double height = pointY - centerY;
		double base = pointX - centerX;
		double angle = Math.atan2(-height, -base);// + adjustForQuadrant;
		dir = (angle + Math.PI);//%(2*Math.PI);
		return dir;// + Math.PI;

	}

	public void move()
	{
		if (this instanceof RangedMinion)
			System.out.println(this + " " + target);
		int dX = (int) Math.round(getSpeed() * Math.cos(direction));
		int dY = (int) Math.round(getSpeed() * Math.sin(direction));
		location.translate(dX, dY);
		if (location.x < 0 || location.x > World.gridWidth || location.y < World.insets.top || location.y > World.gridHeight + World.insets.top)
			onRunIntoWall();
	}

	public void onRunIntoWall()
	{
		die();

	}

}
