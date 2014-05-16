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
public abstract class Minion
{
	private Point location;
	private double direction;
	private List<Minion> enemyList;
	private Minion target;
	private Color color;
	private Point eb;

	/**
	 * Constructs a minion with the given parameters.
	 * 
	 * @param loc
	 *            The location (using a point in (x,y) format).
	 * @param dir
	 *            The direction.
	 * @param enemies
	 *            The list of enemies.
	 */
	public Minion(Point loc, double dir, List<Minion> enemies, Point enemyBase, Color color)
	{
		location = loc;
		direction = dir;
		enemyList = enemies;
		eb = enemyBase;
		setColor(color);
	}

	/**
	 * Sets the location of the Minion
	 * 
	 * @param _location
	 *            The location in (x,y) format.
	 */
	public void setLocation(Point _location)
	{
		location = _location;
	}
	
	public Point getEnemyBase()
	{
		return eb;
	}

	/**
	 * Sets the direction of the Minion.
	 * 
	 * @param _direction
	 *            The new direction
	 */
	public void setDirection(double _direction)
	{
		direction = _direction;
	}

	/**
	 * Sets the health of the Minion
	 * 
	 * @param _health
	 *            The new health
	 */
	public abstract void setHealth(int _health);

	/**
	 * Sets the speed of the Minion
	 * 
	 * @param speed
	 */
	public abstract void setSpeed(int speed);

	public void setColor(Color _color)
	{
		color = _color;
	}

	public Color getColor()
	{
		return color;
	}

	/**
	 * Gets the current location of the Minion
	 * 
	 * @return The location in (x,y) format.
	 */
	public Point getLocation()
	{
		return location;
	}

	/**
	 * Gets the current health.
	 * 
	 * @return The current health
	 */
	public abstract int getHealth();

	/**
	 * Gets the current direction.
	 * 
	 * @return The current direction of the Minion
	 */
	public double getDirection()
	{
		return direction;
	}

	/**
	 * Gets the speed of the Minion
	 * 
	 * @return the speed
	 */
	public abstract int getSpeed();

	/**
	 * Deals damage to the Minion, and tells the Minion to die if necessary.
	 * 
	 * @param amount
	 *            The amount of damage to deal.
	 * @return true iff the Minion died (health <= 0)
	 */
	public boolean takeDamage(int amount)
	{
		setHealth(getHealth() - 1);
		if (getHealth() <= 0)
		{
			die();
			return true;
		}
		return false;
	}

	/**
	 * Finds a Minion to target. May be overriden.
	 * 
	 * @return The minion to target.
	 */
	public Minion findMinion()
	{
		Minion m;
		if (enemyList != null || enemyList.size() != 0)
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

	/**
	 * Sets the targeted Minion
	 * 
	 * @param m
	 */
	public void setTarget(Minion m)
	{
		target = m;
	}

	/**
	 * Gets the current target of the Minion
	 * 
	 * @return the current target, or null if there is none
	 */
	public Minion getTarget()
	{
		return target;
	}

	/**
	 * Gets the list of enemies.
	 * 
	 * @return The list of enemies.
	 */
	public List<Minion> getEnemies()
	{
		return enemyList;
	}

	/**
	 * Attacks the current target. If there is no target, the behavior is
	 * undefined.
	 */
	public abstract void attack();

	/**
	 * Tells the Minion to draw itself. Should use the location provided by
	 * getLocation() as the center, with direction provided by getDirection().
	 * The implementation should also display health in some way.
	 */
	public void draw(Graphics g)
	{
		Color old = g.getColor();
		g.setColor(getColor());
		g.fillOval(location.x - 10, location.y - 10, 20, 20);
		g.setColor(old);
	}

	/**
	 * Tells the Minion to die.
	 */
	public abstract void die();

	/**
	 * Gets the amount of damage this Minion does.
	 */
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
		double height = pointY - centerY;
		double base = pointX - centerX;
		double angle = Math.atan(height / base);
		return dir;
	}

	/**
	 * Moves the Minion
	 */
	public void move()
	{
		if (direction < 0)
		{
			System.out.println(direction);
			direction += 2 * Math.PI;
			System.out.println(direction);
		}
		else if (direction > 2 * Math.PI)
		{
			System.out.println(direction);
			direction -= 2 * Math.PI;
			System.out.println(direction);
		}
		int dX = getSpeed() * (int) Math.round(Math.cos(direction));
		int dY = getSpeed() * (int) Math.round(Math.sin(direction));
		location.translate(dX, dY);
		if (location.x < 0 || location.x > World.gridWidth || location.y < World.insets.top || location.y > World.gridHeight + World.insets.top)
			onRunIntoWall();
	}

	public void onRunIntoWall()
	{
		if (location.x < 0 || location.x > World.gridWidth)
		{
			direction = Math.PI - direction;
			if (location.x < 0)
			{
				location.x = 0;
			}
			else
			{
				location.x = World.gridWidth;
			}
		}
		if (location.y < World.insets.top || location.y > World.gridHeight + World.insets.top)
		{
			direction = -direction;
			if (location.y < World.insets.top)
			{
				location.y += 10;
			}
			else
			{
				location.y -= 10;
			}
		}

	}

}
