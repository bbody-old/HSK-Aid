package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CardsSaveFile {
	private static final String TITLE_NAME = "Title: ";
	private static final String PINYIN_NAME = "Pinyin: ";
	private static final String CHINESE_NAME = "Chinese: ";
	private static final String ENGLISH_NAME = "English: ";
	private int title;
	private int pinyin;
	private int chinese;
	private int english;
	
	private Map<String, Integer> savedResults = new HashMap<String, Integer>();
	
	public int getPinyin() {
		return pinyin;
	}

	public void setPinyin(int pinyin) {
		this.pinyin = pinyin;
	}

	public int getChinese() {
		return chinese;
	}

	public void setChinese(int chinese) {
		this.chinese = chinese;
	}

	public int getEnglish() {
		return english;
	}

	public void setEnglish(int english) {
		this.english = english;
	}

	public Map<String, Integer> getSavedResults() {
		return savedResults;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public void setSavedResults(Map<String, Integer> savedResults) {
		this.savedResults = savedResults;
	}

	public void save(String filename) throws IOException{
		FileWriter fstream = new FileWriter(filename + ".save");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(TITLE_NAME + title + "\n");
		out.write(PINYIN_NAME + pinyin + "\n");
		out.write(CHINESE_NAME + chinese + "\n");
		out.write(ENGLISH_NAME + english + "\n");
		out.close();
	}
	
	
	public void loadProgress(Cards cards) throws IOException{
		for (Card card : cards.cards){
			if (savedResults.containsKey(card.getSimp())){
				card.setCompletedTally(savedResults.get(card.getSimp()));
			}
		}
	}
	
	public void saveProgress(String filename, Cards cards) throws IOException{
		
		FileWriter fstream = new FileWriter(filename + ".save");
		BufferedWriter out = new BufferedWriter(fstream);
		
		for (Card card : cards.cards){
			if (card.getCompletedTally() != 0){
				out.write(card.getSimp() + ":" + card.getCompletedTally() + "\n");
			}
		}
		
		out.close();
	}
	
	public void load(String filename) throws IOException{
		FileReader fstream = new FileReader(filename + ".save");
		BufferedReader out = new BufferedReader(fstream);

		title = Integer.valueOf(out.readLine().replace(TITLE_NAME, ""));
		pinyin = Integer.valueOf(out.readLine().replace(PINYIN_NAME, ""));
		chinese = Integer.valueOf(out.readLine().replace(CHINESE_NAME, ""));
		english = Integer.valueOf(out.readLine().replace(ENGLISH_NAME, ""));
		
		String buffer ="";
		while ((buffer = out.readLine()) != null){
			String[] arr = buffer.split(",");
			savedResults.put(arr[0], Integer.getInteger(arr[1], 0));
		}
		
		out.close();
	}
	
	
}
