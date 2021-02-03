/**
 * @(#)Board.java
 *
 *
 * @Philip Lin 
 * @version 1.00 2007/2/3
 */

public class Board extends Dice
{
	public final int END = 50;
	public final int START = 1;
	int cellPosition;
	int numRolled;
	
    public Board() 
    {
    }
    
    	
	Dice dice = new Dice();
	
	public int setPosition(int cellPosition)
	{
		
		numRolled = dice.rollDice();
		if (cellPosition == 0)
			cellPosition = 1+ cellPosition + numRolled;
		else
			cellPosition = cellPosition + numRolled;
		
			if (cellPosition < START)
				cellPosition = START;
			if (cellPosition > END)
				cellPosition = END;
				
		return cellPosition;
	}
	
	public int getNumRolled()
	{
		return numRolled;
	}
	
	public boolean checkSTART(int cellPosition)
	{
		if (cellPosition == START)
			return true;
		else
			return false;
	}
	
	public boolean checkEND(int cellPosition)
	{
		if (cellPosition == END)
			return true;
		else
			return false;
	}
	
	public boolean checkWin(int cellPosition)
	{
		if (cellPosition >= END)
			return true;
		else
			return false;
	}
	

}