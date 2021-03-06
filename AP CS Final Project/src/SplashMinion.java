import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/***
	Julian Christensen, Ian Loftis, Akshay Srivatsan, Josh Campbell
	Description: A minion that does splash damage, high cost, high health, medium damage, medium range. Meant to counter 
	the overpowering numbers of the melee minions by attacking many minions at a time. It spawns slowly and does extra damage
	to towers.
**/
public class SplashMinion extends MeleeMinion
{
	private final int RANGE = 50;
	private final int DAMAGE = 5;
	int health = 35;
	public SplashMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
	{
		super(loc, dir, enemies, enemyBase, buildings, _color);
		super.setHealth(health);
		super.setMaxHealth(health);
	}

	public int getSpeed()
	{
		return 2;
	}

	public int getDamage()
	{
		return DAMAGE;
	}

	public int getHealth()
	{
		return super.getHealth();
	}
	
	public void findTarget()
	{
		List<Minion> enemies = getEnemies();
		ArrayList<Minion> enemiesInSight = new ArrayList<Minion>();
		ArrayList<Tower> towersInSight = new ArrayList<Tower>();
		for(Tower tower: getEnemyTowers())
		{
			if(tower.location.distance(getLocation()) < sightRange)
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
			super.setTarget(closestTower);
		}
		else
	    {
		    for(Minion enemy: enemies)
		    {
			    if(enemy.getLocation().distance(getLocation()) < sightRange)
			    {
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
	
	public void act()
	{
		Actor target = getTarget();

		if(getEnemies().contains(target) || getEnemyTowers().contains(target))
		{
			setDirection(directionTo(target.getLocation()));
			if(target.getLocation().distance(getLocation()) < attackRange)
				target.removeHealth(DAMAGE);
			else
				move();
		}
		else
		{
			findTarget();
			move();
		}
	}

	public int getRange()
	{
		return RANGE;
	}
	
	public void attack()
	{
		for (Minion m : getEnemies().toArray(new Minion[getEnemies().size()]))
		{
			if (getLocation().distance(m.getLocation()) <= RANGE)
				m.removeHealth(getDamage());
		}
		for (Tower t: getEnemyTowers().toArray(new Tower[getEnemyTowers().size()]))
		{
		    if(getLocation().distance(t.getLocation()) <= RANGE)
		        t.removeHealth(2 * getDamage());
		}
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}

}
