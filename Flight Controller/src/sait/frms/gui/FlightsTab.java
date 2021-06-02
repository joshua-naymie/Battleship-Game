package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.*;

import sait.frms.exception.InvalidCitizenShipException;
import sait.frms.exception.InvalidTravelerNameException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.exception.NullFlightException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**	
 * 
 * @author Mohamed, Jordan, Raph
 *
 */
/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase {
	/**
	 * Instance of flight manager.
	 */
	private FlightManager flightManager;

	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	/**
	 * List of flights.
	 */

	private JList<Flight> flightsList;

	// these are the lists that the user chooses from
	JComboBox<String> fromComboBox;
	JComboBox<String> toComboBox;
	JComboBox<String> daysList;

	JTextField flightField;
	JTextField airlineField;
	JTextField dayField;
	JTextField timeField;
	JTextField costField;
	JTextField citizenshipField;
	JTextField nameField;

	int selectedIndex;

	private DefaultListModel<Flight> flightsModel;

	/**
	 * Creates the components for flights tab.
	 */
	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager      Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		flightsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		JPanel gridLayout = new JPanel();
		JPanel flowLayout = new JPanel();

		// initializing the panel and layouts
		panel.setLayout(new BorderLayout());
		gridLayout.setLayout(new GridLayout(7, 2));
		flowLayout.setLayout(new FlowLayout());
		flowLayout.add(gridLayout);
		
		
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		panel.add(title, BorderLayout.NORTH);

		// Make a flight box then add the lable and field to it
		JLabel flightLabel = new JLabel("Flight:", SwingConstants.RIGHT);
		flightField = new JTextField(10);
		flightField.setEditable(false);
		gridLayout.add(flightLabel);
		gridLayout.add(flightField);


		// Make an airline box then add the lable and field to it
		JLabel airlineLabel = new JLabel("Airline:",SwingConstants.RIGHT);
		airlineField = new JTextField(10);
		airlineField.setEditable(false);
		gridLayout.add(airlineLabel);
		gridLayout.add(airlineField);


		JLabel dayLabel = new JLabel("Day:", SwingConstants.RIGHT);
		dayField = new JTextField(10);
		dayField.setEditable(false);
		gridLayout.add(dayLabel);
		gridLayout.add(dayField);

		
		JLabel timeLabel = new JLabel("Time:", SwingConstants.RIGHT);
		timeField = new JTextField(10);
		timeField.setEditable(false);
		gridLayout.add(timeLabel);
		gridLayout.add(timeField);

		
		JLabel costLabel = new JLabel("Cost:", SwingConstants.RIGHT);
		costField = new JTextField(10);
		costField.setEditable(false);
		gridLayout.add(costLabel);
		gridLayout.add(costField);

		JLabel nameLable = new JLabel("Name:", SwingConstants.RIGHT);
		nameField = new JTextField(10);
		gridLayout.add(nameLable);
		gridLayout.add(nameField);
		

		JLabel citizenshipLable = new JLabel("Citizenship:", SwingConstants.RIGHT);
		citizenshipField = new JTextField(10);
		gridLayout.add(citizenshipLable);
		gridLayout.add(citizenshipField);

		panel.add(flowLayout, BorderLayout.CENTER);
		
		JButton reserveButton = new JButton("Reserve");
		panel.add(reserveButton, BorderLayout.SOUTH);
		reserveButton.addActionListener(new generateReservation());

		return panel;
	}

	private JPanel createSouthPanel() {
		JPanel panel = new JPanel(new GridLayout(0,1)); //new GridLayout(0,1)
		Box box = Box.createVerticalBox();
		panel.setLayout(new BorderLayout());

		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		box.add(title);

		// Make a box for all fields to be added to then add the box to the panel

		// make comboboxes for
		// from
		// To
		// Day

		// FROM
		// create a horizontal box for each dropbox then add that to the vertical box
		Box fromBox = Box.createHorizontalBox();

		ArrayList<String> airportsList = flightManager.getAirports();
		JLabel fromLabel = new JLabel("From: ");
		fromComboBox = new JComboBox(airportsList.toArray());
		fromComboBox.setSelectedIndex(2);

		fromBox.add(fromLabel);
		fromBox.add(fromComboBox);
		box.add(fromBox);

		// TO

		Box toBox = Box.createHorizontalBox();

		JLabel toLable = new JLabel("To:     ");
		toComboBox = new JComboBox(airportsList.toArray());
		toComboBox.setSelectedIndex(2);
		toBox.add(toLable);
		toBox.add(toComboBox);

		box.add(toBox);

		// DAY
		Box dayBox = Box.createHorizontalBox();

		String[] days = { flightManager.ANY, flightManager.SATURDAY, flightManager.SUNDAY, flightManager.MONDAY,
				flightManager.TUESDAY, flightManager.WEDNESDAY, flightManager.THURSDAY, flightManager.FRIDAY };
		JLabel dayLable = new JLabel("Day:   ");
		daysList = new JComboBox(days);
		daysList.setSelectedIndex(2);
		dayBox.add(dayLable);
		dayBox.add(daysList);

		box.add(dayBox);
		// Find Flights Button
		JButton findFlightButton = new JButton("Find Flights");
		findFlightButton.addActionListener(new findFlightListener());
		panel.add(box);
		panel.add(findFlightButton, BorderLayout.SOUTH);
		
		return panel;
	}

	/**
	 * 
	 * @author Mohamed, Jordan
	 *
	 */
	// this class will be called once the user clicks to find matching flights
	public class findFlightListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			flightsModel.removeAllElements();
			String from = (String) fromComboBox.getSelectedItem();
			String to = (String) toComboBox.getSelectedItem();
			String day = (String) daysList.getSelectedItem();
			ArrayList<Flight> flights = flightManager.getFlights();
			ArrayList<Flight> foundFlightList = flightManager.findFlights(from, to, day, flights);
			for (Flight flight : foundFlightList) {
				flightsModel.addElement(flight);
			}
		}
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			populateTextFields();
		}

		/**
		 * 
		 * @author Jordan, Raph
		 *
		 */ 
		private void populateTextFields() {
			selectedIndex = flightsList.getSelectedIndex();
			if (selectedIndex < 0) {
				selectedIndex = 0;
			} else {
				flightField.setText(flightsModel.get(selectedIndex).getCode());
				airlineField.setText(flightsModel.get(selectedIndex).getAirlineName());
				dayField.setText(flightsModel.get(selectedIndex).getWeekDay());
				timeField.setText(flightsModel.get(selectedIndex).getTime());
				costField.setText(String.valueOf(flightsModel.get(selectedIndex).getCostPerSeat()));
			}
		}
	}

	/**
	 * 
	 * @author Mohamed, Jordan
	 *
	 */

	public class generateReservation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			int reserve = flightsList.getSelectedIndex();

			String name = nameField.getText();

			String citizenship = citizenshipField.getText();

			try {
				Flight flightModel = flightsModel.get(reserve);
				reservationManager.makeReservation(flightModel, name, citizenship);
			} catch (Exception ex) {
				if (ex instanceof InvalidTravelerNameException) {
					JOptionPane.showMessageDialog(null, "Your name cannot be empty.");
				} else if (ex instanceof InvalidCitizenShipException) {
					JOptionPane.showMessageDialog(null, "Your citizenship cannot be empty.");
				} else if (ex instanceof NoMoreSeatsException) {
					JOptionPane.showMessageDialog(null,
							"There are no more seats on this flight, please choose another one");
				} else if (ex instanceof NullFlightException) {
					JOptionPane.showMessageDialog(null, "Please choose a flight.");
				} else if (ex instanceof ArrayIndexOutOfBoundsException) {
					JOptionPane.showMessageDialog(null, "Please choose a flight");
				} else {
					JOptionPane.showMessageDialog(null, "Please choose a flight");
				}
			}
			nameField.setText("");
			citizenshipField.setText("");
		}
	}
}