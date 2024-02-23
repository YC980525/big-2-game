public class Evaluator {
    private int passCounter = 3;
    private CardPattern topPlay;
    private boolean evaluationResult;
    private String evaluationMessage;
    private boolean playerPasses;

    public Evaluator() {
        evaluationMessage = "";
    }

    public boolean evaluatePlay(String indexStream, Player player) {
        if (!isValidIndexStreamFormat(indexStream, player)) {
            // System.out.println(indexStream);
            return false;
        }
        playerPasses = false;
        if (isPass(indexStream)) {
            playerPasses = true;
            if (passCounter == 3) {
                evaluationMessage = "You can't pass in the new round.";
                return false;
            }
            else {
                evaluationMessage = String.format("Player %s passes.", player);
                return true;
            }
        }

        CardPattern cardPattern = player.play(indexStream);

        if (isInvalidCardPattern(cardPattern)) {
            return false;
        }

        if (topPlay == null) {
            topPlay = cardPattern;
            evaluationMessage = String.format(
                "Player %s plays a %s %s.",
                player,
                topPlay.getType().toString().toLowerCase(),
                topPlay
            );
            return true;
        }
        if (!isSameCardPattern(cardPattern)) {
            return false;
        }
        else if (cardPattern.compareTo(topPlay) != 1) {
            return false;
        }

        topPlay = cardPattern;
        evaluationMessage = String.format(
            "Player %s plays a %s %s.",
            player,
            topPlay.getType().toString().toLowerCase(),
            topPlay
        );
        return true;
    }

    public boolean checkPlayerPasses() {
        return playerPasses;
    }

    public void printEvaluationMessage(Player player) {
        if (!evaluationMessage.isEmpty()) {
            System.out.println(evaluationMessage);
        }
        else {
            System.out.println("Invalid play, please try again.");
        }
        evaluationMessage = "";
    }

    public boolean getEvaluationResult() {
        return evaluationResult;
    }

    private boolean isSameCardPattern (CardPattern cardPattern) {
        return (cardPattern.getType() == topPlay.getType());
    }

    private boolean isInvalidCardPattern(CardPattern cardPattern) {
        return cardPattern.getType() == CardPattern.Type.INVALID;
    }

    private boolean isValidIndexStreamFormat(String indexString, Player player) {
        String pattern = "";
        int handSize = player.getHandCards().size();
        if (handSize > 10) {
            pattern = String.format("^(?:(?:-1|[0-9]|1[0-%d])(?:\\s|$))+$", handSize-11);
        }
        else {
            pattern = String.format("^(?:(?:-1|[0-%d])(?:\\s|$))+$", handSize-1);
        }
        return indexString.matches(pattern);
    }

    private boolean isPass(String indexStream) {
        return indexStream.equals("-1");
    }

    public void incrementPassCounter() {
        passCounter++;
    }

    public int getPassCounter() {
        return passCounter;
    }

    public CardPattern getTopPlay() {
        return topPlay;
    }

    public void resetPassCounter() {
        passCounter = 0;
    }

    public void resetTopPlay() {
        topPlay = null;
    }


}
