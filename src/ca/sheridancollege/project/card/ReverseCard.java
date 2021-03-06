package ca.sheridancollege.project.card;

import ca.sheridancollege.project.util.Message;

/**
 * The {@code ReverseCard} class represents a Reverse card in UNO.
 *
 * <p>When you play this card, the direction of play reverses
 * (if play is currently to the left, then play changes to the right, and vice versa).
 * This card may only be played on a matching color or on another Reverse card.
 * If this card is turned up at the beginning of play, the dealer goes first,
 * then play moves to the right instead of the left.
 *
 * <ul>
 * <li>Date: February 24, 2021</li>
 * </ul>
 *
 * @author Makoto Sakaguchi
 */
public class ReverseCard extends Card {
    /**
     * Constructs a new {@code ReverseCard} object by the specified {@linkplain Color color}.
     *
     * @param color The colour of this card.
     */
    public ReverseCard(Color color) {
        super(color, Value.REVERSE);
    }

    /**
     * Shows the message about Reverse.
     */
    @Override
    public void showMessage() {
        Message.stdPrintln("reverse.card.reverse.the.direction");
    }
}
