package ca.sheridancollege.project.game;

import ca.sheridancollege.project.card.Card;
import ca.sheridancollege.project.card.Card.Color;
import ca.sheridancollege.project.card.Card.Value;
import ca.sheridancollege.project.card.DrawTwoCard;
import ca.sheridancollege.project.card.ReverseCard;
import ca.sheridancollege.project.card.SkipCard;
import ca.sheridancollege.project.card.WildCard;
import ca.sheridancollege.project.card.WildDrawFourCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@code UnoDeck} class represents the classic Uno cards (108 cards).
 *
 * <p><b>The following consists and effects:</b> (Note: two cards of each colour (4 colours) if not mentioned)</p>
 * <dl>
 *  <dt>{@code ZERO}</dt>
 *  <dd>One of each colour (4 colours)</dd>
 *  <dt>1 through 9</dt>
 *  <dt>SkipCard</dt>
 *  <dd>The next player or the first player at the beginning of the play loses their turn.</dd>
 *  <dt>Reverse</dt>
 *  <dd>
 *    The direction of the play reverse (if a play is currently to the left,
 *    then play changes to the right, and vice versa).
 *  </dd>
 *  <dt>Draw Two</dt>
 *  <dd>The next player or the first player at the beginning of play must draw 2 cards and miss their turn.</dd>
 *  <dt>WildCard</dt>
 *  <dd>Four cards in the UNO deck</dd>
 *  <dd>See the {@link WildCard} class</dd>
 *  <dt>WildCard Draw Four</dt>
 *  <dd>Four cards in the UNO deck</dd>
 *  <dd>See the {@link WildDrawFourCard} class</dd>
 * </dl>
 * <br>
 *
 * <p>Date: February 22, 2021
 *
 * @author Makoto Sakaguchi
 * @see <a href="https://www.unorules.com/">Original Uno Rules</a>
 */
public class UnoDeck {
    /**
     * The number of cards in classic Uno is {@value}.
     */
    public static final int CLASSIC_UNO_CARDS = 108;

    /**
     * The Number of cards 1 to 9, {@code SkipCard}, {@code Reverse}, and {@code Draw Two}
     * are {@value} each in the four colours.
     */
    public static final int COMMON_CARD_SETS = 2;

    /**
     * The Number of {@code WildCard} and {@code WildCard Draw Four} cards are {@value} each in the Uno deck.
     */
    public static final int CLASSIC_WILD_CARDS = 4;

    // The immutable list of the 1 to 9 cards.
    private static final List<Value> COMMON_CARDS;

    static {
        COMMON_CARDS = List.of(Value.ONE, Value.TWO, Value.THREE, Value.FOUR, Value.FIVE, Value.SIX,
            Value.SEVEN, Value.EIGHT, Value.NINE);
    }

    private final ArrayList<Card> cards;

    /**
     * Initializes a newly created {@code UnoDeck} object.
     */
    public UnoDeck() {
        cards = new ArrayList<>(CLASSIC_UNO_CARDS);

        for (Color color : Color.values()) {
            cards.add(new Card(color, Value.ZERO));

            // Add 1 to 9, SkipCard, Reverse, and "Draw Two" cards into the list.
            addCommonCards(color);
        }

        // Add WildCard and "WildCard Draw Four" cards into the list.
        for (int i = 0; i < CLASSIC_WILD_CARDS; i++) {
            cards.add(new WildCard());
            cards.add(new WildDrawFourCard());
        }

        shuffle();
    }

    /**
     * Returns the card list in this deck.
     *
     * @return the card list of this deck
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Returns the {@code Card} at the specified position in this list.
     *
     * @param index index of the card to return
     * @return the card at the specified position in this list
     * @see ArrayList#get(int)
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
     * (If fromIndex and toIndex are equal, the returned list is empty.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @see ArrayList#subList(int, int)
     */
    public List<Card> getCardsInRange(int fromIndex, int toIndex) {
        return cards.subList(fromIndex, toIndex);
    }

    /**
     * Returns the number of cards in this deck.
     *
     * @return the number of cards in this deck
     * @see ArrayList#size()
     */
    public int deckSize() {
        return cards.size();
    }

    /**
     * Randomly permutes the specified list using a default source of randomness.
     *
     * @see Collections#shuffle(List)
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /* Add 1 to 9, SkipCard, Reverse, and "Draw Two" cards into the list. */
    private void addCommonCards(Color color) {
        for (int i = 0; i < COMMON_CARD_SETS; i++) {
            for (Value commonCard : COMMON_CARDS) {
                cards.add(new Card(color, commonCard));
            }

            cards.add(new SkipCard(color));
            cards.add(new ReverseCard(color));
            cards.add(new DrawTwoCard(color));
        }
    }
}
