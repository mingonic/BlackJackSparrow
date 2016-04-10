package blackjack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Card {

	//class attributes
	private String suite;
	private String name;
	private String imageLoc, imageBackLoc;
	private BufferedImage image;
	private static BufferedImage imageBack;			//all images have the same back.
	
	//Constructor
	public Card (String s, String n, String i) {
		suite = s;
		name = n;
		imageLoc = "resources/"+i+".gif";			//location to find card image
		imageBackLoc = "resources/b.gif";			//location to find card back image
		image = initCardImage();
		imageBack = initCardBackImage();
		
	}

	//Accessors
	public String getSuit(){return suite;}
	public String getName(){return name;}
	public BufferedImage getImage(){return image;}
	public BufferedImage getBackImage(){return imageBack;}
	

	public BufferedImage initCardImage(){				//image is read from a file located at imageLoc
		try {
			image = ImageIO.read(new File(imageLoc));
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("IMAGE NOT FOUND");
		}
		return image;
	}
	
	public BufferedImage initCardBackImage(){
		try {
			imageBack = ImageIO.read(new File(imageBackLoc));	//image is read from a file located at imageBackLoc
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("IMAGE NOT FOUND");
		}
		return imageBack;
	}

	
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
