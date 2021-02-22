package ca.sheridancollege.project;

public class Main {
    static class RealPlayer extends Player {
        /**
         * A constructor that allows you to set the player's unique ID
         *
         * @param name the unique ID to assign to this player.
         */
        public RealPlayer(String name) {
            super(name);
        }

        @Override
        public void play() {
            System.out.println("hello");
        }
    }

    public static void main(String[] args) {
        RealPlayer p1 = new RealPlayer("p1");

        p1.play();
    }
}
