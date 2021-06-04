package server.domain;

public class Match extends Observer implements Runnable
{
	enum Turn
	{
		SHIP_PLACEMENT,
		PLAYER1,
		PLAYER2
	}
	
	private
	Client player1,
		   player2;
	
	private
	Turn currentTurn = Turn.SHIP_PLACEMENT;
	
	private
	boolean p1ShipsPlaced = false,
			p2ShipsPlaced = false;
	
	public Match(Client player1, Client player2)
	{
		setPlayers(player1, player2);
	}
	
	private void setPlayers(Client player1, Client player2)
	{
		this.player1 = player1;
		this.player2 = player2;
	}
	
	@Override
	public void run()
	{
		player1.tryWriteToClient(new byte[] { NC.MATCH_STARTED });
		player2.tryWriteToClient(new byte[] { NC.MATCH_STARTED });
		
		while(!p1ShipsPlaced || !p2ShipsPlaced)
		{
			try
			{
				wait();
			}
			catch (InterruptedException exception)
			{
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
		}
	}
	
	private void parseMessage(byte[] bytes)
	{
		
	}
	
	@Override
	public void update()
	{
		switch(currentTurn)
		{
			case SHIP_PLACEMENT:
				if(player1.hasUnreadMessage())
				{
					parseMessage(player1.getState());
				}
				break;
		}
	}

	@Override
	public void update(Subject<?> subject)
	{
		parseMessage((byte[]) subject.getState());
	}

}
