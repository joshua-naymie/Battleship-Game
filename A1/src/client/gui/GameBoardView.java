package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.ByteBuffer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


import client.domain.ConnectionManager;
import client.domain.NC;
import client.domain.Ship;

public class GameBoardView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Board playerBoard, opponentBoard;
	
	private ConnectionManager connection;
	
	private Ship[] allShips;
	
	private ShipButtonArea ships;
	
	private ChatView chat;
	
	
	public GameBoardView(ConnectionManager connection) {
		this.connection = connection;
		
		this.connection.setGameBoardView(this);
		
		JPanel meAndButtons = new JPanel(new BorderLayout());
		Board meBoard = new Board(true, this);
		meBoard.setPreferredSize(new Dimension(900, 500));
		meBoard.setBackground(Color.black);
		Board opponentBoard = new Board(false, this);
		opponentBoard.setPreferredSize(new Dimension(900, 500));
		opponentBoard.setBackground(Color.black);
		opponentBoard.setAlignmentX(TOP_ALIGNMENT);
		
		playerBoard = meBoard;
		this.opponentBoard = opponentBoard;
		
		
		JPanel playerPanel = new JPanel(new BorderLayout());
		meAndButtons.add(meBoard, BorderLayout.NORTH);

		playerPanel.setBackground(Color.black);

		// playerPanel.setLayout(new BorderLayout(2, 2));
		//playerPanel.setBounds(500, 2, 1, 50);
		JPanel shipPanel = new JPanel();
		ships = new ShipButtonArea(meBoard);
		
		allShips = ships.getShips();

		shipPanel.add(ships);
		shipPanel.setLayout(new FlowLayout());
		shipPanel.setBackground(Color.black);
		// create another panel
		// add the parts to this panel
		// add that panel to the playerPanel

		// add meboard to North
		// add shipButtonArea to Center
		//
		// shipPanel.setLocation(5, 5);
		GameLogoPanel game = new GameLogoPanel();
		game.setBackground(Color.black);
		playerPanel.add(game, BorderLayout.PAGE_START);

		meAndButtons.add(shipPanel, BorderLayout.PAGE_END);
//		playerPanel.add(shipPanel, BorderLayout.PAGE_END);

		playerPanel.add(meAndButtons, BorderLayout.WEST);
		chat = new ChatView(this);
		chat.setPreferredSize(new Dimension(300, 200));
		Border border = BorderFactory.createLineBorder(Color.gray);
		chat.setBorder(border);

		chat.setBackground(Color.black);
		playerPanel.add(chat, BorderLayout.CENTER);

		playerPanel.add(opponentBoard, BorderLayout.EAST);

		add(playerPanel);
		setVisible(true);
	}
	
	public void setShipPlacement(boolean isAllowed)
	{
		ships.setButtonsEnabled(isAllowed);
	}
	
	public void setShotTaking(boolean takeShots)
	{
		opponentBoard.setShotTaking(takeShots);
		System.out.println("shots allowed: " + takeShots);
	}
	
	public void submitShips()
	{
		boolean shipsPlaced = true;
		byte[] allCells = new byte[34];
		int i=0;
		
		for (Ship ship : allShips) {
			if(!ship.isPlaced())
			{
				shipsPlaced = false;
			}
			else
			{
				PlayAreaCell[] shipCells = ship.getCells();
				
				for (PlayAreaCell cell : shipCells)
				{
					allCells[i++] = (byte) cell.getPosX();
					allCells[i++] = (byte) cell.getPosY();
				}
			}
		}
		
		if(shipsPlaced)
		{
			
			ByteBuffer buffer = ByteBuffer.allocate(1 + allCells.length);
			buffer.put(NC.SHIP_PLACEMENT);
			buffer.put(allCells);
			
			connection.tryWriteToServer(buffer.array());
			setShipPlacement(false);
		}
	}
	
	public void takeShot(int posX, int posY)
	{
		System.out.println("Shot - X: " + posX + ", Y: " + posY);
		ByteBuffer buffer = ByteBuffer.allocate(3);
		buffer.put(NC.CLIENT_SHOT);
		buffer.put((byte) posX);
		buffer.put((byte) posY);
		
		connection.tryWriteToServer(buffer.array());
		
		setShotTaking(false);
	}

	public void setPlayerBoard(byte[] board)
	{
		playerBoard.setHitMiss(board);
	}
	
	public void setOpponentBoard(byte[] board)
	{
		opponentBoard.setHitMiss(board);
	}
	
	public void sendChatMessage(String message)
	{
		ByteBuffer buffer = ByteBuffer.allocate(1 + message.getBytes().length);
		buffer.put(NC.CHAT_MESSAGE);
		buffer.put(message.getBytes());
		
		connection.tryWriteToServer(buffer.array());
	}
	
	public void recieveChatMessage(byte[] message)
	{
		chat.recieveChatMessage(new String(message));
	}
}
