package connectN;

import java.util.ArrayList;

/***********************************************************************
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFourGame {
    /**
     * Represents it being the turn of player1 and player2
     */
    public static final int PLAYER1_TURN = 0;
    public static final int PLAYER2_TURN = 1;
    /**
     * Represents a blank board cell
     */
    public static final int BLANK_BOARD_CELL = -1;
    /**
     * Represents board cells belonging to player1 and player 2
     */
    public static final int PLAYER_1_BOARD_CELL = 0;
    public static final int PLAYER_2_BOARD_CELL = 1;
    /**
     * Stores all of the data on the contents of the board
     */
    private int[][] board;
    /**
     * Stores a backlog of selected columns to allow undoing
     */
    private ArrayList<Integer> undoStack;
    /**
     * The length of continuous chips required for a victory
     */
    private int winLength;
    /**
     * The status of the game
     */
    private GameStatus status;
    /**
     * Stores who's turn it is
     */
    private int currentTurn;


    /**
     * Instantiates a new ConnectFourGame with a board width and height
     * @param width The width of the board
     * @param height The height of the board
     * @param winLength The number of consecutive
     *                  connections required to win
     */
    public ConnectFourGame(int width, int height, int winLength,
                           int startingPlayerNumber){
        //Initialize a blank board
        this.board = new int[height][width];

        this.winLength = winLength;

        //Set the starting player appropriately
        this.currentTurn = startingPlayerNumber;

        //Setup the undoStack
        this.undoStack = new ArrayList<Integer>();

        //Fill the board with blanks, set status and currentTurn
        resetBoard();


    }

    /**
     * Attempts to place a chip in the selected column belonging to the
     * player currently taking their turn and changes currentTurn so
     * that the other player can take their turn
     *
     * @param column The column to place the chip in
     * @return The row that the chip was placed in
     *         (-1 if the column is full)
     */
    public int selectColumn(int column){
        //Find the appropriate value for row
        int row = board.length;
        do {
            row--;
        } while (row >= 0 && board[row][column] != BLANK_BOARD_CELL);

        //Process the chip entering that location and change currentTurn
        if (row != -1){
            this.undoStack.add(column);

            if (currentTurn == PLAYER1_TURN){
                board[row][column] = PLAYER_1_BOARD_CELL;
                currentTurn = PLAYER2_TURN;
            } else if (currentTurn == PLAYER2_TURN){
                board[row][column] = PLAYER_2_BOARD_CELL;
                currentTurn = PLAYER1_TURN;
            }
        }

        return row;
    }

    /**
     * Set the board to it's original state
     */
    public void resetBoard(){
        //Fill the board with blanks
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[0].length; col++){
                this.board[row][col] = BLANK_BOARD_CELL;
            }
        }

        //Set the game status to indicate the game is starting
        this.status = GameStatus.IN_PROGRESS;

        //Add a -1 to indicate a reset
        this.undoStack.add(-1);
    }

    /**
     * Undoes the most recent move
     * @return Was the undo preformed
     */
    public boolean undoMove(){
        if (undoStack.size() > 1) {
            //Invert turn
            if (currentTurn == PLAYER1_TURN) {
                currentTurn = PLAYER2_TURN;
            } else if (currentTurn == PLAYER2_TURN) {
                currentTurn = PLAYER1_TURN;
            }

            if (undoStack.get(undoStack.size() - 1) != -1) {
                //The most recent entry is not -1
                //We don't have to reconstruct the board
                int relevantColumn = undoStack.remove(undoStack.size() - 1);

                //Scan the board for the first filled spot and fill it
                for (int row = 0; row < board.length; row++) {
                    if (board[row][relevantColumn] != BLANK_BOARD_CELL) {
                        board[row][relevantColumn] = BLANK_BOARD_CELL;

                        getGameStatus(); //Update the status variable for accuracy

                        return true;
                    }
                }
            } else {
                //The most recent entry is -1
                //We have to reconstruct the board

                //Get rid of the board cleared indicator ("-1")
                undoStack.remove(undoStack.size() - 1);

                //Find the most recent reset indicator
                int start = -1;
                for (int i = undoStack.size() - 1; i >= 0; i--){
                    if (undoStack.get(i) == -1){
                        start = i + 1;
                        break;
                    }
                }

                //Cut the old stack out of undoStack to prevent duplication
                //and add it to currentBoardColumnStack
                ArrayList<Integer> currentBoardColumnStack = new ArrayList<Integer>();
                for (int i = start; i < undoStack.size(); i++){
                    currentBoardColumnStack.add(undoStack.remove(start));
                }

                //Reconstruct the board
                for (int col : currentBoardColumnStack){
                    selectColumn(col); //Rebuilds the old stack
                }

                //TODO: Fix this mess
                return true;
            }


        }

        return false;
    }

    /**
     * Compute the current gameStatus
     * @return The current GameStatus of this game
     */
    public GameStatus getGameStatus() {
        boolean player1Victory =
                evaluatePlayerVictory(PLAYER_1_BOARD_CELL);
        boolean player2Victory =
                evaluatePlayerVictory(PLAYER_2_BOARD_CELL);

        if (player1Victory){
            this.status = GameStatus.PLAYER_1_WON;
        } else if (player2Victory){
            this.status = GameStatus.PLAYER_2_WON;
        } else {
            //We need to check for a cats game
            if (isBoardFull()){
                this.status = GameStatus.CATS;
            } else {
                this.status = GameStatus.IN_PROGRESS;
            }
        }

        return this.status;
    }

    /**
     * Searches a cell to see if a victory streak originates here
     * @param row The row of the cell
     * @param col The column of the cel
     * @param player The player to check for victory
     * @return Whether or not there is a victory streak at this cell
     */
    private boolean searchCell(int row, int col, int player){
        //The victory variables start true and
        // get set to false as they are discarded
        boolean horizontalVictory = true;
        boolean verticalVictory = true;
        boolean diagonalVictoryAdding = true;
        boolean diagonalVictorySubtracting = true;

        for (int offset = 0; offset < winLength; offset++) {
            int wrappedRowWithOffset = (row + offset) % board.length;
            int wrappedColWithOffset = (col + offset) % board[0].length;

            int wrappedRowWithNegOffset = (row + offset) % board.length;

            //Complicated so that negative numbers wrap correctly
            int wrappedColWithNegOffset =
                (((col - offset) % board[0].length) + board[0].length)
                % board[0].length;

            //Scan horizontally
            if (board[row][wrappedColWithOffset] != player) {
                horizontalVictory = false;
            }

            //Scan vertically
            if (board[wrappedRowWithOffset][col] != player) {
                verticalVictory = false;
            }

            //Scan diagonally one way
            if (board[wrappedRowWithOffset][wrappedColWithOffset]
                    != player) {
                diagonalVictoryAdding = false;
            }

            //Scan diagonally the other way
            if (board[wrappedRowWithNegOffset][wrappedColWithNegOffset]
                    != player) {
                diagonalVictorySubtracting = false;
            }

            //Stop searching if there's no victory
            if (!(horizontalVictory || verticalVictory
                    || diagonalVictoryAdding
                    || diagonalVictorySubtracting)) {
                return false;
            }
        }

        //There is a victory here
        return true;
    }

    /**
     * Evaluate if a particular player has a winning streak on the board
     * @param player The player to evaluate this for
     * @return Whether or not the player has a winning streak
     */
    private boolean evaluatePlayerVictory(int player){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (searchCell(row, col, player)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Evaluate if the board is full
     * @return Whether or not the board is full
     */
    private boolean isBoardFull(){
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == BLANK_BOARD_CELL){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets the current player that is taking their turn
     * @return An int corresponding to the player who is currently
     *         taking their turn
     */
    public int getTurnTakingPlayer() {
        return currentTurn;
    }

    /**
     * Gets the height of the board
     * @return The height of the board
     */
    public int getBoardHeight(){
        return board.length;
    }

    /**
     * Gets the width of the board
     * @return The width of the board
     */
    public int getBoardWidth(){
        return board[0].length;
    }

    /**
     * Gets a particular cell from the board
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The value of the cell
     */
    public int getBoardCell(int row, int col){
        return board[row][col];
    }
}
