/**
 * SYST 17796 Project Winter 2019 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 */
package ca.sheridancollege.project;

import java.util.ArrayList;


public class Game
{
     private int handSize = 60;
	public Card[] cards = new Card[handSize];
        public void generateHand()
        {
                int countCards = 0;
		for(Card.Colors s: Card.Colors.values())
                {
                    for(Card.Ranks v: Card.Ranks.values())
                    {
                        cards[countCards] = (new Card(s,v));
                        countCards++;
                    }
                }
        }
    private final String gameName;//the title of the game
    private ArrayList <Player> players;// the players of the game
    
    public Game(String givenName)
    {
        gameName = givenName;
        players = new ArrayList();
    }

    /**
     * @return the gameName
     */
    public String getGameName() 
    {
        return gameName;
    }
    
     /**
     * @return the players of this game
     */
    public ArrayList <Player> getPlayers() 
    {
        return players;
    }

    /**
     * @param players the players of this game
     */
    public void setPlayers(ArrayList <Player> players) 
    {
        this.players = players;
    }
    
    /**
     * Play the game. This might be one method or many method calls depending
     * on your game.
     */

}