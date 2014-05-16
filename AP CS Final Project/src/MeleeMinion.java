import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;


public class MeleeMinion extends Minion
{
	int health = 10;
	int speed = 4;
	int damage = 5;
	public MeleeMinion(Point loc, double dir, List<Minion> enemies, Color color)
	{
		super(loc, dir, enemies, color);
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
		// TODO Auto-generated method stub
		speed = _speed;
	}

	@Override
	public int getHealth() 
	{
		// TODO Auto-generated method stub
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
		Minion toAttack = findMinion();
		toAttack.setHealth(toAttack.getHealth() - damage);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDamage() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
