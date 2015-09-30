package connectN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***********************************************************************
 * Displays the ConnectFourGame so the user can play it
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFourPanel extends JPanel {
    /**
     * Defines the text to display in the cells
     */
    private static final String DISPLAY_EMPTY = "-";
    private static final String DISPLAY_PLAYER1 = "X";
    private static final String DISPLAY_PLAYER2 = "O";

    /**
     * Defines the text to display on buttons and labels
     */
    private static final String SELECT_TEXT = "Select";

    /**
     * These JLabels display the status of the game board
     */
    private JLabel[][] board;

    /**
     * A gridlayout panel containing the board JLabels
     */
    private JPanel boardPanel;

    /**
     * A borderlayout panel containing the boardPanel and buttonsPanel
     */
    private JPanel gamePanel;

    /**
     * Stores the number of wins for each player
     */
    private int player1Wins;
    private int player2Wins;

    /**
     * These JLabels display the number of wins for each player
     */
    private JLabel player1WinsLabel;
    private JLabel player2WinsLabel;

    /**
     * These buttons allow the players to play chips
     */
    private JButton[] selection;

    /**
     * A gridlayout panel containing the selection buttons
     */
    private JPanel buttonPanel;

    /**
     * A button to undo the most recent move
     */
    private JButton undoButton;

    /**
     * The buttonListener for the top button row
     */
    private ButtonListener buttonListener;

    /**
     * The menu buttons from the frame above
     */
    private JMenuItem quitButton;
    private JMenuItem newGameButton;

    /**
     * The game object this panel is associated with
     */
    private ConnectFourGame game;

    public ConnectFourPanel(JMenuItem quitItem, JMenuItem newGameItem){
        //Used to process menu button clicks
        MenuButtonListener menuListener = new MenuButtonListener();

        this.quitButton = quitItem;
        this.newGameButton = newGameItem;

        //Add menuListener to these menu buttons to process their clicks
        this.quitButton.addActionListener(menuListener);
        this.newGameButton.addActionListener(menuListener);

        //Setup a new game instance
        this.game = createGameFromPrompts(true);
        int width = game.getBoardWidth();
        int height = game.getBoardHeight();

        //Used for all button events in this panel
        buttonListener = new ButtonListener();

        //Construct the board panel with a
        //two dimensional array of JLabels
        this.boardPanel = new JPanel(new GridLayout(height, width));
        this.board = new JLabel[height][width];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                board[row][col] = new JLabel(DISPLAY_EMPTY);
                boardPanel.add(board[row][col]);
            }
        }

        //Construct an array of selection buttons
        this.selection = new JButton[width];
        this.buttonPanel = new JPanel(new GridLayout(1, width));
        for (int col = 0; col < width; col++){
            selection[col] = new JButton(SELECT_TEXT);
            selection[col].addActionListener(buttonListener);
            buttonPanel.add(selection[col]);
        }

        this.gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(buttonPanel, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);


        //Initialize the win counters to 0
        this.player1Wins = 0;
        this.player2Wins = 0;

        //Initialize the win indicators
        player1WinsLabel = new JLabel("Player 1: " + player1Wins);
        player2WinsLabel = new JLabel("Player 2: " + player2Wins);
        add(player1WinsLabel);
        add(player2WinsLabel);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(buttonListener);
        add(undoButton);

        add(gamePanel);
    }

    /**
     * Closes the parent frame to this panel
     */
    private void closeParentFrame(){
        SwingUtilities.windowForComponent(this).dispose();
    }

    /**
     * Closes the parent frame to this panel
     */
    private void packParentFrame(){
        SwingUtilities.windowForComponent(this).pack();
    }

    /**
     * Creates a game based on prompts to the user about game details
     * @param useDefaultIfEntryInvalid If the user enters an invalid
     *          value and this is true, a default value will be used
     *          instead of prompting the user to re-enter the value
     * @return The generated game
     */
    private ConnectFourGame createGameFromPrompts(
            boolean useDefaultIfEntryInvalid){
        int[] boardInfo = promptForBoardInfo(useDefaultIfEntryInvalid);

        String startingPlayer = JOptionPane.showInputDialog(null,
                "Who starts first? (1 or 2)",
                "Pick a starting player", JOptionPane.PLAIN_MESSAGE);

        int startingPlayerNumber;
        try {
            startingPlayerNumber = Integer.parseInt(startingPlayer) - 1;
        } catch (NumberFormatException e){
            startingPlayerNumber = 0;
        }

        if (startingPlayerNumber < 0 || startingPlayerNumber > 1){
            startingPlayerNumber = 0;
        }

        return new ConnectFourGame(boardInfo[0],
                boardInfo[1], boardInfo[2], startingPlayerNumber);
    }

    private int[] promptForBoardInfo(boolean useDefaultIfEntryInvalid){
        JTextField widthField = new JTextField(2);
        JTextField heightField = new JTextField(2);
        JTextField lengthField = new JTextField(2);

        JPanel dialogFieldPanel = new JPanel();
        dialogFieldPanel.add(new JLabel("Width: "));
        dialogFieldPanel.add(widthField);
        dialogFieldPanel.add(new JLabel("Height: "));
        dialogFieldPanel.add(heightField);
        dialogFieldPanel.add(new JLabel("Win length: "));
        dialogFieldPanel.add(lengthField);

        int width = -1;
        int height = -1;
        int winLength = -1;
        while ((width == -1 || height == -1 || winLength == -1)) {
            int result = JOptionPane.showConfirmDialog(null, dialogFieldPanel,
                    "Please enter the size of the board: ",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String widthString = widthField.getText();
                String heightString = heightField.getText();
                String lengthString = lengthField.getText();

                try {
                    width = Integer.parseInt(widthString);
                    height = Integer.parseInt(heightString);
                    winLength = Integer.parseInt(lengthString);
                } catch (NumberFormatException e) {
                    //Numbers stay at -1
                }
            }

            if (width >= 20 || width <= 3 || height >= 20
                    || height <= 3 || winLength <= 1) {
                width = -1;
                height = -1;
                winLength = -1;

                String warningMessage;
                int messageType;
                if (useDefaultIfEntryInvalid){
                    warningMessage = "Default Values will be used.";
                    messageType = JOptionPane.WARNING_MESSAGE;
                } else {
                    warningMessage = "Please enter valid values.";
                    messageType = JOptionPane.ERROR_MESSAGE;
                }

                JOptionPane.showMessageDialog(null, warningMessage,
                        "Invalid Entry", messageType);
            }

            if ((width == -1 || height == -1 || winLength == -1)
                    && useDefaultIfEntryInvalid){
                width = 10;
                height = 10;
                winLength = 4;
            }
        }

        int[] returnVals = {width, height, winLength};
        return returnVals;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int player = game.getTurnTakingPlayer();

            for (int col = 0; col < selection.length; col++){
                if (e.getSource() == selection[col]) {
                    int chipRow = game.selectColumn(col);

                    if (chipRow != -1) {
                        if (player == ConnectFourGame.PLAYER1_TURN) {
                            board[chipRow][col].setText(DISPLAY_PLAYER1);
                        } else if (player ==
                                ConnectFourGame.PLAYER2_TURN) {
                            board[chipRow][col].setText(DISPLAY_PLAYER2);
                        }
                    }

                    //No need to continue. We found the button
                    break;
                }
            }

            if (e.getSource() == undoButton){
                boolean undone = game.undoMove();
                //TODO: Deal with undoing wins
                //If undoing was possible
                if (undone){
                    for (int row = 0; row < board.length; row++){
                        for (int col = 0; col < board[0].length; col++){
                            int value = game.getBoardCell(row, col);
                            if (value == ConnectFourGame.PLAYER_1_BOARD_CELL) {
                                board[row][col].setText(DISPLAY_PLAYER1);
                            } else if (value == ConnectFourGame.PLAYER_2_BOARD_CELL){
                                board[row][col].setText(DISPLAY_PLAYER2);
                            } else if (value == ConnectFourGame.BLANK_BOARD_CELL){
                                board[row][col].setText(DISPLAY_EMPTY);
                            }
                        }
                    }
                }
            }

            GameStatus currentStatus = game.getGameStatus();
            if (currentStatus != GameStatus.IN_PROGRESS) {
                if (currentStatus == GameStatus.PLAYER_1_WON) {
                    System.out.println("Player 1 wins");
                    player1Wins++;
                } else if (currentStatus == GameStatus.PLAYER_2_WON) {
                    System.out.println("Player 2 wins");
                    player2Wins++;
                } else if (currentStatus == GameStatus.CATS) {
                    System.out.println("Cats game");
                }

                //Reset the game board
                game.resetBoard();

                //Reset the gui board
                for (int row = 0; row < board.length; row++){
                    for (int col = 0; col < board[0].length; col++){
                        board[row][col].setText(DISPLAY_EMPTY);
                    }
                }

                //Update the win indicators
                player1WinsLabel.setText("Player 1: " + player1Wins);
                player2WinsLabel.setText("Player 2: " + player2Wins);
            }
        }
    }

    private class MenuButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGameButton){
                game = createGameFromPrompts(false);
                int width = game.getBoardWidth();
                int height = game.getBoardHeight();

                //Update the win indicators
                player1WinsLabel.setText("Player 1: " + player1Wins);
                player2WinsLabel.setText("Player 2: " + player2Wins);

                remove(gamePanel);

                //Re-Initialize board
                boardPanel = new JPanel(new GridLayout(height, width));
                board = new JLabel[height][width];
                for (int row = 0; row < height; row++){
                    for (int col = 0; col < width; col++){
                        board[row][col] = new JLabel(DISPLAY_EMPTY);
                        boardPanel.add(board[row][col]);
                    }
                }

                //Construct an array of selection buttons
                selection = new JButton[width];
                buttonPanel = new JPanel(new GridLayout(1, width));
                for (int col = 0; col < width; col++){
                    selection[col] = new JButton(SELECT_TEXT);
                    selection[col].addActionListener(buttonListener);
                    buttonPanel.add(selection[col]);
                }

                gamePanel = new JPanel(new BorderLayout());
                gamePanel.add(buttonPanel, BorderLayout.NORTH);
                gamePanel.add(boardPanel, BorderLayout.CENTER);

                add(gamePanel);

                updateUI();
                packParentFrame();


            } else if (e.getSource() == quitButton){
                closeParentFrame();
            }
        }
    }
}

