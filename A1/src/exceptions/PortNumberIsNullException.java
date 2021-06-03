package exceptions;

public class PortNumberIsNullException extends Exception{
	public PortNumberIsNullException() {
		super("Your name cannot be empty or null");
	}

	public PortNumberIsNullException(String port) {
		super("Your name cannot be empty or null" + port);
	}
}
