package server.domain;

import java.nio.ByteBuffer;

public class Match extends Observer implements Runnable
{
	public enum Phase
	{
		SHIP_PLACEMENT,
		PLAYER_TURNS,
		GAME_ENDED
	}
	
	private static final short PLAYER1 = 0, PLAYER2 = 1;
	
	private Client[] players = new Client[2];
	
	private PlayerBoard[] boards = new PlayerBoard[2];
	
	private Phase currentPhase;
	
	private short currentPlayer, waitingPlayer, winningPlayer;
	
	boolean gameIsRunning;
	
	public Match(Client player1, Client player2)
	{
		player1.addObserver(this);
		player2.addObserver(this);
		System.out.println("MATCH STARTED");
		players[PLAYER1] = player1;
		players[PLAYER2] = player2;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run()
	{
		getShipPlacements();
		getPlayerShots();
		getRematch();

	}

	private void getShipPlacements()
	{
		currentPhase = Phase.SHIP_PLACEMENT;
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Client player : players)
		{
			player.tryWriteToClient(NC.SHIP_PLACEMENT);
		}
		while(boards[PLAYER1] == null || boards[PLAYER2] == null)
		{
			tryWait();
		}
		
	}
	
	private void getPlayerShots()
	{
		currentPhase = Phase.PLAYER_TURNS;

		gameIsRunning = true;
		currentPlayer = PLAYER1;
		waitingPlayer = PLAYER2;
		
		while(gameIsRunning)
		{
			System.out.println("current Player: " + currentPlayer);
			players[currentPlayer].tryWriteToClient(NC.CLIENT_TURN);
			tryWait();
			
			//----------
			
			ByteBuffer buffer = ByteBuffer.allocate(1 + 100);
			buffer.put(NC.PLAYER_BOARD);
			buffer.put(boards[currentPlayer].getCellStateData());
			
			players[currentPlayer].tryWriteToClient(buffer.array());
			
			buffer = ByteBuffer.allocate(1 + 100);
			buffer.put(NC.OPPONENT_BOARD);
			buffer.put(boards[waitingPlayer].getCellStateData());
			
			players[currentPlayer].tryWriteToClient(buffer.array());
			
			//----------
			
			buffer = ByteBuffer.allocate(1 + 100);
			buffer.put(NC.PLAYER_BOARD);
			buffer.put(boards[waitingPlayer].getCellStateData());
			
			players[waitingPlayer].tryWriteToClient(buffer.array());
			
			buffer = ByteBuffer.allocate(1 + 100);
			buffer.put(NC.OPPONENT_BOARD);
			buffer.put(boards[currentPlayer].getCellStateData());
			
			players[waitingPlayer].tryWriteToClient(buffer.array());
			
			//----------
			
			System.out.println("boards sent");
			checkGameEnded();
			
			short temp = currentPlayer;
			currentPlayer = waitingPlayer;
			waitingPlayer = temp;	
		}
	}
	
	private void getRematch()
	{
		for (Client player : players)
		{
			player.tryWriteToClient(NC.GAME_FINISHED);
		}
		
		tryWait();
	}
	

	@Override
	public void update()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Subject<?> subject)
	{
		parseMessage((Client) subject);
	}
	
	
	private void parseMessage(Client client)
	{
		ByteBuffer buffer = client.getState();
		buffer.rewind();
		if(buffer.get() == NC.CHAT_MESSAGE)
		{
			buffer.rewind();
			if(client.equals(players[PLAYER1]))
			{
				players[PLAYER2].tryWriteToClient(buffer.array());
			}
			else
			{
				players[PLAYER1].tryWriteToClient(buffer.array());
			}
		}
		else
		{
			switch(currentPhase)
			{
				case SHIP_PLACEMENT:
					setShipPlacement(client);
					break;
				case PLAYER_TURNS:
					setPlayerShot(client);
					break;
				case GAME_ENDED:
					break;
				default:
					break;
			}
		}
	}

	private synchronized void setShipPlacement(Client client)
	{
		int player;
		
		if(client.equals(players[PLAYER1]))
		{
			player = PLAYER1;
		}
		else
		{
			player = PLAYER2;
		}
		
		ByteBuffer message = client.getState();
		message.rewind();
		
		if(message.get() == NC.SHIP_PLACEMENT)
		{
			byte[][] shipLocations = new byte[17][2];
			
			for(int i=0; i<17; i++)
			{
				for(int j=0; j<2; j++)
				{
					shipLocations[i][j] = message.get();
				}
			}
			
			boards[player] = new PlayerBoard(shipLocations);
			
			notify();
			System.out.println("notified");
		}
		else
		{
			System.out.println("not ship placement header");
			clientError(client, NC.SHIP_PLACEMENT);
		}
	}

	private synchronized void setPlayerShot(Client client)
	{
		if(client.equals(players[currentPlayer]))
		{
			ByteBuffer message = client.getState();
			message.rewind();
			
			if(message.get() == NC.CLIENT_SHOT)
			{
				boards[waitingPlayer].shotTaken(message.get(), message.get());
				notify();
			}
			else
			{
				System.out.println("NOT A SHOT");
//				clientError(client, NC.CLIENT_TURN);
			}
		}
		else
		{
			System.out.println("WRONG PLAYER");
			clientError(client, NC.WRONG_TURN);
		}
	}
	
	private void checkGameEnded()
	{
		if(boards[waitingPlayer].noShipsLeft())
		{
			winningPlayer = currentPlayer;
			gameIsRunning = false;
			System.out.println("GAME OVER!!!!");
		}
	}
	
	public synchronized void tryWait()
	{
		try
		{
			wait();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void clientError(Client player, byte step)
	{
		player.tryWriteToClient(new byte[]
		{ NC.ERROR, step });
	}

}
