package sait.frms.exception;


/**	
 * 
 * @author Mohamed, Jordan
 *
 */

public class InvalidTravelerNameException extends Exception {

	public InvalidTravelerNameException() {
		super("Your name cannot be empty or null");
	}

	public InvalidTravelerNameException(String name) {
		super("Your name cannot be empty or null" + name);
	}

}
