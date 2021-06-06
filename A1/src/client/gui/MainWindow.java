package client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

//**NOT FINISHED**
public class MainWindow {
	private JFrame window = new JFrame();

	private LoginPanel login = new LoginPanel(this);

	private static final int WINDOW_SIZE_X = 1500, WINDOW_SIZE_Y = 750;

	// ----------------------------------------

	public MainWindow() throws IOException {
		initWindow();
	}

	// ----------------------------------------

	// make method to call from loginpanel, pass socket, pass boolean loggedIn, passUsername
	
	// make a method for all logic with boolean, no if statement
	
	
	public void initWindow() throws IOException {
		window.setLayout(new GridBagLayout());
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.add(login);
		window.setVisible(true);

	}
	
	public void loginSuccesful(Socket socket, String name)
	{
		window.remove(login);
		// make a class for this
		Board meBoard = new Board(true);
		Board opponentBoard = new Board(false);
		JPanel playerPanel = new JPanel(new BorderLayout());
		// we tried to change the backgroun color to black, but it does not work
		
		playerPanel.setBackground(Color.black);

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
		shipPanel.setLocation(5, 5);

		playerPanel.add(new GameLogoPanel(), BorderLayout.PAGE_START);

		playerPanel.add(shipPanel, BorderLayout.PAGE_END);

		playerPanel.add(meBoard, BorderLayout.WEST);

		playerPanel.add(new ChatView(), BorderLayout.CENTER);

		playerPanel.add(opponentBoard, BorderLayout.EAST);

		window.setResizable(false);

		window.add(playerPanel);
		window.setVisible(true);
		// window.getContentPane().setBackground(Color.black);
	}
}
