package ca.sheridancollege.project.card;

/**
 * This {@code Wild} class represents a Wild card of the UNO.
 *
 * <p>
 * This card can be placed on any card. The player has to choose the next colour
 * (maybe played any turn even if another playable card is available).
 * <p>
 * At the beginning of the play, the first player chooses the first color and plays a card in it.
 * <p>
 *
 * Date: February 22, 2021
 *
 * @author Makoto Sakaguchi
 */
public class Wild extends Card {
    /**
     *
     */
    public Wild() {
        this(null, Value.WILD);
    }

    /**
     * Students should implement this method for their specific children classes
     *
     * @param color
     * @param gVal
     * @return a String representation of a card. Could be an UNO card, a regular playing card etc.
     */
    public Wild(Color color, Value gVal) {
        super(color, gVal);
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
}
