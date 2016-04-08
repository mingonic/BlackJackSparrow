package blackjack;

public class BettingPool 
{
	private double poolVal;
	
	public BettingPool()
	{
		poolVal = 0.0;
	}
	
	public void resetPool()
	{
		poolVal = 0.0;
	}
	
	public void addMoney(double n)
	{
		poolVal+=n;
	}
	
	public double playerWins() {
		return poolVal*2;
	}
	
	public double playerBlackJacks() {
		return poolVal+(poolVal*1.5);
	}
	
	public double getBet(){
		return poolVal;
	}
	
}
