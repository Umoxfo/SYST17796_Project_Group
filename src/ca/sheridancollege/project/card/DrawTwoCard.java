package ca.sheridancollege.project.card;

import ca.sheridancollege.project.util.Message;

/**
 * The {@code DrawTwoCard} class represents a Draw Two card in UNO.
 *
 * <p>When you play this card, the next player must draw 2 cards and miss their turn.
 * This card may only be played on a matching color or on another Draw Two card.
 * If turned up at the beginning of play, the same rule applies.</p>
 * <br>
 *
 * <p>Date: February 25, 2021
 *
 * @author Makoto Sakaguchi
 */
public class DrawTwoCard extends Card implements DrawCard {
    /**
     * Constructs a new {@code DrawTwoCard} object by the specified {@linkplain Color color}.
     *
     * @param color The colour of this card.
     */
    public DrawTwoCard(Color color) {
        super(color, Value.DRAW_TWO);
    }

    @Override
    public int getDraw() {
        return DRAW_TWO;
    }

    /**
     * Shows the message about Draw Two.
     */
    @Override
    public void showMessage() {
        Message.stdPrintf("draw.card.the.next.player.must.draw.cards.and.lose.their.turn", DRAW_TWO);
    }
}
