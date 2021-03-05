package ca.sheridancollege.project;

/**
 * Group Members
 * @author Makoto Sakaguchi
 * @author Bumsoo Park
 * @author Zoran Baboo
 */
public class Card {
    public enum Colors {Red, Yellow, Green, Blue}

    public enum Ranks {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAWTWO, DRAWFOUR, WILDCARD}

    private final Colors colour;
    private final Ranks rank;
    //default modifier for child classes

    /**
     * Students should implement this method for their specific children classes
     *
     * @return a String representation of a card. Could be an UNO card, a regular playing card etc.
     */
    public Card(Colors s, Ranks gVal) {
        colour = s;
        rank = gVal;
    }

    public Ranks getValue() {
        return this.rank;
    }

    public Colors getColors() {
        return this.colour;
    }
}
