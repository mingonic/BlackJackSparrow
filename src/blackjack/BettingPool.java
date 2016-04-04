package blackjack;

public class BettingPool 
{
	private int totalVal;
	private Chip chip;
	
	BettingPool()
	{
		totalVal = 0;
	}
	
	public void resetPool()
	{
		totalVal = 0;
	}
	
	public void check(Chip c)
	{
		totalVal += c.getChip();
	}
	
}
