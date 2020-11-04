package Mastermind2;


import java.util.Observer;

public class Main {

    public static void main(String[] args) {
        GameModel m = new GameModel();
        GameView v = new GameView(m);
    }
}
