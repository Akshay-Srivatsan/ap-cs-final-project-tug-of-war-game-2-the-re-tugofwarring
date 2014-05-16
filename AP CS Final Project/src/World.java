import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class World extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
	public static int gridHeight;
	public static int gridWidth;
	ArrayList<Point> towersArrayList = new ArrayList<Point>();
	public static Insets insets; //This is used to get the height of the title bar.
	
	ArrayList<Minion> playerMinions = new ArrayList<Minion>();
	ArrayList<Minion> enemyMinions = new ArrayList<Minion>();
	
	public boolean placeMelee = false;
	public boolean placeSplash = false;
	public boolean placeRanged = false;
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	ArrayList<Tower> playerTowers = new ArrayList<Tower>();
	ArrayList<Tower> enemyTowers = new ArrayList<Tower>();
	
	public World(int gridWidthIn, int gridHeightIn)
	{
		gridHeight = gridHeightIn;
		gridWidth = gridWidthIn;
		
		insets = getInsets();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gridWidth = (int) screenSize.getWidth();
		gridHeight = (int) screenSize.getHeight() - 22;
		
		
		setSize(gridWidth, gridHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        
        startTimer();
//        playerMinions.add(new SplashMinion(new Point(100, 100), Math.PI/4, enemyMinions, Color.WHITE));
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
		g.setColor(new Color(100,84,82));
		g.fillRect(0, 0, gridWidth, gridHeight);
		
		drawButtons(g);
		
		if (placeMelee == true || placeRanged == true || placeSplash == true)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica", Font.PLAIN, 50));
			g.drawString("X", mouseX - 18, mouseY +16);
		}
		
		for (Point point : towersArrayList)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Helvetica", Font.PLAIN, 20));
			g.drawString("TOWER", (int)point.getX(), (int)point.getY());
		}

		g.setFont(new Font("Helvetica", Font.PLAIN, 15));
		
		
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
		g.setColor(Color.BLACK);
		g.drawString("Melee Tower", gridWidth/8, gridHeight-20);
		g.setColor(new Color(225,56,0));
		g.fillRect(gridWidth/3, gridHeight-50, gridWidth/3, 50);
		g.setColor(Color.BLACK);
		g.drawString("Ranged Tower", gridWidth*7/15, gridHeight-20);
		g.setColor(new Color(96,130,182));
		g.fillRect(2*gridWidth/3, gridHeight-50, gridWidth/3, 50);
		g.setColor(Color.BLACK);
		g.drawString("Splash Tower", gridWidth*16/20, gridHeight-20);
	}
	
	public ArrayList<Minion> getEnemyMinions()
	{
		return enemyMinions;
	}
	
	public ArrayList<Minion> getPlayerMinions()
	{
		return playerMinions;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (placeMelee == false && placeRanged == false && placeSplash == false)
		{
			
			if (e.getX() > 0 && e.getX() < gridWidth/3 && e.getY() > gridHeight-50)
			{
				placeMelee = true;
			}
			if (e.getX() > gridWidth/3 && e.getX() < gridWidth*2/3 && e.getY() > gridHeight-50)
			{
				placeRanged = true;
			}
			if (e.getX() > gridWidth*2/3 && e.getY() > gridHeight-50)
			{
				placeSplash = true;
			}
		}
		else 
		{
			if(e.getY() < gridHeight-50)
			{
				towersArrayList.add(new Point(e.getX(), e.getY()));
			}
			
			placeMelee = false;
			placeRanged = false;
			placeSplash = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
