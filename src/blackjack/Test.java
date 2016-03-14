package blackjack;

public class Test {

	public static void main(String[] args){
		Deck deck = new Deck();												//create new Deck object
		
		System.out.println("This is our deck!\n\n" + deck + "\n");			//Print the deck of cards
		
		deck.shuffle();														//shuffle deck
		
		System.out.println("This is our shuffled deck!\n\n" + deck + "\n");		//Print the shuffled deck
		
		Card myCard = deck.deal();											//deals a card
		
		System.out.println("We drew the " + myCard + "!\n");				//prints that card
		
		System.out.println("Updated deck after drawing:\n" + deck);
	}
	
}
