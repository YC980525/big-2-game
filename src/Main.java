// The big-2 game will be started from the Main class.

public class Main {
    public static void main(String[] args) {
        // System.out.println(args);
        // System.out.println("call");
        Game game = new Game();
        game.setup();
        game.start();
    }
}
