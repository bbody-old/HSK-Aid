package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class StaticFunctions {
	/*
	public static ArrayList<Card> getCardsFromCSV(String filename, int titleRow,
			int englishColumn, int chineseColumn, int pinyinColumn){
		
		File filReadMe = new File(filename);
		ArrayList<Card> cards = new ArrayList<Card>();
		BufferedReader brReadMe;
		try {
			brReadMe = new BufferedReader
				     (new InputStreamReader(new FileInputStream(filReadMe), "UTF-8"));
			String strLine = brReadMe.readLine();
			//for all lines
			strLine = brReadMe.readLine();
			strLine = brReadMe.readLine();
			int offset = 0;
			  while (strLine != null){
				  if (offset >= titleRow){
					  System.out.println(strLine);
					  String [] elements = strLine.split(",");
					  if ((elements.length == 6) && (elements[0].length() > 0)){
	
						  Card c = new Card(elements[chineseColumn],
								  elements[chineseColumn], elements[englishColumn],
								  elements[englishColumn], elements[pinyinColumn]);
						  cards.add(c);
						  System.out.println(c.toString());
					  }
				  }
			   strLine = brReadMe.readLine();
			  } //end for
			  
			  brReadMe.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  
		  
		
		
		return cards;
	}*/
	
	public static enum fileCheck{
		OK,
		NULL,
		NOTHING,
		EXTENSION
	};
	

	public static String getFile(JFrame frame){
		JFileChooser chooser = new JFileChooser();
		
		FileFilter ff = new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				if (arg0.isDirectory()){
					return true;
				}
				return arg0.getName().endsWith(".xls");
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Excel Documents";
			}};
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(ff);
	    int returnVal = chooser.showOpenDialog(frame);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " + //$NON-NLS-1$
	            chooser.getSelectedFile().getName());
	    }
	    String filename;
	    try {
	    	filename = chooser.getSelectedFile().getAbsolutePath();
	    } catch (NullPointerException e){
	    	return null;
	    }
	    
	    return filename;
	    
	}
	
	public static fileCheck filenameCheck(String filename){
		if (filename == null){
			return fileCheck.NULL;
		} else if (!(filename.length() > 0)){
			return fileCheck.NOTHING;
		} else if (!filename.endsWith(".xls")){ //$NON-NLS-1$
			System.out.println(filename.substring(filename.length() - 3, filename.length()));
			return fileCheck.EXTENSION;
		} else {
			return fileCheck.OK;
		}
	}
	
}
