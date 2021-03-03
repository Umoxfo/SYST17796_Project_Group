package ca.sheridancollege.project.player;

import ca.sheridancollege.project.card.Card;
import ca.sheridancollege.project.game.Game;
import ca.sheridancollege.project.util.Message;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A class that models each player in the Uno game.
 * Players have an identifier, which should be unique.<br>
 * <p>
 * Date: February 23, 2021<br>
 * Editor: Makoto Sakaguchi
 *
 * @author dancye, 2018
 * @author Makoto Sakaguchi, Feb 23, 2021
 */
public abstract class Player implements Comparable<Player> {
    // Hand of the player
    protected final LinkedList<Card> handCards;

    // The currently joined game room (session)
    protected Game gameSession = null;

    // The unique ID (or name) for this player
    private String playerID;

    private boolean calledUNO = false;

    /**
     * A constructor that allows you to set the player's unique name.
     *
     * @param name the unique name to assign to this player.
     *
     * @throws IllegalArgumentException
     *         If a player name string is empty or contains only
     *         {@linkplain Character#isWhitespace(int) white space} codepoints.
     */
    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The player name must NOT be empty or only white space.");
        }

        playerID = name.strip();
        handCards = new LinkedList<>();
    }

    /**
     * @return the playerID
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * @param name the new player name
     */
    public void setPlayerID(String name) {
        if (name == null || name.isBlank()) return;
        playerID = name;
    }

    /**
     * Sets the game room (session) only if this player does not join any game.
     *
     * @param game the game room to join
     */
    public void setGameSession(Game game) {
        if (gameSession == null) gameSession = game;
    }

    /**
     * Returns whether or not this player currently joined a game.
     *
     * @return {@code true} if this player is joined the game, otherwise {@code false}.
     */
    public boolean isJoinedGame() {
        return gameSession != null;
    }

    /**
     * Leaves the game room (session) in which this player is currently joined.
     */
    public void leaveGameSession() {
        handCards.clear();
        gameSession.removePlayer(playerID);
        gameSession = null;
    }

    /**
     * Initializes the hand with dealt cards.
     *
     * @param cards the collection containing {@code Card} to be added to the hand.
     * @see LinkedList#addAll(Collection)
     */
    public void initHand(Collection<Card> cards) {
        handCards.clear();
        calledUNO = false;

        handCards.addAll(cards);
    }

    /**
     * Adds the card to this player's hand.
     *
     * @param card the card to add to the hand.
     * @see LinkedList#add(Object)
     */
    public void addHand(Card card) {
        handCards.add(card);
        calledUNO = false;
    }

    /**
     * Returns the number of cards in this hand.
     *
     * @return the number of cards in this hand.
     * @see LinkedList#size()
     */
    public int handSize() {
        return handCards.size();
    }

    /**
     * Returns {@code true} if this player yells "UNO" when playing their next-to-last card.
     *
     * @return {@code true} if this player yells "UNO" when playing their next-to-last card, otherwise {@code false}
     */
    public boolean isCalledUno() {
        return calledUNO;
    }

    /**
     * Returns {@code true} if this player does NOT yell "UNO" when playing their next-to-last card.
     *
     * @return {@code true} if this player does NOT yell "UNO" when playing their next-to-last card, otherwise {@code false}
     */
    public boolean nonCalledUno() {
        return handCards.size() <= 1 && !calledUNO;
    }

    /**
     * Returns {@code true} if this player have a card in the hand that matches tha COLOUR
     * with the specified colour of the card.
     *
     * @param discCardColor the colour of the card
     * @return {@code true} if this player have a card in the hand, otherwise {@code false}
     */
    public boolean hasMatchingColor(Card.Color discCardColor) {
        for (Card c : handCards) {
            if (c.getColor() == discCardColor) return true;
        }

        return false;
    }

    /**
     * Returns {@code true} if this player does NOT have a card in the hand that matches tha COLOUR
     * with the specified colour of the card.
     *
     * @param discCardColor the colour of the card
     * @return {@code true} if this player does NOT have a card in the hand, otherwise {@code false}
     */
    public boolean nonMatchColor(Card.Color discCardColor) {
        for (Card card : handCards) {
            if (card.getColor() == discCardColor) return false;
        }

        return true;
    }

    /**
     * Plays the turn.
     *
     * <p>
     * Player must match a card from their hand to the card, either by number, color or symbol.
     * Alternatively, the player can put down a Wild's card.
     */
    public abstract void play();

    /**
     * Draws the top card of the Draw pile.
     *
     * @implSpec If playable, that card can be put down in the same turn;
     * however, the player may not play any other card from their hand after the draw.
     */
    public abstract void drawCard();

    /**
     * Chooses the colour that continues play (for Wild cards).
     *
     * @return the colour to continue play
     */
    public abstract Card.Color chooseColor();

    /**
     * Yells “UNO” (meaning “one”) when playing the next-to-last card.
     */
    public void callUno() {
        if (handCards.size() == 2 && !calledUNO) {
            calledUNO = true;
            System.out.println("\033[0;1mUNO!\033[0;0m");
        }
    }

    /**
     * Displays the current hand.
     */
    public void showHand() {
        Message.printf("player.player.hand", playerID);
        for (int i = 0; i < handCards.size(); i++) {
            System.out.println(i + ": " + handCards.get(i));
        }
        System.out.println();
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;
        return playerID.equals(player.playerID);
    }
}
