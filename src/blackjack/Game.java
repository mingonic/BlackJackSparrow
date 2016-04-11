package blackjack;

import javax.swing.JButton;

public class Game extends javax.swing.JFrame {				//extend for use of button operations passed from GUI

	//class attributes
	private Player player;										
	private Player dealer;
	private Deck theDeck;
	private BettingPool moneyPit;
	private double bet;	
	private boolean dealersTurn = false;
	private GUI gui;
	
	//constructor
	public Game() {
		player = new Player();
		dealer = new Player();
		theDeck = new Deck();
		theDeck.shuffle();
		moneyPit = new BettingPool();
		gui = new GUI(this);
		
	}

	//mutators and accessors
	public Player getPlayer(){return player;}
	public Player getDealer(){return dealer;}
	public Deck getDeck(){return theDeck;}
	public BettingPool getPool(){return moneyPit;} 
	public void setTurn(boolean n){dealersTurn = n;}
	public boolean getTurn(){return dealersTurn;}
	
	public void hit(JButton stand){								//when the player hits
		player.hit(theDeck);										//hit the deck
		gui.updateHandVal(""+player.getHand().handValue());			//update the GUI hand value
		if (player.getHand().bust()){								//if hitting causes a bust
			gui.standOperations();										//the player must stand
			gui.updateStatus("You busted");								//indicate loss
			gui.allowBets();											//allow bets for new hand
			dealersTurn = false;										//it's not the dealers turn to play
		} else if (player.getHand().has21()) {						//if player has 21
			stand.doClick();											//click stand in GUI
			dealersTurn = true;											//the dealer gets a turn
		}
	}
	
	public void bet(double m, JButton stand){										//when bet is indicated
		dealersTurn = false;															//by default, dears turn is false
		if (m <= player.getWallet().getTotalValue() && m >= 5.0){						//if the bet is more than 5 and less than total wallet value
			newRound();																		//start a new round
			player.bet(m, moneyPit);														//place players bet
			gui.updateWallets(player.getWallet().toString(), dealer.getWallet().toString());//update the wallet in GUI
			gui.allowPlay();																//allow play in the GUI
			gui.disableBets();																//hide the bet button
			dealer.hit(theDeck); dealer.hit(theDeck);										//each player draws to cards
			player.hit(theDeck); player.hit(theDeck);
			gui.updateHandVal(""+player.getHand().handValue());								//update new hand value
			if (player.getHand().has21() || dealer.getHand().has21()) {						//if either player has 21
				dealersTurn = true;																//set the dealers turn to true
				stand.doClick();																//force a stand
			}
		}	
	}
	
	public void resign(){			//if the player resigns
		gui.hidePanels();				//hide the panesl, clear all data, and resign
		newRound();
		gui.resign();	
	}
	
	public void stand() {
		gui.standOperations();
		dealersTurn = true;	
		while (getDealer().getHand().handValue()<17 && dealersTurn) {						//while dealer has hand value of less than 17
			dealer.hit(theDeck);;																//deal dealer a new card
		}
		if (checkWinner())																	//if the player wins
			gui.updateStatus("You win!");														//display that
		else 																				//otherwise
			gui.updateStatus("The dealer wins.");												//the dealer has won
		
		if (getPlayer().getWallet().getTotalValue() < 5.0) 									//if the player's wallet is empty
			gui.updateStatus("You lose and are out of money.\nYou must resign.");				//the player must resign
		else if (getDealer().getWallet().getTotalValue() < 5.0)								//if the dealer's wallet is empty
			gui.updateStatus("The Dealer loses and is out of money.\nThe Dealer must resign.");	//the dealer must resign
		else {																				//otherwise																				//the game can go on
			gui.allowBets();																	//place a bet																									//render the new images
		}
		gui.updateWallets(player.getWallet().toString(), dealer.getWallet().toString());    //update player wallets
	}
	
	/*
	 * Applys logic of winning and losing Blackjack.
	 * player wins when hand is larger than dealers and does not bust.
	 * losses otherwise. Payouts are calculated here.
	 */
	public boolean checkWinner() {
		if (player.getHand().bust()) {
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
		} else if (dealer.getHand().bust()) {
			player.getWallet().addFund(moneyPit.playerWins()); dealer.getWallet().removeFunds(moneyPit.getBet()); moneyPit.resetPool();
			return true;
		} else if (dealer.getHand().blackjack()) {
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
		} else if (player.getHand().blackjack()) {
			player.getWallet().addFund(moneyPit.playerBlackJacks()); dealer.getWallet().removeFunds(moneyPit.playerBlackJacks()); moneyPit.resetPool();
			return true;
		} else if (player.getHand().handValue() > dealer.getHand().handValue()) {
			player.getWallet().addFund(moneyPit.playerWins()); dealer.getWallet().removeFunds(bet); moneyPit.resetPool();
			return true;
		} else {
			dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			return false;
		}
			
	}
	
	public void newRound(){				//when a new round is declared
		theDeck = new Deck();				//create a new deck
		theDeck.shuffle();					
		player.getHand().clearHand();		//clear player hands
		dealer.getHand().clearHand();
		moneyPit.resetPool();				//empty the betting pool
		gui.updateStatus("");				//empty status
	}
		
	public static void main(String[] args) {		//main executes with runnable method where Game is called.
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Game().setVisible(true);
			}
		});
	}
}
