package blackjack;

import java.awt.event.*;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

import sun.security.util.SecurityConstants.AWT;

public class Game extends javax.swing.JFrame {

	private Player player;
	private Player dealer;
	private Deck theDeck;
	private BettingPool moneyPit;
	double bet;	
	ActionListener buttonHit;
	boolean dealersTurn = false;
	GUI gui;
	
	public Game() {
		player = new Player();
		dealer = new Player();
		theDeck = new Deck();
		theDeck.shuffle();
		moneyPit = new BettingPool();
		gui = new GUI(this);
		
	}

	public Player getPlayer(){return player;}
	public Player getDealer(){return dealer;}
	public Deck getDeck(){return theDeck;}
	public BettingPool getPool(){return moneyPit;} 
	public void setTurn(boolean n){dealersTurn = n;}
	public boolean getTurn(){return dealersTurn;}
	
	public void hit(JButton stand){
		player.hit(theDeck);
		gui.updateHandVal(""+player.getHand().handValue());
		if (player.getHand().bust()){
			gui.standOperations();
			gui.updateStatus("You busted");
			gui.allowBets();
			dealersTurn = false;
		} else if (player.getHand().has21()) {
			stand.doClick();
			dealersTurn = true;
		}
	}
	
	public void bet(double m, JButton stand){
		dealersTurn = false;
		if (m <= player.getWallet().getTotalValue() && m >= 5.0){
			newRound();
			player.bet(m, moneyPit);
			gui.updateWallets(player.getWallet().toString(), dealer.getWallet().toString());
			gui.allowPlay();
			gui.disableBets();
			dealer.hit(theDeck); dealer.hit(theDeck);;
			player.hit(theDeck); player.hit(theDeck);
			gui.updateHandVal(""+player.getHand().handValue());
			if (player.getHand().has21() || dealer.getHand().has21()) {
				dealersTurn = true;
				stand.doClick();
			}
		}	
	}
	
	public void resign(){
		gui.hidePanels();
		newRound();
		gui.resign();	
	}
	
	public void stand() {
		gui.standOperations();
		dealersTurn = true;	
		while (getDealer().getHand().handValue()<17 && dealersTurn) {																//while dealer has hand value of less than 17
			dealer.hit(theDeck);;																//deal dealer a new card
		}
		if (checkWinner())																						//if the player wins
			gui.updateStatus("You win!");																					//display that
		else 																									//other wise
			gui.updateStatus("The dealer wins.");																			//the dealer has won
		
		if (getPlayer().getWallet().getTotalValue() < 5.0) 														//if the player's wallet is empty
			gui.updateStatus("You lose and are out of money.\nYou must resign.");											//the player must resign
		else if (getDealer().getWallet().getTotalValue() < 5.0)												//if the dealer's wallet is empty
			gui.updateStatus("The Dealer loses and is out of money.\nThe Dealer must resign.");							//the dealer must resign
		else {																										//otherwise																				//the game can go on
			gui.allowBets();																				//place a bet																									//render the new images
		}
		gui.updateWallets(player.getWallet().toString(), dealer.getWallet().toString());
	}
	
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
	
	public void newRound(){
		theDeck = new Deck();
		theDeck.shuffle();
		player.getHand().clearHand();
		dealer.getHand().clearHand();
		moneyPit.resetPool();
		gui.updateStatus("");
	}
		
	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Game().setVisible(true);
			}
		});
	}
}
