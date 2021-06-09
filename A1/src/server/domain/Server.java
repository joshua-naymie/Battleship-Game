package server.domain;

import server.gui.MainWindow;

public class Server
{
	public static void main(String[] args) throws InterruptedException
	{
		MainWindow window = new MainWindow();
		
		
		ClientManager manager = new ClientManager();
		
		// Temporary solution to keep server running
		for(;;)
		{
			//System.out.println("client count: " + manager.getClientCount());
			Thread.sleep(1000);
		}
	}
}
