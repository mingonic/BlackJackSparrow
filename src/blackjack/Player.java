package blackjack;

public class Player {
	//Shows players status (hit, stand, fold, bet)
	private String status;
	private Hand hand;
	private Wallet wallet;
	String name;
	
	public Player(String n){
		name = n;
		hand = new Hand();
		wallet = new Wallet();
	}
	//Get methods:
	public Hand getHand(){
		return hand;
	}
	public Wallet getWallet(){
		return wallet;
	}
	//player decides to hit, new card gets added to players hand:
	public void hit(Deck d){
		status = "hit";
		System.out.println(name + " has decided to hit.");
		hand.addToHand(d.deal() );
	}
	
	public void addToHand(Card card){
		hand.addToHand(card);
	}
	
	//player decides to stand:
	public void stand(){
		status = "stand";
		System.out.println(name + " has decided to stand.");
	}
	//Player adds funds to the betting pool:
	public void bet(double a, BettingPool b){
		if (wallet.getTotalValue() >= a){
			status = "bet";
			wallet.removeFunds(a);
			b.addMoney(a);
		}
		else
			System.out.println(name + " does not have sufficient funds.");
	}
	//Player decides to fold:
	public void fold(){
		status = "fold";
		System.out.println(name + " has folded");
	}
	//Player quits, program terminates:
	public void quit(){
		System.out.println(name + " has decided to quit. Game Over.");
		System.exit(0);
	}
}
