package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.Cards;
import data.StaticFunctions;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame(Messages.getString("MainWindow.APPLICATION_TITLE"));
		String filename = StaticFunctions.getFile(null);
		switch (StaticFunctions.filenameCheck(filename)){
		case OK:
			ExcelLoader dialog = new ExcelLoader(filename);
			//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			
			break;
		case NULL:
			JOptionPane.showMessageDialog(frame, "Nothing selected.");
			break;
		case NOTHING:
			JOptionPane.showMessageDialog(frame, "Nothing selected.");
			break;
		case EXTENSION:
			JOptionPane.showMessageDialog(frame, "Wrong extension type.");
			break;
		}
	}

}
