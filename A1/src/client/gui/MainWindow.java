package client.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.*;



//**NOT FINISHED**
public class MainWindow
{
	private
	JFrame window = new JFrame();
	
	private
	LoginPanel login = new LoginPanel();
	
	private static final
	int WINDOW_SIZE_X = 1280,
		WINDOW_SIZE_Y = 720;
	
	//----------------------------------------
	
	public MainWindow() throws IOException
	{
		initWindow();
	}

	//----------------------------------------
	
	private void initWindow() throws IOException
	{
		window.setLayout(new BorderLayout());
		window.setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		
//		window.add(login);		// Uncomment for login window
		
		Board board = new Board();
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new BorderLayout());
		playerPanel.add(board, BorderLayout.NORTH);
		playerPanel.add(new ShipButtonArea(board));
		
		window.add(playerPanel);	// Comment for login window
		
		window.setVisible(true);
	}
}