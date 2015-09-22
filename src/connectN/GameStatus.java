package connectN;

/***********************************************************************
 * The four states that the game can be in
 * Created by Andrew on 9/22/15.
 **********************************************************************/
public enum GameStatus {
    /**
     * Player 1 is the winner. Game is finished
     */
    Player1Won,
    /**
     * Player 2 is the winner. Game is finished
     */
    Player2Won,
    /**
     * There is a tie. Game is finished
     */
    Cats,
    /**
     * The game is still in progress
     */
    InProgress
}
