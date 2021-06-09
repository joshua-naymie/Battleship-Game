package client.domain;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

import client.gui.LoginPanel;
import client.gui.MainWindow;
import server.domain.Client;
import server.domain.NC;
import server.domain.Server;

public class ClientDriver {

	public static void main(String[] args) throws IOException, InterruptedException {
		MainWindow window = new MainWindow();
		// delete this later on this is just for convenience so we don't need ot open two programs
		//Server.main(args);
		

//		Commented section below demonstrates changing client name with server

//		try {
//
//			while (!LoginPanel.isLoggedIn) {
////				System.out.println("");
//
//			}
//			System.out.println("Hello, welcome");
//			System.out.println(NC.SET_NAME);
//			System.out.println("isloggedin " + LoginPanel.isLoggedIn);
//
//			// Socket socket = new Socket("127.0.0.1", 9992);
//
////			System.out.println("Socket :" + socket.toString());
////			// DataInputStream input = new DataInputStream(socket.getInputStream());
////
//			//DataOutputStream output = new DataOutputStream(NC.SET_SOCKET.getOutputStream());
//
//			// allocate +1 byte for NC.NAME_CHANGE byte
////			ByteBuffer buffer = ByteBuffer.allocate(1 + userName.getBytes().length);
////			buffer.put(NC.SET_NAME);
////			buffer.put(userName.getBytes());
////			
////
////			byte[] bytes = buffer.array();
////
////			output.writeInt(bytes.length);
////			output.write(bytes)
////
////			output.flush();
//			// socket.close();
//
//		} catch (Exception e) {
//			System.out.println("EEERRRROOOOORRRRR!!!!!");
//			e.printStackTrace();
//		}
//
//		for (;;) {
//
//		}
	}
}