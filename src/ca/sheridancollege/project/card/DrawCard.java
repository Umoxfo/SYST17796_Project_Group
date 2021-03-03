package ca.sheridancollege.project.card;

/**
 * This {@code DrawCard} interface that provides the number of cards that the next player must draw.<br>
 * <p>
 * Date: February 25, 2021<br>
 *
 * @author Makoto Sakaguchi
 */
public interface DrawCard {
    int DRAW_TWO = 2;
    int DRAW_FOUR = 4;

    /**
     * Returns the number of cards that the next player must draw.
     *
     * @return the number of cards the next player draws
     */
    int getDraw();
}
