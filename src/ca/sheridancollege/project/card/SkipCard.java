package ca.sheridancollege.project.card;

import ca.sheridancollege.project.util.Message;

/**
 * The {@code SkipCard} class represents a Skip card in UNO.
 *
 * <p>
 * When you play this card, the next player is skipped their turn.
 * This card may only be played on a matching color or on another Skip card.
 * If a Skip card is turned up at the beginning of play,
 * the first player is loses their turn, hence the next player starts play.
 *
 * <p>
 * Date: February 24, 2021<br>
 *
 * @author Makoto Sakaguchi
 */
public class SkipCard extends Card {
    /**
     * Constructs a new {@code SkipCard} object by the specified {@linkplain Color color}.
     *
     * @param color The colour of this card.
     */
    public SkipCard(Color color) {
        super(color, Value.SKIP);
    }

    /**
     * Shows the message about Skip.
     */
    @Override
    public void showMessage() {
        Message.println("skip.card.the.next.player.or.the.first.player.at.the.beginning.of.play.is.skipped.their.turn");
    }
}
