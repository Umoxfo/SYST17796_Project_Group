package ca.sheridancollege.project.card;

/**
 * This {@code WildDrawFour} class represents a Wild Draw Four card of the UNO.
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
 *
 * Date: February 22, 2021
 *
 * @author Makoto Sakaguchi
 */
public class WildDrawFour extends Wild {
    /**
     */
    public WildDrawFour() {
        this(null, Value.WILD_DRAW_FOUR);
    }

    /**
     * Students should implement this method for their specific children classes
     *
     * @param color
     * @param gVal
     * @return a String representation of a card. Could be an UNO card, a regular playing card etc.
     */
    public WildDrawFour(Color color, Value gVal) {
        super(color, gVal);
    }
}
