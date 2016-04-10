package blackjack;

public class Wallet 
{
	private double total;
	
	public Wallet()
	{
		total = 50.0;
	}
	
	// check if the player has any money/chips left
	public boolean checkEmpty()
	{
		if (total == 0.0)
			return true;
		else
			return false;
	}
	
	// putting funds on the table
	public void removeFunds(double n)
	{
		total -= n;
	}
	
	// adding money
	public void addFund(double n)
	{
		total += n;
	}
	
	// get total number of chips
	public double getTotalValue()
	{
		return total;
	}
	
	public String toString()
	{
		return total+"";
	}
}
