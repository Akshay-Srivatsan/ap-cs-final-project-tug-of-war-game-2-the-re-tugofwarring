import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/***
	Julian Christensen, Ian Loftis, Akshay Srivatsan, Josh Campbell
	Description: The basic minion, close range, cheap, medium health, low damage
	The idea was that this would counter the ranged minion by overtaking with many small minions, as the ranged
	can only target one at a time.
**/
public class MeleeMinion extends Minion
{
	int health;
	int speed;
	int damage;
	int attackRange;
	Color color;
	int sightRange;
	Point basePoint;
	public MeleeMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
	{
		super(loc, dir, enemies, enemyBase, buildings, _color);
		color = _color;
		attackRange = getSize();
		sightRange = 200;
		damage = 2;
		setSpeed(3);
		setHealth(20);
		setMaxHealth(getHealth());
		basePoint = enemyBase;
	}

	public int getRange()
	{
		return attackRange;
	}

	@Override
	public void setHealth(int _health) 
	{
		health = _health;
	}

	@Override
	public void setSpeed(int _speed) 
	{
		speed = _speed;
	}

	@Override
	public int getHealth() 
	{
		return health;
	}

	@Override
	public int getSpeed() 
	{
		return speed;
	}
	
	public void act()
	{
		Actor target = getTarget();

		if(getEnemies().contains(target) || getEnemyTowers().contains(target))
		{
			if (target instanceof Base)
			{
				Actor eb = target;
				findTarget();
				if (getTarget() == null)
					target = eb;
			}
			setDirection(directionTo(target.getLocation()));
			if(target.getLocation().distance(getLocation()) < attackRange)
				attack();
			else
				move();
		}
		else
		{
			findTarget();
			if(getTarget() == null)
			{
				setDirection(super.directionTo(basePoint));
			}
			move();
		}
	}

	@Override
	public void attack()
	{
		Actor target = getTarget();
		target.removeHealth(getDamage());
		if(target.getHealth() <= 0)
		{
			if (getEnemies().contains(target))
				target.die();
			findTarget();
		}
		if (target instanceof Minion)
		 	System.out.println(this + " did " + getDamage() + " damage to " + target + " at distance " + getLocation().distance(target.getLocation()) + "; " + target.getHealth() + " left");
		
	}
	
	public void findTarget()
	{
		List<Minion> enemies = getEnemies();
		ArrayList<Minion> enemiesInSight = new ArrayList<Minion>();
		ArrayList<Tower> towersInSight = new ArrayList<Tower>();
		for(Tower tower : getEnemyTowers())
		{
			if(tower.location.distance(getLocation()) < sightRange)
			{
				//System.out.println("in sight");
				towersInSight.add(tower);
			}
		}
		if(towersInSight.size() > 0)
		{
			Tower closestTower = towersInSight.get(0);
			for(Tower tower: towersInSight)
			{
				if(tower.location.distance(getLocation()) > closestTower.location.distance(getLocation()))
				{
					if (!tower.getLocation().equals(getEnemyBase()) || getEnemyTowers().size() == 0)
						closestTower = tower;
				}
			}
			//System.out.println("found a closest Tower");
			setTarget(closestTower);
		}
		else
	    {
		    for(Minion enemy: enemies)
		    {
			    if(enemy.getLocation().distance(getLocation()) < sightRange)
			    {
			    	//System.out.println("enemy in sight");
				    enemiesInSight.add(enemy);
			    }
		    }
		    if(enemiesInSight.size() > 0)
		    {
			    Minion closestEnemy = enemiesInSight.get(0);
			    for(Minion enemy: enemiesInSight)
			    {
				    if(enemy.getLocation().distance(getLocation()) > closestEnemy.getLocation().distance(getLocation()))
				    {
					    closestEnemy = enemy;
				    }
			    }
			    setTarget(closestEnemy);
		    }
		    else
		    {
			    setTarget(null);
			    setDirection(super.directionTo(basePoint));
		    }
		}
	}

	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
	}

	@Override
	public void die()
	{
	    World.killActor(this);
	}

	@Override
	public int getDamage() 
	{
		return damage;
	}

}
