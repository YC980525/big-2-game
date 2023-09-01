import java.util.*;

public class Card implements Comparator<Card> {

    enum Suit {
        CLUBS ("C"), DIAMONDS ("D"), HEARTS ("H"), SPADES ("S");

        private String representation;

        Suit (String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    enum Rank {
        THREE ("3"), FOUR ("4"), FIVE ("5"),
        SIX ("6"), SEVEN ("7"), EIGHT ("8"),
        NINE ("9"), TEN ("10"), JACK ("J"),
        QUEEN ("Q"), KING ("K"), ACE ("A"),
        TWO ("2");

        private String representation;

        Rank (String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    private Suit suit;
    private Rank rank;

    public Card() {};

    public Card(String s, String r) {
        this.suit = setSuit(s);
        this.rank = setRank(r);
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public int compare(Card c1, Card c2) {

        if (c1.rank.ordinal() < c2.rank.ordinal()) {
            return -1;
        }
        else if (c1.rank.ordinal() > c2.rank.ordinal()) {
            return 1;
        }
        else {
            if (c1.suit.ordinal() < c2.suit.ordinal()) {
                return -1;
            }
            else if (c1.suit.ordinal() > c2.suit.ordinal()) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return suit + "[" + rank + "]";
    }

    private Suit setSuit(String s) {
        for (Suit suit : Suit.values()) {
            if (suit.toString().equals(s)) { return suit; }
        }
        throw new IllegalArgumentException("Invalid suit: " + s);
    }

    private Rank setRank(String r) {
        for (Rank rank : Rank.values()) {
            if (rank.toString().equals(r)) { return rank; }
        }
        throw new IllegalArgumentException("Invalid rank: " + r);
    }
}
