package blackjack;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
	
	
	private Game game;
	private Card card;
	Card newCard;

	static int count = 0;
	private JPanel board, dash, playerInfo, betInfo, playerWalletInfo, dealerWalletInfo, gameInfo;
	private JButton hit, stand, resign, placeBet;
	private JLabel handVal, walletVal, dealerWallet, betValue, gameStatus, gameOver;
	private JTextField playerNameField, currentHandVal, currentWalletVal, currentDWalletVal, currentBetVal;
	
	public GUI(){
		card = new Card("clubs", "ace", "2c");
		game = new Game();
		
		JFrame frame = new JFrame("BlackJackSparrow");
		board = new JPanel(new BorderLayout());
		dash = new JPanel(new GridLayout(2, 4, 3, 3));
		playerInfo = new JPanel();
		betInfo = new JPanel();
		playerWalletInfo = new JPanel();
		dealerWalletInfo = new JPanel();
		gameInfo = new JPanel();
		
		hit = new JButton("Hit!");
		hit.addActionListener(this);
		hit.setEnabled(false);
		stand = new JButton("Stand");
		stand.addActionListener(this);
		resign = new JButton("Resign");
		resign.addActionListener(this);
		resign.setVisible(false);
		placeBet = new JButton("Bet");
		placeBet.addActionListener(this);
		
		handVal = new JLabel("Player's hand value: ");
		walletVal = new JLabel("Player's wallet value: ");
		dealerWallet = new JLabel("Dealer's wallet value: ");
		gameOver = new JLabel("");
		betValue = new JLabel("Current bet: $");
		gameStatus = new JLabel("");
		gameStatus.setVisible(true);
		
		playerNameField = new JTextField(15);
		playerNameField.setEditable(false);
		currentHandVal = new JTextField(2);
		currentHandVal.setEditable(false);
		currentWalletVal = new JTextField(6);
		currentWalletVal.setEditable(false);
		currentDWalletVal = new JTextField(6);
		currentDWalletVal.setEditable(false);
		currentBetVal = new JTextField(6);

		gameInfo.add(gameStatus, BorderLayout.NORTH);
		gameInfo.add(gameOver, BorderLayout.CENTER);
		
		playerInfo.add(handVal);
		playerInfo.add(currentHandVal);
		
		betInfo.add(betValue);
		betInfo.add(currentBetVal);
		
		playerWalletInfo.add(walletVal);
		playerWalletInfo.add(currentWalletVal);
		
		dealerWalletInfo.add(dealerWallet);
		dealerWalletInfo.add(currentDWalletVal);
		
		board.add(gameInfo, BorderLayout.CENTER);

		
		dash.add(playerInfo);
		dash.add(hit);
		dash.add(betInfo);
		dash.add(placeBet);
		dash.add(playerWalletInfo);
		dash.add(stand);
		dash.add(dealerWalletInfo);
		dash.add(resign);
		
		//add(gameInfo, BorderLayout.CENTER);
		add(board, BorderLayout.NORTH);
		add(dash, BorderLayout.SOUTH);
		
		setTitle("BlackJackSparrow");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {																	//when an action is detected
		if (e.getSource() == hit) {																						//if the player hit
			newCard = game.getDeck().deal();																				//deal a new card
			game.getPlayer().addToHand(newCard);																			//give the card to the player
			currentHandVal.setText(""+game.getPlayer().getHand().handValue());												//update the player's hand value
			if (game.getPlayer().getHand().bust()) {																		//if the player busted
				hit.setEnabled(false);																							//then the player can't hit
				stand.setEnabled(false);																						//and the player can't stand
				gameStatus.setText("You lose");																					//the player loses this hand
				placeBet.setVisible(true);																						//to play again, place another bet
				currentBetVal.setEditable(true);																				//allow player to place another bet
				//game.setTurn(true);																								
			} else if (game.getPlayer().getHand().has21()) {																//if the player has a hand value of 21
				stand.doClick();																								//indicate stand automatically
				stand.setEnabled(false);																						//the player is already standing
				game.setTurn(true);																								//it's the dealers turn
				hit.setEnabled(false);																							//the player cannot hit
			}
			repaint();																										//render new card images
			
		} else if (e.getSource() == placeBet) {																			//if the player placed a bet
			if (Double.parseDouble(currentBetVal.getText()) <= game.getPlayer().getWallet().getTotalValue() 				//if the player has enough to bet
					&& Double.parseDouble((currentBetVal.getText())) >= 5.0){
			
				game.newRound();																							//then start a new round
				game.getPool().resetPool();
				
				game.getPlayer().bet(Double.parseDouble(this.currentBetVal.getText()), game.getPool());							//place the indicated bet
				hit.setEnabled(true);																							//allow player to hit
				stand.setEnabled(true);																							//allow player to stand
				game.setTurn(false);																							//allow dealers next turn
				currentWalletVal.setText(game.getPlayer().getWallet().toString());												//update player wallet value
				currentBetVal.setEditable(false);																				//disable editing of bet field
				currentDWalletVal.setText(game.getDealer().getWallet().toString());												//update dealer wallet value
				placeBet.setVisible(false);																						//the bet has been placed. hide this
				game.getDealer().addToHand(game.getDeck().deal()); game.getDealer().addToHand(game.getDeck().deal());			//give the dealer two cards
				game.getPlayer().addToHand(game.getDeck().deal()); game.getPlayer().addToHand(game.getDeck().deal());			//give the player two cards
				currentHandVal.setText(""+game.getPlayer().getHand().handValue());												//update players hand value
				if (game.getPlayer().getHand().has21() || game.getDealer().getHand().has21()) {									//if the player or dealer have 21 pts
					resign.setVisible(true);																							//show the resign button
					if (game.getPlayer().getHand().has21()) {																			//if the player has 21
						stand.doClick();																									//indicate stand
					} 
					if (game.getDealer().getHand().has21()) {																			//if the dealer has 21
						placeBet.setVisible(true);
						gameStatus.setText("The dealer wins.");
					}
					stand.setEnabled(false);																							//player's already standing
					hit.setEnabled(false);																								//player cannot hit
				}
			}
			repaint();																										//render new cards																										
			
		} else if(e.getSource() == stand){																				//if the player stands
			hit.setEnabled(false);																							//the player cannot hit
			stand.setEnabled(false);																						//the player cannot stand
			game.setTurn(true);																								//it is the dealers turn
			resign.setVisible(true);																						//allow player to resign
			
			while (game.getDealer().getHand().handValue()<17) {																//while dealer has hand value of less than 17
				game.getDealer().addToHand(game.getDeck().deal());																//deal dealer a new card
			}
			if (game.checkWinner()) {																						//if the player wins
				gameStatus.setText("You win!");																					//display that
			} else {																										//other wise
				gameStatus.setText("The dealer wins.");																			//the dealer has won
			}
			
			if (game.getPlayer().getWallet().getTotalValue() < 5.0) {														//if the player's wallet is empty
				gameStatus.setText("You lose and are out of money.\nYou must resign.");											//the player must resign
			} else if (game.getDealer().getWallet().getTotalValue() < 5.0) {												//if the dealer's wallet is empty
				gameStatus.setText("The Dealer loses and is out of money.\nThe Dealer must resign.");							//the dealer must resign
			} else {																										//otherwise
				placeBet.setVisible(true);																						//the game can go on
				currentBetVal.setEditable(true);																				//place a bet																									//render the new images
			}
			
			repaint();																										//render the new images
			
		} else if(e.getSource() == resign) {																			//if the player resigns
			dash.setVisible(false);																							//hide the dash board
			game.newRound();																								//reset hands to hide images
			repaint();																										//render nothing
			gameOver.setText("Game Over");																					//say game over conditions
		}
		
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);

		g.drawImage(card.getBackImage(), 800, 250, null);
		//g.drawImage(ImageIO.read(new File("resources/2c.gif")), 0, 0, null);

		for(int i = 1; i < game.getDealer().getHand().getPlayerHand().size()+1; i++) {
			if (i==1)
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null);
			else if ((game.getDealer().getHand().handValue() > 16 && game.getTurn())|| game.getPlayer().getHand().handValue() >= 21)
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null);
			else
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getBackImage(), 50*i, 50, null);
		}
		
		for(int i = 1; i < game.getPlayer().getHand().getPlayerHand().size()+1; i++) {
			g.drawImage(game.getPlayer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 500, null);
		}

		//g.drawImage(card.getImage(), 50, 50, null);
		//g.drawImage(card.getBackImage(), 800, 250, null);
		/*
		if (count==0) {
			g.drawRect(230,  80,  10,  10);
			g.setColor(Color.BLACK);
		} else if (count==1){
			g.setColor(Color.RED);
			g.drawRect(230,  200,  10,  10);
		} else {
			g.drawRect(230,  80,  10,  10);
			g.setColor(Color.BLACK);
		}
		*/
			
	}
	
	public static void main(String[] args){
		GUI game = new GUI();
	}

}
