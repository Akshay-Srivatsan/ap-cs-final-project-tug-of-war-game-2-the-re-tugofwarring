import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

public class World extends JFrame implements ActionListener 
{
	int gridHeight;
	int gridWidth;
	
	ArrayList<Minion> playerMinions = new ArrayList<Minion>();
	ArrayList<Minion> enemyMinions = new ArrayList<Minion>();
	
	ArrayList<Tower> playerTowers = new ArrayList<Tower>();
	ArrayList<Tower> enemyTowers = new ArrayList<Tower>();
	
	public World(int gridWidthIn, int gridHeightIn)
	{
		gridHeight = gridHeightIn;
		gridWidth = gridWidthIn;
		setSize(gridWidth, gridHeight);
        setLocationRelativeTo(null);
        setVisible(true);
        startTimer();
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
			t.spawn();
		}
		for (Tower t : enemyTowers)
		{
			t.spawn();
		}
		repaint();
	}

	public void paint(Graphics g)
    {
		g.setColor(new Color(100,84,82));
		g.fillRect(0, 22, gridWidth, gridHeight);

		drawButtons(g);
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
	
	public void drawButtons(Graphics g)
	{
		g.setColor(new Color(225,255,0));
		g.fillRect(0, gridHeight-50, gridWidth/3, 50);
		g.setColor(new Color(225,56,0));
		g.fillRect(gridWidth/3, gridHeight-50, gridWidth/3, 50);
		g.setColor(new Color(96,130,182));
		g.fillRect(2*gridWidth/3, gridHeight-50, gridWidth/3, 50);
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