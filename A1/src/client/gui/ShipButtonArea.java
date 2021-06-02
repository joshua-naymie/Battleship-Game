package client.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

import client.domain.Ship;

public class ShipButtonArea extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//CLASS FIELDS
	//----------------------------------------
	
	private static final
	Ship[] SHIPS = { new Ship("Destroyer", 2),
					 new Ship("Submarine", 3),
					 new Ship("Cruiser", 3),
					 new Ship("Battleship", 4),
					 new Ship("Carrier", 5) };
	
	//INSTANCE FIELDS
	//----------------------------------------
	
	private
	ShipButton[] buttons;
	
	//CONSTRUCTOR
	//----------------------------------------
	
	/**
	 * Constructor for ShipButtonArea
	 * @param board the Board the ShipButtons are assigned to
	 */
	public ShipButtonArea(Board board)
	{
		initPanel();
		initComponents(board);
		initLayout();
	}
	
	//INIT PANEL
	//----------------------------------------

	/**
	 * Configures this panels settings
	 */
	private void initPanel()
	{
		this.setLayout(new FlowLayout());
	}
	
	//INIT COMPONENTS
	//----------------------------------------
	
	/**
	 * Initializes and configures all components for this panel
	 * @param board the Board the ShipButtons are assigned to
	 */
	private void initComponents(Board board)
	{
		buttons = new ShipButton[SHIPS.length];
		
		int counter = 0;
		for (Ship ship : SHIPS)
		{
			ShipButton button = new ShipButton(ship, board);
			button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent event)
				{
					ShipButton button = (ShipButton) event.getSource();
					button.buttonClicked();
				}
			});
			
			buttons[counter++] = button;
		}
	}
	
	//INIT LAYOUT
	//----------------------------------------
	
	/**
	 * Adds all components to this panel
	 */
	private void initLayout()
	{
		for (ShipButton shipButton : buttons)
		{
			this.add(shipButton);
		}
	}
}