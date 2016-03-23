package blackjack;

public class Card {

	private String suite;
	private String name;
	
	//Constructor
	public Card (String s, String n) {
		suite = s;
		name = n;
	}
	//this is a test comment
	//Mutators
	public String getSuit(){return suite;}
	public String getName(){return name;}
	
	
	public int loVal(){								//Checks the name of the card
		if (name.equals("two"))						//match up the name of the card with the
			return 2;								//value that it's worth
		else if (name.equals("three"))
			return 3;
		else if (name.equals("four"))
			return 4;
		else if (name.equals("five"))
			return 5;
		else if (name.equals("six"))
			return 6;
		else if (name.equals("seven"))
			return 7;
		else if (name.equals("eight"))
			return 8;
		else if (name.equals("nine"))
			return 9;								//10, Jack, Queen, King are all worth 10
		else if (name.equals("ten") || name.equals("Jack") || name.equals("Queen") || name.equals("King"))
			return 10;
		else										//Ace's are worth 1 if playing for low value
			return 1;
	}
	
	
	public int hiVal(){								//exact same logic as above except for the else statement
		if (name.equals("two"))
			return 2;
		else if (name.equals("three"))
			return 3;
		else if (name.equals("four"))
			return 4;
		else if (name.equals("five"))
			return 5;
		else if (name.equals("six"))
			return 6;
		else if (name.equals("seven"))
			return 7;
		else if (name.equals("eight"))
			return 8;
		else if (name.equals("nine"))
			return 9;
		else if (name.equals("ten") || name.equals("Jack") || name.equals("Queen") || name.equals("King"))
			return 10;
		else											//ace returns a value of 11 when played for high value
			return 11;
	}
	
	public String toString(){
		return name + " of " + suite;					//ex String: "7 of Hearts"
	}
	
	public boolean isAce(){								//if this card object is an ace, then return true
		if (name == "ace")								//otherwise false
			return true;
		return false;									//useful for checking if we need to use high or low values.
	}
	
}
