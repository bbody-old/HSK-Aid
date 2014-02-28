package ui;

import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import data.Cards;
import data.CardsSaveFile;
import data.Excel;
import data.StaticFunctions;

public class Main {
	private static final String LAST_FILENAME = "LASTFILENAME";
	private static Preferences prefs;
	
	public static boolean close(){
		int result = JOptionPane.showConfirmDialog(null, "Want more?", "Input",
                JOptionPane.YES_NO_OPTION);
		return (result == JOptionPane.YES_OPTION);
	}
	
	public static void main(String[] args) {
		prefs = Preferences.userRoot().node("lastfile");

		if (prefs.get(LAST_FILENAME, "").length() > 0){
			String filename = prefs.get(LAST_FILENAME, "");
			try {
				CardsSaveFile csf = new CardsSaveFile();
				csf.load(filename);
				Excel excel = new Excel(filename);
				Cards cards = excel.getCards(csf.getTitle(), csf.getEnglish(), csf.getChinese(), csf.getPinyin());
				csf.loadProgress(cards);
				MainWindow mw = new MainWindow(cards, filename);
			} catch (IOException e){
				ExcelLoader dialog = new ExcelLoader(filename);
				dialog.setVisible(true);
			} catch (InvalidFormatException e) {
				ExcelLoader dialog = new ExcelLoader(filename);
				e.printStackTrace();
			}
		} else {
			// Set up frame
			JFrame frame = new JFrame(Messages.getString("MainWindow.APPLICATION_TITLE"));

			// Open a dialog box to get file
			String filename = StaticFunctions.getFile(frame);

			// Check if filename has any problems
			switch (StaticFunctions.filenameCheck(filename)){
			case OK:
				ExcelLoader dialog = new ExcelLoader(filename);
				dialog.setVisible(true);
				prefs.put(LAST_FILENAME, filename);
				break;
			case NULL:
				// TODO: Make this a close dialog.
				//JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
				if (Main.close()){
					System.exit(0);
				}
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

}
