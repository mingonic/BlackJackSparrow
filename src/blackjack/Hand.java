package blackjack;

import java.util.ArrayList;

public class Hand {

	//instance variables
	private ArrayList<Card> playerHand;
	
	public Hand() {
		playerHand = new ArrayList<Card>();											//constructor creates new hand, an ArrayList
	}

	public void addToHand(Card card) {
		playerHand.add(card);														//adds a card object to the hand
	}
	
	public void clearHand() {
		playerHand.clear();
	}
	
	public int handValue(){																
		int score = 0;																//initial score is 0
		int aces = 0;																//num of aces we have in our hand
		int delta = 0;																//difference between hi and low val
		for (int i = 0; i < playerHand.size(); i++){								//for every card in our hand
			score += playerHand.get(i).hiVal();											//add its high value to our score
			if (playerHand.get(i).isAce()){													//if the card is an ace
				aces += 1;																		//add 1 to our ace counter
				delta = playerHand.get(i).hiVal()-playerHand.get(i).loVal();					//add 10 to our delta (diff of hival - loval)
			}
		}
		while (score > 21 && aces > 0){												//while our score is greater than 21 and we have aces in our hand
			score -= delta;																//decrease the score by delta (10)
			aces -= 1;																	//decrease the number of aces in our hand by 1.
		}
		return score;																//return the score
	}
	
	public boolean bust(){															
		if (handValue() > 21)														//if our hand value is greater than 21
			return true;																//return true, we busted
		return false;																//otherwise return false
	}
	
	public boolean blackjack(){
		if (handValue() == 21)														//if our hand value is equal to 21
			return true;																//return true
		return false;																//otherwise return false
	}
	
	public boolean has21() {
		if (handValue() != 21)
			return false;
		return true;
	}
	
	public String toString(){
		String result = "";
		for (int i = 0; i < playerHand.size(); i++){
			result+=playerHand.get(i).toString() + "\n";
		}
		return result;
	}
	
}