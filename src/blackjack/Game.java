package blackjack;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		
		//set up the game
		Scanner uinput = new Scanner(System.in);
		Deck theDeck = new Deck();
		theDeck.shuffle();
		boolean dealersTurn = true;
		String decision;															//determines hit or stand
		
		System.out.print("Enter your name: ");
		
		Player player = new Player(uinput.next());
		Player dealer = new Player("the Dealer ");
		
		//add two cards to each player's hand. The dealer shows one of his cards.
		dealer.addToHand(theDeck.deal());
		player.addToHand(theDeck.deal());
		Card revealedCard = theDeck.deal();												
		dealer.addToHand(revealedCard);													//this is the dealers revealed card
		player.addToHand(theDeck.deal());
		
		System.out.println("The Dealer is showing the " + revealedCard);
		
		System.out.println("Your hand: \n" + player.getHand());	
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
			System.out.println("\nThe Dealer has: " + dealer.getHand());
			System.out.println("The Dealer has a total of " + dealer.getHand().handValue());
			
			if (dealer.getHand().handValue() > 16 && !dealer.getHand().bust()) {
				System.out.println("The Dealer stands.");
				dealersTurn = false;
			} else {
				Card newCard = theDeck.deal();
				System.out.println("The dealer hits and draws the " + newCard);
				dealer.getHand().addToHand(newCard);
			}
		}
			
			if (player.getHand().bust())
				System.out.println("You busted! The Dealer wins!");
			else if (dealer.getHand().bust())
				System.out.println("The Dealer busted! You win!");
			else if (dealer.getHand().blackjack())
				System.out.println("The Dealer has a Blackjack! The Dealer wins!");
			else if (player.getHand().blackjack())
				System.out.println("You have a Blackjack! You win!");
			else if (player.getHand().handValue() > dealer.getHand().handValue())
				System.out.println("You win!");
			else
				System.out.println("The dealer wins!");
		
	}
	
}
