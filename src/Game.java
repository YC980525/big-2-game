import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private Scanner scanner;
    private List<Player> players;
    private int currentPlayerIndex;
    private CardPattern topPlay;
    private Player topPlayer;
    private Player currentPlayer;
    private boolean isOver;
    private boolean isLegalPlay;
    private int passCounter;

    public Game() {
        scanner = new Scanner(System.in);
        players = new ArrayList<>();
    }

    public void setup() {
        String deckStream = scanner.nextLine();
        Stack<Card> deck = setDeck(deckStream);
        currentPlayerIndex = 0;

        for (int i = 0; i < 4; i++) {
            players.add(new Player(scanner.nextLine()));
        }

        int initialPlayerIndex = 0;

        while (deck.size() != 0) {
            Card card = deck.pop();
            if ((card.getRank() == Card.Rank.THREE) && (card.getSuit() == Card.Suit.CLUBS)) {
                initialPlayerIndex = currentPlayerIndex;
            }
            players.get(currentPlayerIndex).addHandCard(card);
            currentPlayerIndex = (currentPlayerIndex + 1) % 4;

        }
        currentPlayerIndex = initialPlayerIndex;
        for (Player player : players) {
            player.sortHandCard();
        }
        passCounter = 3;
    }

    public void start() {

        while (!isOver) {
            startTurn();

            while (true) {
                String indexStrem = scanner.nextLine();
                CardPattern cardPlayed = currentPlayer.play(indexStrem);
                isLegalPlay = evaluatePlay(cardPlayed);

                if (isLegalPlay) {
                    if (cardPlayed.getCards().size() == 0) {
                        System.out.println("Player " + currentPlayer + " passes.");
                        passCounter++;
                    }
                    else {
                        System.out.printf("Player %s plays a %s %s.%n",
                            currentPlayer,
                            cardPlayed.getType().toString().toLowerCase(),
                            cardPlayed
                        );
                        currentPlayer.discard(indexStrem);
                        passCounter = 0;
                    }
                    break;
                }
                else {
                    currentPlayer.showHandCards();
                }
            }
            endTurn();
        }
    }

    public boolean evaluatePlay(CardPattern cardPattern) {

        if (cardPattern.getCards().size() == 0) {
            if (passCounter == 3) {
                System.out.println("You can't pass in the new round.");
                return false;
            }
            else {
                return true;
            }
        }

        if (cardPattern.getType() == CardPattern.Type.INVALID) {
            System.out.println("Invalid play, please try again.");
            return false;
        }

        if (topPlay == null) {
            topPlay = cardPattern;
            return true;
        }

        if (cardPattern.getType() != topPlay.getType()) {
            System.out.println("Invalid play, please try again.");
            return false;
        }
        else if (cardPattern.compareTo(topPlay) != 1) {
            System.out.println("Invalid play, please try again.");
            return false;
        }

        topPlay = cardPattern;
        return true;
    }

    public void startTurn() {
        if (passCounter == 3) {
            System.out.println("New round begins.");
            topPlay = null;
        }
        currentPlayer = players.get(currentPlayerIndex);
        System.out.println("Next turn: " + players.get(currentPlayerIndex));
        currentPlayer.showHandCards();
    }

    public void endTurn() {
        topPlayer = players.get(currentPlayerIndex);
        if (topPlayer.getHandCards().size() == 0) {
            System.out.printf("Game over, the winner is %s.\n", topPlayer);
            isOver = true;
            scanner.close();
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    private Stack<Card> setDeck(String deckStream) {
        Stack<Card> deck = new Stack<>();
        Pattern pattern = Pattern.compile("([SHDC])\\[(\\d+|J|Q|K|A)\\]");
        Matcher matcher;

        for (String cardString : deckStream.split(" ")) {
            matcher = pattern.matcher(cardString);
            matcher.find();
            Card card = new Card(matcher.group(1), matcher.group(2));
            deck.push(card);
        }
        return deck;
    }
}
