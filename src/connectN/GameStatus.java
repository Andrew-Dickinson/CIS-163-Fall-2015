package connectN;

/***********************************************************************
 * The four states that the game can be in
 * Created by Andrew on 9/22/15.
 **********************************************************************/
public enum GameStatus {
    /**
     * Player 1 is the winner. Game is finished
     */
    PLAYER_1_WON,
    /**
     * Player 2 is the winner. Game is finished
     */
    PLAYER_2_WON,
    /**
     * There is a tie. Game is finished
     */
    CATS,
    /**
     * The game is still in progress
     */
    IN_PROGRESS
}
