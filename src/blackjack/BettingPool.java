package blackjack;

public class BettingPool 
{
	private double poolVal;				//amount which is bet
	
	public BettingPool() {				//Initialize at 0
		poolVal = 0.0;
	}
	
	public void resetPool() {			//reset pool to 0
		poolVal = 0.0;
	}
	
	public void addMoney(double n) {  	//add $n to pool
		poolVal+=n;
	}
	
	public double playerWins() {		//player win twice the amount in the pool
		return poolVal*2;
	}
	
	public double playerBlackJacks() {	//player wins pool val + val*1.5
		return poolVal+(poolVal*1.5);
	}
	
	public double getBet(){				//returns bet amount
		return poolVal;
	}
	
}
