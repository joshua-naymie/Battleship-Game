package client.gui;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.swing.*;

import server.domain.NC;

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

	// make method to call from loginpanel, pass socket, pass boolean loggedIn,
	// passUsername

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

	public void loginSuccesful(Socket socket, String userName) {
		
		System.out.println("inside loginSuccefull");
		window.remove(login);

		// make a class for this

		GameBoardView gameView = new GameBoardView();
		window.add(gameView);

		
		
//		try {
//			DataInputStream input = new DataInputStream(socket.getInputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		ByteBuffer buffer = ByteBuffer.allocate(1 + userName.getBytes().length);
//		buffer.put(NC.SET_NAME);
//		buffer.put(userName.getBytes());
	}
}
