package sait.frms.problemdomain;


/**	
 * 
 * @author Mohamed, Jordan, Raph
 *
 */
public class Reservation {
	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;

	public Reservation(String code, String flightCode, String airline, String name, String citizenship, double cost,
			boolean active) {
		super();
		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCode() {
		return code;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public String getAirline() {
		return airline;
	}

	public double getCost() {
		return cost;
	}

	@Override
	public String toString() {
//		return "Reservation [code=" + code + ", flightCode=" + flightCode + ", airline=" + airline + ", name=" + name
//				+ ", citizenship=" + citizenship + ", cost=" + cost + ", active=" + active + "]";
		return code;
	}

}
