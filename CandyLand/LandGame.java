/**
 * @(#)LandGame.java
 *
 *
 * @Philip Lin 
 * @version 1.00 2007/2/3
 */
import chn.util.*;
import java.util.*;

public class LandGame extends Board
{
    
    public static void main (String[] args)
    {
    	final int PLAYERS = 4;
    	boolean winStatus;
    	int ASCII;
    	
    	ConsoleIO keyboard = new ConsoleIO();
    	Board play = new Board();
    	
    	int[] players = new int[PLAYERS];
    	int[] numRolled = new int[PLAYERS];
    	
    	
    	do{  	
    	
    	winStatus = false;
    
    	System.out.print("Roll?: ");
    	String status = keyboard.readLine();
    	
    	if(status.equals("Y") || status.equals("y"))
    	{	
    		for(int turn = 0; turn < players.length; turn++)
    		{	
				ASCII = turn + 65;
				players[turn] = play.setPosition(players[turn]);
				numRolled[turn] = Math.abs(play.getNumRolled());
				
	
					
				System.out.println("Player " + (char)(ASCII) + " Rolled: " + numRolled[turn]);
				
				for(int checkSame = 0; checkSame < players.length; checkSame++)
				{
					if (checkSame != turn)
					{
						if (players[checkSame] == players[turn])
						{
							players[checkSame] = 1;
							System.out.println("Player " + (char)(checkSame+65) + " at START by Contact");
						}	
					}		
				}
				
				/*
				 *	final end print statements could go here if user wanted
				 *	the position of each player to be displayed immediately after roll
				 **/
				
				ASCII++;	
				
				if(play.checkWin(players[turn]) == true)
				{	
					System.out.println("**********************");
					System.out.println("Player " + (char)(ASCII-1) + " wins!!!");
					System.out.println("**********************");	
					winStatus = true;	
				}
				
				
    		}
    		
    	for(int i = 0; i < players.length; i++)
		{
			ASCII = i + 65;
			
			if (play.checkSTART(players[i]) == true)
				System.out.println("\tPlayer " + (char)(ASCII) + " at START");
			if (play.checkEND(players[i]) == true)
				System.out.println("\tPlayer " + (char)(ASCII) + " at END"); 
			if (play.checkSTART(players[i]) == false && play.checkEND(players[i]) == false)
				System.out.println("\tPlayer " + (char)(ASCII) + " at " + players[i]);
				
			ASCII++;
    	}	
    	}
    	else
    		break;		
    		System.out.println();			
	
    	}while(winStatus != true);	
    }  
}