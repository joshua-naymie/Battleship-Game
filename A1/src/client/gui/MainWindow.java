package client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;

//**NOT FINISHED**
public class MainWindow {
	private static JFrame window = new JFrame();

	private static LoginPanel login = new LoginPanel();

	private static final int WINDOW_SIZE_X = 1280, WINDOW_SIZE_Y = 720;

	// ----------------------------------------

	public MainWindow() throws IOException {
		initWindow();
	}

	// ----------------------------------------

	public static void initWindow() throws IOException {
		window.setLayout(new BorderLayout());
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
			playerPanel.setLayout(new BorderLayout());

			playerPanel.add(meBoard, BorderLayout.WEST);
			playerPanel.add(opponentBoard, BorderLayout.EAST);

			playerPanel.add(new ShipButtonArea(meBoard));
			window.add(playerPanel);
			window.setVisible(true);

		}

	}
}