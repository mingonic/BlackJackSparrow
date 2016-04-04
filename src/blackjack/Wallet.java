package blackjack;

import java.util.ArrayList;

public class Wallet 
{
	private ArrayList<Chip> chip;
	
	public Wallet()
	{
		
		chip = new ArrayList<Chip>(50);
	}
	
	// check if the player has any money/chips left
	public boolean checkEmpty()
	{
		return chip.isEmpty();
	}
	
	// putting funds on the table
	public void removeFunds(int n)
	{
		int limit = (chip.size()-1) - n;
		
		for (int i=chip.size()-1; i>=limit; i--)
		{
			chip.remove(i);
		}
	}
	
	// adding money
	public void addFund(int n, Chip c)
	{
		for (int i=0; i<n; i++)
		{
			chip.add(c);
		}
	}
	
	// get total number of chips
	public int getTotalValue()
	{
		return chip.size();
	}
}
