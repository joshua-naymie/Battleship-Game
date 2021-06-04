package server.domain;

public class Match extends Observer implements Runnable
{
	private
	Client player1,
		   player2;
	private
	short player1Id,
		  player2Id;
	
	public Match(Client player1, Client player2)
	{
		setPlayers(player1, player2);
	}
	
	private void setPlayers(Client player1, Client player2)
	{
		this.player1 = player1;
		player1Id = player1.getId();
		
		this.player2 = player2;
		player2Id = player2.getId();
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

}
