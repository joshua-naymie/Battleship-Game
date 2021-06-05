package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.Border;

public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField messageToSend;
	private JTextArea displayMessage;
	private JButton send;

//	public static void main(String[] args) {
//		new ChatView();
//	}

	public ChatView() {
		createClientGUI();
	}

	public void createClientGUI() {

//		this.setBounds(400, 300, 500, 700);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		Border buttonEdge = BorderFactory.createRaisedBevelBorder();

		// Code block that builds the gui components to send messages
		JPanel fp = new JPanel(new GridLayout(3, 1));
		JLabel sendLabel = new JLabel("Message to Send:");
		messageToSend = new JTextField();
		JPanel sendPanel = new JPanel();
		send = new JButton("Send");
		send.setBorder(buttonEdge);
		send.addActionListener(new ButtonListener());
		send.setEnabled(false);
		sendPanel.add(send);
		fp.add(sendLabel);
		fp.add(messageToSend);
		fp.add(sendPanel);
		mainPanel.add(fp, BorderLayout.NORTH);

		// Code block to display any message sent from the client
		JPanel sp = new JPanel(new BorderLayout());
		JLabel receiveLabel = new JLabel("Message Board");
		displayMessage = new JTextArea();
		displayMessage.setBorder(BorderFactory.createEtchedBorder());
		displayMessage.setEditable(false);
		displayMessage.setPreferredSize(new Dimension(300,300));
		sp.add(receiveLabel, BorderLayout.NORTH);
		sp.add(displayMessage, BorderLayout.CENTER);
		mainPanel.add(sp, BorderLayout.CENTER);
		this.add(mainPanel);
		// this.pack();
		this.setVisible(true);
//		frame.add(mainPanel);
//		frame.setVisible(true);

	}

}

class ButtonListener implements ActionListener {

	// this is what Ali wrote in his code

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == connect) {
//			// if i can get to this button, it means i want to start a new chat session
//			newSession = true;
//			connectMe();
//		}
//		if (e.getSource() == send) {
//			Message m = new Message(userName, messageToSend.getText(), new Date());
//
//			try {
//				oos.writeObject(m);
//				displayMessage.append("Me: " + m.getMsg() + " (" + m.getTimeStamp() + ")\n");
//				messageToSend.setText("");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//
//		if (e.getSource() == disconnect) {
//			// if i hit this button, it means i want to quit and next time i connect,
//			// i will need to provide my username again
//			newSession = true;
//			Message m = new Message(userName, "has disconnected.", new Date());
//			try {
//				oos.writeObject(m);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//			disconnectMe();
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
