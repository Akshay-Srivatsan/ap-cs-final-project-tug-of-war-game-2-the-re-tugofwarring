import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class World extends JFrame implements ActionListener 
{
	public static int gridHeight;
	public static int gridWidth;
	public static Insets insets; //This is used to get the height of the title bar.
	
	ArrayList<Minion> playerMinions = new ArrayList<Minion>();
	ArrayList<Minion> enemyMinions = new ArrayList<Minion>();
	
	ArrayList<Tower> playerTowers = new ArrayList<Tower>();
	ArrayList<Tower> enemyTowers = new ArrayList<Tower>();
	
	public World(int gridWidthIn, int gridHeightIn)
	{
		gridHeight = gridHeightIn;
		gridWidth = gridWidthIn;
		setSize(gridWidth, gridHeight + 22);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        insets = getInsets();
        startTimer();
        playerMinions.add(new SplashMinion(new Point(100, 100), Math.PI/4, enemyMinions, Color.BLUE));
	}
	
	public void startTimer()
	{
		Timer clock = new Timer(17, this);
        clock.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		for (Minion m : playerMinions)
		{
			m.move();
		}
		for (Minion m : enemyMinions)
		{
			m.move();
		}
		for (Tower t : playerTowers)
		{
			Minion m = t.spawn();
			if (m != null)
				playerMinions.add(m);
		}
		for (Tower t : enemyTowers)
		{
			Minion m = t.spawn();
			if (m != null)
				enemyMinions.add(m);
		}
		repaint();
	}

	public void paint(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillRect(0, 22, gridWidth, gridHeight);
		for (Minion m : playerMinions)
		{
			m.draw(g);
		}
		for (Minion m : enemyMinions)
		{
			m.draw(g);
		}
		for (Tower t : playerTowers)
		{
			t.draw(g);
		}
		for (Tower t : enemyTowers)
		{
			t.draw(g);
		}
    }
	
	public ArrayList<Minion> getEnemyMinions()
	{
		return enemyMinions;
	}
	
	public ArrayList<Minion> getPlayerMinions()
	{
		return playerMinions;
	}
	
}