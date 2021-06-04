package client.domain;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

import client.gui.LoginPanel;
import client.gui.MainWindow;
import server.domain.Client;
import server.domain.NC;

public class ClientDriver {

	public static void main(String[] args) throws IOException {
		MainWindow window = new MainWindow();

//		Commented section below demonstrates changing client name with server

		try {

			while (!LoginPanel.isLoggedIn) {

			}
			System.out.println("INSIDE IF STATMENT");
			Socket socket = new Socket("127.0.0.1", 9992);
			System.out.println("Socket :" + socket.toString());
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			String userName = LoginPanel.clientLoggedIn.getName();
			System.out.println(LoginPanel.clientLoggedIn.toString());
			System.out.println("Username " + userName);
			// allocate +1 byte for NC.NAME_CHANGE byte
			ByteBuffer buffer = ByteBuffer.allocate(1 + userName.getBytes().length);
			buffer.put(NC.SET_NAME);
			buffer.put(userName.getBytes());

			byte[] bytes = buffer.array();

			output.writeInt(bytes.length);
			output.write(bytes);
			output.flush();
			socket.close();

		} catch (Exception e) {
			System.out.println("EEERRRROOOOORRRRR!!!!!");
			e.printStackTrace();
		}

		for (;;) {

		}
	}
}