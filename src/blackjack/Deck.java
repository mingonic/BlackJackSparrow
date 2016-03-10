package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	//instance variables
	private ArrayList<Card> cards = new ArrayList<Card>();	//ArrayList of Card objects.
	private String[] ranks = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
	private String[] suites = new String[]{"spades", "hearts", "diamonds", "clubs"};
	
	//constructor
	public Deck(Card card){
		for (int i = 0; i < suites.length-1; i++){				//for every element in suites
			for (int j = 0; j<ranks.length-1; j++){				//and for every element in ranks
				Card newCard = new Card(i, j);					//create a new Card, newCard, with suite i and rank j
				cards.add(newCard);								//add this card to the ArrayList, cards
			}
		}
	}
	
	//deal method deals a new Card object to the player. This card is the top of the deck (front of ArrayList)
	public Card deal(){
		Card topCard = cards.get[0];			//top card is the front of array list
		cards.remove[0];						//we drew the card, remove from deck
		return topCard;							//return Card to player
	}
	
	public void shuffle(){						//shuffle deck so cards in ArrayList are in random order
		Collections.shuffle(cards);
	}
	
	public String toString(){
		String result = "";
		for (int i = 0; i<this.cards.getLength() - 1; i++){
			result += cards.get(i);
		}
		return result;
	}
	
}