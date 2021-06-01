package client.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.domain.Ship;
import client.gui.PlayAreaCell.PlayAreaCellColor;

public class PlayArea extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//CLASS FIELDS
	//----------------------------------------
	
	private static final
	Dimension PANEL_DIMENSIONS = new Dimension(450, 450);

	private static final
	String BACKGROUND_IMG_DIR = "res/Client/Images/PlayArea.jpg";
	
	private static final
	int GRID_SIZE = 10;
	
	private static
	BufferedImage backgroundImage;
	
	//INSTANCE FIELDS
	//----------------------------------------
	
	private
	PlayAreaCell[][] gridCells = new PlayAreaCell[GRID_SIZE][GRID_SIZE];
	
	private
	PlayAreaCell[] currentSelection;
	
	private
	PlayAreaCell currentCell;
	
	private
	int length = 0;
	
	private
	boolean isVertical = true,
			isValid;
	
	private
	Ship currentShip;
	
	//CONSTRUCTOR
	//----------------------------------------
	
	/**
	 * Constructor for PlayArea
	 */
	public PlayArea()
	{
		initPanel();
		initMouseControls();
		initCellGrid();
	}
	
	//INIT PANEL
	//----------------------------------------
	
	/**
	 * Configures this panel
	 */
	private void initPanel()
	{
		try
		{
			backgroundImage = ImageIO.read(new FileInputStream(BACKGROUND_IMG_DIR));
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
		
		this.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		this.setPreferredSize(PANEL_DIMENSIONS);
		this.setMinimumSize(PANEL_DIMENSIONS);
		this.setMaximumSize(PANEL_DIMENSIONS);
	}
	
	//INIT MOUSE CONTROLS
	//----------------------------------------
	
	/**
	 * Adds a MouseWheelListener to this panel to handle ship rotation
	 */
	private void initMouseControls()
	{
		this.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent event)
			{
				rotateSelection();
			}
		});
	}

	//INIT CELL GRID
	//----------------------------------------
	
	/**
	 * Initializes cells and populates this panel with them
	 */
	private void initCellGrid()
	{
		for(int i=0; i<GRID_SIZE; i++)
		{
			for(int j=0; j<GRID_SIZE; j++)
			{
				PlayAreaCell newCell = new PlayAreaCell(this, i, j);
				newCell.addMouseListener(new MouseListener()
				{
					
					@Override
					public void mouseReleased(MouseEvent event)
					{
						// Rotate on right click
						if(SwingUtilities.isRightMouseButton(event))
						{
							rotateSelection();
						}
						// Place ship on left click
						else if(SwingUtilities.isLeftMouseButton(event))
						{
							PlayAreaCell cell = (PlayAreaCell) event.getSource();
							cell.cellSelected();
						}
					}
					
					@Override
					public void mousePressed(MouseEvent event)
					{
						;
					}
					
					// Remove overlay when mouse exits cell
					@Override
					public void mouseExited(MouseEvent event)
					{
						PlayAreaCell cell = (PlayAreaCell) event.getSource();
						cell.removeOriginCell();
					}
					
					// Add overlay when mouse enters cell
					@Override
					public void mouseEntered(MouseEvent event)
					{
						PlayAreaCell cell = (PlayAreaCell) event.getSource();
						cell.setOriginCell();
					}
					
					@Override
					public void mouseClicked(MouseEvent event)
					{
						;
					}
				});
				
				gridCells[i][j] = newCell;
				this.add(newCell);
			}
		}
	}
	
	//ROTATE SELECTION
	//----------------------------------------
	
	/**
	 * Toggles ship placement direction (vertical/horizontal)
	 */
	public void rotateSelection()
	{
		isVertical = !isVertical;
		removeOverlayGridCells();
		
		currentCell.setOriginCell();
	}
	
	//ADD CELL OVERLAY
	//----------------------------------------
	
	/**
	 * Adds hover overlay from currentSelection of cells
	 * @param originCell The cell that the ship originates from (cell the mouse is over)
	 */
	public void overyalGridCells(PlayAreaCell originCell)
	{
		currentCell = originCell;
		
		checkIfValid();
		
		PlayAreaCellColor color;
		
		if(isValid)
		{
			color = PlayAreaCellColor.GREEN;
		}
		else
		{
			color = PlayAreaCellColor.RED;
		}
		
		for(PlayAreaCell gridCell : currentSelection)
		{
			// Cells will be null if outside grid bounds
			if(gridCell != null)
			{
				gridCell.addOverlay(color);
			}
		}
	}
	
	//REMOVE CELL OVERLAY
	//----------------------------------------
	
	/**
	 * Removes hover overlay (green/red) from currentSelection of cells
	 */
	public void removeOverlayGridCells()
	{
		for (PlayAreaCell gridCell : currentSelection)
		{
			if(gridCell != null)
			{
				gridCell.removeOverlay();
			}
		}
	}
	
	//PLACE SHIP
	//----------------------------------------
	
	/**
	 * Places a currentShip on the PlayArea if it is a valid placement
	 */
	public void placeShip()
	{
		if(isValid)
		{
			for (PlayAreaCell gridCell : currentSelection)
			{
				gridCell.setShip(true);
			}
		}
	}
	
	//SET SHIP
	//----------------------------------------
	
	/**
	 * Assigns the current Ship
	 * @param ship the Ship to assign
	 */
	public void setShip(Ship ship)
	{
		currentShip = ship;
		setLength(currentShip.getLength());
	}
	
	//CHECK IF VALID
	//----------------------------------------
	
	/**
	 * Checks if current position is valid for ship placement
	 */
	private void checkIfValid()
	{
		int posX = currentCell.getPosX(),
			posY = currentCell.getPosY(),
			endPos,
			counter = 0;
		
		// Creates an array of cells to specified length
		currentSelection = new PlayAreaCell[length];
		
		isValid = true;
		
		if(isVertical)
		{
			// Calculates ending cell
			endPos = posX + (length - 1);
			
			// Checks and limits endPos so it's within grid bounds
			if(endPos >= GRID_SIZE)
			{
				endPos = GRID_SIZE - 1;
				isValid = false;
			}
			
			// Adds cells to currentSelection and checks if each cell already has a ship
			for(int i=posX; i<=endPos; i++)
			{
				currentSelection[counter++] = gridCells[i][posY];
				if(gridCells[i][posY].hasShip())
				{
					isValid = false;
				}
			}
		}
		else
		{
			// Calculates ending cell
			endPos = posY + (length - 1);
			
			// Checks and limits endPos so it's within grid bounds
			if(endPos >= GRID_SIZE)
			{
				endPos = GRID_SIZE - 1;
				isValid = false;
			}
			
			// Adds cells to currentSelection and checks if each cell already has a ship
			for(int i=posY; i<=endPos; i++)
			{
				currentSelection[counter++] = gridCells[posX][i];
				if(gridCells[posX][i].hasShip())
				{
					isValid = false;
				}
			}
		}
	}
	
	//SET LENGTH
	//----------------------------------------
	
	/**
	 * Sets the number of cells to select
	 * @param length The length of a ship
	 */
	private void setLength(int length)
	{
		this.length = length;
	}
	
	//GRAPHICS
	//----------------------------------------
	
	/**
	 * Overrides the paintComonent method of JPanel
	 * Adds background image to the panel
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(backgroundImage,0, 0, (int)PANEL_DIMENSIONS.getWidth(), (int)PANEL_DIMENSIONS.getHeight(), this);
	}
}
