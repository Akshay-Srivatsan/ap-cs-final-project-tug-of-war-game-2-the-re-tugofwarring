import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class MeleeMinion extends Minion
{
	int health;
	int speed;
	int damage;
	int attackRange;
	Color color;
	int sightRange;
	Point basePoint = new Point();
	public MeleeMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
	{
		super(loc, dir, enemies, enemyBase, buildings, _color);
		color = _color;
		attackRange = 2;
		sightRange = 200;
		damage = 2;
		speed = 2;
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

	@Override
	public void attack() 
	{
		Minion target = getTarget();
		target.takeDamage(getDamage());
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

	@Override
	public void draw(Graphics g) 
	{
		super.draw(g);
	}

	@Override
	public void die()
	{
		
	}

	@Override
	public int getDamage() 
	{
		return damage;
	}

}
