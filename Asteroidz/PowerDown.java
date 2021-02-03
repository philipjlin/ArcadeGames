
import java.awt.Polygon;
import java.awt.Rectangle;

public class PowerDown extends BaseVectorShape
{
	//dimensions
	//private int[] powerdx = {-15, -10, -15, -5, 0, 5, 15, 10, 15, 0};
	//private int[] powerdy = {15, 0, 15, -8, -8, -8, -8, 0, 15, 7};
	
	private int[] powerdx = {-10, 0, 0, 10, -10};
	private int[] powerdy = {0, 20, -20, 0, 0};
	
	

	PowerDown()
	{
		setShape(new Polygon(powerdx, powerdy, powerdx.length));
		setAlive(true);
		setRotationVelocity(0.0);
	}
	
	//rotate speed
	protected double rotVel;
	
	public double getRotationVelocity()
	{
		return rotVel;
	}
	
	public void setRotationVelocity(double v)
	{
		rotVel = v;
	}
	
	//bounding rectangle
	public Rectangle getBounds()
	{
		Rectangle r;
		r = new Rectangle((int)getX() - 15, (int)getY() - 15, 30, 30);
		return r;
	}
	
}