package blackjack;

public class Player {
	//Shows players status (hit, stand, fold, bet)
	private Hand hand;
	private Wallet wallet;
	//private String name;
	
	public Player(){
		//name = n;
		hand = new Hand();
		wallet = new Wallet();
	}
	//Get methods:
	public Hand getHand(){return hand;}
	public Wallet getWallet(){return wallet;}
	//public String getName(){return name;}
	
	//player decides to hit, new card gets added to players hand:
	public void hit(Deck d){
		System.out.println("You have decided to hit.");
		hand.addToHand(d.deal() );
	}
	
	public void addToHand(Card card){
		hand.addToHand(card);
	}
	
	//player decides to stand:
	public void stand(){
		System.out.println("You have decided to stand.");
	}
	//Player adds funds to the betting pool:
	public void bet(double a, BettingPool b){
		System.out.println("in player");
		if (wallet.getTotalValue() >= a){
			wallet.removeFunds(a);
			b.addMoney(a);
		}
		else
			System.out.println("You do not have sufficient funds.");
	}
	//Player decides to fold:
	public void fold(){
		System.out.println("You have folded");
	}

}
