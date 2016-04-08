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
	private JPanel board, dash, playerInfo, betInfo, playerWalletInfo, dealerWalletInfo;
	private JButton hit, stand, resign, play, placeBet;
	private JLabel handVal, walletVal, dealerWallet, betValue, deck, playerName, gameStatus;
	private JLabel[] playerHandHolder, dealerHandHolder;
	private JTextField playerNameField, betField, currentHandVal, currentWalletVal, currentDWalletVal, currentBetVal;
	
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
		
		hit = new JButton("Hit!");
		hit.addActionListener(this);
		hit.setEnabled(false);
		stand = new JButton("Stand");
		stand.addActionListener(this);
		resign = new JButton("Resign");
		play = new JButton("Play");
		placeBet = new JButton("Bet");
		placeBet.addActionListener(this);
		
		handVal = new JLabel("Player's hand value: ");
		walletVal = new JLabel("Player's wallet value: ");
		dealerWallet = new JLabel("Dealer's wallet value: ");

		betValue = new JLabel("Current bet: $");
		deck = new JLabel();
		playerName = new JLabel("Player's Name");
		gameStatus = new JLabel("");
		gameStatus.setVisible(true);
		
		playerHandHolder = new JLabel[11];
		dealerHandHolder = new JLabel[11];
		
		playerNameField = new JTextField(15);
		playerNameField.setEditable(false);
		betField = new JTextField(6);
		currentHandVal = new JTextField(2);
		currentHandVal.setEditable(false);
		currentWalletVal = new JTextField(6);
		currentWalletVal.setEditable(false);
		currentDWalletVal = new JTextField(6);
		currentDWalletVal.setEditable(false);
		currentBetVal = new JTextField(6);

		
		playerInfo.add(handVal);
		playerInfo.add(currentHandVal);
		
		betInfo.add(betValue);
		betInfo.add(currentBetVal);
		
		playerWalletInfo.add(walletVal);
		playerWalletInfo.add(currentWalletVal);
		
		dealerWalletInfo.add(dealerWallet);
		dealerWalletInfo.add(currentDWalletVal);
		
		board.add(gameStatus);

		
		dash.add(playerInfo);
		dash.add(hit);
		dash.add(betInfo);
		dash.add(placeBet);
		dash.add(playerWalletInfo);
		dash.add(stand);
		dash.add(dealerWalletInfo);
		
		add(board, BorderLayout.NORTH);
		add(dash, BorderLayout.SOUTH);
		
		setTitle("BlackJackSparrow");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hit) {
			newCard = game.getDeck().deal();
			game.getPlayer().addToHand(newCard);
			currentHandVal.setText(""+game.getPlayer().getHand().handValue());
			if (game.getPlayer().getHand().handValue() > 21) {
				hit.setEnabled(false);
				stand.setEnabled(false);
				gameStatus.setText("You lose");
				placeBet.setVisible(true);
				currentBetVal.setEditable(true);
				game.setTurn(true);
			} else if (game.getPlayer().getHand().has21()) {
				stand.doClick();
				stand.setEnabled(false);
				game.setTurn(true);
				hit.setEnabled(false);
			}
			repaint();
			
		} else if (e.getSource() == placeBet) {
			game.newRound();

			game.getPlayer().bet(Double.parseDouble(this.currentBetVal.getText()), game.getPool());
			hit.setEnabled(true);
			stand.setEnabled(true);
			game.setTurn(false);
			currentWalletVal.setText(game.getPlayer().getWallet().toString());
			currentBetVal.setEditable(false);
			currentDWalletVal.setText(game.getDealer().getWallet().toString());
			placeBet.setVisible(false);
			game.getDealer().addToHand(game.getDeck().deal()); game.getDealer().addToHand(game.getDeck().deal());
			game.getPlayer().addToHand(game.getDeck().deal()); game.getPlayer().addToHand(game.getDeck().deal());
			currentHandVal.setText(""+game.getPlayer().getHand().handValue());
			if (game.getPlayer().getHand().has21() || game.getDealer().getHand().has21()) {
				if (game.checkWinner()) {
					gameStatus.setText("You win!");
				} else {
					gameStatus.setText("The dealer wins.");
				}
			}
			repaint();
			
		} else if(e.getSource() == stand){
			hit.setEnabled(false);
			stand.setEnabled(false);
			game.setTurn(true);
			while (game.getDealer().getHand().handValue()<17) {
				game.getDealer().addToHand(game.getDeck().deal());
			}
			if (game.checkWinner()) {
				gameStatus.setText("You win!");
			} else {
				gameStatus.setText("The dealer wins.");
			}			
			placeBet.setVisible(true);
			currentBetVal.setEditable(true);
			repaint();
			
		}
		
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);

		g.drawImage(card.getBackImage(), 800, 250, null);
		//g.drawImage(ImageIO.read(new File("resources/2c.gif")), 0, 0, null);

		for(int i = 1; i < game.getDealer().getHand().getPlayerHand().size()+1; i++) {
			if (i==1 && !(game.getDealer().getHand().handValue() > 16))
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
