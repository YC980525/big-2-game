import java.util.*;

public class Player {
    private String name;
    private List<Card> handCards;

    public Player(String name) {
        this.name = name;
        this.handCards = new ArrayList<>();
    }

    public CardPattern play(String indexStream) {
        List<Card> cards = new ArrayList<>();

        if (!indexStream.equals("-1")) {
            for (String index : indexStream.split(" ")) {
                cards.add(handCards.get(Integer.parseInt(index)));
            }
        }

        CardPattern cardPattern = new CardPattern(cards);

        return cardPattern;
    }

    public void discard(String indexStream) {
        List<Integer> indices = new ArrayList<>();
        for (String index : indexStream.split(" ")) {
            indices.add(Integer.parseInt(index));
        }
        Collections.sort(indices, Collections.reverseOrder());
        for (int index : indices) {
            handCards.remove(index);
        }
    }

    public void addHandCard(Card card) {
        handCards.add(card);
    }

    public void sortHandCard() {
        Collections.sort(this.handCards, new Card());
    }

    public List<Card> getHandCards() {
        return handCards;
    }

    public void showHandCards() {

        for (int i = 0; i < handCards.size(); i++) {
            String cardString = handCards.get(i).toString();
            System.out.printf("%-" + cardString.length() + "d ", i);
        }
        System.out.println("");

        for (Card card : handCards) {
            System.out.print(card+" ");
        }
        System.out.println("");
    }

    public String toString() {
        return name;
    }


}
