package ca.sheridancollege.project.card;

/**
 * This {@code WildCard} class represents a WildCard card of the UNO.
 *
 * <p>
 * This card can be placed on any card. The player has to choose the next colour
 * (maybe played any turn even if another playable card is available).
 * <p>
 * At the beginning of the play, the first player chooses the first color and plays a card in it.
 * <p>
 * <p>
 * Date: February 22, 2021
 *
 * @author Makoto Sakaguchi
 */
public class WildCard extends Card {
    /**
     * Constructs a new {@code WildCard} object.
     */
    public WildCard() {
        super(null, Value.WILD);
    }

    /**
     * Constructs a new {@code WildCard} object by the specified {@linkplain Value gVal}.
     *
     * @param gVal The {@linkplain Value value} of this card.
     */
    protected WildCard(Value gVal) {
        super(null, gVal);
    }

    /**
     * Sets the colour of the card declared by the player.
     *
     * @param color the colour of the card declared by the player
     */
    @Override
    public void setColor(Color color) {
        super.setColor(color);
    }

    /**
     * Unsets the colour of this card.
     */
    public void clearColor() {
        super.setColor(null);
    }
}
