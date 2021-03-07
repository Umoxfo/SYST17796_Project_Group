package ca.sheridancollege.project.util;

import ca.sheridancollege.project.card.Card;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * <p>This class consists only of static methods that operate the commands of this program.</p>
 *
 * <p>Date: February 26, 2021
 *
 * @author Makoto Sakaguchi
 */
public class Command {
    private static final String GAME_PROMPT_BASE_PATTERN = "\\A([%s]|\\d+)\\b";

    private static final Pattern confirmPromptPattern;
    private static final Pattern gamePromptPattern;

    private static Scanner scanner;

    static {
        confirmPromptPattern = Pattern.compile("\\A(y(es)?|no?)\\b", Pattern.CASE_INSENSITIVE);

        String regex = GAME_PROMPT_BASE_PATTERN.formatted(Game.toCommandsString());
        gamePromptPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Clears the console screen and resets the cursor position at the top of the screen.
     */
    public static void clearScreen() {
        // Clear the console screen
        System.out.print("\033[H\033[2J");
        // Reset the cursor position at the top of the screen
        System.out.flush();
    }

    /**
     * Displays a prompt with the message of the specified message key.
     *
     * @param messageKey the key for the message string
     * @return the user input string, with all leading and trailing {@code white space} removed
     */
    public static String prompt(String messageKey) {
        initScanner();

        Message.print(messageKey);
        return scanner.nextLine().strip();
    }

    /**
     * Displays a prompt for an {@code int} value with the message of the specified message key.
     *
     * @param messageKey the key for the message string
     * @return the {@code int} value from the input
     * @see Scanner#nextInt()
     */
    public static int promptInt(String messageKey) {
        initScanner();

        Message.print(messageKey);
        return scanner.nextInt();
    }

    /**
     * Displays a confirmation prompt.
     *
     * @param messageKey the key for the confirmation message string
     * @param args       Arguments referenced by the format specifiers in this string.
     * @return {@code true} if confirmed by the user, otherwise {@code false}.
     * @see java.util.Formatter
     */
    public static boolean confirmPrompt(String messageKey, Object... args) {
        initScanner();

        Message.showConfirmMessage(messageKey, args);
        String str = scanner.findInLine(confirmPromptPattern);
        if (str == null) return false;

        char ch = str.charAt(0);
        return ch == 'y' || ch == 'Y';
    }

    /**
     * Displays the game prompt.
     *
     * @return the user input string, with all leading and trailing {@code white space} removed
     */
    public static String showGamePrompt() {
        // Displays the game commands for this program.
        Message.stdPrintf("command.game.commands", Game.SHOW_HAND.command, Game.SHOW_PLAYABLE_CARDS.command,
            Game.UNO.command, Game.DRAW_A_CARD.command, Game.QUIT.command);

        initScanner();
        Message.print("command.game.enter.the.card.number.or.the.game.command");
        String str = scanner.findInLine(gamePromptPattern);
        if (str == null) {
            // Displays the message about the unknown (invalid) command.
            Message.stdPrintln("error.unknown.command.please.enter.again");
            return showGamePrompt();
        }

        return str;
    }

    /**
     * Displays the card colour prompt.
     *
     * @return the {@linkplain Card.Color card color}
     */
    public static Card.Color showCardColorPrompt() {
        Message.stdPrintln("command.color.select.a.colour");
        Card.Color[] colors = Card.Color.values();
        for (int i = 0; i < colors.length; i++) {
            System.out.println(i + ": " + colors[i]);
        }

        int index = 0;
        try {
            index = promptInt("command.color.enter.the.colour.number.you.selected");
            return colors[index];
        } catch (InputMismatchException e) {
            Message.errPrintln("error.command.color.invalid.input.please.enter.a.number.between.0.and.3");
        } catch (ArrayIndexOutOfBoundsException e) {
            Message.errPrintf("error.command.color.index.is.out.of.the.colour.number", index);
        }

        // For exception is handled
        return showCardColorPrompt();
    }

    /**
     * Displays the message that a player has chosen an unplayable card.
     */
    public static void showUnplayableCardMessage() {
        Message.stdPrintf("error.human.player.unplayable.card", Game.SHOW_PLAYABLE_CARDS.command);
    }

    /**
     * Displays a confirmation prompt and exit this program if the user confirms.
     *
     * @param messageKey the key for the confirmation message string
     */
    public static void exit(String messageKey) {
        if (confirmPrompt(messageKey)) System.exit(0);
    }

    private static synchronized void initScanner() {
        if (scanner == null) scanner = new Scanner(System.in);

        // Clear the input buffer
        // System.out.println();
        // scanner.nextLine();
    }

    /**
     * The game commands of this program.
     */
    public enum Game {
        SHOW_HAND('L'),
        SHOW_PLAYABLE_CARDS('P'),
        UNO('U'),
        DRAW_A_CARD('D'),
        QUIT('Q'),
        None(Character.MIN_VALUE);

        private final char command;

        Game(char command) {
            this.command = command;
        }

        /**
         * Returns an array containing the {@code command} of this enum type, in the order they're declared.
         *
         * @return an array containing the {@code command} of this enum type, in the order they're declared
         */
        public static char[] commands() {
            Game[] values = values();
            int size = values.length;

            char[] commands = new char[size];
            for (int i = 0; i < values.length; i++) {
                commands[i] = values[i].command;
            }

            return commands;
        }

        /**
         * Returns the enum constant of this type with the specified {@code command}.
         * The character must match an identifier used to declare an enum constant in this type.
         * (Ignores case differences.)
         *
         * @param command the {@code command} of the constant to return
         * @return the enum constant with the specified {@code command}
         */
        public static Game valueOf(char command) {
            command = Character.toUpperCase(command);

            for (Game value : values()) {
                if (value.command == command) return value;
            }

            return None;
        }

        /**
         * Returns the {@code command}s string of this enum type, in the order they're declared.
         *
         * @return the {@code command}s string of this enum type, in the order they're declared
         */
        private static String toCommandsString() {
            StringBuilder sb = new StringBuilder();
            for (Game game : values()) sb.append(game.command);

            return sb.toString();
        }

        public char getCommand() {
            return command;
        }
    }
}
