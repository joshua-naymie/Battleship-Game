package sait.frms.manager;

/**	
 * 
 * @author Mohamed, Jordan
 *
 */

import java.io.*;

import java.util.*;

import javax.swing.DefaultListModel;

import sait.frms.problemdomain.Flight;

public class FlightManager {

	public final String ANY = "Any";
	public final String SUNDAY = "Sunday";
	public final String MONDAY = "Monday";
	public final String TUESDAY = "Tuesday";
	public final String WEDNESDAY = "Wednesday";
	public final String THURSDAY = "Thursday";
	public final String FRIDAY = "Friday";
	public final String SATURDAY = "Saturday";
	private ArrayList<Flight> flights = new ArrayList<>();
	private ArrayList<String> airports = new ArrayList<>();

	public FlightManager() {
		try {
			populateFlights();
			populateAirports();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getAirports() {
		return this.airports;
	}

	public ArrayList<Flight> getFlights() {
		return this.flights;
	}

	public String findAirportByCode(String code) {
		String desiredAirport = "";

		for (String airport : airports) {
			if (airport.contains(code)) {
				desiredAirport = airport;
			}
		}
		return desiredAirport;
	}

	public Flight findFlightByCode(String code) {

		Flight desiredFlight = null;
		for (Flight flight : flights) {
			if (flight.getCode().equals(code)) {
				desiredFlight = flight;
			}
		}
		return desiredFlight;
	}

	public ArrayList<Flight> findFlights(String from, String to, String day, ArrayList<Flight> flights) {
		ArrayList<Flight> foundFlights = new ArrayList<>();
		for (Flight flight : flights) {
			if (flight.getFrom().equals(from) && flight.getTo().equals(to) && day.equalsIgnoreCase("Any")) {
				foundFlights.add(flight);
			} else if (flight.getFrom().equals(from) && flight.getTo().equals(to) && flight.getWeekDay().equals(day)) {
				foundFlights.add(flight);
			}
		}
		return foundFlights;
	}

	private void populateFlights() throws FileNotFoundException {
		final String PATH = "./res/flights.csv";
		File reader = new File(PATH);
		Scanner scanner = new Scanner(reader);
		ArrayList<String> flightsStrings = new ArrayList<>();
		while (scanner.hasNextLine()) {
			flightsStrings.add(scanner.nextLine());
		}
		scanner.close();
		for (String line : flightsStrings) {
			String[] splittedLine = line.split(",");
			Flight flight = new Flight(splittedLine[0], "", splittedLine[1], splittedLine[2], splittedLine[3],
					splittedLine[4], Integer.valueOf(splittedLine[5]), Double.valueOf(splittedLine[6]));
			if (!flight.getAirlineName().equals("Invalid")) {
				flights.add(flight);
			}
		}
	}

	private void populateAirports() throws FileNotFoundException {
		final String PATH = "./res/airports.csv";
		File reader = new File(PATH);
		Scanner scanner = new Scanner(reader);
		while (scanner.hasNextLine()) {
			String airportLine = scanner.nextLine();
			String[] splitted = airportLine.split(",");
			airports.add(splitted[0]);
		}
		scanner.close();
	}
}