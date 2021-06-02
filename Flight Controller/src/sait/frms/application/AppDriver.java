package sait.frms.application;

import sait.frms.gui.MainWindow;

import java.io.FileNotFoundException;
import java.io.IOException;

import sait.frms.gui.*;

/**
 * Application driver.
 * 
 */
public class AppDriver {

	/**
	 * Entry point to Java application.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
		
		
		
	}

}
