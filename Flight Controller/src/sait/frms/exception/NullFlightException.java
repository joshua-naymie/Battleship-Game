package sait.frms.exception;

import sait.frms.problemdomain.Flight;

/**	
 * 
 * @author Mohamed, Jordan
 *
 */

public class NullFlightException extends Exception {

	public NullFlightException() {
		super("Flight cannot be empty, please choose a flight");
	}

	public NullFlightException(Flight flight) {
		super("Flight cannot be empty, please choose a flight" + flight);
	}
}

