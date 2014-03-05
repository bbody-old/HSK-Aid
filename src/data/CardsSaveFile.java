package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class CardsSaveFile {
	private static final String LAST_FILENAME = "LASTFILENAME";
	private static final String PREFERENCES_NAME = "PREFERENCES";
	private static final String TITLE_NAME = "Title: ";
	private static final String PINYIN_NAME = "Pinyin: ";
	private static final String CHINESE_NAME = "Chinese: ";
	private static final String ENGLISH_NAME = "English: ";
	private int title;
	private int pinyin;
	private int chinese;
	private int english;
	
	private Preferences prefs = Preferences.userRoot().node(PREFERENCES_NAME);
	
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
		Writer osr = new OutputStreamWriter(new FileOutputStream(getConfigFilename()), "UTF-8");
		BufferedWriter out = new BufferedWriter(osr);
		out.write(TITLE_NAME + title + "\n");
		out.write(PINYIN_NAME + pinyin + "\n");
		out.write(CHINESE_NAME + chinese + "\n");
		out.write(ENGLISH_NAME + english + "\n");
		out.close();
	}
	
	
	public void loadProgress(Cards cards){
		for (Card card : cards.cards){
			if (savedResults.containsKey(card.getSimp())){
				card.setCompletedTally(savedResults.get(card.getSimp()));
			}
		}
	}
	
	public void saveProgress(Cards cards) throws IOException{
		Writer osr = new OutputStreamWriter(new FileOutputStream(getProgressFilename()), "UTF-8");
		BufferedWriter out = new BufferedWriter(osr);
		
		for (Card card : cards.cards){
			if (card.getCompletedTally() != 0){
				out.write(card.getSimp() + ":" + card.getCompletedTally() + "\n");
			}
		}
		
		out.close();
	}
	
	public void load(String filename) throws IOException, FileNotFoundException{
		setFilename(filename);
		BufferedReader out = null;
		
			InputStreamReader isr = new InputStreamReader(new FileInputStream(getConfigFilename()), "utf8");
			out = new BufferedReader(isr);
			String tt = out.readLine();
			title = Integer.valueOf(tt.replace(TITLE_NAME, ""));
			pinyin = Integer.valueOf(out.readLine().replace(PINYIN_NAME, ""));
			chinese = Integer.valueOf(out.readLine().replace(CHINESE_NAME, ""));
			english = Integer.valueOf(out.readLine().replace(ENGLISH_NAME, ""));
			try {
				loadProgress();
			}
			catch (IOException e){
				// No such file
			}
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public void loadProgress() throws NumberFormatException, IOException{
		InputStreamReader isr = new InputStreamReader(new FileInputStream(getProgressFilename()), "utf8");
		BufferedReader out = new BufferedReader(isr);
		String buffer ="";
		while ((buffer = out.readLine()) != null){
			String[] arr = buffer.split(":");
			savedResults.put(arr[0], Integer.valueOf(arr[1]));
		}
		out.close();
	}
	
	public void setFilename(String filename){
		prefs.put(LAST_FILENAME, filename);
	}
	public boolean hasFilename(){
		return prefs.get(LAST_FILENAME, "").length() > 0;
	}
	
	public String getFilename(){
		return prefs.get(LAST_FILENAME, "");
	}
	
	public String getConfigFilename(){
		return getFilename() + ".config";
	}
	
	public String getProgressFilename(){
		return getFilename() + ".progress";
	}
}
