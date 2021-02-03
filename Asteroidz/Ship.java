
import java.awt.Polygon;
import java.awt.Rectangle;

public class Ship extends BaseVectorShape 
{
	//Ship dimensions
	private int[] shipx = {000 , -6 , -15  , 00 , 15 , 6, 000};	//default -6, -3, 0, 3, 6, 00
	private int[] shipy = {-15 , 06 , 010  , 15 , 10 , 6, -15};	//default +6, +7, 7, 7, 6, -7
	
	Ship()
	{
		setShape(new Polygon(shipx, shipy, shipx.length));
		setAlive(true);
	}
	
	//bounding rectangle
	public Rectangle getBounds()
	{
		Rectangle r;
		r = new Rectangle((int)getX() - 10, (int)getY() - 10, 22, 22);
		return r;
	}
}
