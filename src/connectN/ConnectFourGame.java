package connectN;

import java.util.ArrayList;

/***********************************************************************
 * Stores all of the information required to play a game of connectN
 * Including:
 *      - An undo feature (Even wins)
 *      - A variable size game board
 *      - A variable number of connections to win
 *      - A choice of which player starts first
 *
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFourGame {
    /**
     * Represents it being the turn of player1
     */
    public static final int PLAYER1_TURN = 0;

    /**
     * Represents it being the turn of player2
     */
    public static final int PLAYER2_TURN = 1;

    /**
     * Represents a blank board cell
     */
    public static final int BLANK_BOARD_CELL = -1;

    /**
     * Represents board cells belonging to player1
     */
    public static final int PLAYER_1_BOARD_CELL = 0;

    /**
     * Represents board cells belonging to player 2
     */
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


    /*******************************************************************
     * Instantiates a new ConnectFourGame with a board width and height
     *
     * @param width The width of the board
     * @param height The height of the board
     * @param winLength The number of consecutive
     *                  connections required to win
     ******************************************************************/
    public ConnectFourGame(int width, int height, int winLength,
                           int numberOfPlayers,
                           int startingPlayerNumber){
        //Initialize a blank board
        this.board = new int[height][width];

        //Set the win length appropriately
        this.winLength = winLength;

        //Set the starting player appropriately
        this.currentTurn = startingPlayerNumber;

        //Initialize the undoStack
        this.undoStack = new ArrayList<Integer>();

        //Fill the board with blanks, set game status,
        //and add a -1 to the undoStack
        resetBoard();
    }

    /*******************************************************************
     * Attempts to place a chip in the selected column belonging to the
     * player currently taking their turn and changes currentTurn so
     * that the next player can take their turn
     *
     * @param column The column to place the chip in
     * @return The row that the chip was placed in
     *         (-1 if the column is full)
     ******************************************************************/
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

    /*******************************************************************
     * Set the board to its original state
     *
     * Fills the board with blanks, sets the game status,
     * and adds a -1 to the undo stack to indicate a reset
     ******************************************************************/
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

    /*******************************************************************
     * Undoes the most recent move
     *
     * @return -2 if an undo was not preformed;
     *         -1 if no player won right before the undo;
     *         If a player won right before the undo, their number
     ******************************************************************/
    public int undoMove(){
        //An undo is impossible if there is only
        //one element in the undostack: -1
        if (undoStack.size() > 1) {
            //Invert turn
            if (currentTurn == PLAYER1_TURN) {
                currentTurn = PLAYER2_TURN;
            } else if (currentTurn == PLAYER2_TURN) {
                currentTurn = PLAYER1_TURN;
            }

            if (undoStack.get(undoStack.size() - 1) != -1) {
                //The most recent entry is not -1
                //This is a non winning undo
                //We don't have to reconstruct the board

                //The column to undo from. Get and remove from stack
                int relevantColumn =
                                 undoStack.remove(undoStack.size() - 1);

                //Scan the board for the first filled spot and fill it
                for (int row = 0; row < board.length; row++) {
                    if (board[row][relevantColumn] != BLANK_BOARD_CELL){
                        board[row][relevantColumn] = BLANK_BOARD_CELL;

                        //Update the status variable for accuracy
                        getGameStatus();

                        return -1;
                    }
                }
            } else {
                //The most recent entry is a -1
                //We must undo a game ending move
                undoWinningMove();
            }
        }

        //Nothing was undone. The stack was too small
        return -2;
    }

    /*******************************************************************
     * Undoes the most recent move if that move was a win
     *
     * PreCondition: The most recent entry in the undo stack is -1 and
     *      the undo stack is longer than 1 element
     *
     * @return -1 if no player won right before the undo;
     *         If a player won right before the undo, their number
     ******************************************************************/
    private int undoWinningMove(){
        // Because the last move was a winning move,
        // we have to reconstruct the board from the last reset

        // Get rid of the board cleared indicator ("-1")
        undoStack.remove(undoStack.size() - 1);

        //Find the most recent reset indicator
        int start = -1;
        for (int i = undoStack.size() - 1; i >= 0; i--) {
            if (undoStack.get(i) == -1) {
                start = i + 1;
                break;
            }
        }

        //Add each item from the most recent -1 onward to currentStack
        ArrayList<Integer> currentStack = new ArrayList<Integer>();
        for (int i = start; i < undoStack.size(); i++){
            currentStack.add(undoStack.get(i));
        }

        //Take currentStack out of undoStack to prevent duplication
        //Has to count down to prevent out of bounds
        int size  =  undoStack.size();
        for (int i = size - 1; i >= start; i--){
            undoStack.remove(i);
        }

        //If the number of turns we're going back is even,
        //we need to switch who's turn it is so that the calls
        // to selectColumn make sense
        int numberOfTurnsAgo = size - start;
        if (numberOfTurnsAgo % 2 == 0){
            //Invert turn
            if (currentTurn == PLAYER1_TURN) {
                currentTurn = PLAYER2_TURN;
            } else if (currentTurn == PLAYER2_TURN) {
                currentTurn = PLAYER1_TURN;
            }
        }

        //Reconstruct the board by calling selectColumn as if the
        //buttons had been pressed
        for (int col : currentStack){
            selectColumn(col); //Adds entries back into undoStack
        }

        //Undoes the game ending move that the user
        //was trying to undo in the first place
        undoMove();

        //Return the appropriate value
        if (getGameStatus() == GameStatus.PLAYER_1_WON){
            return PLAYER1_TURN;
        } else if (getGameStatus() == GameStatus.PLAYER_2_WON){
            return PLAYER2_TURN;
        } else {
            //Must be a cat's game
            return -1;
        }
    }

    /*******************************************************************
     * Compute the current gameStatus according the status of the board
     *
     * @return The current GameStatus of this game
     ******************************************************************/
    public GameStatus getGameStatus() {
        //Determine if individual players won
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

    /*******************************************************************
     * Evaluate if a particular player has a winning streak on the board
     *
     * @param player The player to evaluate this for
     * @return Whether or not the player has a winning connection
     *             on the board
     ******************************************************************/
    private boolean evaluatePlayerVictory(int player){
        //For every cell on the board, call searchCell
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (searchCell(row, col, player)){
                    return true;
                }
            }
        }

        return false;
    }

    /*******************************************************************
     * Searches a cell to see if a victory streak originates here
     *
     * @param row The row of the cell
     * @param col The column of the cel
     * @param player The player to check for victory
     * @return Whether or not there is a victory streak at this cell
     ******************************************************************/
    private boolean searchCell(int row, int col, int player){
        //The victory variables start true and
        //get set to false as they are discarded
        boolean horizontalVictory = true;
        boolean verticalVictory = true;
        boolean diagonalVictoryAdding = true;
        boolean diagonalVictorySubtracting = true;

        //Offset stores the distance away from row, col
        for (int offset = 0; offset < winLength; offset++) {
            int wrappedRowWithOffset = (row + offset) % board.length;
            int wrappedColWithOffset = (col + offset) % board[0].length;

            //Wraps around the board in the opposite direction
            // of wrappedRowWithOffset
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
            if (board[wrappedRowWithOffset][wrappedColWithNegOffset]
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

        //If the above statement never returns false
        //then there is a victory starting at this location
        return true;
    }


    /*******************************************************************
     * Evaluate if the board is full
     *
     * @return Whether or not the board is full
     ******************************************************************/
    private boolean isBoardFull(){
        //Iterate over the whole board
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                //If we encounter a single blank cell, return false
                if (board[row][col] == BLANK_BOARD_CELL){
                    return false;
                }
            }
        }

        //We did not encounter any blank cells. The board is full
        return true;
    }

    /*******************************************************************
     * Gets the current player that is taking their turn
     *
     * @return An int corresponding to the player who is currently
     *         taking their turn
     ******************************************************************/
    public int getTurnTakingPlayer() {
        return currentTurn;
    }

    /*******************************************************************
     * Gets the height of the board
     *
     * @return The height of the board
     ******************************************************************/
    public int getBoardHeight(){
        return board.length;
    }

    /*******************************************************************
     * Gets the width of the board
     *
     * @return The width of the board
     ******************************************************************/
    public int getBoardWidth(){
        return board[0].length;
    }

    /*******************************************************************
     * Gets a particular cell from the board
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The value of the cell
     ******************************************************************/
    public int getBoardCell(int row, int col){
        return board[row][col];
    }
}
