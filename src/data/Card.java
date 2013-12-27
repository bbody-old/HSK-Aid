package data;

public class Card {
	private String trad;
	private String simp;
	private String english;
	private String grammar;
	private String pinyin;
	private int completedTally;

	
	public Card(String trad, String simp, String english, String grammar, String pinyin){
		this.trad = trad;
		this.simp = simp;
		this.english = english;
		this.grammar = grammar;
		this.pinyin = pinyin;
		completedTally = 0;
	}
	public String getTrad() {
		return trad;
	}
	public void setTrad(String trad) {
		this.trad = trad;
	}
	public String getSimp() {
		return simp;
	}
	public void setSimp(String simp) {
		this.simp = simp;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getGrammar() {
		return grammar;
	}
	public void setGrammar(String grammar) {
		this.grammar = grammar;
	}
	
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	public int getCompletedTally() {
		return completedTally;
	}
	public void success() {
		completedTally++;
	}	

}
