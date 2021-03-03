package ca.sheridancollege.project.util;

import java.io.PrintStream;
import java.util.ResourceBundle;

/**
 * This class consists only of static methods that display the messages of this program. <br>
 * <p>
 * Date: February 26, 2021<br>
 *
 * @author Makoto Sakaguchi
 */
public class Message {
    protected static ResourceBundle messageBundle = ResourceBundle.getBundle("Message");

    /**
     * @see ResourceBundle#getString(String)
     */
    public static String getMessage(String key) {
        return messageBundle.getString(key);
    }

    public static void print(String key) {
        System.out.print(messageBundle.getString(key));
    }

    public static void println(String key) {
        println(System.out, key);
    }

    /**
     * Displays a message for the specified key.
     *
     * @param key  the key for the message string
     * @param args see {@link PrintStream#printf(String, Object...)}
     * @see PrintStream#printf(String, Object...)
     */
    public static void printf(String key, Object... args) {
        printf(System.out, key, args);
    }

    /**
     * Displays an error message for the specified key.
     *
     * @param key  the key for the error message string
     * @see System#err
     * @see PrintStream#println(String)
     */
    public static void errPrintln(String key) {
        println(System.err, key);
    }

    /**
     * Displays an error message for the specified key.
     *
     * @param key  the key for the error message string
     * @param args see {@link PrintStream#printf(String, Object...)}
     * @see System#err
     * @see PrintStream#printf(String, Object...)
     */
    public static void errPrintf(String key, Object... args) {
        printf(System.err, key, args);
    }

    /**
     * Displays a confirmation message for the specified key.
     *
     * @param key  the key for the confirmation message string
     * @param args Arguments referenced by the format specifiers in this string.
     * @see PrintStream#printf(String, Object...)
     */
    public static void showConfirmMessage(String key, Object... args) {
        System.out.printf((messageBundle.getString(key)) + "%n", args);
        System.out.println(messageBundle.getString("prompt.yes.no"));
    }

    private static void println(PrintStream printStream, String key) {
        printStream.println(messageBundle.getString(key));
    }

    private static void printf(PrintStream printStream, String key, Object... args) {
        printStream.printf(messageBundle.getString(key), args);
    }
}
