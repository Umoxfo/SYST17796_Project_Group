package ca.sheridancollege.project;

import ca.sheridancollege.project.game.Game;
import ca.sheridancollege.project.player.HumanPlayer;
import ca.sheridancollege.project.util.Command;
import ca.sheridancollege.project.util.Message;

/**
 * The {@code Card} class represents a UNO program.
 *
 * <p>Date: February 22, 2021</p>
 *
 * <b>Group Members</b>
 *
 * @author Makoto Sakaguchi
 * @author Bumsoo Park
 * @author Zoran Baboo
 */
public class Uno {
    public static void main(String[] args) {
        Client client = new Client();
        client.playGame();
    }
}

class Client {
    private final HumanPlayer player;

    // For a single play (vs a computer)
    private final Game gameSession;

    public Client() {
        player = new HumanPlayer(Command.prompt("client.enter.your.player.name"));

        // TODO: Support multiplayer
        gameSession = new Game(Command.prompt("client.enter.this.game.room.name"));

        enterGameRoom();
    }

    void playGame() {
        gameSession.playGame();
    }

    private void enterGameRoom() {
        while (!gameSession.addPlayer(player)) {
            Message.stdPrintf("client.player.name.already.exists.please.use.a.different.name", player.getPlayerId());

            // Update the player name
            player.setPlayerId(Command.prompt("client.enter.your.player.name"));
        }

        player.setGameSession(gameSession);
    }
}
