import java.awt.Color;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;

/***
	Ian Loftis, Akshay Srivatsan, Josh Campbell, Julian Christensen
	Description: The ranged minion has low health, medium cost, medium damage, long range
	Meant to counter splash minion with it's long range, focusing on taking down the splash Minion's high health.
	It is countered by many melee minions, as it can only focus on one thing at a time
**/
public class RangedMinion extends Minion
{
    public final int RANGE = 100;
    public final int DAMAGE = 5;
    public int speed = 2;
    public int health = 15;
    Point basePoint;
    
    public RangedMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
    {
        super(loc, dir, enemies, enemyBase, buildings, _color);
        basePoint = enemyBase;
        setMaxHealth(health);
    }
    
    public int getHealth()
    {
    	return health;
    }

    public int getRange()
    {
    	return RANGE;
    }

    public void attack()
	{
	    Actor target = super.getTarget();
	    if(target instanceof Minion && target.getLocation().distance(getLocation()) < RANGE){
	        target.removeHealth(DAMAGE);
	    }
	    else if(target instanceof Tower && target.getLocation().distance(getLocation()) < RANGE)
	    {
	        target.removeHealth(DAMAGE/4);
	    }
	}

	public void findTarget()
	{
		List<Minion> enemies = getEnemies();
		ArrayList<Minion> enemiesInSight = new ArrayList<Minion>();
		ArrayList<Tower> towersInSight = new ArrayList<Tower>();
		for(Tower tower : getEnemyTowers())
		{
			if(tower.location.distance(getLocation()) < RANGE)
			{
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
			setTarget(closestTower);
		}
		else
	    {
		    for(Minion enemy: enemies)
		    {
			    if(enemy.getLocation().distance(getLocation()) < RANGE)
			    {
				    enemiesInSight.add(enemy);
			    }
		    }
		    if(enemiesInSight.size() > 0)
		    {
			    Minion strongestEnemy = enemiesInSight.get(0);
			    for(Minion enemy: enemiesInSight)
			    {
				    if(enemy.getHealth() > strongestEnemy.getHealth())
				    {
					    strongestEnemy = enemy;
				    }
			    }
			    setTarget(strongestEnemy);
		    }
		    else
		    {
			    setTarget(null);
			    setDirection(super.directionTo(basePoint));
		    }
		}
	}

	public int getDamage()
	{
		return DAMAGE;
	}

	public void die()
	{
		World.killActor(this);
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int s)
	{
		speed = s;
	}

	public void setHealth(int h)
	{
		health = h;
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
			if(target.getLocation().distance(getLocation()) < RANGE)
				attack();
			else
				move();
		}
		else
		{
			System.out.println(this + " has no target");
			findTarget();
			move();
		}
	}
}
