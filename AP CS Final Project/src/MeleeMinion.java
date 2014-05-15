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
	public MeleeMinion(Point loc, double dir, List<Minion> enemies, Color _color)
	{
		super(loc, dir, enemies);
		color = _color;
		attackRange = 2;
		sightRange = 200;
	}

	@Override
	public void setHealth(int _health) 
	{
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return speed;
	}

	@Override
	public void attack() 
	{
		Minion target = getTarget();
		if(target == null)
		{
			
		}
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
	}

	@Override
	public void draw(Graphics g) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDamage() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
