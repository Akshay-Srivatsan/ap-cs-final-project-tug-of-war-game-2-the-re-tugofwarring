import java.awt.Point;

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
	private int speed;
	public int damage;

	/**
	 * Constructs a Minion with a location of (-1, -1), direction of -1, and a
	 * health of -1. All values should be set later using mutators.
	 */
	public Minion()
	{
		location = new Point(-1, -1);
		direction = -1;
		health = -1;
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
	public Minion(Point _loc, double _dir, int _health)
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
	 * Deals damage to the Minion, and tells the Minion to die if necessary.
	 * 
	 * @param amount
	 *            The amount of damage to deal.
	 * @return true iff the Minion died (health <= 0)
	 */
	public boolean receiveDamage(int amount)
	{
		health -= amount;
		if (health <= 0)
		{
			die();
			return true;
		}
		return false;
	}
	
	

	/**
	 * Tells the Minion to draw itself. Should use the location provided by
	 * getLocation() as the center, with direction provided by getDirection().
	 * The implementation should also display health in some way.
	 */
	public abstract void draw();

	/**
	 * Tells the Minion to die.
	 */
	public abstract void die();

}
