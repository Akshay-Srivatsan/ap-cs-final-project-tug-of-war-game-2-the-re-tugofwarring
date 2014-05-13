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

	/**
	 * Constructs a minion with the given parameters.
	 * 
	 * @param _loc
	 *            The location (using a point in (x,y) format).
	 * @param _dir
	 *            The direction.
	 * @param _health
	 *            The starting health.
	 */
	public Minion(Point loc, double dir, List<Minion> enemies)
	{
		location = loc;
		direction = dir;
		enemyList = enemies;
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
	 * @param speed
	 */
	public abstract void setSpeed(double speed);

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
	 * Sets the speed of the Minion
	 * @param _speed
	 */
	public abstract double getSpeed();

	/**
	 * Deals damage to the Minion, and tells the Minion to die if necessary.
	 * 
	 * @param amount
	 *            The amount of damage to deal.
	 * @return true iff the Minion died (health <= 0)
	 */
	public boolean takeDamage(int amount)
	{
		setHealth(getHealth()-1);
		if (getHealth() <= 0)
		{
			die();
			return true;
		}
		return false;
	}
	
	/**
	 * Finds a Minion to target. May be overriden.
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
	 * @param m
	 */
	public void setTarget(Minion m)
	{
		target = m;
	}
	
	public Minion getTarget()
	{
		return target;
	}
	
	public List<Minion> getEnemies()
	{
		return enemyList;
	}
	
	public abstract void attack();

	/**
	 * Tells the Minion to draw itself. Should use the location provided by
	 * getLocation() as the center, with direction provided by getDirection().
	 * The implementation should also display health in some way.
	 */
	public abstract void draw(Graphics g);

	/**
	 * Tells the Minion to die.
	 */
	public abstract void die();
	
	/**
	 * Gets the amount of damage this Minion does.
	 */
	public abstract int getDamage();
	
	/**
	 * Moves the Minion
	 */
	public void move()
	{
		int dX = (int) Math.round(Math.cos(direction));
		int dY = (int) Math.round(Math.sin(direction));
		location.translate(dX, dY);
	}
	

}
