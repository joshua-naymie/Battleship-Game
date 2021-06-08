package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;

import javax.swing.*;
import javax.swing.border.Border;

import client.domain.Message;
import client.domain.NC;

public class ChatView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField messageToSend;
	private JTextArea displayMessage;
	private JButton send, disconnect;

//	public static void main(String[] args) {
//		new ChatView();
//	}

	public ChatView() {
		createClientGUI();
	}

	public void createClientGUI() {

		this.setBounds(5, 5, 100, 100);
		

		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout(1, 1));
		Border buttonEdge = BorderFactory.createRaisedBevelBorder();

		// Code block that builds the gui components to send messages
		JPanel fp = new JPanel(new GridLayout(3, 1));
		JLabel sendLabel = new JLabel("Message to Send:");
		messageToSend = new JTextField();
		JPanel sendPanel = new JPanel();
		send = new JButton("Send");
		send.setBorder(buttonEdge);
		send.addActionListener(new ButtonListener());
		send.setEnabled(true);
		sendPanel.add(send);
		fp.add(sendLabel);
		fp.add(messageToSend);
		fp.add(sendPanel);
		mainPanel.add(fp, BorderLayout.NORTH);

		// add a disconnect button to send the NC if the user hits disconnect
		disconnect = new JButton("Disconnect");

		disconnect.setBorder(buttonEdge);
		disconnect.addActionListener(new ButtonListener());
		disconnect.setEnabled(false);
		

		// Code block to display any message sent from the client
		JPanel sp = new JPanel(new BorderLayout());
		JLabel receiveLabel = new JLabel("Message Board");
		displayMessage = new JTextArea();
		displayMessage.setBorder(BorderFactory.createEtchedBorder());
		displayMessage.setEditable(false);
		displayMessage.setPreferredSize(new Dimension(300, 300));
		sp.add(receiveLabel, BorderLayout.NORTH);
		sp.add(displayMessage, BorderLayout.CENTER);
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(disconnect, BorderLayout.SOUTH);
		this.add(mainPanel);
		// this.pack();
		this.setVisible(true);
//		frame.add(mainPanel);
//		frame.setVisible(true);

	}

	class ButtonListener implements ActionListener {

		// this is what Ali wrote in his code

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == send) {
				Message m = new Message(NC.SET_NAME, messageToSend.getText());

				displayMessage.append(m.getUser() + ": " + m.getMsg() + "\n");
				messageToSend.setText("");
			}
			if(e.getSource() == disconnect) {
				// send NC to know that we disconnected
			}
		}
	}
}
