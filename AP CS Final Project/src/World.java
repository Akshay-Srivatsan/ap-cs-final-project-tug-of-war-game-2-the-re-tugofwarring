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
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import javax.swing.JOptionPane;

import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import java.net.URL;

/*******************
* By: Julian, Akshay, Josh, Ian
* World Class
* This class runs the world, creates the towers, and keeps them in arraylists, and paints them and makes them act.
********************/
public class World extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
	public static int gridHeight;
	public static int gridWidth;
	public static ArrayList<Point> towersArrayList = new ArrayList<Point>();
	public static Tower ENEMYBASE = null;
	public static Tower PLAYERBASE = null;
	public static Insets insets; //This is used to get the height of the title bar.
	
	public static ArrayList<Minion> playerMinions = new ArrayList<Minion>();
	public static ArrayList<Minion> enemyMinions = new ArrayList<Minion>();
	
	public static boolean winner = false;
	public static boolean loser = false;

	public static boolean placeDecoy = false;
	public static boolean placeMelee = false;
	public static boolean placeSplash = false;
	public static boolean placeRanged = false;

	public static int enemyResources = 0;

	public static long timeClicks = 0;
	public static long gameTime = 0;
	
	public static ArrayList<Tower> playerTowers = new ArrayList<Tower>();
	public static ArrayList<Tower> enemyTowers = new ArrayList<Tower>();

	public static final int COST_DECOY = 50;
	public static final int COST_MELEE = 100;
	public static final int COST_RANGED = 200;
	public static final int COST_SPLASH = 300;

	public static int resources = COST_MELEE + COST_RANGED + COST_SPLASH; //So that the user has resources equivalent to the enemy.

	public static final boolean SHOW_TARGETS = true; //Displays a line from a minion to targets.

	public static boolean paused = true;

	public static int mouseX = 0;
	public static int mouseY = 0;
	
	public World(int gridWidthIn, int gridHeightIn)
	{
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gridWidth = (int) screenSize.getWidth();
		gridHeight = (int) screenSize.getHeight();

		// gridHeight = gridHeightIn;
		// gridWidth = gridWidthIn;
		
		//Point pointIn, int widthIn, int heightIn, List<Minion> enemies, Point enemyBaseIn, List<Tower> enemyTowersIn

		//Tower playerBase = new Base(new Point(gridWidth/4, gridHeight*3/4), 60, 60, enemyMinions, new Point(gridWidth*3/4, gridHeight/4), enemyTowers);
		Tower playerBase = new Base(new Point(120, gridHeight-120-50), 60, 60, enemyMinions, new Point(gridWidth-120, 120), enemyTowers);
		playerTowers.add(playerBase);
		PLAYERBASE = playerBase;

		//Tower enemyBase = new Base(new Point(gridWidth*3/4, gridHeight/4), 60, 60, playerMinions, new Point(gridWidth/4, gridHeight*3/4), playerTowers);
		Tower enemyBase = new Base(new Point(gridWidth-120, 120), 60, 60, playerMinions, new Point(120, gridHeight-120-50), playerTowers);
		enemyTowers.add(enemyBase);
		ENEMYBASE = enemyBase;

		//Tower enemyMelee = new MeleeTower(new Point(gridWidth*3/4 - 110, gridHeight/4), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		Tower enemyMelee = new MeleeTower(new Point(ENEMYBASE.getLocation().x - 110, ENEMYBASE.getLocation().y), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		enemyTowers.add(enemyMelee);
		//Tower enemyRanged = new RangedTower(new Point(gridWidth*3/4 + 110, gridHeight/4), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		Tower enemyRanged = new RangedTower(new Point(ENEMYBASE.getLocation().x - 110, ENEMYBASE.getLocation().y + 110), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		enemyTowers.add(enemyRanged);
		//Tower enemySplash = new SplashTower(new Point(gridWidth*3/4, gridHeight/4 + 110), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		Tower enemySplash = new SplashTower(new Point(ENEMYBASE.getLocation().x, ENEMYBASE.getLocation().y + 110), 40, 40, playerMinions, playerBase.getLocation(), playerTowers);
		enemyTowers.add(enemySplash);

		insets = getInsets();
		
		setSize(gridWidth, gridHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        createBufferStrategy(2);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this); //Makes the window go into real full screen.

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
		timeClicks = (timeClicks + 1) % 330;
		if (paused)
		{
			repaint();
			return;
		}
		gameTime++;
		if (timeClicks % 3 == 0)
		{
			resources++;
			enemyResources++;
		}
		for (int i = 0; i < playerMinions.size(); i++)
		{
			playerMinions.get(i).act();
		}
		for (int i = 0; i < enemyMinions.size(); i++)
		{
			enemyMinions.get(i).act();
		}
		for(int i = 0; i < playerTowers.size(); i++)
		{
			Tower t = playerTowers.get(i);
			Minion m = t.spawn();
			if (m != null)
				playerMinions.add(m);
		}
		for (int i = 0; i < enemyTowers.size(); i++)
		{
			Minion m = enemyTowers.get(i).spawn();
			if (m != null)
				enemyMinions.add(m);
		}

		double r = Math.random();
		// Point newPoint = new Point(ENEMYBASE.getLocation());
		// newPoint.translate((int)-(Math.random() * 200)-100, (int)(Math.random() * 200)-100);
		Point newPoint = new Point((int) (gridWidth - Math.random()*(gridWidth/2)), (int)(Math.random()*(gridHeight-50)/2));
		int multiplier = 1;
		if (ENEMYBASE.getHealth() <= ENEMYBASE.getMaxHealth()/3)
			multiplier *= 2;
		if (ENEMYBASE.getHealth() <= ENEMYBASE.getMaxHealth()*2/3)
			multiplier *= 2;
		if (r < 0.01 * multiplier && enemyResources > COST_SPLASH)
		{
			enemyTowers.add(new SplashTower(newPoint, 40, 40, playerMinions, PLAYERBASE.getLocation(), playerTowers));
			enemyResources -= COST_SPLASH;
		}
		else if (r < 0.005 * multiplier && enemyResources > COST_RANGED)
		{
			enemyTowers.add(new RangedTower(newPoint, 40, 40, playerMinions, PLAYERBASE.getLocation(), playerTowers));
			enemyResources -= COST_RANGED;
		}
		else if (r < 0.0025 * multiplier && enemyResources > COST_MELEE)
		{
			enemyTowers.add(new MeleeTower(newPoint, 40, 40, playerMinions, PLAYERBASE.getLocation(), playerTowers));
			enemyResources -= COST_MELEE;
		}
		else if (r < 0.00125 * multiplier && enemyResources > COST_DECOY)
		{
			enemyTowers.add(new DecoyTower(newPoint, 40, 40, playerMinions, PLAYERBASE.getLocation(), playerTowers));
			enemyResources -= COST_DECOY;
		}

		repaint();
	}

	public void paint(Graphics graphics)
    {
    	Graphics g;
    	if (this.getBufferStrategy() == null)
    		g = graphics;
    	else
    		g = this.getBufferStrategy().getDrawGraphics();


    	if (g == null)
    		return;

		g.setColor(new Color(100,84,82));
		g.fillRect(0, 0, gridWidth, gridHeight);

		g.setColor(new Color(120,104,102));
		g.drawRect(0, (gridHeight-50)/2, gridWidth/2, (gridHeight-50)/2);

		g.setColor(new Color(120, 52, 51));
		g.drawRect(gridWidth/2, 0, gridWidth/2, (gridHeight-50)/2);

		g.setColor(Color.BLACK);
		g.drawLine(0, (gridHeight-50)/2, gridWidth/2, 0);
		g.drawLine(gridWidth/2, (gridHeight-50), gridWidth, (gridHeight-50)/2);

		drawButtons(g);
		
		// for (Point point : towersArrayList)
		// {
		// 	g.setColor(Color.WHITE);
		// 	g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		// 	g.drawString("TOWER", (int)point.getX(), (int)point.getY());
		// }

		g.setFont(new Font("Helvetica", Font.PLAIN, 15));
		
		for (int i = 0; i < playerTowers.size(); i++)
		{
			playerTowers.get(i).draw(g);
		}
		for (int i = 0; i < enemyTowers.size(); i++)
		{
			enemyTowers.get(i).draw(g);
		}

		for (int i = 0; i < playerMinions.size(); i++)
		{
			playerMinions.get(i).draw(g);
		}

		for (int i = 0; i < enemyMinions.size(); i++)
		{
			enemyMinions.get(i).draw(g);
		}

		if (placeDecoy == true)
		{
			g.setColor(new Color(225,255,255));
			g.setFont(new Font("Helvetica", Font.PLAIN, 50));
			g.drawString("X", mouseX - 18, mouseY +16);
		}
		if (placeMelee == true)
		{
			g.setColor(new Color(225,255,0));
			g.setFont(new Font("Helvetica", Font.PLAIN, 50));
			g.drawString("X", mouseX - 18, mouseY +16);
		}
		else if (placeRanged == true)
		{
			g.setColor(new Color(225,56,0));
			g.setFont(new Font("Helvetica", Font.PLAIN, 50));
			g.drawString("X", mouseX - 18, mouseY +16);
		}
		else if (placeSplash == true)
		{
			g.setColor(new Color(96,130,182));
			g.setFont(new Font("Helvetica", Font.PLAIN, 50));
			g.drawString("X", mouseX - 18, mouseY +16);
		}

		if (placeDecoy || placeMelee || placeRanged || placeSplash)
			if ((placeDecoy && resources < COST_DECOY) || (placeMelee && resources < COST_MELEE) || (placeRanged && resources < COST_RANGED) || (placeSplash && resources < COST_SPLASH))
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
		else
			g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 30));
		g.drawString("" + resources, 10, 50);

		int resourcesWidth = g.getFontMetrics().stringWidth("" + resources);
		int resourcesHeight = g.getFontMetrics().getHeight();
		

		g.setColor(Color.BLACK);
		g.drawString("" + enemyResources, gridWidth-g.getFontMetrics().stringWidth("" + enemyResources) - 10, 50);

		mouseX = MouseInfo.getPointerInfo().getLocation().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y;

		if (mouseX > 10 && mouseY > 70 && mouseX < 60 && mouseY < 120)
    		g.setColor(new Color(255, 255, 255, 128));
    	else
    		g.setColor(new Color(255, 255, 255, 64));

    	g.fillRect(10, 70, 50, 50);
    	g.setColor(new Color(255, 255, 255, 255));
    	if (paused)
    	{
    		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
    		g.drawString("Tug of War II", centerHorizontally(g, 0, gridWidth, "Tug of War II"), (gridHeight-50)/2 - 25);
    		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    		g.drawString("The Re-TugOfWarring", centerHorizontally(g, 0, gridWidth, "The Re-TugOfWarring"), (gridHeight-50)/2 + g.getFontMetrics().getHeight() -15);
    		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
    		g.drawString("Akshay Srivatsan", centerHorizontally(g, 0, gridWidth, "Akshay Srivatsan"), (gridHeight-50)/2 + g.getFontMetrics().getHeight()*1 + 70);
    		g.drawString("Ian Loftis", centerHorizontally(g, 0, gridWidth, "Ian Loftis"), (gridHeight-50)/2 + g.getFontMetrics().getHeight()*2 + 70);
    		g.drawString("Josh Campbell", centerHorizontally(g, 0, gridWidth, "Josh Campbell"), (gridHeight-50)/2 + g.getFontMetrics().getHeight()*3 + 70);
    		g.drawString("Julian Christensen", centerHorizontally(g, 0, gridWidth, "Julian Christensen"), (gridHeight-50)/2 + g.getFontMetrics().getHeight()*4 + 70);
    		g.fillPolygon(new int[]{22, 52, 22, 22}, new int[]{80, 95, 110, 90}, 4);
    		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    		if (timeClicks > 50)
    			g.drawString("Choose towers to place.", centerHorizontally(g, 0, gridWidth, "Choose towers to place."), gridHeight-75);
    		if (timeClicks > 100)
    		g.drawString("Destroy the enemy's base.", ENEMYBASE.location.x - g.getFontMetrics().stringWidth("Destroy the enemy's base.")/2, ENEMYBASE.location.y - 75);
    		if (timeClicks > 150)
    			g.drawString("Protect your own base.", PLAYERBASE.location.x - g.getFontMetrics().stringWidth("Protect your own base.")/2, PLAYERBASE.location.y - 75);
    		if (timeClicks > 200)
    			g.drawString("<- Earn resources by defeating enemy minions and bases!", 10 + resourcesWidth + 10, centerVertically(g, 50-resourcesHeight, 50));
    		if (timeClicks > 250)
    		{
    			g.setColor(Color.GREEN);
    			g.drawString("<- Click here to start playing!", 70, centerVertically(g, 70, 120));
    		}

    	}
    	else
    	{
    		g.fillRect(22, 80, 10, 30);
    		g.fillRect(38, 80, 10, 30);
    	}

		if (winner)
		{
			g.setFont(new Font("Helvetica", Font.PLAIN, 30));
			g.setColor(new Color(100,84,82));
    	    g.fillRect(0, 0, World.gridWidth, World.gridHeight);
   	    	g.setColor(Color.WHITE);
    	    g.drawString("Good job! You won after " + gameTime + " time clicks!", 100,100);
		}

		if (loser)
		{
			g.setFont(new Font("Helvetica", Font.PLAIN, 30));
			g.setColor(new Color(100,84,82));
    	    g.fillRect(0, 0, World.gridWidth, World.gridHeight);
   	    	g.setColor(Color.WHITE);
    	    g.drawString("Sorry, you lost after " + gameTime + " time clicks!", 100,100);
		}

		if (mouseX < gridWidth - 10 && mouseY > 70 && mouseX > gridWidth - 60 && mouseY < 120)
    		g.setColor(new Color(255, 0, 0, 128));
    	else
    		g.setColor(new Color(255, 0, 0, 64));
    	g.fillRect(gridWidth - 60, 70, 50, 50);
    	g.setColor(new Color(255, 255, 255, 255));
    	g.fillPolygon(new int[]{gridWidth - 55, gridWidth - 50, gridWidth - 35, gridWidth - 20, gridWidth - 15, gridWidth - 30, gridWidth - 15, gridWidth - 20, gridWidth - 35, gridWidth - 50, gridWidth - 55, gridWidth - 40}, new int[]{80, 75, 90, 75, 80, 95, 110, 115, 100, 115, 110, 95}, 12);



		if (this.getBufferStrategy() != null)
		{
			g.dispose();
			this.getBufferStrategy().show();
			Toolkit.getDefaultToolkit().sync();
		}
    }

    public int centerVertically(Graphics g, int startY, int endY)
    {
		return startY + (endY-startY - g.getFontMetrics().getHeight())/2 + g.getFontMetrics().getHeight();
    }

    public int centerHorizontally(Graphics g, int startX, int endX, String s)
    {
		return startX + (endX-startX - g.getFontMetrics().stringWidth(s))/2;// + g.getFontMetrics().stringWidth(s);
    }
	
	// public void drawButtons(Graphics g)
	// {
	// 	g.setFont(new Font("Helvetica", Font.PLAIN, 15));
	// 	g.setColor(new Color(225,255,0));
	// 	g.fillRect(0, gridHeight-50, gridWidth/3, 50);
	// 	g.setColor(Color.BLACK);
	// 	g.drawString("Melee Tower", gridWidth/8, gridHeight-20);
	// 	g.drawString("100", 0, gridHeight-38);
	// 	g.setColor(new Color(225,56,0));
	// 	g.fillRect(gridWidth/3, gridHeight-50, gridWidth/3, 50);
	// 	g.setColor(Color.BLACK);
	// 	g.drawString("Ranged Tower", gridWidth*7/15, gridHeight-20);
	// 	g.drawString("150", gridWidth/3, gridHeight-38);
	// 	g.setColor(new Color(96,130,182));
	// 	g.fillRect(2*gridWidth/3, gridHeight-50, gridWidth/3, 50);
	// 	g.setColor(Color.BLACK);
	// 	g.drawString("Splash Tower", gridWidth*16/20, gridHeight-20);
	// 	g.drawString("200", gridWidth*2/3, gridHeight-38);
	// }

	public void drawButtons(Graphics g)
	{


		g.setFont(new Font("Helvetica", Font.PLAIN, 15));
		g.setColor(new Color(255,255,255,192));
		if (mouseX > 0 && mouseX < gridWidth/4 && mouseY > gridHeight-50)
			g.setColor(new Color(255, 255, 255));
		if (resources < COST_DECOY)
			g.setColor(new Color(255, 255, 255, 64));
		g.fillRect(0, gridHeight-50, gridWidth/4, 50);
		g.setColor(Color.BLACK);
		g.drawString("Decoy Tower", gridWidth/8-(g.getFontMetrics().stringWidth("Decoy Tower")/2), gridHeight-20);
		g.drawString("" + COST_DECOY, 0, gridHeight-38);


		g.setColor(new Color(225,255,0,192));
		if (mouseX > gridWidth/4 && mouseX < gridWidth*2/4 && mouseY > gridHeight-50)
			g.setColor(new Color(225,255,0));
		if (resources < COST_MELEE)
			g.setColor(new Color(225, 255, 0, 64));
		g.fillRect(gridWidth/4, gridHeight-50, gridWidth/4, 50);
		g.setColor(Color.BLACK);
		g.drawString("Melee Tower", gridWidth/4+gridWidth/8-(g.getFontMetrics().stringWidth("Melee Tower")/2), gridHeight-20);
		g.drawString("" + COST_MELEE, gridWidth/4, gridHeight-38);


		g.setColor(new Color(225,56,0,192));
		if (mouseX > gridWidth*2/4 && mouseX < gridWidth*3/4 && mouseY > gridHeight-50)
			g.setColor(new Color(225,56,0));
		if (resources < COST_RANGED)
			g.setColor(new Color(225, 56, 0, 64));
		g.fillRect(gridWidth/2, gridHeight-50, gridWidth/4, 50);
		g.setColor(Color.BLACK);
		g.drawString("Ranged Tower", gridWidth/2+gridWidth/8-(g.getFontMetrics().stringWidth("Ranged Tower")/2), gridHeight-20);
		g.drawString("" + COST_RANGED, gridWidth/2, gridHeight-38);


		g.setColor(new Color(96,130,182,192));
		if (mouseX > gridWidth*3/4 && mouseY > gridHeight-50)
			g.setColor(new Color(96,130,182));
		if (resources < COST_SPLASH)
			g.setColor(new Color(96, 130, 182, 64));
		g.fillRect(3*gridWidth/4, gridHeight-50, gridWidth/4, 50);
		g.setColor(Color.BLACK);
		g.drawString("Splash Tower", 3*gridWidth/4+gridWidth/8-(g.getFontMetrics().stringWidth("Splash Tower")/2), gridHeight-20);
		g.drawString("" + COST_SPLASH, gridWidth*3/4, gridHeight-38);
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
		mouseX = e.getX();
		mouseY = e.getY();
		// TODO Auto-generated method stub
		if (e.getX() > 10 && e.getY() > 70 && e.getX() < 60 && e.getY() < 120)
		{
			paused ^= true;
		}

		if (e.getX() < gridWidth - 10 && e.getY() > 70 && e.getX() > gridWidth - 60 && e.getY() < 120)
		{
			boolean oldPaused = paused;
			paused = true;
        	GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == JOptionPane.YES_OPTION)
				System.exit(0);
        	GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
			paused = oldPaused;
		}

		if (paused == false && placeDecoy == false && placeMelee == false && placeRanged == false && placeSplash == false)
		{
			
			if (e.getX() > 0 && e.getX() < gridWidth/4 && e.getY() > gridHeight-50)
			{
				placeDecoy = true;
			}
			else if (e.getX() > gridWidth/4 && e.getX() < gridWidth*2/4 && e.getY() > gridHeight-50)
			{
				placeMelee = true;
			}
			else if (e.getX() > gridWidth*2/4 && e.getX() < gridWidth*3/4 && e.getY() > gridHeight-50)
			{
				placeRanged = true;
			}
			else if (e.getX() > gridWidth*3/4 && e.getY() > gridHeight-50)
			{
				placeSplash = true;
			}
			else if (e.getX() < gridWidth/2 && e.getY() > (gridHeight-50)/2 && e.getY() < gridHeight - 50)
			{
				for (Tower t : playerTowers.toArray(new Tower[playerTowers.size()]))
				{
					Point loc = t.getLocation();
					if (!(t instanceof Base) && Math.abs(e.getX() - loc.x) <= 30 && Math.abs(e.getY() - loc.y) <= 30)
					{
						playerTowers.remove(t);
						int towerResources = 0;
						if (t instanceof DecoyTower)
							towerResources = COST_DECOY;
						if (t instanceof MeleeTower)
							towerResources = COST_MELEE;
						if (t instanceof RangedTower)
							towerResources = COST_RANGED;
						if (t instanceof SplashTower)
							towerResources = COST_SPLASH;
						towerResources = (int) (towerResources * t.getHealth()/(double)t.getMaxHealth());
						resources += towerResources;
					}
				}
			}
		}
		else if (paused == false)
		{
			if(e.getY() < gridHeight-50 && e.getY() > (gridHeight-50)/2 && e.getX() < gridWidth/2)
			{
			    Tower t = null;
				if (placeDecoy == true && resources >= COST_DECOY)
				{
					t = new DecoyTower(new Point(e.getX() - 5, e.getY() - 5), 40, 40, enemyMinions, ENEMYBASE.getLocation(), enemyTowers);
					playerTowers.add(t);
					resources -= COST_DECOY;
				}
				if (placeMelee == true && resources >= COST_MELEE)
				{
					t = new MeleeTower(new Point(e.getX() - 5, e.getY() - 5), 40, 40, enemyMinions, ENEMYBASE.getLocation(), enemyTowers);
					playerTowers.add(t);
					resources -= COST_MELEE;
				}
				if (placeRanged == true && resources >= COST_RANGED)
				{
					t = new RangedTower(new Point(e.getX() - 5, e.getY() - 5), 40, 40, enemyMinions, ENEMYBASE.getLocation(), enemyTowers);
					playerTowers.add(t);
					resources -= COST_RANGED;
				}
				if (placeSplash == true && resources >= COST_SPLASH)
				{
					t = new SplashTower(new Point(e.getX() - 5, e.getY() - 5), 40, 40, enemyMinions, ENEMYBASE.getLocation(), enemyTowers);
					playerTowers.add(t);
					resources -= COST_SPLASH;
				}
				//playerTowers.add(t); *** Moved so that playerTowers never contains null ***
				//towersArrayList.add(new Point(e.getX(), e.getY()));
			}
			placeDecoy = false;
			placeMelee = false;
			placeRanged = false;
			placeSplash = false;
		}
	}

    public static void killActor(Actor a)
    {
    	if (enemyMinions.contains(a) || enemyTowers.contains(a))
    		resources += a.getMaxHealth()/5;
    	if (playerMinions.contains(a) || playerTowers.contains(a))
    		enemyResources += a.getMaxHealth()/5;
        Iterator<Minion> i = enemyMinions.iterator();
        while (i.hasNext())
        {
        	Minion b = i.next();
            if (b == a || (b.getLocation().equals(a.getLocation()) && a instanceof Tower))
            {
                i.remove();
            }
        }

        i = playerMinions.iterator();
        while (i.hasNext())
        {
        	Minion b = i.next();
            if (b == a || (b.getLocation().equals(a.getLocation()) && a instanceof Tower))
            {
                i.remove();
            }
        }
        

        Iterator<Tower> j = enemyTowers.iterator();
        while (j.hasNext())
        {
        	Tower b = j.next();
            if (b == a || b.getLocation().equals(a.getLocation()))
            {
            	if (b == ENEMYBASE)
        		{
        			winner = true;
        			paused = true;
        		}
                j.remove();
            }
        }       
        
        j = playerTowers.iterator();
        while (j.hasNext())
        {
        	Tower b = j.next();
            if (b == a)
            {
            	if (b == PLAYERBASE)
        		{
        			loser = true;
        			paused = true;
        		}
                j.remove();
            }
        }
    }

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}	
}