package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StaticFunctions {
	public static ArrayList<Card> getCardsFromCSV(String filename){
		
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
			  while (strLine != null){
				  //System.out.println(strLine);
				  String [] elements = strLine.split(",");
				  if ((elements.length == 6) && (elements[0].length() > 0)){

					  Card c = new Card(elements[2], elements[1], elements[4], elements[5], elements[3]);
					  cards.add(c);
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
	}	
	
}
