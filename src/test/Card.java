package test;

public class Card {

    private int value;

    private Card() {
    }

    public Card(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Card : " + value;
    }


}
