package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.swing.*;
import javax.swing.border.Border;

import server.domain.NC;

//**NOT FINISHED**
public class MainWindow {
	private JFrame window = new JFrame();

	private LoginPanel login = new LoginPanel(this);

	private static final int WINDOW_SIZE_X = 1500, WINDOW_SIZE_Y = 750;

	private JButton disconnect;

	// ----------------------------------------

	public MainWindow() {
		initWindow();
	}

	public void initWindow() {
//		window.setLayout(new GridBagLayout());
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.add(login);
		window.setVisible(true);

		// disconnect button

	}

	public void loginSuccesful(String userName) {

		window.remove(login);

		GameBoardView gameView = new GameBoardView();
		window.add(gameView);

		window.revalidate();

//		try {
//			DataInputStream input = new DataInputStream(socket.getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		ByteBuffer buffer = ByteBuffer.allocate(1 + userName.getBytes().length);
		buffer.put(NC.SET_NAME);
		buffer.put(userName.getBytes());
	}

}