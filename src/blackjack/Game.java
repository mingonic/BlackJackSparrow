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
		
		Hand dealer = new Hand();
		Hand player = new Hand();
		
		//add two cards to each player's hand. The dealer shows one of his cards.
		dealer.addToHand(theDeck.deal());
		player.addToHand(theDeck.deal());
		Card revealedCard = theDeck.deal();												
		dealer.addToHand(revealedCard);													//this is the dealers revealed card
		player.addToHand(theDeck.deal());
		
		System.out.println("The Dealer is showing the " + revealedCard);
		
		System.out.println("Your hand: \n" + player);	
		System.out.println("You have a total of " + player.handValue() + " points."); //otherwise print the amount of points the player has
		System.out.println("Enter 'h' to hit or 's' to stand: ");	 					//and get the decision to hit or stand.
		decision = uinput.next();
		
		
		
		while (decision.equals("h")) {			//while the player decides to hit
			player.addToHand(theDeck.deal());
			System.out.println("Your hand: \n" + player);										//print the players hand
			
			if (player.bust()){																//if the player busts
				dealersTurn = false;															//the dealer does not need to go, the player has lost.
				decision = "s";
			} else if (player.blackjack()) {													//if the player has a blackjack
				System.out.println("You have a Blackjack!");									//print out that fact
				decision = "s";
			} else if (player.has21()) {														//if the player has 21 points
				System.out.println("you have 21 points!");  									//print out that fact
				decision = "s";
			} else {
				System.out.println("You have a total of " + player.handValue() + " points."); //otherwise print the amount of points the player has
				System.out.println("Enter 'h' to hit or 's' to stand: ");	 					//and get the decision to hit or stand.
				decision = uinput.next();
			}
		}
			
		while (dealersTurn) {
			System.out.println("\nThe Dealer has: " + dealer);
			System.out.println("The Dealer has a total of " + dealer.handValue());
			
			if (dealer.handValue() > 16 && !dealer.bust()) {
				System.out.println("The Dealer stands.");
				dealersTurn = false;
			} else {
				Card newCard = theDeck.deal();
				System.out.println("The dealer hits and draws the " + newCard);
				dealer.addToHand(newCard);
			}
		}
			
			if (player.bust())
				System.out.println("You busted! The Dealer wins!");
			else if (dealer.bust())
				System.out.println("The Dealer busted! You win!");
			else if (dealer.blackjack())
				System.out.println("The Dealer has a Blackjack! The Dealer wins!");
			else if (player.blackjack())
				System.out.println("You have a Blackjack! You win!");
			else if (player.handValue() > dealer.handValue())
				System.out.println("You win!");
			else
				System.out.println("The dealer wins!");
		
	}
	
}
