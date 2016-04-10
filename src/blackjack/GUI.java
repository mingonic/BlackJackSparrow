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
	
	public GUI(Game parentGame){
		card = new Card("clubs", "ace", "2c");
		game = parentGame;
		
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
			game.hit(stand);
			repaint();
		} else if (e.getSource() == placeBet) {
			game.bet(Double.parseDouble(currentBetVal.getText()), stand);
			repaint();
		} else if (e.getSource() == stand) {
			game.stand();
			repaint();
		} else if (e.getSource() == resign) {
			resign.setVisible(false);
			game.resign();
			repaint();
		}
	}

	

	public void paint(Graphics g) {
		super.paintComponents(g);

		g.drawImage(card.getBackImage(), 800, 250, null);

		for(int i = 1; i < game.getDealer().getHand().getPlayerHand().size()+1; i++) {
			if (i==1)
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null);
			else if ((game.getDealer().getHand().handValue() > 16 && game.getTurn())|| game.getPlayer().getHand().handValue() >= 21) {
				System.out.println("oops");
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 50, null);
			} else
				g.drawImage(game.getDealer().getHand().getPlayerHand().get(i-1).getBackImage(), 50*i, 50, null);
		}
		
		for(int i = 1; i < game.getPlayer().getHand().getPlayerHand().size()+1; i++) {
			g.drawImage(game.getPlayer().getHand().getPlayerHand().get(i-1).getImage(), 50*i, 500, null);
		}
			
	}
	
	public void updateHandVal(String h) {
		currentHandVal.setText(h);
	}
	
	public void updateWallets(String p, String d){
		currentWalletVal.setText(p);
		currentDWalletVal.setText(d);
	}
	
	public void updateStatus(String s) {
		gameStatus.setText(s);
	}

	public void standOperations(){
		hit.setEnabled(false);																							//the player cannot hit
		stand.setEnabled(false);
		resign.setVisible(true);
	}
	
	public void allowPlay() {
		hit.setEnabled(true);																							//allow player to hit
		stand.setEnabled(true);	
		resign.setVisible(false);
	}
	
	public void allowBets() {
		placeBet.setVisible(true);																						//to play again, place another bet
		currentBetVal.setEditable(true);	
	}
	
	public void disableBets() {
		placeBet.setVisible(false);																						//to play again, place another bet
		currentBetVal.setEditable(false);	
	}
	
	public void hidePanels() {
		dash.setVisible(false);
		board.setVisible(false);
	}

}
