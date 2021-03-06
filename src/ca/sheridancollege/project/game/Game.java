package ca.sheridancollege.project.game;

import ca.sheridancollege.project.card.Card;
import ca.sheridancollege.project.card.DrawCard;
import ca.sheridancollege.project.card.WildCard;
import ca.sheridancollege.project.card.WildDrawFourCard;
import ca.sheridancollege.project.player.HumanPlayer;
import ca.sheridancollege.project.player.Player;
import ca.sheridancollege.project.util.Command;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The {@code Game} class performs the Uno game as a dealer.<br>
 *
 * <ul style="list-style-type: none">
 * <li>Date: February 23, 2021</li>
 * <li>Editor: Makoto Sakaguchi</li>
 * </ul>
 *
 * @author Makoto Sakaguchi
 * @see <a href="https://service.mattel.com/us/productDetail.aspx?prodno=GDJ85&siteid=27">UNO&#174; Instruction Sheets</a>
 */
public class Game extends GameRoom {
    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("message.UnoGame");

    // Minimum number of players in UNO
    private static final int MINIMUM_PLAYERS = 2;

    // Maximum number of players in UNO
    private static final int MAXIMUM_PLAYERS = 10;

    // Every player starts with seven cards
    private static final int INITIAL_HAND_CARDS = 7;

    // The Uno card deck used in the game.
    private final UnoDeck deck;
    private final ArrayDeque<Card> discardPile;
    private ListIterator<Card> drawPile;

    /**
     * Constructs a new {@code GameRoom} object by the specified game name
     * and the {@linkplain UnoDeck classic Uno} deck.
     *
     * @param gameName The name of this game
     */
    public Game(String gameName) {
        this(gameName, new UnoDeck());
    }

    /**
     * Constructs a new {@code GameRoom} object by the specified game name and Uno deck.
     *
     * @param gameName The name of this game
     * @param deck     The Uno deck used in this game
     */
    public Game(String gameName, UnoDeck deck) {
        super(gameName);
        this.deck = deck != null ? deck : new UnoDeck();
        discardPile = new ArrayDeque<>();
    }

    /**
     * Returns {@code true} if the specified card is a Wild Draw 4 card.
     *
     * @param card the {@link Card card}
     * @return {@code true} if the specified card is a Wild Draw 4 card, otherwise {@code false}
     */
    public static boolean isWildDrawFour(Card card) {
        return card instanceof WildDrawFourCard || card.getValue() == Card.Value.WILD_DRAW_FOUR;
    }

    private static void resetWildCard(Card card) {
        if (card instanceof WildCard) ((WildCard) card).clearColor();
    }

    private static void showPenaltyMessage(String playerName, PenaltyTypes penalty) {
        String baseMessage = messageBundle.getString("game.penalty");

        System.out.printf(baseMessage, playerName, penalty.number, messageBundle.getString(penalty.reason));
    }

    /**
     * Returns the card on the top of the Discard pile.
     *
     * @return the card on the top of the Discard pile
     */
    public Card getTopDiscardCard() {
        return discardPile.getFirst();
    }

    /**
     * Returns {@code true} if the specified card is playable.
     *
     * <p>A playable card matches the card on the top of the Discard pile, either by colour, number, or symbol.
     * Alternatively, a Wild's card.
     *
     * @return {@code true} if the card is playable, otherwise {@code false}.
     */
    public boolean isPlayableCard(Card card) {
        return card != null && (card.matchCard(discardPile.peekFirst()) || card instanceof WildCard);
    }

    /**
     * Starts this game.
     */
    public void playGame() {
        // Number of players in this game session
        int players = ThreadLocalRandom.current().nextInt(MINIMUM_PLAYERS, MAXIMUM_PLAYERS + 1);
        generateComputers(players, this);

        setup();

        Player curPlayer;
        do {
            Command.clearScreen();

            showCurrentDiscard();

            curPlayer = playerList.getFirst();
            System.out.printf(messageBundle.getString("game.player.turn"), curPlayer.getPlayerId());
            curPlayer.play();

            nextPlayer();

            // Automatically caught if forgetting to yell "UNO."
            catchNonUnoCall(curPlayer);
        } while (curPlayer.handSize() < 1);

        // Result
        Command.clearScreen();
        System.out.printf(messageBundle.getString("game.result.winner"), curPlayer.getPlayerId());

        // Remove computer players
        removeComputers();

        // TODO: Send a score report to each player (client).
    }

    /**
     * Performs processing after putting down a card in the Discard pile.
     *
     * @param card the card to play
     */
    public void playCard(Card card) {
        if (card instanceof WildCard && card.getColor() == null) {
            ((WildCard) card).setColor(playerList.getFirst().chooseColor());
        }

        discardPile.push(card);
        showCurrentDiscard();

        card.showMessage();
        // The head of the player list is the current player.
        switch (card.getValue()) {
            case SKIP -> nextPlayer();
            case REVERSE -> Collections.reverse(playerList);
            case DRAW_TWO, WILD_DRAW_FOUR -> {
                // TODO: a Wild Draw 4 card handling

                // The next player must draw n cards and lose their turn.
                drawPenalty(((DrawCard) card).getDraw(), playerList.get(1));
                nextPlayer();
            }
            default -> throw new IllegalStateException("Unexpected value: " + card.getValue());
        }
    }

    /**
     * Displays the card on the top of the Discard pile.
     */
    public void showCurrentDiscard() {
        System.out.printf(messageBundle.getString("game.current.card.on.the.discard.pile"), discardPile.peekFirst());
    }

    /**
     * Draws the top card of the Draw pile.
     *
     * @param playerId the string of the player ID (name) who draws draw a card
     * @return the top card of the Draw pile
     */
    public Card draw(String playerId) {
        System.out.printf(messageBundle.getString("game.draws.a.card"), playerId);

        if (!drawPile.hasNext()) {
            regenerateDrawPile();
        }

        return drawPile.next();
    }

    /**
     * Catches that the last player played their next-to-last card but did not yell "UNO."
     *
     * @implNote Expects after the play moves on to the next player in turn.
     */
    public void catchNonUnoCall(Player lastPlayer) {
        if (lastPlayer.nonCalledUno()) penalty(lastPlayer, PenaltyTypes.NOT_YELL_UNO);
    }

    // TODO: Multithreading is required to support WildDraw4 card rules.

    /**
     * Challenges a player suspected of illegally playing a Wild Draw 4 card (i.e. the player has a matching card).
     *
     * <p>The challenged player must show the challenger their hand.
     * If guilty, the challenged player must draw the 4 cards instead.
     * Otherwise, if the player is innocent,
     * the challenger must draw 4 cards PLUS 2 additional cards (for a total of 6 cards).</p>
     *
     * @param challenger the player that challenged the previous player
     */
    private void challengeIllegalPlay(Player challenger) {
        Player challengedPlayer = playerList.getLast();

        // Only if the challenger is NOT a computer,
        if (challenger instanceof HumanPlayer) {
            // the challenged player shows the challenger their hand.
            challengedPlayer.showHand();
        }

        // The challenged player is ...
        if (challengedPlayer.nonMatchColor(discardPile.getFirst().getColor())) {
            // guilty.
            penalty(challengedPlayer, PenaltyTypes.ILLEGAL_WILD_DRAW_FOUR_CARD_PLAY);
        } else {
            // innocent.
            penalty(challenger, PenaltyTypes.CHALLENGE_FAILED);
        }
    }

    /**
     * Sets up the new Uno game.
     */
    private void setup() {
        randomFirstPlayer();

        deck.shuffle();

        // Deal 7 cards to each player from the deck
        int fromIndex = 0;
        int toIndex = INITIAL_HAND_CARDS;
        for (Player pl : playerList) {
            pl.initHand(deck.getCardsInRange(fromIndex, toIndex));

            // Update the indexes of the sublist
            fromIndex = toIndex;
            toIndex += INITIAL_HAND_CARDS;
        }

        // Ensure that the first card from the Draw Pile
        Card card = deck.getCard(fromIndex);
        // The card is NOT a Wild Draw Four card
        while (isWildDrawFour(card)) {
            Collections.shuffle(deck.getCardsInRange(fromIndex, deck.deckSize()));
            card = deck.getCard(fromIndex);
        }

        drawPile = deck.getCards().listIterator(fromIndex + 1);

        discardPile.clear();
        playCard(card);
    }

    /**
     * Draws the specified number of cards from the Draw pile and
     * adds them to the specified player's hand.
     *
     * @param n      the number of cards to draw
     * @param player the player adds the drawn cards to his or her hand.
     */
    private void drawPenalty(int n, Player player) {
        String playerId = player.getPlayerId();
        for (int i = 0; i < n; i++) player.addHand(draw(playerId));
    }

    /**
     * Draws the specified number of cards as a <b>penalty</b>.
     */
    private void penalty(Player player, PenaltyTypes penaltyType) {
        showPenaltyMessage(player.getPlayerId(), penaltyType);
        drawPenalty(penaltyType.number, player);
    }

    /* Moves the next player. */
    private void nextPlayer() {
        playerList.offer(playerList.poll());
    }

    /*
     * Rotates the first player to index 0.
     *
     * The player with the highest number is considered to be the dealer, and play begins to the left of the dealer.
     * https://www.ultraboardgames.com/uno/game-rules.php
     */
    private void randomFirstPlayer() {
        deck.shuffle();

        // Each player draws a card (then collect numbers from cards)
        List<Integer> cards = deck.getCardsInRange(0, playerList.size())
                                  .stream().map(c -> c.getValue().getNumber()).collect(Collectors.toList());

        // Find the index of the highest number card in the drawn card list
        int index = cards.indexOf(Collections.max(cards));

        // Rotate the first player to index 0; the index of the card list equals to the player index
        Collections.rotate(playerList, playerList.size() - (index + 1));
    }

    private void regenerateDrawPile() {
        /* Regenerate a new Draw pile */
        List<Card> cards = discardPile.stream().peek(Game::resetWildCard).collect(Collectors.toList());
        Collections.shuffle(cards);
        drawPile = cards.listIterator();

        // Store the card on the top of the Discard pile
        Card card = discardPile.pop();

        discardPile.clear();

        // Restore the last card
        discardPile.push(card);
    }

    private enum PenaltyTypes {
        NOT_YELL_UNO(2, "game.penalty.reason.not.yell.uno"),
        ILLEGAL_WILD_DRAW_FOUR_CARD_PLAY(4, "game.penalty.reason.illegal.wild.draw.4.card.play"),
        // The challenged player is innocent
        CHALLENGE_FAILED(6, "game.penalty.reason.challenge.failed");

        private final int number;
        private final String reason;

        PenaltyTypes(int number, String reason) {
            this.number = number;
            this.reason = reason;
        }
    }
}
