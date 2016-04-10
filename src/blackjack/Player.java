package blackjack;

public class Player {

	private Hand hand;
	private Wallet wallet;
	
	public Player() {
		hand = new Hand();
		wallet = new Wallet();
	}
	
	//Get methods:
	public Hand getHand(){return hand;}
	public Wallet getWallet(){return wallet;}

	
	//player decides to hit, new card gets added to players hand:
	public void hit(Deck d) {
		hand.addToHand(d.deal() );
	}
	
	public void addToHand(Card card) {
		hand.addToHand(card);
	}
	
	//Player adds funds to the betting pool:
	public void bet(double a, BettingPool b) {
		if (wallet.getTotalValue() >= a){
			wallet.removeFunds(a);
			b.addMoney(a);
		}

	}

}
