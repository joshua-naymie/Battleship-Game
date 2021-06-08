package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.swing.*;
import javax.swing.border.Border;

import client.domain.ConnectionManager;
import server.domain.NC;

//**NOT FINISHED**
public class MainWindow
{
	private JFrame window = new JFrame();

	private LoginPanel login = new LoginPanel(this);

	private static final int WINDOW_SIZE_X = 1500, WINDOW_SIZE_Y = 800;

	private JButton disconnect;

	// ----------------------------------------


	public MainWindow() {
		initWindow();
	}

	// ----------------------------------------

	// make method to call from loginpanel, pass socket, pass boolean loggedIn,
	// passUsername

	// make a method for all logic with boolean, no if statement


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
	public void loginSuccesful(Socket socket, String userName)
	{
		ConnectionManager manager = new ConnectionManager(socket);
		
		ByteBuffer buffer = ByteBuffer.allocate(1 + userName.getBytes().length);
		buffer.put(NC.SET_NAME);
		buffer.put(userName.getBytes());
		
		manager.tryWriteToServer(buffer.array());
		

		window.remove(login);

		GameBoardView gameView = new GameBoardView(manager);
		window.add(gameView);

		window.revalidate();
	}

}