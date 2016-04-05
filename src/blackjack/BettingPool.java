package blackjack;

public class BettingPool 
{
	private double poolVal;
	private double bet;
	
	BettingPool()
	{
		poolVal = 0.0;
		bet = 5.0;
	}
	
	public void resetPool()
	{
		poolVal = 0.0;
	}
	
	public void addMoney(double n)
	{
		if (n == bet)
			poolVal += n;
		else if (n > bet)
			poolVal += n;
		else if (n < bet)
			System.out.println("Insufficient funds. You must check more money.");
	}
	
	public void addMoneyRaise(double m)
	{
		poolVal += m;
		bet += m;
	}
	
}
