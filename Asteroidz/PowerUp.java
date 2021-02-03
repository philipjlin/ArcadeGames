import java.awt.Polygon;
import java.awt.Rectangle;

public class PowerUp extends BaseVectorShape
{
	//dimensions
	private int[] powerx = {-15, -5, 0, 5, 15, 5, 0, -5};
	private int[] powery = {0, 5, 15, 5, 0, -5, -15, -5};


	PowerUp()
	{
		setShape(new Polygon(powerx, powery, powerx.length));
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
