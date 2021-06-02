package client.domain;

import java.io.*;

import client.gui.MainWindow;

public class ClientDriver
{

	public static void main(String[] args) throws IOException
	{
		MainWindow window = new MainWindow();
		
		//Commented section below demonstrates changing client name with server
		
//		try
//		{
//			Socket socket = new Socket("127.0.0.1", 9992);
//			
//			DataInputStream input = new DataInputStream(socket.getInputStream());
//			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//			
//			String name = "Zapp Brannigan";
//		
//			//allocate +1 byte for NC.NAME_CHANGE byte
//			ByteBuffer buffer = ByteBuffer.allocate(1 + name.getBytes().length);
//			buffer.put(NC.CHANGE_NAME);
//			buffer.put(name.getBytes());
//			
//			byte[] bytes = buffer.array();
//			
//			output.writeInt(bytes.length);
//			output.write(bytes);
//			output.flush();
//		}
//		catch(Exception e)
//		{
//			System.out.println("EEERRRROOOOORRRRR!!!!!"); 
//			e.printStackTrace();
//		}
//		for(;;)
//		{
//			
//		}
	}
}