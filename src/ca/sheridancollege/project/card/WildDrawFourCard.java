package ca.sheridancollege.project.card;

import ca.sheridancollege.project.util.Message;

/**
 * This {@code WildDrawFourCard} class represents a WildCard Draw Four card of the UNO.
 *
 * <p>
 * This card can be placed on any card. The player has to choose the next colour,
 * and also the next player has to draw 4 cards as their turn.
 * <p>
 * It may legally play only if the player has a matching card. If you play this card illegally,
 * the other player may challenge you to show your hand. If guilty, you must draw 4 cards.
 * If the challenged player is innocent, the challenger must draw 6 cards.
 * <p>
 * At the beginning of the play, return this card to the Draw pile, shuffle, and turn up a new one.
 * <p>
 * <p>
 * Date: February 22, 2021
 *
 * @author Makoto Sakaguchi
 */
public class WildDrawFourCard extends WildCard implements DrawCard {
    /**
     * Constructs a new {@code WildDrawFourCard} object.
     */
    public WildDrawFourCard() {
        super(Value.WILD_DRAW_FOUR);
    }

    @Override
    public int getDraw() {
        return DRAW_FOUR;
    }

    /**
     * Shows the message about Wild Draw 4.
     */
    @Override
    public void showMessage() {
        Message.printf("draw.card.the.next.player.must.draw.cards.and.lose.their.turn", DRAW_FOUR);
    }


}
