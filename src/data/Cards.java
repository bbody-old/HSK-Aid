package data;

import java.util.ArrayList;
import java.util.Random;

public class Cards {
	ArrayList<Card> cards;
	Random generator;
	public Cards(ArrayList<Card> cards){
		this.cards = cards;
		generator = new Random();
	}
	
	public Card getCard(int index){
		return cards.get(index);
	}
	
	int checker = 0;
	// TODO: FIX THIS
	private int getNextInt(){
		if (checker < 100){
			int index = generator.nextInt(cards.size());
			if (cards.get(index).getCompletedTally() < 3){
				checker = 0;
				return index;
			} else {
				checker++;
				return getNextInt();
			}
		} else {
			if (isCompleted()){
				return -1;
			} else {
				checker = 0;
				return getNextInt();
			}
		}
	}
	
	private boolean isCompleted(){
		for (int i = 0; i < cards.size(); i++){
			if (cards.get(i).getCompletedTally() < 3){
				return false;
			}
		}
		return true;
	}
	
	int last = -1;

	public Card getNext(boolean pass) {
		if ((last != -1) && pass){
			cards.get(last).success();
		}
		
		Card c = null;
		int i = getNextInt();
		last = i;
		try {
		c = cards.get(i);
		} catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
		return c;
	}

	public int getSize() {
		// TODO Auto-generated method stub
		return cards.size();
	}
}
