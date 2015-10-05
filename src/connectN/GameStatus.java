package connectN;

/***********************************************************************
 * Two of the states the game can be in. Other states range from
 * 0 to 9 and represent that the corresponding player number has won
 *
 * Created by Andrew on 9/22/15.
 **********************************************************************/
public class GameStatus {
    /**
     * There is a tie. Game is finished
     */
    public static final int CATS = -2;

    /**
     * The game is still in progress
     */
    public static final int IN_PROGRESS = -1;
}
