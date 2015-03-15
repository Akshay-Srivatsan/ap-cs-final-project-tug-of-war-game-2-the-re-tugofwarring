import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/*************************
* By: Julian, Akshay, Josh, Ian
* Base Class
* This class is the main base. It extends tower, and when it dies, the game ends.
**************************/

class Base extends Tower
{
    public int health = 1000;
    public Color color = new Color(40, 183, 141);
    public Point location;
    public int timeClicks = 0;
    
    public Base(Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBaseIn, List<Tower> enemyTowersIn)
    {
        super(pointIn, widthIn, heightIn, enemies, enemyBaseIn, enemyTowersIn);
        setMaxHealth(health);
        location = pointIn;
    }
    
    public void removeHealth(int healthRemoved)
    {
        health -= healthRemoved;
        if (Math.abs(health - 2*getMaxHealth()/3) < 5 || Math.abs(health - getMaxHealth()/3) < 5)
        {
            for (Minion enemy : getEnemies().toArray(new Minion[getEnemies().size()]))
            {
                //if (enemy.getTarget() == this)
                if (getEnemies() == World.enemyMinions && enemy.getLocation().x <= World.gridWidth/2 && enemy.getLocation().y >= World.gridHeight/2)
                {
                    World.killActor(enemy);
                }
                if (getEnemies() == World.playerMinions && enemy.getLocation().x >= World.gridWidth/2 && enemy.getLocation().y <= World.gridHeight/2)
                {
                    World.killActor(enemy);
                }
            }
        }
        if (health <= 0)
        {
            die();
            
        }
    }
    
    public Minion spawn()
    {
        timeClicks++;
        if (timeClicks % 3 == 0 && health < getMaxHealth() && Math.abs(health - 2*getMaxHealth()/3) >= 5 && Math.abs(health - getMaxHealth()/3) >= 5)
        {
            health++;
        }

        double r = Math.random();
        if (r < 0.004)
        {
            Minion minion = new MeleeMinion(new Point(location), Math.PI, getEnemies(), enemyBase, enemyTowers, color);
            return minion;
        }
        else if (r < 0.006)
        {
            Minion minion = new RangedMinion(new Point(location), Math.PI, getEnemies(), enemyBase, enemyTowers, color);
            return minion;
        }
        else if (r < 0.007)
        {
            Minion minion = new SplashMinion(new Point(location), Math.PI, getEnemies(), enemyBase, enemyTowers, color);
            return minion;
        }

        if (Math.abs(health - 2*getMaxHealth()/3) < 5 || Math.abs(health - getMaxHealth()/3) < 5)
            health -= 1;


        return null;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(location.x - width/2, location.y - height/2, width, height);
        if (getEnemies() == World.enemyMinions)
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.RED);
        g.drawRect(location.x - width/2, location.y - height/2, width, height);
        g.setColor(new Color(124, 252, 0));
        g.fillRect(location.x - 50, location.y - 60, (int)(.1*1000), 10);
        g.setColor(new Color(62, 180, 137));


        if (health < getMaxHealth()/3)
        {
            g.setColor(new Color(255, 0, 0, 255));
            g.fillRect(location.x - 50, location.y - 60, (int)(.1*health), 10);
        }
        else if (health < getMaxHealth()*2/3)
        {
            g.setColor(new Color(255, 255, 0, 255));
            g.fillRect(location.x - 50, location.y - 60, (int)(.1*health), 10);
        }
        else
        {
            g.fillRect(location.x - 50, location.y - 60, (int)(.1*health), 10);
        }

        g.setColor(Color.BLACK);
        g.drawRect(location.x - 50, location.y - 60, (int)(.1*1000), 10);
        g.drawRect(location.x - 50, location.y - 60, (int)(.1/3*1000), 10);
        g.drawRect(location.x - 50 + (int)(.1/3*1000), location.y - 60, (int)(.1/3*1000), 10);
    }
    
    public void displayWinScreen(Graphics g)
    {
        g.setColor(new Color(100,84,82));
        g.fillRect(0, 0, World.gridWidth, World.gridHeight);
        g.setColor(Color.WHITE);
        g.drawString("WINNER", 100,100);
    }


    public int getHealth()
    {
        return health;
    }

    public void die()
    {
        World.killActor(this);
    }
}