import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SplashMinion extends MeleeMinion
{
	private final int RANGE = 50;
	private final int DAMAGE = 5;

	public SplashMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
	{
		super(loc, dir, enemies, enemyBase, buildings, _color);
	}

	public int getDamage()
	{
		return DAMAGE;
	}
	
	public void findTarget()
	{
		List<Minion> enemies = getEnemies();
		ArrayList<Minion> enemiesInSight = new ArrayList<Minion>();
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
		}
	}
	
	public void act()
	{
		Minion target = getTarget();
		if(target != null)
		{
			setDirection(directionTo(target.getLocation()));
			if(target.getLocation().distance(getLocation()) < attackRange)
				target.attack();
			else
				move();
		}
		else
		{
			findTarget();
			move();
		}
	}
	
	public void attack()
	{
		for (Minion m : getEnemies())
		{
			if (getLocation().distance(m.getLocation()) <= RANGE)
				m.takeDamage(getDamage());
		}
	}
	
	public void draw(Graphics g)
	{
		super.draw(g);
	}

}
