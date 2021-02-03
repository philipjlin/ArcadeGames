/**
 * Asteroidz 1.0
 * A twist of the Atari version
 * CONTROLS:
 * UP ARROW = Move Forward
 * DOWN ARROW = Move Backwards
 * LEFT ARROW = Rotate Left
 * RIGHT ARROW = Rotate Right
 * SHIFT = Stop (in place)
 * SPACE = Fire
 * R = Restart (buggy)
 *
 * Inspired Mainly by Harbour, Jonathan S. in Beginning Java 5 Game Programming
 *
 * @authors: Auroni Gupta and Philip Lin
 */
//package asteroidz;
import java.awt.*;
import java.applet.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.sound.sampled.Clip;



public class Asteroidz extends Applet implements Runnable, KeyListener 
{
	//the main thread becomes the game loop
	Thread gameloop;
	//use this as a double buffer
	BufferedImage backbuffer;
	//the main drawing object for the back buffer
	Graphics2D g2d;
	//toggle for drawing bounding boxes
	boolean showBounds = false;
	
	//create the asteroid array
	int ASTEROIDS = 25;
	Asteroid[] ast = new Asteroid[ASTEROIDS];
	int numAsteroids = ASTEROIDS;
	
	int POWERUPS = 5;
	PowerUp[] pow = new PowerUp[POWERUPS];
	int numPowerUps;
	
	int POWERDOWNS = 5;
	PowerDown[] powd = new PowerDown[POWERDOWNS];
	int numPowerDowns;
	
	//lives
	int lives = 3;
	
	//score
	int score = 0;
	
	//create the bullet array
	int BULLETS = 50;
	Bullet[] bullet = new Bullet[BULLETS];
	int currentBullet = 0;
	
	//the player's ship
	Ship ship = new Ship();
	
	//create the identity transform (0,0)
	AffineTransform identity = new AffineTransform();
	
	//create a random number generator
	Random rand = new Random();
	
	
	public void init() {
		//create the back buffer for smooth graphics
		backbuffer = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);	//default 640,480 //def. app. window 500,300
		g2d = backbuffer.createGraphics();
		
		//set up the ship
		ship.setX(500);		//default 320
		ship.setY(300);		//default 240
		
		//set up the bullets
		for (int n = 0; n<BULLETS; n++)
		{
			bullet[n] = new Bullet();
		}
		
		//set up the asteroids
		for (int n = 0; n < ASTEROIDS; n++)
		{
			ast[n] = new Asteroid();
			ast[n].setRotationVelocity(rand.nextInt(3)+1);
			ast[n].setX((double)rand.nextInt(1000)+20);	//default 600
			ast[n].setY((double)rand.nextInt(200)+20);	//default 440
			ast[n].setMoveAngle(rand.nextInt(360));
			double ang = ast[n].getMoveAngle() - 90;
			ast[n].setVelX(calcAngleMoveX(ang));
			ast[n].setVelY(calcAngleMoveY(ang));
		}
		
		//set up the powerups
		for (int n = 0; n < POWERUPS; n++)
		{
			pow[n] = new PowerUp();
			pow[n].setRotationVelocity(rand.nextInt(3)+1);
			pow[n].setX((double)rand.nextInt(1000)+20);	//default 600
			pow[n].setY((double)rand.nextInt(600)+20);	//default 440
			pow[n].setMoveAngle(rand.nextInt(360));
			double ang = pow[n].getMoveAngle() - 90;
			pow[n].setVelX(calcAngleMoveX(ang));
			pow[n].setVelY(calcAngleMoveY(ang));
			
		}
		
		//set up the powerdowns
		for (int n = 0; n < POWERDOWNS; n++)
		{
			powd[n] = new PowerDown();
			powd[n].setRotationVelocity(rand.nextInt(3)+1);
			powd[n].setX((double)rand.nextInt(1000)+20);	//default 600
			powd[n].setY((double)rand.nextInt(600)+20);	//default 440
			powd[n].setMoveAngle(rand.nextInt(360));
			double ang = powd[n].getMoveAngle() - 90;
			powd[n].setVelX(calcAngleMoveX(ang));
			powd[n].setVelY(calcAngleMoveY(ang));
			
		}
		//load sound effects
		/*AudioInputStream explode;
		URL url = new URL("http://www.mediacollege.com/downloads/sound-effects/explosion/bomb-02.wav");
		explode = AudioSystem.getAudioInputStream(url);
		Clip clip = AudioSystem.getClip();
		clip.open(explode);*/
	
		//start the user input listener
		addKeyListener(this);
	}
	
        public void restart() {
                //create the back buffer for smooth graphics
                backbuffer = new BufferedImage(1000, 700, BufferedImage.TYPE_INT_RGB);  //default 640,480 //def. app. window 500,300
                g2d = backbuffer.createGraphics();
                
                //set up the ship
                ship.setX(500);         //default 320
                ship.setY(350);         //default 240
                
                //set up the bullets
                for (int n = 0; n<BULLETS; n++)
                {
                        bullet[n] = new Bullet();
                }
                
                //set up the asteroids
                for (int n = 0; n < ASTEROIDS; n++)
                {
                        ast[n] = new Asteroid();
                        ast[n].setRotationVelocity(rand.nextInt(3)+1);
                        ast[n].setX((double)rand.nextInt(1000)+20);     //default 600
                        ast[n].setY((double)rand.nextInt(100)+20);      //default 440
                        ast[n].setMoveAngle(rand.nextInt(360));
                        double ang = ast[n].getMoveAngle() - 90;
                        ast[n].setVelX(calcAngleMoveX(ang));
                        ast[n].setVelY(calcAngleMoveY(ang));
                }
                
                //set up the powerups
                for (int n = 0; n < POWERUPS; n++)
                {
                        pow[n] = new PowerUp();
                        pow[n].setRotationVelocity(rand.nextInt(3)+1);
                        pow[n].setX((double)rand.nextInt(1000)+20);     //default 600
                        pow[n].setY((double)rand.nextInt(600)+20);      //default 440
                        pow[n].setMoveAngle(rand.nextInt(360));
                        double ang = pow[n].getMoveAngle() - 90;
                        pow[n].setVelX(calcAngleMoveX(ang));
                        pow[n].setVelY(calcAngleMoveY(ang));
                        
                }
                
                //set up the powerdowns
                for (int n = 0; n < POWERDOWNS; n++)
                {
                        powd[n] = new PowerDown();
                        powd[n].setRotationVelocity(rand.nextInt(3)+1);
                        powd[n].setX((double)rand.nextInt(1000)+20);    //default 600
                        powd[n].setY((double)rand.nextInt(600)+20);     //default 440
                        powd[n].setMoveAngle(rand.nextInt(360));
                        double ang = powd[n].getMoveAngle() - 90;
                        powd[n].setVelX(calcAngleMoveX(ang));
                        powd[n].setVelY(calcAngleMoveY(ang));
                        
                }

        }
	
	public void drawShip()
	{
		//draws the ship
		g2d.setTransform(identity);
		g2d.translate(ship.getX(), ship.getY());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.setColor(Color.CYAN);
		g2d.fill(ship.getShape());
		
		//draw bounding rectangle around ship
		if (showBounds)
		{
			g2d.setTransform(identity);
			g2d.setColor(Color.BLUE);
			g2d.draw(ship.getBounds());
		}		
	}
	
	public void drawBullets()
	{
		for(int n = 0; n < BULLETS; n++)
		{
			if (bullet[n].isAlive())
			{
				//draw the bullet
				g2d.setTransform(identity);
				g2d.translate(bullet[n].getX(), bullet[n].getY());
				g2d.setColor(Color.WHITE);
				g2d.draw(bullet[n].getShape());
			}
		}	
	}
	
	public void drawAsteroids()
	{
		for(int n = 0; n < ASTEROIDS; n++)
		{
			if (ast[n].isAlive())
			{
				//draw the asteroid
				g2d.setTransform(identity);
				g2d.translate(ast[n].getX(), ast[n].getY());
				g2d.rotate(Math.toRadians(ast[n].getMoveAngle()));
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(ast[n].getShape());
				
				//draw bounding rectangle
				if(showBounds)
				{
					g2d.setTransform(identity);
					g2d.setColor(Color.BLUE);
					g2d.draw(ast[n].getBounds());
				}
			}
		}
	}
	
	public void drawPowerUps()
	{
		for (int n = 0; n < POWERUPS; n++)
		{
			if (pow[n].isAlive())
			{
				//draw the powerup
				g2d.setTransform(identity);
				g2d.translate(pow[n].getX(), pow[n].getY());
				g2d.rotate(Math.toRadians(pow[n].getMoveAngle()));
				g2d.setColor(Color.YELLOW);
				g2d.fill(pow[n].getShape());
				
				//draw bounding rectangle
				if(showBounds)
				{
					g2d.setTransform(identity);
					g2d.setColor(Color.BLUE);
					g2d.draw(pow[n].getBounds());
				}
			}
	
		}
	}
	
	//draw Powerdowns
	public void drawPowerDowns()
	{
		for (int n = 0; n < POWERDOWNS; n++)
		{
			if (powd[n].isAlive())
			{
				//draw the powerup
				g2d.setTransform(identity);
				g2d.translate(powd[n].getX(), powd[n].getY());
				g2d.rotate(Math.toRadians(powd[n].getMoveAngle()));
				g2d.setColor(Color.RED);
				g2d.fill(powd[n].getShape());
				
				//draw bounding rectangle
				if(showBounds)
				{
					g2d.setTransform(identity);
					g2d.setColor(Color.BLUE);
					g2d.draw(powd[n].getBounds());
				}
			}
	
		}
	}

	
	public void update(Graphics g)
	{
		//start off transforms at id
		g2d.setTransform(identity);
		
		//erase background
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		
		//print status info
		g2d.setColor(Color.WHITE);
		g2d.drawString("Ship: " + Math.round(ship.getX()) + "," + Math.round(ship.getY()), 5, 10);
		g2d.drawString("Move Angle: " + Math.round(ship.getMoveAngle())+90, 5, 25);
		g2d.drawString("Face Angle: " + Math.round(ship.getFaceAngle()), 5, 40);
		g2d.drawString("Score: " + score, 5, 55);
		g2d.drawString("Lives: " + lives, 5, 70);
		g2d.drawString("Asteroids Remaining: " + numAsteroids, 5, 85);
		
		//draw the game graphics
		drawShip();
		drawBullets();
		drawAsteroids();
		drawPowerUps();
		drawPowerDowns();
		
		//repaint applet windows
		paint(g);
		
		//if 3 lives are lost stop
		if (lives < 1)
		{
			stop();
			g.setColor(Color.ORANGE);
			g.drawString("You Were Destroyed", 400, 200);
			g.drawString("Score: " + score, 400, 290);
			g.drawString("Press 'R' to Restart", 400, 380);
		}
		else if (numAsteroids < 1)
		{
			stop();
			g.setColor(Color.GREEN);
			g.drawString("You Saved Earth From Certain Destruction", 400, 200);
			g.drawString("Score: " + score, 400, 290);
			g.drawString("Press 'R' to Restart", 400, 380);			
		}
	}

	public void paint(Graphics g) 
	{
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void start()
	{
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	public void run()
	{
		//aquire the current thread
		Thread t = Thread.currentThread();
		//keep going as long as thread is alive
		while (t == gameloop)
		{
			try
			{
				//update the game loop
				gameUpdate();
				//shoot for 50 FPS
				Thread.sleep(20);
			} 
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}	
	}
	
	public void stop()
	{
		gameloop = null;		
	}
	
	//move and animate game objects
	private void gameUpdate()
	{
		updateShip();
		updateBullets();
		updateAsteroids();
		updatePowerUps();
		updatePowerDowns();
		checkCollisions();
	}
	
	//Update the ship position
	public void updateShip()
	{
		//update the ships X position, wrap around left/right
		ship.incX(ship.getVelX());
		if (ship.getX() < -10)
			ship.setX(getSize().width + 10);
		else if (ship.getX() > getSize().width + 10)
			ship.setX(-10);
			
		ship.incY(ship.getVelY());
		if (ship.getY() < -10)
			ship.setY(getSize().height + 10);
		else if (ship.getY() > getSize().height + 10)
			ship.setY(-10);
	}
	
	
	public void updateBullets()
	{
		//move the bullets
		for(int n = 0; n < BULLETS; n++)
		{
			if (bullet[n].isAlive())
			{
				//update bullet's x position
				bullet[n].incX(2 * bullet[n].getVelX());
				//bullet disappears at left/right edge
				if (bullet[n].getX() < 0 || bullet[n].getX() > getSize().width)
				{
					bullet[n].setAlive(false);
				}
				//update bullet's y position
				bullet[n].incY(2 * bullet[n].getVelY());
				//bullet disappears at top/bottom edge
				if (bullet[n].getY() < 0 || bullet[n].getY() > getSize().height)
				{
					bullet[n].setAlive(false);
				}
			}
		}
	}
	
	public void updateAsteroids()
	{
		//move and rotate the asteroids
		for (int n = 0; n < ASTEROIDS; n++)
		{
			if (ast[n].isAlive())
			{
				//update the asteroid's X value
				ast[n].incX(2 * ast[n].getVelX());
				if (ast[n].getX() < -10)
					ast[n].setX(getSize().width + 10);
				else if (ast[n].getX() > getSize().width + 10)
					ast[n].setX(-10);
					
				//update the asteroid's Y value
				ast[n].incY(2 * ast[n].getVelY());
				if (ast[n].getY() < -10)
					ast[n].setY(getSize().height + 10);
				else if (ast[n].getY() > getSize().height + 10)
					ast[n].setY(-10);
				
				//update the asteroid's rotation
				ast[n].incMoveAngle(ast[n].getRotationVelocity());
				if (ast[n].getMoveAngle() < 0)
					ast[n].setMoveAngle(360 - ast[n].getRotationVelocity());
				else if (ast[n].getMoveAngle() > 360)
					ast[n].setMoveAngle(ast[n].getRotationVelocity());	
			}
			
		}
	}
	
	public void updatePowerUps()
	{
		//move and rotate the powerups
		for (int n = 0; n < POWERUPS; n++)
		{
			if (pow[n].isAlive())
			{
				//update the powerups X value
				pow[n].incX(5 * pow[n].getVelX());
				if (pow[n].getX() < -5)
					pow[n].setX(getSize().width + 5);
				else if (pow[n].getX() > getSize().width + 5)
					pow[n].setX(-5);
					
				//update the powerups Y value
				pow[n].incY(5 * pow[n].getVelY());
				if (pow[n].getY() < -5)
					pow[n].setY(getSize().height + 5);
				else if (pow[n].getY() > getSize().height + 5)
					pow[n].setY(-5);
				//powerups rotation
				pow[n].incMoveAngle(pow[n].getRotationVelocity());
				if (pow[n].getMoveAngle() < 0)
					pow[n].setMoveAngle(360 - pow[n].getRotationVelocity());
				else if (pow[n].getMoveAngle() > 360)
					pow[n].setMoveAngle(pow[n].getRotationVelocity());	
			}
			
		}
	}
	
	
	//update powerdowns
	public void updatePowerDowns()
	{
		//move and rotate the powerdowns
		for (int n = 0; n < POWERDOWNS; n++)
		{
			if (powd[n].isAlive())
			{
				//update the powerdowns X value
				powd[n].incX(3 * powd[n].getVelX());
				if (powd[n].getX() < -5)
					powd[n].setX(getSize().width + 5);
				else if (powd[n].getX() > getSize().width + 5)
					powd[n].setX(-5);
					
				//update the powerdowns Y value
				powd[n].incY(3 * powd[n].getVelY());
				if (powd[n].getY() < -5)
					powd[n].setY(getSize().height + 5);
				else if (powd[n].getY() > getSize().height + 5)
					powd[n].setY(-5);
				//powerdowns rotation
				powd[n].incMoveAngle(powd[n].getRotationVelocity());
				if (powd[n].getMoveAngle() < 0)
					powd[n].setMoveAngle(360 - powd[n].getRotationVelocity());
				else if (powd[n].getMoveAngle() > 360)
					powd[n].setMoveAngle(powd[n].getRotationVelocity());	
			}	
		}
	}
	
	
	public void checkCollisions()
	{
		//check for ship and bullet collisions with asteroids
		for(int m = 0; m < ASTEROIDS; m++)
		{
			if (ast[m].isAlive())
			{
				//check for bullet collisions
				for (int n = 0; n < BULLETS; n++)
				{
					if (bullet[n].isAlive())
					{
						//perform the collision test
						if(ast[m].getBounds().contains(bullet[n].getX(), bullet[n].getY()))
						{
							bullet[n].setAlive(false);
							ast[m].setAlive(false);
							//explode.start();
							score+=10;
							numAsteroids = numAsteroids - 1;
							continue;
						}
					}
				}
			
				//check for ship collision
				if (ast[m].getBounds().intersects(ship.getBounds()))
				{
					ast[m].setAlive(false);
					//explode.start();
					ship.setX(500);
					ship.setY(350);
					ship.setFaceAngle(0);
					ship.setVelX(0);
					ship.setVelY(0);
					lives -= 1;
					numAsteroids -= 1;
					score -= 20;
					continue;	
				}
			}
		}
		//check ship and bullet collisions with POWERUPS
		for(int m = 0; m < POWERUPS; m++)
		{
			if (pow[m].isAlive())
			{
				//check ship collisions
				if (pow[m].getBounds().intersects(ship.getBounds()))
				{
					pow[m].setAlive(false);
					numPowerUps -= 1;
					score += 20;
					lives += 1;
					continue;	
				}
				//check bullet collisions
				for (int n = 0; n < BULLETS; n++)
				{
					if (bullet[n].isAlive())
					{
						//perform the collision test
						if(pow[m].getBounds().contains(bullet[n].getX(), bullet[n].getY()))
						{
							bullet[n].setAlive(false);
							pow[m].setAlive(false);
							numPowerUps -= 1;
							score += 10;
							lives += 1;
							continue;
						}
					}
				}	
			}
		}
		//check ship and bullet collisions with POWERDOWNS
		for(int m = 0; m < POWERDOWNS; m++)
		{
			if (powd[m].isAlive())
			{
				//check ship collisions
				if (powd[m].getBounds().intersects(ship.getBounds()))
				{
					powd[m].setAlive(false);
					numPowerDowns -= 1;
					score -= 10;
					lives -= 2;
					continue;	
				}
				//check bullet collisions
				for (int n = 0; n < BULLETS; n++)
				{
					if (bullet[n].isAlive())
					{
						//perform the collision test
						if(powd[m].getBounds().contains(bullet[n].getX(), bullet[n].getY()))
						{
							bullet[n].setAlive(false);
							powd[m].setAlive(false);
							numPowerDowns -= 1;
							score -= 10;
							lives -= 2;
							continue;
						}
					}
				}	
			}
		}
	}
			


	//KeyListener Events
	public void keyReleased(KeyEvent k)
	{
	}

	public void keyTyped(KeyEvent k)
	{
	}

	public void keyPressed(KeyEvent k)
	{
		int keyCode = k.getKeyCode();
	

	switch (keyCode)
	{
		case KeyEvent.VK_LEFT:
			ship.incFaceAngle(-20);
			if(ship.getFaceAngle() < 0)
				ship.setFaceAngle(360-5);
		break;
		
		case KeyEvent.VK_RIGHT:
			ship.incFaceAngle(20);
			if(ship.getFaceAngle() > 360)
				ship.setFaceAngle(5);
		break;
		
		case KeyEvent.VK_UP:
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(calcAngleMoveX(ship.getMoveAngle()) * 0.20);
			ship.incVelY(calcAngleMoveY(ship.getMoveAngle()) * 0.20);
		break;
		
		case KeyEvent.VK_CONTROL:
			score -= 2;
        	int num = rand.nextInt(2);
        	if(num == 0)
        	{
        		lives += 1;	
        	}
        	else if (num == 1)
        	{
        		lives -= 1;
        	}
        break;
                        
		case KeyEvent.VK_W:
		        numAsteroids = 0;
		break;

		case KeyEvent.VK_ENTER:
		        ship.setX(500);
		        ship.setY(350);
        break;
		        
		case KeyEvent.VK_SPACE:
			//fire a bullet
			score -= 1;
			currentBullet++;
			if (currentBullet > BULLETS - 1)
				currentBullet = 0;
				bullet[currentBullet].setAlive(true);
				//point bullet in ship's direction
				bullet[currentBullet].setX(ship.getX());
				bullet[currentBullet].setY(ship.getY());
				bullet[currentBullet].setMoveAngle(ship.getFaceAngle() - 90);
				//fire bullet at ship's angle
				double angle = bullet[currentBullet].getMoveAngle();
				double svx = ship.getVelX();
				double svy = ship.getVelY();
				bullet[currentBullet].setVelX(svx + calcAngleMoveX(angle) * 2);
				bullet[currentBullet].setVelY(svy + calcAngleMoveY(angle) * 2);
			
			
		break;
			
		case KeyEvent.VK_DOWN:
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(calcAngleMoveX(ship.getMoveAngle()) * -0.20);
			ship.incVelY(calcAngleMoveY(ship.getMoveAngle()) * -0.20);
		break;
		
		
		case KeyEvent.VK_SHIFT:
			ship.setVelX(0);
			ship.setVelY(0);
		break;
			
		case KeyEvent.VK_B:
			//toggle bounding rectangles
			showBounds = !showBounds;
		break;
		
		case KeyEvent.VK_R:
			//restart
			start();
			lives = 3;
			score = 0;
			numAsteroids = ASTEROIDS;
			numPowerUps = 5;
			numPowerDowns = 5;
            restart  ();
			start();
					
		break;		
	}
	}
		
	public double calcAngleMoveX(double angle)
	{
		return(double)(Math.cos(angle * Math.PI/180));
	}		

	public double calcAngleMoveY(double angle)
	{
		return(double)(Math.sin(angle * Math.PI/180));
	}		
}