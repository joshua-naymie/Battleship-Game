package server.domain;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.ByteBuffer;



public class Client extends Subject<byte[]>
{
	private static final String SOCKET_CLOSED_ERROR = "ERROR: socket is closed!",
			SOCKET_NULL_ERROR = "ERROR: socket is null!";

	private ClientManager manager;

	private Socket socket;

	private DataInputStream clientInput;

	private DataOutputStream clientOutput;

	private String name;

	private short id;

	private boolean lookingForMatch = false,
					clientConnected = true,
					unreadMessage = false;

	// CONSTRUCTOR
	// ----------------------------------------

	/**
	 * Constructor for Client
	 * @param clientSocket the socket the client is connected through
	 * @throws SocketException thrown if socket is null or closed
	 */
	public Client(Socket clientSocket, short id, ClientManager manager) throws SocketException
	{
		setSocket(clientSocket);
		setId(id);
		setManager(manager);
		initThread();
	}

	// RUN
	// ----------------------------------------

	/**
	 * Overrides the run method While the client is connected, waits for input from
	 * the client socket and responds
	 */
	@Override
	public void run()
	{
		if (id > NC.ERROR)
		{
			while (clientConnected)
			{
				// Get length of client message
				int length = tryReadLength();

				if (length > 0)
				{
					// Reads client byte stream
					byte[] message = tryReadDataStream(length);
					// Creates a ByteBuffer of data only (offsets first byte)
					ByteBuffer buffer = ByteBuffer.wrap(message);
					buffer.put(0, BigInteger.valueOf(id).toByteArray());

					// **NOT COMPLETE**
					// Determines action based on first byte
					switch (message[0])
					{
						case NC.SHIP_PLACEMENT:
							state = message;
							unreadMessage = true;
							notifyObservers();
							break;

						case NC.SET_NAME:
							byte[] name = new byte[buffer.remaining()];
							buffer.get(buffer.position(), name, 0, buffer.remaining());
							setName(new String(name));
							break;

						case NC.END_SESSION:
							clientConnected = false;
							tryWriteDataStream(new byte[] { NC.END_SESSION });
							closeConnections();
							break;
					}
				}
			}
		}
		// TODO: disconnect client
		else
		{

		}
	}

	// SET SOCKET
	// ----------------------------------------

	/**
	 * Sets the client's socket and creates input and output streams
	 * @param clientSocket the Socket the client is connected through
	 * @throws SocketException thrown if socket is null or closed
	 */
	private void setSocket(Socket clientSocket) throws SocketException
	{
		try
		{
			if (clientSocket == null)
			{
				throw new SocketException(SOCKET_NULL_ERROR);
			} else if (clientSocket.isClosed())
			{
				throw new SocketException(SOCKET_CLOSED_ERROR);
			} else
			{
				socket = clientSocket;

				setInputStream(new DataInputStream(socket.getInputStream()));
				setOutpuStream(new DataOutputStream(socket.getOutputStream()));
			}
		} catch (IOException e)
		{
			System.out.println("CLIENT SOCKET ERROR");
		}
	}

	// ID
	// ----------------------------------------

	/**
	 * Assigns the clients ID
	 * @param id the ID to assign
	 */
	private void setId(short id)
	{
		this.id = id;
	}

	// --------------------

	/**
	 * Gets the clients ID
	 * @return the ID of the client
	 */
	public short getId()
	{
		return id;
	}

	// MANAGER
	// ----------------------------------------

	/**
	 * Assigns the ClientManager for this client
	 * @param manager the ClientManager for this client
	 */
	private void setManager(ClientManager manager)
	{
		this.manager = manager;
	}
	
	// ----------------------------------------
	
	public boolean hasUnreadMessage()
	{
		return unreadMessage;
	}

	// INPUT-STREAM
	// ----------------------------------------

	/**
	 * Assigns the clients DataInputStream
	 * @param inputStream the DataInputStream to assign
	 */
	private void setInputStream(DataInputStream inputStream)
	{
		this.clientInput = inputStream;
	}

	// --------------------

	/**
	 * Gets the clients DataInputStream
	 * @return the clients DataInputStream
	 */
	public DataInputStream getInputStream()
	{
		return clientInput;
	}

	// OUTPUT-STREAM
	// ----------------------------------------

	/**
	 * Assigns the clients DataOutputStream
	 * @param outputStream the DataOutputStream to assign
	 */
	private void setOutpuStream(DataOutputStream outputStream)
	{
		this.clientOutput = outputStream;
	}

	// --------------------

	/**
	 * Gets the clients DataOutputStream
	 * @return the clients DataOutputStream
	 */
	public DataOutputStream getOutputStream()
	{
		return clientOutput;
	}

	// NAME
	// ----------------------------------------

	/**
	 * Sets the clients name
	 * @param name the name of the client
	 */
	public void setName(String name)
	{
		this.name = name;
		System.out.println("Name Set: " + this.name);
	}

	// --------------------

	/**
	 * Gets the clients name
	 * @return the clients name
	 */
	public String getName()
	{
		return name;
	}

	// LOOKING FOR MATCH
	// ----------------------------------------

	/**
	 * Sets whether or not the player is looking for a match
	 * @param lookingForMatch whether or not the player is looking for a match
	 */
	private void setLookingForMatch(boolean lookingForMatch)
	{
		this.lookingForMatch = lookingForMatch;
	}

	// --------------------

	/**
	 * Gets whether or not the player is looking for a match
	 * @return whether or not the player is looking for a match
	 */
	public boolean isLookingForMatch()
	{
		return lookingForMatch;
	}

	// DATA STREAMS
	// ----------------------------------------

	public boolean tryWriteToClient(byte[] message)
	{
		if (tryWriteLength(message.length))
		{
			if (tryWriteDataStream(message))
			{
				return true;
			}

		}

		return false;
	}

	/**
	 * Tries to read the length of the incoming message Returns 0 if an error is
	 * encountered
	 * @return the length of the incoming message
	 */
	private int tryReadLength()
	{
		try
		{
			return clientInput.readInt();
		} catch (IOException e)
		{
			// Adds 1s delay so console isn't spammed
			e.printStackTrace();
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return 0;
	}

	// --------------------

	/**
	 * Attempts to write the length of the outgoing message to the client
	 * @param length the length of the message about to be sent
	 * @return returns true if length successfuly sent, false if not
	 */
	private boolean tryWriteLength(int length)
	{
		try
		{
			clientOutput.writeInt(length);
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	// ----------------------------------------

	/**
	 * Tries to read the incoming message
	 * @param length the number of bytes to read
	 * @return the incoming message. returns NC.ERROR code if an error is
	 *         encountered
	 */
	private byte[] tryReadDataStream(int length)
	{
		try
		{
			byte[] bytes = new byte[length];
			clientInput.readFully(bytes, 0, length);
			return bytes;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new byte[]
		{ NC.ERROR };
	}

	// ----------------------------------------

	/**
	 * Tries to write data stream to the client
	 * @param data the data to write
	 * @return returns true if data successfully written
	 */
	private boolean tryWriteDataStream(byte[] data)
	{
		try
		{
			clientOutput.write(data);

			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	// ----------------------------------------

	/**
	 * Attempts to close the connection to the client
	 */
	private void closeConnections()
	{
		try
		{
			clientInput.close();
			clientOutput.close();
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void notifyObservers()
	{
		for (Observer observer : observers)
		{
			observer.update(this);
		}
	}

	@Override
	public boolean equals(Object objectToCompare)
	{
		if (objectToCompare instanceof Client)
		{
			return ((Client) objectToCompare).getId() == this.getId();
		}

		return false;
	}
}