package sait.frms.problemdomain;

import java.awt.Font;
import java.time.DayOfWeek;

import javax.xml.crypto.Data;

/**
 * 
 * @author Mohamed, Jordan, Raph
 *
 */
public class Flight {

	private String code;
	private String airlineName;
	private String from;
	private String to;
	private String weekDay;
	private String time;
	private int seats;
	private double costPerSeat;

	public Flight() {
		
	}
	
	public Flight(String code, String airlineName, String from, String to, String weekDay, String time, int seats,
			double costPerSeat) {
		super();
		this.code = code;
		this.airlineName = airlineName;
		this.from = from;
		this.to = to;
		this.weekDay = weekDay;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;
		parseCode(this.code);
		
		
	}
	

	public String getCode() {
		return this.code;
	}

	public String getAirlineName() {
		return this.airlineName;
	}

	public String getFrom() {
		return this.from;
	}

	public String getTo() {
		return this.to;
	}

	public String getWeekDay() {
		return this.weekDay;
	}

	public String getTime() {
		return this.time;
	}

	public int getSeats() {
		return this.seats;
	}

	public void setSeats(int numberOfSeats) {
		this.seats+=numberOfSeats;
	}

	public double getCostPerSeat() {
		return this.costPerSeat;
	}

	public boolean isDomestic() {
		boolean isDomestic = false;

		Character firstAirportLetter = this.from.charAt(0);
		Character secondAirportLetter = this.to.charAt(0);

		if (firstAirportLetter.equals('Y') && secondAirportLetter.equals('Y')) {
			isDomestic = true;
		}
		return isDomestic;
	}

	private void parseCode(String code) {
		String shortend = code.substring(0, 2);

		
		
		if (shortend.toLowerCase().equals("oa")) {
			this.airlineName = "Otto Airlines";
		} else if (shortend.toLowerCase().equals("ca")) {
			this.airlineName = "Conned Air";
		} else if (shortend.toLowerCase().equals("tb")) {
			this.airlineName = "Try a Bus Airways";
		} else if (shortend.toLowerCase().equals("va")) {
			this.airlineName = "Vertical Airways";
		}else {
			this.airlineName = "Invalid";
		}
		
		
	}

	@Override
	public String toString() {

		return code + ", From" + from + ",To:" + to + ",Day:" + weekDay + ",Cost:" + costPerSeat;

	}

}
