package blackjack;

public class Wallet 
{
	private double total;
	
	public Wallet() {
		total = 50.0;	//player starts with $50
	}
	
	// check if the player has any money left
	public boolean checkEmpty() {
		if (total == 0.0)
			return true;
		else
			return false;
	}
	
	// putting funds on the table removes money from wallet
	public void removeFunds(double n) {
		total -= n;	
	}
	
	// adding money to wallet
	public void addFund(double n) {
		total += n;
	}
	
	// get total value of wallet
	public double getTotalValue() {
		return total;
	}
	
	public String toString() {
		return total+"";
	}
}
