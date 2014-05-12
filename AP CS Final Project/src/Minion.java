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
	private int health;
	private double speed;
	private int damage;
	private List<Minion> world;
	private Minion target;

	/**
	 * Constructs a Minion with a location of (-1, -1), direction of -1, and a
	 * health of -1. All values should be set later using mutators.
	 */
	public Minion()
	{
		location = new Point(-1, -1);
		direction = -1;
		health = 1;
		speed = 1;
		damage = 1;
	}

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
	public Minion(Point _loc, double _dir, int _health, int _speed, int _damage)
	{
		location = _loc;
		direction = _dir;
		health = _health;
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
	public void setHealth(int _health)
	{
		health = _health;
	}
	
	/**
	 * Sets the speed of the Minion
	 * @param _speed
	 */
	public void setSpeed(double _speed)
	{
		speed = _speed;
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
	public int getHealth()
	{
		return health;
	}

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
	public double getSpeed()
	{
		return speed;
	}

	/**
	 * Deals damage to the Minion, and tells the Minion to die if necessary.
	 * 
	 * @param amount
	 *            The amount of damage to deal.
	 * @return true iff the Minion died (health <= 0)
	 */
	public boolean takeDamage(int amount)
	{
		health -= amount;
		if (health <= 0)
		{
			die();
			return true;
		}
		return false;
	}
	
	public Minion findMinion()
	{
		Minion m;
		if (world != null || world.size() != 0)
			m = world.get(0);
		else
			return null;
		for (Minion i : world)
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
	
	public void move()
	{
		
	}
	

}
