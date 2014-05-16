import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class SplashMinion extends MeleeMinion
{
	private final int RANGE = 50;
	private final int DAMAGE = 5;

	public SplashMinion(Point loc, double dir, List<Minion> enemies, Color color)
	{
		super(loc, dir, enemies, color);
	}

	public int getDamage()
	{
		return DAMAGE;
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
