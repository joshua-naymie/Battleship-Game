package client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

//**NOT FINISHED**
public class MainWindow {
	private static JFrame window = new JFrame();

	private static LoginPanel login = new LoginPanel();

	private static final int WINDOW_SIZE_X = 1500, WINDOW_SIZE_Y = 1000;

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
			JPanel playerPanel = new JPanel();

			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;

			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.weightx = 0.3;
			constraints.weighty = 0.3;

			playerPanel.add(meBoard, constraints);
			
			constraints.gridx = 5;
			playerPanel.add(opponentBoard, constraints);

//			constraints.gridx = 0;
//			playerPanel.add(new ChatView(), constraints);

			constraints.gridy = 0;
			constraints.gridx = -6;
			playerPanel.add(new ShipButtonArea(meBoard), constraints);
			window.add(playerPanel);
			window.setVisible(true);

		}

	}
}