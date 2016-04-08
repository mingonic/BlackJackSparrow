package blackjack;

import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		
		//set up the game variables
		BettingPool moneyPit = new BettingPool();									//initialize the bettingPool object
		Scanner uinput = new Scanner(System.in);									//get ready for some sick user input
		String decision;															//to hit, or not to hit, this is the question
		String again = "Y";															//play again? Y for yes, N for no
		double bet;																	//how much are you willing to bet?
		
		System.out.print("Enter your name: ");										//We're friendly here
		
		Player player = new Player(uinput.next());									//Initialize both players
		Player dealer = new Player("the Dealer ");
		
		//display our stacks of cash
		System.out.println("Levi's wallet: " + player.getWallet().getTotalValue() + "\nDealer's wallet: " + player.getWallet().getTotalValue());
		
		while (again.equals("Y") && player.getWallet().getTotalValue()>=5.00) {											//while again is Y and we have enough money to bet
			
			Deck theDeck = new Deck();																					//create a new deck
			theDeck.shuffle();																							//shuffle the deck
			player.getHand().clearHand(); dealer.getHand().clearHand();													//clear the player's hands
			boolean dealersTurn = true;																					//the dealer has to know when to deal
			
			System.out.print("Place a bet higher than $5.00: ");														//demand cash
			bet = uinput.nextDouble();																					//store the cash in memory
			while (bet < 5.00 || bet > player.getWallet().getTotalValue()) {											//while the cash is less than min bet or more than the player owns
				if (bet > player.getWallet().getTotalValue()) {																//if the bet is more than the player owns
					System.out.print("You do not have that much to bet. Enter an amount you can bet: ");						//display and demand less money
					bet = uinput.nextDouble();
				} else {																								//otherwise display and demand more money
					System.out.print("Enter a bet higher than $5.00: ");
					bet = uinput.nextDouble();
				}
			}
			
			player.bet(bet, moneyPit);																					//place the bet in the moneyPit
			
			dealer.addToHand(theDeck.deal());																			//add two cards to each player's hand. The dealer shows one of his cards.
			player.addToHand(theDeck.deal());
			Card revealedCard = theDeck.deal();												
			dealer.addToHand(revealedCard);																				//this is the dealers revealed card
			player.addToHand(theDeck.deal());
			
			System.out.println("The Dealer is showing the " + revealedCard);											//show the revealed card
			
			System.out.println("\nYour hand: \n" + player.getHand());													//print the user's hand
			System.out.println("You have a total of " + player.getHand().handValue() + " points."); 					//print the amount of points the player has
			System.out.println("Enter 'h' to hit or 's' to stand: ");	 												//demand player hit or stand
			decision = uinput.next();																					
		
			while (decision.equals("h")) {																				//while the player decides to hit
				player.addToHand(theDeck.deal());																			//deal the player a card
				System.out.println("Your hand: \n" + player.getHand());														//print the players hand
				
				if (player.getHand().bust()){																				//if the player busts
					dealersTurn = false;																						//the dealer does not need to go, the player has lost.
					decision = "s";
				} else if (player.getHand().blackjack()) {																	//if the player has a blackjack
					System.out.println("You have a Blackjack!");																//print out that fact
					decision = "s";																								//player doesn't need to hit
				} else if (player.getHand().has21()) {																		//if the player has 21 points
					System.out.println("you have 21 points!");  																//print out that fact
					decision = "s";
				} else {
					System.out.println("You have a total of " + player.getHand().handValue() + " points."); 				//otherwise print the amount of points the player has
					System.out.println("Enter 'h' to hit or 's' to stand: ");	 											//and get the decision to hit or stand.
					decision = uinput.next();
				}
			}
				
			while (dealersTurn) {																						//while it's the dealers turn to hit
				System.out.println("\nThe Dealer has: \n" + dealer.getHand());												//print the dealer's hand
				System.out.println("The Dealer has a total of " + dealer.getHand().handValue());							//and his hand value
				
				if (dealer.getHand().handValue() > 16) {																	//if the dealers hand value is > 16
					System.out.println("\nThe Dealer stands.\n");																//the dealer will stand
					dealersTurn = false;
				} else {																									//otherwise
					Card newCard = theDeck.deal();																				//draw the dealer a new card
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
