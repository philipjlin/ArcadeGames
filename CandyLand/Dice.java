/**
 * @(#)Dice.java
 *
 *
 * @Philip Lin 
 * @version 1.00 2007/2/3
 */
import java.util.*;

public class Dice 
{
    final int SIDES = 8;
    
    public Dice() 
    {
    }
    
    Random rand = new Random();
    
    public int rollDice()
    {
    	int roll = rand.nextInt(SIDES) + 1;
    	
    	if (roll == 4 || roll == 6)
    		roll *= -1;
    	else
    		roll *= 1;

    	return roll;			
    }
    
}