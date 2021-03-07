package ca.sheridancollege.project.game;

import ca.sheridancollege.project.player.ComputerPlayer;
import ca.sheridancollege.project.player.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p>The {@code GameRoom} class represents the Uno game room.</p>
 * <br>
 *
 * <ul style="list-style-type: none">
 * <li>Date: February 23, 2021</li>
 * <li>Editor: Makoto Sakaguchi</li>
 * </ul>
 *
 * @author Makoto Sakaguchi
 */
public class GameRoom {
    // The title of the game
    protected final String gameName;

    protected final LinkedList<Player> playerList;

    // TODO: Maximum number of players

    /**
     * Constructs a new {@code GameRoom} object by the specified game (room) name.
     *
     * @param gameName The name of this game room
     */
    public GameRoom(String gameName) {
        this.gameName = gameName;
        playerList = new LinkedList<>();
    }

    /**
     * Returns the name of this game room.
     *
     * @return the game name string
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Returns a synchronized (thread-safe) list that contains the player list of this game.
     *
     * @return the player list of this game
     */
    public List<Player> getPlayerList() {
        return Collections.synchronizedList(playerList);
    }

    /**
     * Adds the player to this game room.
     *
     * @param player the player to join this game room
     * @return {@code true} if the player is successfully added to the player list in this room;
     *     {@code false} if there is already a player with the same player ID in this room.
     * @see LinkedList#add(Object)
     */
    public boolean addPlayer(Player player) {
        // TODO: Message that reached the maximum number of players.
        return !playerList.contains(player) && playerList.add(player);
    }

    /**
     * Removes the player from this game room.
     *
     * @param playerId the string representing the player ID to be removed from this game room.
     * @see LinkedList#removeIf(Predicate)
     */
    public void removePlayer(String playerId) {
        playerList.removeIf(pl -> pl.getPlayerId().equals(playerId));
    }

    /**
     * Generates the number of computer players.
     *
     * @param players the number of computer players to add in this room.
     */
    protected void generateComputers(int players, Game gameSession) {
        removeComputers();

        int coms = players - playerList.size();
        for (int i = 1; i <= coms; i++) playerList.addLast(new ComputerPlayer(i, gameSession));
    }

    /**
     * Removes all computer players.
     */
    protected void removeComputers() {
        playerList.removeIf(ComputerPlayer.class::isInstance);
    }
}
