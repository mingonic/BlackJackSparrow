package blackjack;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		
		//set up the game
		BettingPool moneyPit = new BettingPool();
		Scanner uinput = new Scanner(System.in);
		boolean dealersTurn = true;
		String decision;															//determines hit or stand
		String again = "Y";
		double bet;
		
		System.out.print("Enter your name: ");
		
		Player player = new Player(uinput.next());
		Player dealer = new Player("the Dealer ");
		
		System.out.println("Levi's wallet: " + player.getWallet().getTotalValue() + "\nDealer's wallet: " + player.getWallet().getTotalValue());
		
		while (again.equals("Y") && player.getWallet().getTotalValue()>=5.00) {
			
			Deck theDeck = new Deck();
			theDeck.shuffle();
			player.getHand().clearHand(); dealer.getHand().clearHand();
			
			System.out.print("Place a bet higher than $5.00: ");
			bet = uinput.nextDouble();
			while (bet < 5.00 || bet > player.getWallet().getTotalValue()) {
				if (bet > player.getWallet().getTotalValue()) {
					System.out.print("You do not have that much to bet. Enter an amount you can bet: ");
					bet = uinput.nextDouble();
				} else {
					System.out.print("Enter a bet higher than $5.00: ");
					bet = uinput.nextDouble();
				}
			}
			
			player.bet(bet, moneyPit);
			
			//add two cards to each player's hand. The dealer shows one of his cards.
			dealer.addToHand(theDeck.deal());
			player.addToHand(theDeck.deal());
			Card revealedCard = theDeck.deal();												
			dealer.addToHand(revealedCard);													//this is the dealers revealed card
			player.addToHand(theDeck.deal());
			
			System.out.println("The Dealer is showing the " + revealedCard);
			
			System.out.println("\nYour hand: \n" + player.getHand());	
			System.out.println("You have a total of " + player.getHand().handValue() + " points."); //otherwise print the amount of points the player has
			System.out.println("Enter 'h' to hit or 's' to stand: ");	 					//and get the decision to hit or stand.
			decision = uinput.next();
		
			while (decision.equals("h")) {			//while the player decides to hit
				player.addToHand(theDeck.deal());
				System.out.println("Your hand: \n" + player.getHand());										//print the players hand
				
				if (player.getHand().bust()){																//if the player busts
					dealersTurn = false;															//the dealer does not need to go, the player has lost.
					decision = "s";
				} else if (player.getHand().blackjack()) {													//if the player has a blackjack
					System.out.println("You have a Blackjack!");									//print out that fact
					decision = "s";
				} else if (player.getHand().has21()) {														//if the player has 21 points
					System.out.println("you have 21 points!");  									//print out that fact
					decision = "s";
				} else {
					System.out.println("You have a total of " + player.getHand().handValue() + " points."); //otherwise print the amount of points the player has
					System.out.println("Enter 'h' to hit or 's' to stand: ");	 					//and get the decision to hit or stand.
					decision = uinput.next();
				}
			}
				
			while (dealersTurn) {
				System.out.println("\nThe Dealer has: \n" + dealer.getHand());
				System.out.println("The Dealer has a total of " + dealer.getHand().handValue());
				
				if (dealer.getHand().handValue() > 16 || !dealer.getHand().bust()) {
					System.out.println("\nThe Dealer stands.\n");
					dealersTurn = false;
				} else {
					Card newCard = theDeck.deal();
					System.out.println("\nThe dealer hits and draws the " + newCard);
					dealer.getHand().addToHand(newCard);
				}
			}		
			
			if (player.getHand().bust()) {
				System.out.println("You busted! The Dealer wins!");
				dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
				
			} else if (dealer.getHand().bust()) {
				System.out.println("The Dealer busted! You win " + bet*2 + "!");
				player.getWallet().addFund(moneyPit.playerWins()); moneyPit.resetPool();
				
			} else if (dealer.getHand().blackjack()) {
				System.out.println("The Dealer has a Blackjack! The Dealer wins!");
				dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
				
			} else if (player.getHand().blackjack()) {
				System.out.println("You have a Blackjack! You win " + moneyPit.playerBlackJacks() + "!");
				player.getWallet().addFund(moneyPit.playerBlackJacks()); moneyPit.resetPool();
				
			} else if (player.getHand().handValue() > dealer.getHand().handValue()) {
				System.out.println("You win " + moneyPit.playerWins() + "!");
				player.getWallet().addFund(moneyPit.playerWins()); moneyPit.resetPool();
				
			} else {
				System.out.println("The dealer wins!");
				dealer.getWallet().addFund(moneyPit.getBet()); moneyPit.resetPool();
			}
			
			System.out.println("Levi's wallet: " + player.getWallet().getTotalValue() + "\nDealer's wallet: " + player.getWallet().getTotalValue());
			
			System.out.println("\n\nEnd of round.\n");
			System.out.print("Play again? 'Y' or 'N'? ");
			again = uinput.next();
			while (!(again.equals("Y") || again.equals("N"))){
				System.out.println("Enter Y for yes, N for no: ");
				again = uinput.next();
			}
			
			
			
		}
		
	}
	
}
