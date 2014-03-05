package ui;

import java.awt.Frame;
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

	public static boolean close(){
		int result = JOptionPane.showConfirmDialog(null, "Are sure you want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION);
		return (result == JOptionPane.YES_OPTION);
	}
	public static String filenameChooser(boolean close){
		// Set up frame
		JFrame frame = new JFrame(Messages.getString("MainWindow.APPLICATION_TITLE"));

		// Open a dialog box to get file
		String filename = StaticFunctions.getFile(frame);

		// Check if filename has any problems
		switch (StaticFunctions.filenameCheck(filename)){
		case OK:
			//loadingFile(filename);
			return filename;
		case NULL:
			// TODO: Make this a close dialog.
			//JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
			if (close && Main.close()){
				System.exit(0);
			}
		case NOTHING:
			if (close)
			JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NOTHING_SELECTED"));
			return null;
		case EXTENSION:
			JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_EXTENSION"));
			return null;
		case NOT_EXIST:
			JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.MESSAGE_NON_EXIST"));
			return null;
		}
		return null;
	}
	
	public static void loadingFile(String filename){
		Cards cards = null;
		try {
			csf.load(filename);
			Excel excel = new Excel(filename);
			cards = excel.getCards(csf.getTitle(), csf.getEnglish(), csf.getChinese(), csf.getPinyin());
			csf.loadProgress(cards);
			
		} catch (Exception e1) {
			ExcelLoader dialog = new ExcelLoader(filename, true);
			
			dialog.setVisible(true);
			if (dialog.cards != null){
				cards = dialog.cards;
			}
		}

			MainWindow mw = new MainWindow(cards, filename);
	}
	private static CardsSaveFile csf;

	public static void entryPoint(Frame frame){
		String filename = "";
		if ((filename = filenameChooser(false)) != null){
			frame.dispose();
			loadingFile(filename);
		}
	}
	public static void entryPoint(){
		csf = new CardsSaveFile();

		if (csf.hasFilename()){
			loadingFile(csf.getFilename());
			
		} else {
			String filename = "";
			while ((filename = filenameChooser(true)) == null);
			
			loadingFile(filename);
		}
	}
	
	public static void main(String[] args) {
		entryPoint();
	}

}
