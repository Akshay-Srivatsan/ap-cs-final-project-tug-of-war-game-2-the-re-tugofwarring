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
	
	public World(int gridWidthIn, int gridHeightIn)
	{
		gridHeight = gridHeightIn;
		gridWidth = gridWidthIn;
		setSize(gridWidth, gridHeight + 22);
        setLocationRelativeTo(null);
        setVisible(true);
        startTimer();
	}
	
	public void startTimer()
	{
		Timer clock = new Timer(17, this);
        clock.start();
	}
	
	public void paint(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillRect(0, 22, gridWidth, gridHeight);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		repaint();
	}
	
}
