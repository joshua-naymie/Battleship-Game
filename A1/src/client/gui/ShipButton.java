package client.gui;

import javax.swing.JButton;

import client.domain.Ship;

public class ShipButton extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//INSTANCE FIELDS
	//----------------------------------------
	
	private
	Ship ship;
	
	private
	Board board;
	
	//CONSTRUCTOR
	//----------------------------------------
	
	/**
	 * Constructor for ShipButton
	 * @param ship the Ship this ShipButton represents
	 * @param board the Board this ShipButton is assigned to
	 */
	public ShipButton(Ship ship, Board board)
	{
		setShip(ship);
		setBoard(board);
	}
	
	
	//SHIP
	//----------------------------------------
	
	/**
	 * Assigns the Ship this ShipButton represents
	 * @param ship the Ship this ShipButton represents
	 */
	private void setShip(Ship ship)
	{
		this.ship = ship;
		
		setText(ship.getName());
	}
	
	//--------------------
	
	/**
	 * Gets the Ship the ShipButton represents
	 * @return
	 */
	public Ship getShip()
	{
		return ship;
	}
	
	//BOARD
	//----------------------------------------
	
	/**
	 * Assigns the Board this ShipButton is assigned to
	 * @param board
	 */
	private void setBoard(Board board)
	{
		this.board = board;
	}
	
	//CLICKED
	//----------------------------------------
	
	/**
	 * Sets the Boards current Ship
	 */
	public void buttonClicked()
	{
		board.setShip(ship);
	}
}
