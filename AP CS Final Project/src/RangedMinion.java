import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;


public class RangedMinion extends Minion
{
	int health;
	int speed;
	int damage;
	int range;
	 public RangedMinion(Point loc, double dir, List<Minion> enemies, Color _color)
	 {
		 super(loc, dir, enemies, _color);
		 setHealth(100);
		 setSpeed(5);
		 range = 100;
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
		return 0;
	}

	@Override
	public double getSpeed() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void attack() 
	{
		Minion target = getTarget();
		
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		return 0;
	}
	 
	 

}
