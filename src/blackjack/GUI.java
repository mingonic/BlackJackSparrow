package blackjack;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
	Card card;
	static int count = 0;
	private JPanel board, dash, playerInfo, betInfo, playerWalletInfo, dealerWalletInfo;
	private JButton hit, stand, resign;
	private JLabel handVal, walletVal, dealerWallet, betValue, deck, playerName;
	private JLabel[] playerHandHolder, dealerHandHolder;
	private JTextField playerNameField, betField, currentHandVal, currentWalletVal, currdentDWalletVal, currentBetVal;
	
	public GUI(){
		card = new Card("clubs", "ace", "2c");
		JFrame frame = new JFrame("BlackJackSparrow");
		board = new JPanel(new BorderLayout());
		dash = new JPanel(new GridLayout(2, 3, 3, 3));
		playerInfo = new JPanel();
		betInfo = new JPanel();
		playerWalletInfo = new JPanel();
		dealerWalletInfo = new JPanel();
		
		hit = new JButton("Hit!");
		hit.addActionListener(this);
		stand = new JButton("Stand");
		resign = new JButton("Resign");
		
		JLabel handVal = new JLabel("Player's hand value: ");
		JLabel walletVal = new JLabel("Player's wallet value: ");
		JLabel dealerWallet = new JLabel("Dealer's wallet value: ");
		JLabel promptBet = new JLabel("Enter your bet: $");
		JLabel betValue = new JLabel("Current bet: $");
		JLabel deck = new JLabel();
		JLabel playerName = new JLabel("Player's Name");
		
		JLabel[] playerHandHolder = new JLabel[11];
		JLabel[] dealerHandHolder = new JLabel[11];
		
		JTextField playerNameField = new JTextField(15);
		playerNameField.setEditable(false);
		JTextField betField = new JTextField(6);
		JTextField currentHandVal = new JTextField(2);
		currentHandVal.setEditable(false);
		JTextField currentWalletVal = new JTextField(6);
		currentWalletVal.setEditable(false);
		JTextField currentDWalletVal = new JTextField(6);
		currentDWalletVal.setEditable(false);
		JTextField currentBetVal = new JTextField(6);
		currentBetVal.setEditable(false);
		
		//board.add(dealerHandHolder, BorderLayout.NORTH);
		//board.add(playerHandHolder, BorderLayout.SOUTH);
		
		playerInfo.add(handVal);
		playerInfo.add(currentHandVal);
		
		betInfo.add(betValue);
		betInfo.add(currentBetVal);
		
		playerWalletInfo.add(walletVal);
		playerWalletInfo.add(currentWalletVal);
		
		dealerWalletInfo.add(dealerWallet);
		dealerWalletInfo.add(currentDWalletVal);
		

		dash.add(playerInfo);
		dash.add(hit);
		dash.add(playerWalletInfo);
		dash.add(betInfo);
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
			System.out.println("ITS A ME" + count);
			repaint();
			count++;
		}
		
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);


		//g.drawImage(ImageIO.read(new File("resources/2c.gif")), 0, 0, null);

		g.drawImage(card.getImage(), 0, 0, null);

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
			
	}
	
	public static void main(String[] args){
		GUI game = new GUI();
	}
	
	
	
	
}
