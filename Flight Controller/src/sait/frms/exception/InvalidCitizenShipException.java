package sait.frms.exception;


/**	
 * 
 * @author Mohamed, Jordan
 *
 */

public class InvalidCitizenShipException extends Exception {
	public InvalidCitizenShipException() {
		super("Citizenship cannot be empty or null");
	}

	public InvalidCitizenShipException(String citizenship) {
		super("Citizenship cannot be empty or null" + citizenship);
		
	}

}
