package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel {
	public Vector<Vector<String>> excel;
	private int rowsSize = 0;
	private int columnsSize = 0;
	
	public Excel(String filename) throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream(filename);
		excel = new Vector<Vector<String>>();
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		//Row row = sheet.getRow(0);
		//Cell cell = row.getCell(3);
		for (Iterator<Row> row = sheet.rowIterator(); row.hasNext();){
			rowsSize++;
			int columnsOfRow = 0;
			Vector<String> rowList = new Vector<String>();
			for (Iterator<Cell> cell = row.next().cellIterator(); cell.hasNext();){
				columnsOfRow++;
				Cell c = cell.next();
				try{
					String value = c.getStringCellValue();
					//System.out.println(value);
					rowList.addElement(value);
				} catch(IllegalStateException e){
					Double d = new Double(c.getNumericCellValue());
					rowList.addElement(d.toString());
				}
			}
			excel.addElement(rowList);
			if (columnsOfRow > columnsSize){
				columnsSize = columnsOfRow;
			}
		}
		
	}
	
	public int columnSize(){
		return Math.min(columnsSize, 5);
	}
	
	public int rowSize(){
		return Math.min(rowsSize, 5);
	}
	
	public String[][] getValues(){
		String [][] values = new String[Math.min(rowsSize, 5)][Math.min(columnsSize, 5)];
		
		for (int i = 0; i < Math.min(rowsSize, 5); i++){
			for (int j = 0; j < Math.min(columnsSize, 5); j++){
				try{
				values[i][j] = excel.get(i).get(j);
				//System.out.println(values[i][j]);
				}
				catch (NullPointerException e){
					values[i][j] = "";
				}
				catch (ArrayIndexOutOfBoundsException e){
					values[i][j] = "";
				}
			}
		}
		return values;
	}
	
	public Cards getCards(int titleRow, int englishColumn, 
			int chineseColumn, int pinyinColumn){
		ArrayList<Card> cards = new ArrayList<Card>();
		
		for (int i = titleRow + 1; i < excel.size(); i++){
			Vector<String> temp = excel.get(i);
			if (i == titleRow){
			System.out.println(temp.toString());
			}
			if (temp.size() > 3){
			Card c = new Card(temp.get(chineseColumn),
					temp.get(chineseColumn),
					temp.get(englishColumn),
					temp.get(englishColumn),
					temp.get(pinyinColumn));
			cards.add(c);
			}
		}
		
		return new Cards(cards);
	}
}
