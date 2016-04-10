package blackjack;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {												//require actionlistener interface

	//class attributes
	private Game game;																					//we need to call game methods
	private Card card;																					//we need a card for the deck
	private JPanel board, dash, playerInfo, betInfo, playerWalletInfo, dealerWalletInfo, gameInfo;
	private JButton hit, stand, resign, placeBet;
	private JLabel handVal, walletVal, dealerWallet, betValue, gameStatus, gameOver;
	private JTextField currentHandVal, currentWalletVal, currentDWalletVal, currentBetVal;
	
	//constructor
	public GUI(Game parentGame){										//this GUI is for the parentGame
		card = new Card("clubs", "ace", "2c");							//card to the deck image
		game = parentGame;												//game to call methods on
		
		board = new JPanel(new GridLayout(3, 3, 3, 3));					//new board panel for images
		dash = new JPanel(new GridLayout(2, 4, 3, 3));					//dash will handle most operations
		playerInfo = new JPanel();										//the following panels host components		
		betInfo = new JPanel();
		playerWalletInfo = new JPanel();
		dealerWalletInfo = new JPanel();
		gameInfo = new JPanel();
		
		hit = new JButton("Hit!");					//allow players to hit
		hit.addActionListener(this);
		hit.setEnabled(false);
		stand = new JButton("Stand");				//allow players to stand
		stand.addActionListener(this);
		resign = new JButton("Resign");				//allow players to resign
		resign.addActionListener(this);
		resign.setVisible(false);
		placeBet = new JButton("Bet");				//allow players to bet
		placeBet.addActionListener(this);
		
		handVal = new JLabel("Player's hand value: ");			
		walletVal = new JLabel("Player's wallet value: $");
		dealerWallet = new JLabel("Dealer's wallet value: $");
		gameOver = new JLabel("");
		gameOver.setFont(new Font("Serif", Font.BOLD, 20));
		betValue = new JLabel("Current bet: $");
		gameStatus = new JLabel("");
		gameStatus.setVisible(true);
		gameStatus.setFont(new Font("Serif", Font.BOLD, 20));
		
		currentHandVal = new JTextField(2);			//Displays player hand value
		currentHandVal.setEditable(false);			//shouldn't be edited
		currentWalletVal = new JTextField(6);		//Displays player wallet value
		currentWalletVal.setEditable(false);
		currentDWalletVal = new JTextField(6);		//Displays dealer's wallet value
		currentDWalletVal.setEditable(false);
		currentBetVal = new JTextField(6);			//Accepts betting amount

		gameInfo.add(gameStatus, BorderLayout.NORTH);	//add the gameStatus and gameOver JLabels to panel
		gameInfo.add(gameOver, BorderLayout.CENTER);
		
		playerInfo.add(handVal);						//components for hand value
		playerInfo.add(currentHandVal);
		
		betInfo.add(betValue);							//Components for bet value
		betInfo.add(currentBetVal);
		
		playerWalletInfo.add(walletVal);				//Components for wallet value
		playerWalletInfo.add(currentWalletVal);
		
		dealerWalletInfo.add(dealerWallet);				//Components for dealer
		dealerWalletInfo.add(currentDWalletVal);
		
		dash.add(playerInfo);							//add the components which display
		dash.add(hit);									//info to the dashboard panel
		dash.add(betInfo);
		dash.add(placeBet);
		dash.add(playerWalletInfo);
		dash.add(stand);
		dash.add(dealerWalletInfo);
		dash.add(resign);
		
		add(board, BorderLayout.NORTH);					//add all panels to the JFrame
		add(gameInfo, BorderLayout.CENTER);
		add(dash, BorderLayout.SOUTH);
		
		setTitle("BlackJackSparrow");					//Title of frame
		setSize(900, 700);								//size
		setLocationRelativeTo(null);					
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when it's closed... close it
		setResizable(false);							//resizing will hide elements such as cards
		setVisible(true);								//we want to see the window
		
	}
	
	public void actionPerformed(ActionEvent e) {								//when an action is detected
		if (e.getSource() == hit) {													//if the player hit
			game.hit(stand);															//call the games hit method, get it the stand button
			repaint();																	//rerender game (call to paint())
		} else if (e.getSource() == placeBet) {										//if a bet is placed
			game.bet(Double.parseDouble(currentBetVal.getText()), stand);				//give the game the bet and stand
			repaint();																	//rerender game
		} else if (e.getSource() == stand) {										//if the player stands
			game.stand();																//call the games stand method
			repaint();																	//rerender game
		} else if (e.getSource() == resign) {										//if the player resigns
			resign.setVisible(false);													//hide the resign button
			game.resign();																//call games resign method
			repaint();																	//render the game
		}
	}

	public void paint(Graphics g) {														//when rendering the game
		super.paintComponents(g);															//paint components

		g.drawImage(card.getBackImage(), 800, 250, null);										//paint the deck image

		for(int i = 1; i < game.getDealer().getHand().getPlayerHand().size()+1; i++) {			//for every card in dealers hand
			if (i==1)																				//always paint the first one face up
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null);
			else if ((game.getDealer().getHand().handValue() > 16 && game.getTurn())|| game.getPlayer().getHand().handValue() >= 21) {
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null); //paint the others face up if the game is over
			} else																					//otherwise paint them facedown
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getBackImage(), 50*i, 50, null);
		}
		
		for(int i = 1; i < game.getPlayer().getHand().getPlayerHand().size()+1; i++) {			//always paint the players cards face up
			g.drawImage(game.getPlayer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 500, null);
		}
			
	}
	
	public void updateHandVal(String h) {
		currentHandVal.setText(h);					//set JLabel text to h
	}
	
	public void updateWallets(String p, String d){
		currentWalletVal.setText(p);				//set JLabel text to p
		currentDWalletVal.setText(d);				//set JLabel text to d
	}
	
	public void updateStatus(String s) {
		gameStatus.setText(s);
	}

	public void standOperations(){
		hit.setEnabled(false);						//the player cannot hit
		stand.setEnabled(false);					//the player cannot stand
		resign.setVisible(true);					//the player is allowed to resign
	}
	
	public void allowPlay() {
		hit.setEnabled(true);						//allow player to hit
		stand.setEnabled(true);						//allow player to stand
		resign.setVisible(false);					//player can't resign during round
	}
	
	public void allowBets() {
		placeBet.setVisible(true);					//to play again, place another bet
		currentBetVal.setEditable(true);			//allow bet input
	}
	
	public void disableBets() {
		placeBet.setVisible(false);					//Disable bets while in play
		currentBetVal.setEditable(false);			//disable bet input while in play
	}
	
	public void hidePanels() {
		dash.setVisible(false);						//hide the game
		board.setVisible(false);
	}
	
	public void resign() {
		gameStatus.setText("Game Over:");				//upon resigning, indicate game over
		gameOver.setText(" You have resigned");
	}

}
