package sait.frms.manager;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;

import sait.frms.exception.InvalidCitizenShipException;
import sait.frms.exception.InvalidTravelerNameException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.exception.NullFlightException;
import sait.frms.gui.FlightsTab.findFlightListener;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * 
 * @author Mohamed, Jordan
 *
 */

public class ReservationManager {
	private ArrayList<Reservation> reservations;

	// res Code 5 chars 10 BYTES
	// code = 10 chars -> 20 BYTES
	// airline = 50 chars -> 100 BYTES

	// costPerSeat = -> 8 BYTES
	// name -> 50 -> 100 BYTES
	// citizenship -> 50 -> 100 BYTES
	// isAvtive -> 1 BYTE

	private static final String BINARY_FILE = "bin/reservations.bin";
	private static final int RESERVATION_SIZE = 338;
	private static final String MODE = "rw";
	private static RandomAccessFile raf;

	public ReservationManager() throws IOException {
		this.raf = new RandomAccessFile(BINARY_FILE, MODE);
		this.reservations = new ArrayList<>();
		populateFromBinary();

	}

	public void makeReservation(Flight flight, String travelerName, String citizenShip)
			throws InvalidTravelerNameException, NullFlightException, InvalidCitizenShipException, NoMoreSeatsException,
			IOException {

		String reservationCode = generateReservationCode(flight);
		String code = flight.getCode();
		String airlineName = flight.getAirlineName();
		double cost = flight.getCostPerSeat();
		boolean active = true;
		if (travelerName.isEmpty() || travelerName == null) {
			throw new InvalidTravelerNameException();
		}
		if (flight == null) {
			throw new NullFlightException();
		}
		if (citizenShip.isEmpty() || citizenShip == null) {
			throw new InvalidCitizenShipException();
		}

		if (getAvailableSeats(flight) <= 0) {
			throw new NoMoreSeatsException();
		}
		flight.setSeats(-1);
		Reservation newReservation = new Reservation(reservationCode, code, airlineName, travelerName, citizenShip,
				cost, active);

		reservations.add(newReservation);
		persist();

	}

	public ArrayList<Reservation> findReservations(String code, String airline, String name) {

		ArrayList<Reservation> foundReservations = new ArrayList<>();
		for (Reservation reservation : reservations) {
			if (reservation.getCode().equalsIgnoreCase(code) || reservation.getAirline().equalsIgnoreCase(airline)
					|| reservation.getName().equalsIgnoreCase(name)) {
				foundReservations.add(reservation);
			}
		}
		return foundReservations;
	}

	public Reservation findReservationByCode(String code) {
		Reservation desiredReservation = null;
		for (Reservation reservation : reservations) {
			if (reservation.getCode().equals(code)) {
				desiredReservation = reservation;
			}
		}
		return desiredReservation;
	}

	public void persist() throws IOException {
		long position = 0;
		for (Reservation reservation : reservations) {
			this.raf.seek(position);
			this.writeRecord(reservation);
			position += RESERVATION_SIZE;
		}
	}

	public int getAvailableSeats(Flight flight) {
		return flight.getSeats();
	}

	private String generateReservationCode(Flight flight) {

		String generatedCode = "";
		int randomNumber = (int) (Math.random() * 9999);
		if (randomNumber <= 1000) {
			randomNumber = randomNumber + 1000;
		}

		if (flight.isDomestic()) {
			generatedCode = "D" + String.valueOf(randomNumber);
		} else {
			generatedCode = "I" + String.valueOf(randomNumber);
		}

		return generatedCode;
	}

	private void populateFromBinary() throws IOException {

		for (long position = 0; position < this.raf.length(); position += RESERVATION_SIZE) {

			try {
				this.raf.seek(position);
				Reservation reservation = readRecord();
				if (reservation.isActive()) {
					reservations.add(reservation);
				} else if (!reservation.isActive()) {
					FlightManager manager = new FlightManager();
					Flight foundFlight = manager.findFlightByCode(reservation.getFlightCode());
					foundFlight.setSeats(1);
				}

			} catch (EOFException e) {
				System.out.println("File reached the end");
			}
		}
	}

	private Reservation readRecord() throws IOException {
		String reservationCode = this.raf.readUTF().trim();
		String code = this.raf.readUTF().trim();
		String airline = this.raf.readUTF().trim();
		String name = this.raf.readUTF().trim();
		String citizenship = this.raf.readUTF().trim();
		double costPerSeats = this.raf.readDouble();
		boolean isActive = this.raf.readBoolean();
		Reservation reservation = new Reservation(reservationCode, code, airline, name, citizenship, costPerSeats,
				isActive);
		reservation.setActive(isActive);
		return reservation;
	}

	public void add() throws IOException {
		persist();
	}

	public void writeRecord(Reservation reservation) throws IOException {
		String reservationCode = String.format("%-4s", reservation.getCode());
		this.raf.writeUTF(reservationCode);
		String flightCode = String.format("%-10s", reservation.getFlightCode());
		this.raf.writeUTF(flightCode);
		String airline = String.format("%-50s", reservation.getAirline());
		this.raf.writeUTF(airline);
		String clientName = String.format("%-50s", reservation.getName());
		this.raf.writeUTF(clientName);
		String clientCitizenship = String.format("%-50s", reservation.getCitizenship());
		this.raf.writeUTF(clientCitizenship);
		this.raf.writeDouble(reservation.getCost());
		this.raf.writeBoolean(reservation.isActive());
	}

	public void close() throws IOException {
		this.raf.close();
	}
}
