package blackjack;

import java.util.Scanner;

public class Game {

	private Player player;
	private Player dealer;
	private Deck theDeck;
	private BettingPool moneyPit;
	Scanner uinput = new Scanner(System.in);									//get ready for some sick user input
	String decision;															//to hit, or not to hit, this is the question
	String again = "Y";															//play again? Y for yes, N for no
	double bet;	
	boolean dealersTurn = false;
	
	public Game() {
		player = new Player();
		dealer = new Player();
		theDeck = new Deck();
		theDeck.shuffle();
		moneyPit = new BettingPool();
	}
	
	public Player getPlayer(){return player;}
	public Player getDealer(){return dealer;}
	public Deck getDeck(){return theDeck;}
	public BettingPool getPool(){return moneyPit;}
	public boolean getTurn(){return dealersTurn;}
	public void setTurn(boolean n){dealersTurn=n;}
	
	public boolean checkWinner() {
		if (player.getHand().bust()) {
			System.out.println("You busted! The Dealer wins!");
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
			
		} else if (dealer.getHand().bust()) {
			System.out.println("The Dealer busted! You win " + moneyPit.playerWins() + "!");
			player.getWallet().addFund(moneyPit.playerWins()); dealer.getWallet().removeFunds(bet); moneyPit.resetPool();
			return true;
			
		} else if (dealer.getHand().blackjack()) {
			System.out.println("The Dealer has a Blackjack! The Dealer wins!");
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
		} else if (player.getHand().blackjack()) {
			System.out.println("You have a Blackjack! You win " + moneyPit.playerBlackJacks() + "!");
			player.getWallet().addFund(moneyPit.playerBlackJacks()); dealer.getWallet().removeFunds(moneyPit.playerBlackJacks()); moneyPit.resetPool();
			return true;
		} else if (player.getHand().handValue() > dealer.getHand().handValue()) {
			System.out.println("You win " + moneyPit.playerWins() + "!");
			player.getWallet().addFund(moneyPit.playerWins()); dealer.getWallet().removeFunds(bet); moneyPit.resetPool();
			return true;
		} else {
			System.out.println("The dealer wins!");
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
		}
	}
	
	public void newRound(){
		theDeck = new Deck();
		theDeck.shuffle();
		player.getHand().clearHand();
		dealer.getHand().clearHand();
	}
	
}
