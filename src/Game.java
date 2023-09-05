import java.util.*;

public class Game {
    private Scanner scanner;
    private List<Player> players;
    private int currentPlayerIndex;
    private Player topPlayer;
    private Player currentPlayer;
    private boolean isOver;
    private boolean isLegalPlay;
    private Evaluator evaluator;

    public Game() {}

    public void setup() {
        scanner = new Scanner(System.in);
        evaluator = new Evaluator();
        // deck = new Stack<>();
        // deck = setDeck(scanner.nextLine());
        players = new ArrayList<>();
        addPlayers();
        dealCards();
    }

    private void addPlayers() {

        for (int i = 1; i < 5; i++) {
            System.out.printf("Enter player %d names:\n", i);
            players.add(new Player(scanner.nextLine()));
        }
    }

    private void dealCards() {

        System.out.println("Dealing cards...");
        List<Card> deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(suit.toString(), rank.toString()));
            }
        }

        Collections.shuffle(deck);

        currentPlayerIndex = 0;
        int initialPlayerIndex = 0;

        while (deck.size() != 0) {
            Card card = deck.remove(deck.size() - 1);
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
    }

    public void start() {

        while (!isOver) {
            startTurn();

            while (true) {
                String indexStream = scanner.nextLine();

                isLegalPlay = evaluator.evaluatePlay(indexStream, currentPlayer);;
                evaluator.printEvaluationMessage(currentPlayer);

                if (!isLegalPlay) {
                    currentPlayer.showHandCards();
                }
                else {
                    if (evaluator.checkPlayerPasses()) {
                        evaluator.incrementPassCounter();
                    }
                    else {
                        currentPlayer.discard(indexStream);
                        evaluator.resetPassCounter();
                    }
                    break;
                }
            }
            endTurn();
        }
    }

    public void startTurn() {
        if (evaluator.getPassCounter() == 3) {
            System.out.println("New round begins.");
            evaluator.resetTopPlay();
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
}
