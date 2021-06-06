package server.domain;

import java.nio.ByteBuffer;

public class Match extends Observer implements Runnable {
	enum Phase {
		SHIP_PLACEMENT, TURN, GAME_ENDED
	}

	private static final int GRID_DIMENSIONS = 2, SHIP_LOCATIONS = 17;

	private Client player1, player2;

	private Phase currentPhase = Phase.SHIP_PLACEMENT;

	private byte[][] p1ShipLocations = null, p2ShipLocations = null;

	private boolean p1ShipsPlaced = false, p2ShipsPlaced = false, gameIsRunning = true;

	public Match(Client player1, Client player2) {
		setPlayers(player1, player2);
	}

	private void setPlayers(Client player1, Client player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	@Override
	public void run() {
		player1.tryWriteToClient(new byte[] { NC.MATCH_STARTED });
		player2.tryWriteToClient(new byte[] { NC.MATCH_STARTED });

		while (currentPhase.equals(Phase.SHIP_PLACEMENT)) {
			try {
				wait();
				if (!p1ShipLocations.equals(null) && !p2ShipLocations.equals(null)) {
					currentPhase = Phase.TURN;
				}
			} catch (InterruptedException exception) {
				// TODO Auto-generated catch block
				exception.printStackTrace();
			}
		}

		while (currentPhase.equals(Phase.TURN)) {

			try {
				player1.tryWriteToClient(new byte[] { NC.CLIENT_TURN });
				wait();

				player2.tryWriteToClient(new byte[] { NC.CLIENT_TURN });
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void parseMessage(ByteBuffer buffer) {
		buffer.rewind();
		switch (currentPhase) {
		case SHIP_PLACEMENT:
			if (buffer.getShort() == player1.getId()) {
				if (buffer.get() == NC.SHIP_PLACEMENT) {
					if (buffer.remaining() == (GRID_DIMENSIONS * SHIP_LOCATIONS)) {
						for (int i = 0; i < SHIP_LOCATIONS; i++) {
							for (int j = 0; j < GRID_DIMENSIONS; j++) {
								p1ShipLocations[i][j] = buffer.get();
								p1ShipsPlaced = true;
							}
						}
					} else {
//							clientError(player1, NC.SHIP_PLACEMENT);
					}
				} else {
//						clientError(player1, NC.SHIP_PLACEMENT);
				}

			} else {
				if (buffer.get() == NC.SHIP_PLACEMENT) {
					if (buffer.remaining() == (GRID_DIMENSIONS * SHIP_LOCATIONS)) {
						for (int i = 0; i < SHIP_LOCATIONS; i++) {
							for (int j = 0; j < GRID_DIMENSIONS; j++) {
								p2ShipLocations[i][j] = buffer.get();
								p2ShipsPlaced = true;
							}
						}
					} else {
//							clientError(player1, NC.SHIP_PLACEMENT);
					}
				} else {
//						clientError(player1, NC.SHIP_PLACEMENT);
				}
			}

			this.notify();
			break;

		case TURN:
			break;
		default:
			break;
		}
	}

	private void clientError(Client player, byte stage) {
		player.tryWriteToClient(new byte[] { NC.ERROR, stage });
	}

	@Override
	public void update() {

	}

	@Override
	public void update(Subject<?> subject) {
		parseMessage((ByteBuffer) subject.getState());
	}

}
