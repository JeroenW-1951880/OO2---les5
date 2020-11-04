package Mastermind2;

/**
 * class to keep track of the data of a player
 * @author Jeroen Weltens
 */
public class Player {
    private boolean mIsBot;
    private int mTurnsNeeded = 0;

    /**
     * Constructor for a player
     * @param bot a parameter to distinct a bot and a human player
     */
    public Player(boolean bot){ mIsBot=bot; }
    public void set_turnsneeded(int turns){ mTurnsNeeded = turns; }

    /**
     * @return true if the player is bot
     */
    public boolean isBot() {
        return mIsBot;
    }

    /***
     * @return the number of turns the player needed to find the code, if not found yet, returns 0
     */
    public int getmTurnsNeeded() {
        return mTurnsNeeded;
    }
}
