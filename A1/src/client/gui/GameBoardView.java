package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import client.domain.ConnectionManager;

public class GameBoardView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connection;
	
	
	public GameBoardView(ConnectionManager connection) {
		this.connection = connection;
		
		JPanel meAndButtons = new JPanel(new BorderLayout());
		Board meBoard = new Board(true);
		meBoard.setPreferredSize(new Dimension(800,500));
		meBoard.setBackground(Color.black);
		Board opponentBoard = new Board(false);
		opponentBoard.setPreferredSize(new Dimension(800,500));
		opponentBoard.setBackground(Color.black);
		JPanel playerPanel = new JPanel(new BorderLayout());
		meAndButtons.add(meBoard, BorderLayout.NORTH);
		
		playerPanel.setBackground(Color.black);
		
		//playerPanel.setLayout(new BorderLayout(2, 2));
		playerPanel.setBounds(500, 2, 1, 50);
		JPanel shipPanel = new JPanel();
		ShipButtonArea ships = new ShipButtonArea(meBoard);
		
		shipPanel.add(ships);
		shipPanel.setLayout(new FlowLayout());
		shipPanel.setBackground(Color.black);
		// create another panel
		// add the parts to this panel
		// add that panel to the playerPanel

		// add meboard to North
		// add shipButtonArea to Center
		//
		//shipPanel.setLocation(5, 5);
		GameLogoPanel game = new GameLogoPanel();
		game.setBackground(Color.black);
		playerPanel.add(game, BorderLayout.PAGE_START);

		meAndButtons.add(shipPanel, BorderLayout.PAGE_END);
//		playerPanel.add(shipPanel, BorderLayout.PAGE_END);

		playerPanel.add(meAndButtons, BorderLayout.WEST);
		ChatView chat = new ChatView();
		chat.setPreferredSize(new Dimension(200, 200));
		Border border = BorderFactory.createLineBorder(Color.gray);
		chat.setBorder(border);
		
		chat.setBackground(Color.black);
		playerPanel.add(chat, BorderLayout.CENTER);

		playerPanel.add(opponentBoard, BorderLayout.EAST);

		add(playerPanel);
		setVisible(true);
	}
}
