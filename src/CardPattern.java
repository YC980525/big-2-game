import java.util.*;

public class CardPattern implements Comparable<CardPattern> {
    enum Type {
        INVALID ("Invalid"), SINGLE ("Single"), PAIR ("Pair"),
        STRAIGHT ("Straight"), FULLHOUSE("Full House");

        private String representation;

        Type (String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    private List<Card> cards;
    private Type type;
    private Card highestOrderCard;

    public CardPattern(List<Card> cards) {
        this.cards = cards;
        Collections.sort(this.cards, new Card());

        switch (this.cards.size()) {
            case 1:
                type = Type.SINGLE;
                break;
            case 2:
                type = isPair(cards.get(0), cards.get(1))
                    ? Type.PAIR
                    : Type.INVALID;
                break;
            case 5:
                type = (isStraight(cards))
                    ? Type.STRAIGHT
                    : (isFullHouse(cards))
                        ? Type.FULLHOUSE
                        : Type.INVALID;
                break;
            default:
                type = Type.INVALID;
        }
        setHighestOrderCard();
    }

    public Type getType() {
        return type;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getHighestOrderCard() {
        return highestOrderCard;
    }

    public boolean isPair(Card c1, Card c2) {
        return c1.getRank() == c2.getRank();
    }

    public boolean isTriple(List<Card> card) {
        if (card.size() != 3) return false;

        if (
            (isPair(card.get(0), card.get(1)))
            && (isPair(card.get(1), card.get(2)))
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isStraight(List<Card> cards) {
        if (cards.size() != 5) {return false;}

        for (int i = 0; i < cards.size(); i++) {
            int currOrdinal = cards.get(i).getRank().ordinal();
            for (int j = 1; j < cards.size(); j++) {
                int nextOrdinal = cards.get((i+j)%cards.size()).getRank().ordinal();
                if ((currOrdinal + 1) % 13 != nextOrdinal) {
                    break;
                }
                currOrdinal = nextOrdinal;

                if (j == cards.size() - 1) return true;
            }
        }

        return false;
    }

    public boolean isFullHouse(List<Card> cards) {
        if (cards.size() != 5) {
            return false;
        }

        if (isPair(cards.get(0), cards.get(1)) && isTriple(cards.subList(2, 5))) {
            highestOrderCard = cards.subList(2, 5).get(2);
            return true;
        }
        else if ((isPair(cards.get(3), cards.get(4)) && isTriple(cards.subList(0, 3)))) {
            highestOrderCard = cards.subList(0, 3).get(2);
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(CardPattern pattern) {
        return new Card().compare(getHighestOrderCard(), pattern.getHighestOrderCard());
    }

    @Override
    public String toString() {
        String representation = "";
        for (Card card : cards) {
            representation += card + " ";
        }
        return representation.substring(0, representation.length()-1);
    }

    private void setHighestOrderCard() {
        if (!(isFullHouse(cards)) && cards.size() != 0) {
            highestOrderCard = cards.get(cards.size()-1);
        }
    }

    public static void main(String[] args) {
    }
}
