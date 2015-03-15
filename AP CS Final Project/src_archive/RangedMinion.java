public class RangedMinion 
{
    public final int RANGE = 150;
    public final int DAMAGE = 10;
    
    public RangedMinion(Point loc, double dir, List<Minion> enemies, Point enemyBase, List<Tower> buildings, Color _color)
    {
        super(loc, dir, enemies, enemyBase, buildings, _color);
    }
    
    public void attack()
	{
	    Actor target = super.getTarget();
	    if(target instanceof Minion && target.getLocation().distance(getLocation()) < RANGE){
	        target.removeHealth(DAMAGE)
	    }
	    else if(target instanceof Tower && target.getLocation().distance(getLocation()) < RANGE)
	    {
	        target.removeHealth(DAMAGE/4);
	    }
	}
	
	public void act()
	{
		Minion target = getTarget();
		if(target != null)
		{
			setDirection(directionTo(target.getLocation()));
			if(target.getLocation().distance(getLocation()) < attackRange)
				attack();
			else
				move();
		}
		else
		{
			findTarget();
			move();
		}
	}
}
