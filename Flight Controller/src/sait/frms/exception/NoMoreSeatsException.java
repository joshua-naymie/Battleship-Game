package sait.frms.exception;

import sait.frms.problemdomain.Flight;

/**	
 * 
 * @author Mohamed, Jordan
 *
 */

public class NoMoreSeatsException extends Exception {

	public NoMoreSeatsException() {
		super("This flight does not have enough seats.");
	}

	public NoMoreSeatsException(Flight flight) {
		super("This flight does not have enough seats." + flight);

	}

}
