package ca.sheridancollege.project.player;

import ca.sheridancollege.project.card.Card;
import ca.sheridancollege.project.card.WildCard;
import ca.sheridancollege.project.util.Command;
import ca.sheridancollege.project.util.Message;

/**
 * The {@code HumanPlayer} class represents a human player.
 * Players have an identifier, which should be unique.<br>
 * <p>
 * Date: February 23, 2021<br>
 * Editor: Makoto Sakaguchi
 *
 * @author Makoto Sakaguchi, Feb 23, 2021
 */
public class HumanPlayer extends Player {
    /**
     * A constructor that allows to set the player's unique name
     *
     * @param name    the unique name to assign to this player.
     */
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void play() {
        showHand();

        gamePrompt();
    }

    @Override
    public void drawCard() {
        Message.println("human.player.take.a.card.from.the.draw.pile");

        Card card = gameSession.draw(getPlayerID());

        if (gameSession.isPlayableCard(card) && Command.confirmPrompt("human.player.is.playable.do.you.play.it", card)) {
            gameSession.playCard(card);
        } else {
            addHand(card);
        }
    }

    @Override
    public Card.Color chooseColor() {
        return Command.showCardColorPrompt();
    }

    /**
     * Compares this {@code HumanPlayer} object with the specified {@code Player} object for order.
     *
     * @implNote
     * This class has a natural ordering that is inconsistent with {@code equals}.
     *
     * @param anotherPlayer the {@code Player} to be compared.
     * @return the value {@code 0} if the argument {@code Player} is an instance or subclass of this object,
     *         otherwise {@link Integer#MIN_VALUE}.
     */
    @Override
    public int compareTo(Player anotherPlayer) {
        return anotherPlayer instanceof HumanPlayer ? 0 : Integer.MIN_VALUE;
    }

    /**
     * Displays the playable cards of this player's hand on this turn.
     */
    protected void showPlayableCardList() {
        Card discardCard = gameSession.getTopDiscardCard();

        int count = 0;
        int size = handCards.size();
        for (int i = 0; i < size; i++) {
            Card card = handCards.get(i);
            if (discardCard.matchCard(card) || card instanceof WildCard) {
                System.out.println(i + ": " + card);
                count++;
            }
        }

        // Have any playable cards?
        if (count == 0) Message.println("human.player.you.have.no.playable.cards");
    }

    private void gamePrompt() {
        String userInput = Command.showGamePrompt();

        char firstChar = userInput.charAt(0);
        if (Character.isLetter(firstChar)) {
            executeGameCommand(firstChar);
        } else {
            chooseCard(userInput);
        }
    }

    private void executeGameCommand(char firstChar) {
        switch (Command.Game.valueOf(firstChar)) {
            case SHOW_HAND -> showHand();
            case SHOW_PLAYABLE_CARDS -> showPlayableCardList();
            case UNO -> callUno();
            case DRAW_A_CARD -> {
                drawCard();
                return;
            }
            case QUIT -> Command.exit("are.you.sure.you.want.to.quit.this.game.and.exit.the.program");
            default -> Message.println("error.unknown.command.please.enter.again");
        }

        gamePrompt();
    }

    private void chooseCard(String cardIndex) {
        try {
            int index = Integer.parseInt(cardIndex);
            if (!gameSession.isPlayableCard(handCards.get(index))) {
                Command.showUnplayableCardMessage();
                gamePrompt();
                return;
            }

            // Auto UNO call
            callUno();

            // Take a card.
            Card card = handCards.remove(index);
            if (card instanceof WildCard) ((WildCard) card).setColor(chooseColor());

            gameSession.playCard(card);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println(e.getLocalizedMessage());
            gamePrompt();
        }
    }
}
