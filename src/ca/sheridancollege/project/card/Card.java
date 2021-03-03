package ca.sheridancollege.project.card;

import ca.sheridancollege.project.util.Message;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code Card} class represents a basic UNO card.<br>
 * <p>
 * Date: February 22, 2021<br>
 * <p>
 * Group Members
 *
 * @author Makoto Sakaguchi
 * @author Bumsoo Park
 * @author Zoran
 */
public class Card implements Comparable<Card> {
    private final Value value;
    private Color color;

    /**
     * Constructs a new {@code Card} object by the specified {@linkplain Color color} and {@linkplain Value gVal}.
     *
     * @param color The colour of this card.
     * @param gVal  The {@linkplain Value value} of this card.
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
        return color;
    }

    /**
     * Sets the colour of the card declared by the player.
     * Used with {@code WildCard} and {@code WildDrawFourCard} cards.
     *
     * @param color The colour of the card declared by the player
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
        return value;
    }

    /**
     * Determines whether or not this card matches the colour or value of the specified card.
     *
     * @param card the card to compare this card against
     * @return {@code true} if the given card matches this card, otherwise {@code false}
     */
    public boolean matchCard(Card card) {
        return card != null && (color == card.color || value == card.value);
    }

    /**
     * Shows the message about this card.
     */
    public void showMessage() { /* NOTHING IMPLEMENT */ }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    /**
     * Compares this card to the specified object.
     * The result is {@code true} if and only if the argument is not {@code null} and
     * is a {@code Card} object that has the same {@link Color colour} and {@link Value value} as this object.
     *
     * @param o the object to compare this {@code Card} against
     * @return {@code true} if the given object represents a {@code Card} equivalent to this card, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        return color == card.color && value == card.value;
    }

    @Override
    public int compareTo(Card anotherCard) {
        if (color == null || anotherCard.color == null) return value.compareTo(anotherCard.value);
        return color.compareTo(anotherCard.color);
    }

    /**
     * Returns a {@code String} object representing this {@code Card}'s value.
     *
     * @return a string representation of the values of this object
     */
    @Override
    public String toString() {
        return String.format(Message.getMessage("card.value.of.color"), value, color);
    }

    /**
     * UNO card colours
     */
    public enum Color {Blue, Green, Yellow, Red}

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
     *  <dd>See the {@link WildCard} class</dd>
     *  <dt>Wild Draw Four</dt>
     *  <dd>Four cards in the UNO deck</dd>
     *  <dd>See the {@link WildDrawFourCard} class</dd>
     *  <dt>Wild Shuffle Hands</dt>
     *  <dd>One card in the UNO deck</dd>
     *  <dd>Since 2017</dd>
     *  <dd>Shuffle all the cards together and then deal them back to each player,
     *  starting with the player to the left of the player who played the card.</dd>
     *  <dt>Wild Customizable</dt>
     *  <dd>Three cards in the UNO deck</dd>
     *  <dd>Since 2016</dd>
     *  <dd>Rewritable any house rule</dd>
     * </dl>
     */
    public enum Value {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        SKIP(0, 20),
        REVERSE(0, 20),
        DRAW_TWO(0, 20),
        WILD(0, 50),
        WILD_DRAW_FOUR(0, 50),
        WILD_SHUFFLE_HANDS(0, 40),
        WILD_CUSTOMIZABLE(0, 40);

        private final int number;
        private final int points;

        /**
         * Initializes the {@code Value} with the specified value.
         * The {@code points} are the same as the {@code number}, and the {@code type} is {@code UnoCardType.Number}.
         *
         * @param n the {@code number} of the {@code Value}
         */
        Value(int n) {
            this(n, n);
        }

        /**
         * Initializes the {@code Value} with the specified values.
         *
         * @param n      the number of the {@code Value}
         * @param points the score points of the {@code Value}
         */
        Value(int n, int points) {
            number = n;
            this.points = points;
        }

        public int getNumber() {
            return number;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return Stream.of(name().split("_"))
                         .map(str -> str.charAt(0) + str.substring(1).toLowerCase(Locale.ROOT))
                         .collect(Collectors.joining(" "));
        }
    }
}
