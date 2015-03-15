import javax.swing.Timer;
import javax.xml.stream.events.StartDocument;


public class GameRunner
{

	public static World w;
	
	public static void main(String[] args)
	{
		w = new World(1200,800);
	    w.addMouseListener(w);
	    w.addMouseMotionListener(w);
	}

}
