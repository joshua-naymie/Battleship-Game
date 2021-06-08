package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameBoardView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameBoardView() {
		Board meBoard = new Board(true);
		Board opponentBoard = new Board(false);
		JPanel playerPanel = new JPanel(new BorderLayout());

		playerPanel.setBackground(Color.black);
		
		//playerPanel.setLayout(new BorderLayout(2, 2));
		playerPanel.setBounds(500, 2, 1, 50);
		JPanel shipPanel = new JPanel();
		ShipButtonArea ships = new ShipButtonArea(meBoard);
		shipPanel.add(ships);
		shipPanel.setLayout(new FlowLayout());
		// create another panel
		// add the parts to this panel
		// add that panel to the playerPanel

		// add meboard to North
		// add shipButtonArea to Center
		//
		//shipPanel.setLocation(5, 5);

		playerPanel.add(new GameLogoPanel(), BorderLayout.PAGE_START);

		playerPanel.add(shipPanel, BorderLayout.PAGE_END);

		playerPanel.add(meBoard, BorderLayout.WEST);

		playerPanel.add(new ChatView(), BorderLayout.CENTER);

		playerPanel.add(opponentBoard, BorderLayout.EAST);

		add(playerPanel);
		setVisible(true);
		

	}

}
