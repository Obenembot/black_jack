package test;

import java.util.ArrayList;

public class PlayersHand {

	private ArrayList<Card> hand = new ArrayList<Card>();

	public void addhand(Card card) {
		this.hand.add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	@Override
	public String toString() {
		return " hand : [" + hand + "]";
	}

}
