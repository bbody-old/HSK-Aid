package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.StaticFunctions;

public class Main {
	public static void main(String[] args) {
		// Set up frame
		JFrame frame = new JFrame(Messages.getString("MainWindow.APPLICATION_TITLE"));
		
		// Open a dialog box to get file
		String filename = StaticFunctions.getFile(frame);
		
		// Check if filename has any problems
		switch (StaticFunctions.filenameCheck(filename)){
			case OK:
				ExcelLoader dialog = new ExcelLoader(filename);
				dialog.setVisible(true);
				break;
			case NULL:
				// TODO: Make this a close dialog.
				JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
				break;
			case NOTHING:
				JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
				break;
			case EXTENSION:
				JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_EXTENSION"));
				break;
			case NOT_EXIST:
				JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NON_EXIST"));
				break;
		}
	}

}
