package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	//instance variables
	private ArrayList<Card> cards = new ArrayList<Card>();	//ArrayList of Card objects.
	private String[] ranks = new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "Jack", "Queen", "King", "ace"};
	private String[] suites = new String[]{"spades", "hearts", "diamonds", "clubs"};
	String filechar;							//for GUI
	String filerank;							//for GUI
	String imgFile;								//for GUI
	//String dir = "/resources";					//for GUI
	
	//constructor
	public Deck(){
		for (int i = 0; i < suites.length; i++){				//for every element in suites
			if (suites[i].equals("spades")){				//starting here, determine which image in resources directory should be used
				filechar = "s";
			} else if (suites[i].equals("hearts")) {
				filechar = "h";
			} else if (suites[i].equals("diamonds")) {
				filechar = "d";
			} else {
				filechar = "c";
			}
			for (int j = 0; j<ranks.length; j++){				//and for every element in ranks
				
				if (ranks[j].equals("two")){
					filerank = "2";
				}else if (ranks[j].equals("three")){
					filerank = "3";
				}else if (ranks[j].equals("four")){
					filerank = "4";
				}else if (ranks[j].equals("five")){
					filerank = "5";
				}else if (ranks[j].equals("six")){
					filerank = "6";
				}else if (ranks[j].equals("seven")){
					filerank = "7";
				}else if (ranks[j].equals("eight")){
					filerank = "8";
				}else if (ranks[j].equals("nine")){
					filerank = "9";
				}else if (ranks[j].equals("ten")){
					filerank = "t";
				}else if (ranks[j].equals("Jack")){
					filerank = "j";
				}else if (ranks[j].equals("Queen")){
					filerank = "q";
				}else if (ranks[j].equals("King")){
					filerank = "k";
				}else{
					filerank = "a";
				}
				
				imgFile = filerank+filechar;								//concatenate the string
				Card newCard = new Card(suites[i], ranks[j], imgFile);		//create a new Card, newCard, with suite i and rank j
				cards.add(newCard);											//add this card to the ArrayList, cards
			}
		}
	}
	
	//deal method deals a new Card object to the player. This card is the top of the deck (back of ArrayList)
	public Card deal(){
		Card topCard = cards.get(cards.size()-1); //top card is the front of array list
		cards.remove(cards.size()-1);			  //we drew the card, remove from deck
		return topCard;						      //return Card to player
	}
	
	public void shuffle(){						//shuffle deck so cards in ArrayList are in random order
		Collections.shuffle(cards);
	}
	
	public String toString(){							//prints out the Cards of the deck on every line
		String result = "";
		for (int i = 0; i<this.cards.size(); i++){
			result += cards.get(i) + "\n";
		}
		return result;
	}
	
}