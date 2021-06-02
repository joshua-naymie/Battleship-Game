package sait.frms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.exception.InvalidCitizenShipException;
import sait.frms.exception.InvalidTravelerNameException;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**	
 * 
 * @author Mohamed, Jordan, Raph
 *
 */

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase {

	JTextField textCodeFalse;
	JTextField textAirlineFalse;
	JTextField textNameTrue;
	JComboBox statusBoxTrue;

	JTextField textCode;
	JTextField textFlight;
	JTextField textAirline;
	JTextField textCost;
	JTextField textName;
	JTextField textCitizenship;
	JComboBox statusBox;

	int selectedIndex;
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	/**
	 * List of reservations.
	 */
	private JList<Reservation> reservationsList;

	/**
	 * Default list mode.
	 */
	private DefaultListModel<Reservation> reservationModel;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		JPanel westPanel = createWestPanel();
		panel.add(westPanel, BorderLayout.WEST);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
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

		reservationModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationModel);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);

		reservationsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	/**
	 * Creates the east panel.
	 * 
	 * @return JPanel that goes in east.
	 */
	private JPanel createEastPanel() {
		// declared panel and layouts
		JPanel panel = new JPanel();
		JPanel gridLayout = new JPanel();
		JPanel flowLayout = new JPanel();

		// initializing the panel and layouts
		panel.setLayout(new BorderLayout());
		gridLayout.setLayout(new GridLayout(7, 2));
		flowLayout.setLayout(new FlowLayout());
		flowLayout.add(gridLayout);

		// east panel title
		JLabel reserve = new JLabel("Reserve", SwingConstants.CENTER);
		reserve.setFont(new Font("serif", Font.PLAIN, 25));
		panel.add(reserve, BorderLayout.NORTH);

		JLabel code = new JLabel("Code:", SwingConstants.RIGHT);
		textCodeFalse = new JTextField(10);
		textCodeFalse.setEditable(false);
		gridLayout.add(code);
		gridLayout.add(textCodeFalse);

		JLabel flight = new JLabel("Flight:", SwingConstants.RIGHT);
		textFlight = new JTextField(10);
		textFlight.setEditable(false);
		gridLayout.add(flight);
		gridLayout.add(textFlight);

		JLabel airline = new JLabel("Airline:", SwingConstants.RIGHT);
		textAirlineFalse = new JTextField(10);
		textAirlineFalse.setEditable(false);
		gridLayout.add(airline);
		gridLayout.add(textAirlineFalse);

		JLabel cost = new JLabel("Cost:", SwingConstants.RIGHT);
		textCost = new JTextField(10);
		textCost.setEditable(false);
		gridLayout.add(cost);
		gridLayout.add(textCost);

		JLabel name = new JLabel("Name:", SwingConstants.RIGHT);
		textNameTrue = new JTextField(10);
		textNameTrue.setEditable(true);
		gridLayout.add(name);
		gridLayout.add(textNameTrue);

		JLabel citizenship = new JLabel("Citizenship:", SwingConstants.RIGHT);
		textCitizenship = new JTextField(10);
		textCitizenship.setEditable(true);
		gridLayout.add(citizenship);
		gridLayout.add(textCitizenship);

		JLabel status = new JLabel("Status:", SwingConstants.RIGHT);
		String[] statusString = { "Active", "Inactive" };
		statusBox = new JComboBox(statusString);
		gridLayout.add(status);
		gridLayout.add(statusBox);

		panel.add(flowLayout, BorderLayout.CENTER);

		// update button
		JButton updateButton = new JButton("Update");
		panel.add(updateButton, BorderLayout.SOUTH);
		updateButton.addActionListener(new UpdateButtonListener());

		return panel;
	}

	/**
	 * Creates the south panel.
	 * 
	 * @return JPanel that goes in south.
	 */
	private JPanel createSouthPanel() {
		// panels and layouts
		JPanel panel = new JPanel();
		JPanel gridLayout = new JPanel();
		JPanel textPanel = new JPanel();
		
		

		// setting the panel and layouts
		panel.setLayout(new BorderLayout());
		gridLayout.setLayout(new GridLayout(3, 1));
		textPanel.setLayout(new GridLayout(3, 1));
 
		// south panel title
		JLabel searchText = new JLabel("Search", SwingConstants.CENTER);
		searchText.setFont(new Font("serif", Font.PLAIN, 25));
		panel.add(searchText, BorderLayout.NORTH);

		// JLabels and JTextFields
		JLabel codeInput = new JLabel("Code:", SwingConstants.RIGHT);
		textCode = new JTextField();
		textCode.setEditable(true);
		gridLayout.add(codeInput);
		textPanel.add(textCode);

		JLabel airlineInput = new JLabel("Airline:", SwingConstants.RIGHT);
		textAirline = new JTextField();
		textAirline.setEditable(true);
		gridLayout.add(airlineInput);
		textPanel.add(textAirline);

		JLabel nameInput = new JLabel("Name:", SwingConstants.RIGHT);
		textName = new JTextField();
		textName.setEditable(true);
		gridLayout.add(nameInput);
		textPanel.add(textName);

		panel.add(gridLayout, BorderLayout.WEST);
		panel.add(textPanel, BorderLayout.CENTER);

		// find reservation button
		JButton findReservationsButton = new JButton("Find Reservations");
		
		findReservationsButton.addActionListener(new MyActionListener());
		panel.add(findReservationsButton, BorderLayout.SOUTH);

		return panel;

	}

	/**
	 * Creates the west panel.
	 * 
	 * @return JPanel that goes in west.
	 */
	private JPanel createWestPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		return panel;
	}

	/**
	 * Called when user selects an item in the JList.
	 * 
	 * @author Mohamed, Jordan
	 * @version March 19, 2021
	 */
	private class MyListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {

			populateTextFields();
		}

		private void populateTextFields() {
			selectedIndex = reservationsList.getSelectedIndex();
			if (selectedIndex < 0) {
				selectedIndex = 0;
			} else {
				textCodeFalse.setText(reservationModel.get(selectedIndex).getCode());
				textFlight.setText(reservationModel.get(selectedIndex).getFlightCode());
				textAirlineFalse.setText(reservationModel.get(selectedIndex).getAirline());
				textCost.setText(String.valueOf(reservationModel.get(selectedIndex).getCost()));
				textNameTrue.setText(reservationModel.get(selectedIndex).getName());
				textCitizenship.setText(reservationModel.get(selectedIndex).getCitizenship());

			}
		}

	}

	/**
	 * Inner action listener that listens for the reserve button to be clicked.
	 * 
	 * @author Mohamed, Jordan
	 * @version March 19, 2021
	 */
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			reservationModel.removeAllElements();

			String code = textCode.getText();
			String airline = textAirline.getText();
			String name = textName.getText();
			ArrayList<Reservation> foundReservations = reservationManager.findReservations(code, airline, name);
			for (Reservation reservation : foundReservations) {
				reservationModel.addElement(reservation);
			}
		
		}

	}

	/**
	 * 
	 * @author Mohamed, Jordan , Altamish, Raph
	 *
	 */
	private class UpdateButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int reserve = 0;
			Reservation reservationItem = null;
			try {
				reservationItem = reservationModel.get(reserve);
				reserve = reservationsList.getSelectedIndex();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Please select a reservation");
			}
			String name = textNameTrue.getText();
			String citizenship = textCitizenship.getText();
			String active = (String) statusBox.getSelectedItem();
			boolean isActive;
			if (active.equals("Active")) {
				isActive = true;
			} else {
				isActive = false;
			}
			try {
				if (name.isEmpty() || name == null || name.equals(" ")) {
					throw new InvalidTravelerNameException();
				} else if (citizenship.isEmpty() || citizenship == null || citizenship.equals(" ")) {
					throw new InvalidCitizenShipException();
				} else {
					reservationItem.setName(name);
					reservationItem.setCitizenship(citizenship);
					reservationItem.setActive(isActive);
					reservationManager.add();
					reservationModel.removeAllElements();
				}

			} catch (Exception e2) {
				if (e2 instanceof InvalidTravelerNameException) {
					JOptionPane.showMessageDialog(null, "Your name cannot be empty.");
				} else if (e2 instanceof InvalidCitizenShipException) {
					JOptionPane.showMessageDialog(null, "Your citizenship cannot be empty.");
				}
			}
			textNameTrue.setText("");
			textCitizenship.setText("");
		}
	}
}
