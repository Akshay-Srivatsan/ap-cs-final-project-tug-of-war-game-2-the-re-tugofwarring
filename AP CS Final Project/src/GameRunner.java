/***
By: , Akshay, Ian, Josh, Julian
Description: The wrapper class for World, just creates a World class instance.
**/
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
