package client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import exceptions.PortNumberIsNullException;
import exceptions.ServerAddressIsNullException;
import exceptions.UserNameIsNullException;

public class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// CLASS FIELDS
	// ----------------------------------------

	private static final Dimension LOGIN_BUTTON_DIMENSIONS = new Dimension(330, 50);

	private static final Insets TEXT_FIELD_INSET = new Insets(0, 5, 0, 5);

	private static final Border PANEL_MARGIN = BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 30, true),
			PANEL_BORDER = BorderFactory.createLineBorder(new Color(245, 189, 2, 255), 8, false);

	private static final Font LABEL_FONT = new Font("Sans Serif", Font.BOLD, 16),
			LOGIN_BUTTON_FONT = new Font("Sans Serif", Font.BOLD, 25);

	private static final Color LOGIN_BUTTON_COLOR = new Color(206, 32, 41), BACKGROUND_COLOR = new Color(0, 0, 0, 192),
			LABEL_FONT_COLOR = Color.WHITE;

	private static final String MILITARY_FONT_DIR = "res/Client/Fonts/Military Stencil.ttf",
			BACKGROUND_IMG_DIR = "res/Client/Images/LoginBackground.jpg";

	private static final int FIELD_SPACING = 30, SPECIAL_SPACING = 70;

	private static BufferedImage backgroundImage;

	private static Font partialFont, bigFont, smallFont;

	public boolean isLoggedIn;
	// CLASS CONSTRUCTOR
	// ----------------------------------------

	static {
		try {
			InputStream stream = new BufferedInputStream(new FileInputStream(MILITARY_FONT_DIR));
			partialFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			bigFont = partialFont.deriveFont(Font.PLAIN, 84);
			smallFont = partialFont.deriveFont(Font.PLAIN, 28);

			backgroundImage = ImageIO.read(new FileInputStream(BACKGROUND_IMG_DIR));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	// INSTANCE FIELDS
	// ----------------------------------------

	private JPanel panel = new JPanel();

	private JLabel titleLabel = new JLabel("BATTLESHIP"), usernameLabel = new JLabel("Username:"),
			serverAddressLabel = new JLabel("Address:"), serverPortLabel = new JLabel("Port:");

	private final JLabel[] allLabels = { usernameLabel, serverAddressLabel, serverPortLabel };

	private JTextField username = new JTextField(), serverAddress = new JTextField("127.0.0.1"),
			serverPort = new JTextField("6969");

	private final JTextField[] allTextFields = { username, serverAddress, serverPort };

	private JButton loginButton = new JButton("LAUNCH");

	// CONSTRUCTOR
	// ----------------------------------------

	/**
	 * Constructor for LoginPanel
	 */
	public LoginPanel() {
		initPanels();
		initComponents();
		initLayout();

	}

	// INIT PANELS
	// ----------------------------------------

	/**
	 * Configures this panel and all other panels
	 */
	private void initPanels() {
		this.setLayout(new GridBagLayout());

		// ----------

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(BACKGROUND_COLOR);

		panel.setBorder(BorderFactory.createCompoundBorder(PANEL_BORDER, PANEL_MARGIN));
	}

	// INIT COMPONENTS
	// ----------------------------------------

	/**
	 * Initializes and configures all components
	 */
	private void initComponents() {
		// Title

		titleLabel.setFont(bigFont);
		titleLabel.setForeground(LABEL_FONT_COLOR);

		// Labels
		// ----------

		for (JLabel label : allLabels) {
			label.setFont(LABEL_FONT);
			label.setForeground(LABEL_FONT_COLOR);
		}

		// TextFields
		// ----------

		for (JTextField textfield : allTextFields) {
			textfield.setFont(smallFont);
		}

		// Login button
		// ----------

		loginButton.setPreferredSize(LOGIN_BUTTON_DIMENSIONS);
		loginButton.setMinimumSize(LOGIN_BUTTON_DIMENSIONS);
		loginButton.setMaximumSize(LOGIN_BUTTON_DIMENSIONS);

		loginButton.setFont(LOGIN_BUTTON_FONT);
		loginButton.setForeground(LABEL_FONT_COLOR);
		loginButton.setBackground(LOGIN_BUTTON_COLOR);

		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);

		// Align all components
		// ----------

		username.setAlignmentX(LEFT_ALIGNMENT);
		username.setMargin(TEXT_FIELD_INSET);

		serverAddress.setAlignmentX(LEFT_ALIGNMENT);
		serverAddress.setMargin(TEXT_FIELD_INSET);

		serverPort.setAlignmentX(LEFT_ALIGNMENT);
		serverPort.setMargin(TEXT_FIELD_INSET);

		loginButton.setAlignmentX(LEFT_ALIGNMENT);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isLoggedIn = true;
				try {
					launchGame(username.getText(), serverAddress.getText(), serverPort.getText());

				} catch (Exception ex) {

					if (ex instanceof UserNameIsNullException) {
						JOptionPane.showMessageDialog(null, "Please Enter Your name");
					} else if (ex instanceof ServerAddressIsNullException) {
						JOptionPane.showMessageDialog(null, "Please Enter A Valid Address");
					} else if (ex instanceof PortNumberIsNullException) {
						JOptionPane.showMessageDialog(null, "Please Enter the Port Number");
					}

					// TODO Auto-generated catch block

				}

			}
		});

	}

	private void launchGame(String userName, String serverAdress, String portNumber)
			throws UserNameIsNullException, ServerAddressIsNullException, IOException {

		if (userName.isEmpty() || userName == null || userName.equals("")) {
			System.out.println("Username is null");
			throw new UserNameIsNullException();
		} else if (serverAdress.isEmpty() || serverAdress == null) {
			throw new ServerAddressIsNullException();
		} else if (portNumber.isEmpty() || portNumber == null) {
			throw new ServerAddressIsNullException();
		} else {
			MainWindow.initWindow();
		}
	}

	// INIT LAYOUT
	// ----------------------------------------

	/**
	 * Configures the layout and adds all components
	 */
	private void initLayout() {
		Box box = Box.createVerticalBox();

		// Title
		box.add(titleLabel);

		box.add(Box.createVerticalStrut(SPECIAL_SPACING));

		// Username
		box.add(usernameLabel);
		box.add(Box.createVerticalGlue());
		box.add(username);

		box.add(Box.createVerticalStrut(FIELD_SPACING));

		// Server Address
		box.add(serverAddressLabel);
		box.add(Box.createVerticalGlue());
		box.add(serverAddress);

		box.add(Box.createVerticalStrut(FIELD_SPACING));

		// Server Port
		box.add(serverPortLabel);
		box.add(Box.createVerticalGlue());
		box.add(serverPort);

		box.add(Box.createVerticalStrut(SPECIAL_SPACING));

		// Login Button
		box.add(loginButton);

		// ----------

		panel.add(box);

		this.add(panel);
	}

	// GRAPHICS
	// ----------------------------------------

	/**
	 * Overrides the paintComponent method to include a background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		Dimension size = getParent().getSize();
//		g2.drawImage(backgroundImage, 0, 0, size.width, size.height, this);
	}

}

//class switchToMainView implements ActionListener {
//	public boolean isLoggedIn;
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		isLoggedIn = true;
//
//	}
//
//}
