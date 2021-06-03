package exceptions;

public class ServerAddressIsNullException extends Exception {
	public ServerAddressIsNullException() {
		super("The address entered is null or invalid");
	}

	public ServerAddressIsNullException(String address) {
		super("The address entered is null or invalid" + address);
	}
}
