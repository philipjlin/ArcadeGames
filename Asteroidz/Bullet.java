
import java.awt.*;
import java.awt.Rectangle;

public class Bullet extends BaseVectorShape {
	//default constructor
	Bullet()
	{
		//create the bullet shape
		setShape(new Rectangle(0, 0, 2, 2));
		setAlive(false);
	}
	//bounding rectangle
	public Rectangle getBounds()
	{
		Rectangle r;
		r = new Rectangle((int)getX() - 20, (int)getY(), 1, 1);
		return r;
	}
	
	
}
