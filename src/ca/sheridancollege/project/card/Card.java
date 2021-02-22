package ca.sheridancollege.project.card;

/**
 * Group Members
 *
 * @author Makoto Sakaguchi
 * @author Bumsoo Park
 * @author Zoran
 */
public class Card {
    private Color color;
    private final Value value;

    /**
     * Students should implement this method for their specific children classes
     *
     * @return a String representation of a card. Could be an UNO card, a regular playing card etc.
     */
    public Card(Color color, Value gVal) {
        this.color = color;
        value = gVal;
    }

    /**
     * Returns the colour of the card.
     *
     * @return the colour of the card.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the colour of the card declared by the player.
     * Used with {@code Wild} and {@code WildDrawFour} cards.
     *
     * @param color the colour of the card declared by the player
     */
    protected void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the value, or action, of the card.
     *
     * @return the value, or action, of the card.
     */
    public Value getValue() {
        return this.value;
    }

    /** UNO card colours */
    public enum Color { Red, Yellow, Green, Blue }

    /**
     * Kind of UNO cards<br>
     *
     * <p>
     * <b>The following consists and effects:</b> (Note: two cards of each colour (4 colours) if not mentioned)
     * <dl>
     *  <dt>{@code ZERO}</dt>
     *  <dd>One of each colour (4 colours)</dd>
     *  <dt>1 through 9</dt>
     *  <dt>Skip</dt>
     *  <dd>The next player or the first player at the beginning of the play loses their turn.</dd>
     *  <dt>Reverse</dt>
     *  <dd>The direction of the play reverse (if a play is currently to the left, then play changes to the right, and vice versa).</dd>
     *  <dt>Draw Two</dt>
     *  <dd>The next player or the first player at the beginning of play must draw 2 cards and miss their turn.</dd>
     *  <dt>Wild</dt>
     *  <dd>Four cards in the UNO deck</dd>
     *  <dd>See the {@link Wild} class</dd>
     *  <dt>Wild Draw Four</dt>
     *  <dd>Four cards in the UNO deck</dd>
     *  <dd>See the {@link WildDrawFour} class</dd>
     * </dl>
     */
    public enum Value { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR }
}
