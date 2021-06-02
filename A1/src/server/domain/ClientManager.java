package server.domain;

import java.util.LinkedList;

public class ClientManager extends Observer {
	ConnectionManager connections = new ConnectionManager();
	LinkedList<Client> clients = new LinkedList<Client>();

	// CONSTRUCTOR
	// ----------------------------------------

	/**
	 * Constructor for ClientManager Creates a ConnectionManager and sets itself as
	 * an Observer
	 */
	public ClientManager() {
		setSubject(connections);
		connections.addObserver(this);
	}

	// UPDATE
	// ----------------------------------------

	/**
	 * Overrides the update method Gets a new Client from the ConnectionManager and
	 * 
	 * adds it to clients
	 */

	@Override
	public void update() {
		Client newClient = connections.getState();
		if (newClient == null) {
			System.out.println("NULL");
		}
		// should we put this inside an else? bcz otherwise it will be ran anyways after
		// we check for null
		// or we can return; after the first if condition
		newClient.addObserver(this);

		clients.add(newClient);
	}

	// CLIENT COUNT
	// ----------------------------------------

	/**
	 * Gets the number of Clients
	 * 
	 * @return the number of Clients
	 */
	public int getClientCount() {
		return clients.size();
	}

	// MATCH PLAYERS
	// ----------------------------------------

	/**
	 * Looks for 2 players ready for a match NOT COMPLETE
	 */
	public void matchPlayers() {
		Client client;
		Client[] matchedClients = new Client[2];
		int counter = 0, clientCounter = 0;

		while ((clientCounter < 2) && (counter < clients.size())) {
			client = clients.get(counter++);

			if (client.isLookingForMatch()) {
				matchedClients[counter++] = client;
			}
		}
	}
}