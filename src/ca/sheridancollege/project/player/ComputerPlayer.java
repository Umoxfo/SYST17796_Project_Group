package ca.sheridancollege.project.player;

import ca.sheridancollege.project.card.Card;
import ca.sheridancollege.project.card.WildCard;
import ca.sheridancollege.project.game.Game;
import ca.sheridancollege.project.util.Message;

import java.util.Objects;
import java.util.Random;

/**
 * <p>The {@code ComputerPlayer} class represents a CPU player.
 * Players have an identifier, which should be unique.</p>
 * <br>
 *
 * <ul style="list-style-type: none">
 * <li>Date: March 1, 2021</li>
 * <li>Editor: Makoto Sakaguchi</li>
 * </ul>
 *
 * @author Makoto Sakaguchi
 */
public class ComputerPlayer extends Player {
    private static final String PLAYER_NAME_PREFIX = Message.getMessage("comp.player.name.prefix");

    private final int playerNumber;

    // For multi-thread
    //ThreadLocalRandom.current()
    private final Random rng;

    /**
     * A constructor that sets the player name in the format "Player {number}".
     *
     * @param id player number as a suffix for this player ID
     * @param gameSession the game room
     */
    public ComputerPlayer(int id, Game gameSession) {
        super(PLAYER_NAME_PREFIX.formatted(id));
        playerNumber = id;
        this.gameSession = gameSession;

        rng = new Random(new Random().nextLong());
    }

    /**
     * Returns the player number of this computer player.
     *
     * @return the player number of this computer player.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void play() {
        playHandCard();

        // Randomly decide whether to play from their hand or draw a card
        if (rng.nextBoolean()) playHandCard();
        else drawCard();
    }

    @Override
    public void drawCard() {
        Card card = gameSession.draw(getPlayerId());

        // If the drawn card is playable, randomly decide whether to play it or add it to their hand
        if (gameSession.isPlayableCard(card) && rng.nextBoolean()) {
            gameSession.playCard(card);
        } else {
            addHand(card);
        }
    }

    @Override
    public Card.Color chooseColor() {
        Card.Color[] colors = Card.Color.values();

        // Randomly choose from available colours
        return colors[rng.nextInt(colors.length)];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        return super.equals(o) && o instanceof ComputerPlayer && playerNumber == ((ComputerPlayer) o).playerNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), playerNumber);
    }

    /**
     * Compares this {@code ComputerPlayer} object with the specified {@code Player} object for order.
     *
     * @param anotherPlayer the {@code Player} to be compared.
     * @return the value {@code 0} if the argument {@code Player} is an instance or subclass of this object
     *         and has the same player number;
     *         a value less than {@code 0} if the player number of this object is numerically less than
     *         the argument {@code Player};
     *         and a value greater than {@code 0} if the player number of this object is
     *         numerically greater than the argument the argument {@code Player},
     *         otherwise {@code 1}.
     */
    @Override
    public int compareTo(Player anotherPlayer) {
        return anotherPlayer instanceof ComputerPlayer
                   ? playerNumber - ((ComputerPlayer) anotherPlayer).playerNumber : 1;
    }

    private void playHandCard() {
        Card discardCard = gameSession.getTopDiscardCard();
        boolean noMatchingColor = nonMatchColor(discardCard.getColor());

        int handSize = handCards.size();
        for (int i = 0; i < handSize; i++) {
            Card card = handCards.get(i);
            if (discardCard.matchCard(card)
                    || (Game.isWildDrawFour(card) ? noMatchingColor : card instanceof WildCard)) {
                // Auto UNO call
                callUno();

                // Put a card (set a colour for a Wild card later)
                gameSession.playCard(handCards.remove(i));
                return;
            }
        }

        drawCard();
    }
}
