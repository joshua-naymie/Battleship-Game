package client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

//**NOT FINISHED**
public class MainWindow {
	private static JFrame window = new JFrame();

	private static LoginPanel login = new LoginPanel();

	private static final int WINDOW_SIZE_X = 1500, WINDOW_SIZE_Y = 750;

	// ----------------------------------------

	public MainWindow() throws IOException {
		initWindow();
	}

	// ----------------------------------------

	public static void initWindow() throws IOException {
		window.setLayout(new GridBagLayout());
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.add(login);
		window.setVisible(true);

		if (login.isLoggedIn) {

			window.remove(login);

			Board meBoard = new Board(true);
			Board opponentBoard = new Board(false);
			JPanel playerPanel = new JPanel(new BorderLayout());
			// we tried to change the backgroun color to black, but it does not work
			playerPanel.setBackground(Color.black);

			JPanel shipPanel = new JPanel();
			ShipButtonArea ships = new ShipButtonArea(meBoard);
			shipPanel.add(ships);
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
}
