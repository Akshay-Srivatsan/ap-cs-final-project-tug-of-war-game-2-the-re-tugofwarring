import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/***
Ian Loftis, Akshay Srivatsan, Josh Campbell, Julian Christensen
Description: A minion that does no damage, low cost, low health. Distracts enemies.
**/
public class DecoyMinion extends MeleeMinion
{
	public DecoyMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
	{
		super(loc, dir, enemies, enemyBase, buildings, _color);
		sightRange = 150;
		damage = 0;
		setSpeed(8);
		setHealth(15);
		setMaxHealth(getHealth());
	}

	@Override
	public void attack()
	{
		Actor target = getTarget();
		if (target == null)
			return;
		target.removeHealth(getDamage());
		if(target.getHealth() <= 0)
		{
			if (getEnemies().contains(target))
				target.die();
		}
		findTarget();
		
	}

	public void findTarget()
	{
		Actor currentTarget = getTarget();
		List<Minion> enemies = getEnemies();
		ArrayList<Minion> enemiesInSight = new ArrayList<Minion>();
		ArrayList<Tower> towersInSight = new ArrayList<Tower>();
		for(Tower tower : getEnemyTowers())
		{
			if(tower.location.distance(getLocation()) < sightRange && tower != currentTarget)
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
			    if(enemy.getLocation().distance(getLocation()) < sightRange && enemy != currentTarget)
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

}
