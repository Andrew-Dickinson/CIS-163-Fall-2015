package connectN;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/***********************************************************************
 * Displays the ConnectFourGame so the user can play it
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFourPanel extends JPanel {
    public static final String CATS_GAME_TITLE
            = "Cat's Game";
    /**
     * Defines the text to display on the select buttons
     */
    private static final String SELECT_TEXT = "Pick";
    /**
     * The text to display on the undo button
     */
    private static final String UNDO_BUTTON_TEXT = "Undo";
    /**
     * The text to display on the wins indicators
     */
    private static final String PLAYER_TOTAL_WINS_TEXT
            = "Wins for player ";
    /**
     * The text of the labels that appear on the setup prompt
     */
    private static final String SETUP_DIALOG_TITLE
                         = "Please enter information about the board: ";
    private static final String WIDTH_PROMPT_LABEL = "Board Width: ";
    private static final String HEIGHT_PROMPT_LABEL = "Board Height: ";
    private static final String WIN_LENGTH_PROMPT_LABEL = "Win length: ";
    private static final String PLAYERS_PROMPT_LABEL
                                           = "Number of Players: ";
    private static final String FIRST_PLAYER_PROMPT_LABEL
                                           = "Player that goes first: ";
    /**
     * The text to appear on warning dialogs
     */
    private static final String INVALID_ENTRY_TEXT = "Invalid Entry";
    private static final String DEFAULT_VALUES_WILL_BE_USED
                                       = "Default Values will be used.";
    private static final String ENTER_VALID_VALUES_TEXT
                                       = "Please enter valid values.";
    private static final String INVALID_SELECTION_TITLE = "Invalid Selection";
    private static final String COLUMN_FULL_ERROR
          = "The column you selected is full. \n Please select another";
    /**
     * The text of the win dialog boxes
     */
    private static final String PLAYER_WIN_TEXT
            = "The following player won the game: Player ";
    private static final String PLAYER_WIN_TITLE
            = "Win for player ";
    private static final String CATS_GAME_TEXT
            = "The board is full and there is no winner!";
    /**
     * These JLabels display the status of the game board
     */
    private JLabel[][] board;

    /**
     * A gridlayout panel containing the board JLabels
     */
    private JPanel boardPanel;

    /**
     * A borderlayout panel containing the gui elements for this panel
     */
    private JPanel overallPanel;

    /**
     * Stores the number of wins for each player
     */
    private int[] playerWins;

    /**
     * These JLabels display the number of wins for each player
     */
    private JLabel[] playerWinLabels;

    /**
     * Panel to store the playerWinLabels
     */
    private JPanel playerWinLabelsPanel;

    /**
     * These buttons allow the players to play chips in specific columns
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
     * A menu button from the frame above for closing the frame
     */
    private JMenuItem quitButton;

    /**
     * A menu button from the frame above for creating a new game
     */
    private JMenuItem newGameButton;

    /**
     * The game object this panel is associated with
     */
    private ConnectFourGame game;

    /*******************************************************************
     * Sets up the panel to begin playing a game on it
     *
     * @param quitItem The quit menu item from the frame above
     * @param newGameItem The new game menu item from the frame above
     ******************************************************************/
    public ConnectFourPanel(JMenuItem quitItem, JMenuItem newGameItem){
        //Processes menu button clicks
        MenuButtonListener menuListener = new MenuButtonListener();

        //Handles all other button clicks in this panel
        buttonListener = new ButtonListener();

        //Set the menu button instance variables
        this.quitButton = quitItem;
        this.newGameButton = newGameItem;

        //Add menuListener to these menu buttons to process their clicks
        this.quitButton.addActionListener(menuListener);
        this.newGameButton.addActionListener(menuListener);

        //Instantiate the undo button.
        // It gets added in the setUpNewGamePanel method
        undoButton = new JButton(UNDO_BUTTON_TEXT);
        undoButton.addActionListener(buttonListener);

        //Call the private helper method to prompt for game info
        //and create the board GUI
        setUpNewGamePanel(true);
    }

    /*******************************************************************
     * Sets up a new game. Prompts the user to enter board size,
     * win length, and starting player. Sets up the board GUI including
     * board buttons and win indicators
     *
     * @param useDefaultIfEntryInvalid If the user enters an invalid
     *          value in a prompt and this is true, a default value
     *          will be used instead of prompting the user to re-enter
     *          the value
     ******************************************************************/
    private void setUpNewGamePanel(boolean useDefaultIfEntryInvalid){
        //Instantiate the game panel
        JPanel gamePanel = new JPanel(new BorderLayout());

        //Setup a new game instance from prompts given to the user
        this.game = createGameFromPrompts(useDefaultIfEntryInvalid);

        //Get the width and height from the game object
        int width = game.getBoardWidth();
        int height = game.getBoardHeight();

        //So that we don't erase current win data
        int[] old_player_wins = null;
        if (playerWins != null){
            old_player_wins = playerWins.clone();
        }

        //Initialize the win indicators and add them to the panel
        playerWins = new int[game.getNumberOfPlayers()];
        playerWinLabels = new JLabel[game.getNumberOfPlayers()];
        playerWinLabelsPanel = new JPanel(
                new GridLayout(game.getNumberOfPlayers(), 1)
        );

        playerWinLabelsPanel.setPreferredSize(
                new Dimension(200, 30 * game.getNumberOfPlayers())
        );

        JPanel tempFlowPanel = new JPanel(new FlowLayout());
        tempFlowPanel.add(playerWinLabelsPanel);

        for (int i = 0; i < game.getNumberOfPlayers(); i++){
            playerWins[i] = 0;
            playerWinLabels[i] = new JLabel();
            playerWinLabels[i]
                    .setHorizontalAlignment(SwingConstants.CENTER);
            playerWinLabels[i].setBorder(
                    new BorderUIResource.BevelBorderUIResource(
                            BevelBorder.LOWERED
                    )
            );
            playerWinLabelsPanel.add(playerWinLabels[i]);
        }

        //Restore the old win data
        if (old_player_wins != null){
            int max = 0;
            if (playerWins.length > old_player_wins.length){
                max = old_player_wins.length;
            } else {
                max = playerWins.length;
            }

            for (int i = 0; i < max; i++){
                playerWins[i] = old_player_wins[i];
            }
        }

        updateWinIndicators();

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(tempFlowPanel, BorderLayout.CENTER);
        rightPanel.add(undoButton, BorderLayout.NORTH);

        //Construct the board panel with a
        //two dimensional array of JLabels
        this.boardPanel = new JPanel(new GridLayout(height, width));
        this.board = new JLabel[height][width];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                try {
                    Image img = ImageIO.read(ConnectFourPanel.class
                            .getResourceAsStream("blankCell.png"));
                    board[row][col] = new JLabel(new ImageIcon(img));
                    boardPanel.add(board[row][col]);
                } catch (IOException e){
                    e.printStackTrace();
                }
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

        gamePanel.add(buttonPanel, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);

        overallPanel = new JPanel(new BorderLayout());
        overallPanel.add(gamePanel, BorderLayout.CENTER);
        overallPanel.add(rightPanel, BorderLayout.EAST);
        overallPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        overallPanel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);

        add(overallPanel);
    }

    /*******************************************************************
     * Closes the parent frame to this panel
     ******************************************************************/
    private void closeParentFrame(){
        //Get the parent frame and call its dispose method
        SwingUtilities.windowForComponent(this).dispose();
    }

    /*******************************************************************
     * Calls the parent frame's pack method
     ******************************************************************/
    private void packParentFrame(){
        //Get the parent frame and call its pack method
        SwingUtilities.windowForComponent(this).pack();
    }

    /*******************************************************************
     * Creates a game object from prompts to the user about game details
     *
     * @param useDefaultIfEntryInvalid If the user enters an invalid
     *          value and this is true, a default value will be used
     *          instead of prompting the user to re-enter the value
     * @return The generated game object
     ******************************************************************/
    private ConnectFourGame createGameFromPrompts(
            boolean useDefaultIfEntryInvalid){

        //4-19 is the range for the width and height of a board
        DefaultComboBoxModel<String> widthModel =
                new DefaultComboBoxModel<String>();
        DefaultComboBoxModel<String> heightModel =
                new DefaultComboBoxModel<String>();
        for (int i = 4; i < 20; i++){
            widthModel.addElement(Integer.toString(i));
            heightModel.addElement(Integer.toString(i));
        }

        //Select a 10 by 10 board by default
        widthModel.setSelectedItem(Integer.toString(10));
        heightModel.setSelectedItem(Integer.toString(10));

        //4-19 is the range for the win length. We check below if
        // it's greater than the size of the board allows
        DefaultComboBoxModel<String> winLengthModel =
                new DefaultComboBoxModel<String>();
        for (int i = 4; i < 20; i++){
            winLengthModel.addElement(Integer.toString(i));
        }

        //2-10 is the range for the number of players
        DefaultComboBoxModel<String> playersModel =
                new DefaultComboBoxModel<String>();
        for (int i = 2; i < 11; i++){
            playersModel.addElement(Integer.toString(i));
        }

        //1-10 is the range for the selection of a player
        DefaultComboBoxModel<String> playerSelModel =
                new DefaultComboBoxModel<String>();
        for (int i = 1; i < 11; i++){
            playerSelModel.addElement(Integer.toString(i));
        }

        //Create
        JComboBox widthComboBox = new JComboBox<String>(widthModel);
        JComboBox heightComboBox = new JComboBox<String>(heightModel);
        JComboBox winLengthCBox = new JComboBox<String>(winLengthModel);
        JComboBox numPlayersCBox = new JComboBox<String>(playersModel);
        JComboBox firstPlayerCBox = new JComboBox<String>(playerSelModel);

        JPanel dialogFieldPanel = new JPanel(new GridLayout(5, 2));
        dialogFieldPanel.add(new JLabel(WIDTH_PROMPT_LABEL));
        dialogFieldPanel.add(widthComboBox);
        dialogFieldPanel.add(new JLabel(HEIGHT_PROMPT_LABEL));
        dialogFieldPanel.add(heightComboBox);
        dialogFieldPanel.add(new JLabel(WIN_LENGTH_PROMPT_LABEL));
        dialogFieldPanel.add(winLengthCBox);
        dialogFieldPanel.add(new JLabel(PLAYERS_PROMPT_LABEL));
        dialogFieldPanel.add(numPlayersCBox);
        dialogFieldPanel.add(new JLabel(FIRST_PLAYER_PROMPT_LABEL));
        dialogFieldPanel.add(firstPlayerCBox);

        boolean entriesInvalid = true;

        //Default values. They get overwritten
        int width = -1;
        int height = -1;
        int winLength = -1;
        int players = -1;
        int startingPlayer = -1;
        while (entriesInvalid) {
            int result = JOptionPane.showConfirmDialog(null,
                    dialogFieldPanel,
                    SETUP_DIALOG_TITLE,
                    JOptionPane.OK_CANCEL_OPTION);

            //If cancel was pressed, we leave all the values at -1
            if (result == JOptionPane.OK_OPTION) {
                //Get the string values from the comboboxes
                String widthString =
                        (String) widthComboBox.getSelectedItem();
                String heightString =
                        (String) heightComboBox.getSelectedItem();
                String lengthString =
                        (String) winLengthCBox.getSelectedItem();
                String playersString =
                        (String) numPlayersCBox.getSelectedItem();
                String startingString =
                        (String) firstPlayerCBox.getSelectedItem();

                //Parse the strings out into ints
                width = Integer.parseInt(widthString);
                height = Integer.parseInt(heightString);
                winLength = Integer.parseInt(lengthString);
                players = Integer.parseInt(playersString);
                startingPlayer = Integer.parseInt(startingString);
            }

            //Determine if the win length is longer than
            //the smaller dimension of the board
            boolean winLengthTooLong;
            if (width < height) {
                winLengthTooLong = winLength > width;
            } else {
                winLengthTooLong = winLength > height;
            }

            //If any condition is invalid, including
            // if width or height is still -1, we set entriesInvalid
            if (width >= 20 || width <= 3 || height >= 20
                    || height <= 3 || winLength <= 1
                    || winLengthTooLong || startingPlayer > players) {
                entriesInvalid = true;
            } else {
                entriesInvalid = false;
            }

            //If things are bad, we tell the user
            if (entriesInvalid) {
                String warningMessage;
                int messageType;
                if (useDefaultIfEntryInvalid) {
                    warningMessage = DEFAULT_VALUES_WILL_BE_USED;
                    messageType = JOptionPane.WARNING_MESSAGE;
                } else {
                    warningMessage = ENTER_VALID_VALUES_TEXT;
                    messageType = JOptionPane.ERROR_MESSAGE;
                }

                JOptionPane.showMessageDialog(null, warningMessage,
                        INVALID_ENTRY_TEXT, messageType);
            }

            //Fill in default values if necessary
            if (entriesInvalid && useDefaultIfEntryInvalid){
                width = 10;
                height = 10;
                winLength = 4;
                players = 2;
                startingPlayer = 1;

                entriesInvalid = false;
            }
        }

        //Build and return the board object
        return new ConnectFourGame(width, height, winLength,
                players, startingPlayer - 1);
    }

    /*******************************************************************
     * Checks for a win or cat's game and updates the
     * board and win counters accordingly
     ******************************************************************/
    private void checkForAndProcessWin(){
        int currentStatus = game.getGameStatus();
        if (currentStatus != GameStatus.IN_PROGRESS) {
            if (currentStatus > -1 && currentStatus < 10){
                playerWins[currentStatus]++;

                //+1's convert to 1-Based indexes
                JOptionPane.showMessageDialog(null,
                        PLAYER_WIN_TEXT + (currentStatus + 1),
                        PLAYER_WIN_TITLE + (currentStatus + 1),
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (currentStatus == GameStatus.CATS) {
                JOptionPane.showMessageDialog(null,
                        CATS_GAME_TEXT,
                        CATS_GAME_TITLE, JOptionPane.INFORMATION_MESSAGE);
            }

            //Reset the game board
            game.resetBoard();

            //Reset the gui board
            updateBoardFromGameObject();

            //Update the win indicators
            updateWinIndicators();
        }
    }

    /*******************************************************************
     * Updates the JLabels of the board based on the game object
     ******************************************************************/
    private void updateBoardFromGameObject(){
        //Iterate over the board
        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[0].length; col++){
                //For each cell on the board, update it based
                //on the corresponding game object cell
                int value = game.getBoardCell(row, col);
                setCell(row, col, value);
            }
        }
    }

    /**
     * Updates the win indicators from the wins instance variable
     */
    private void updateWinIndicators(){
        for (int i = 0; i < playerWins.length; i++){
            try {
                Image img = ImageIO.read(ConnectFourPanel.class
                        .getResourceAsStream(getIconPath(i)));

                img = img.getScaledInstance(20, 20, Image.SCALE_DEFAULT);

                //Convert image to icon
                ImageIcon icon = new ImageIcon(img);

                playerWinLabels[i].setIcon(icon);
            } catch (IOException e){
                e.printStackTrace();
            }

            playerWinLabels[i].setText(PLAYER_TOTAL_WINS_TEXT
                    + (i + 1) + ": " + playerWins[i]);
        }
    }

    /*******************************************************************
     * Sets a specific cell to an image corresponding to
     * the player provided
     *
     * @param row The row of the cell
     * @param col The column of the cell
     * @param player The player to set it to (or -1 for blank)
     ******************************************************************/
    private void setCell(int row, int col, int player){
        try {
            //Get image
            Image img = ImageIO.read(ConnectFourPanel.class
                  .getResourceAsStream(getIconPath(player)));

            //Convert image to icon
            ImageIcon icon = new ImageIcon(img);

            //Set icon on board
            board[row][col].setIcon(icon);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Computes an icon path for finding a particular icon
     * @param player The player to get it for (or -1 for blank)
     * @return The path of the image file
     */
    private String getIconPath(int player){
        if (player != -1) {
            return "player" + player + "Cell.png";
        } else {
            return "blankCell.png";
        }
    }

    /*******************************************************************
     * Handles button clicks other than those on the menu items
     ******************************************************************/
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int player = game.getTurnTakingPlayer();

            //For each button of the selection buttons
            for (int col = 0; col < selection.length; col++){
                if (e.getSource() == selection[col]) {
                    //Call the game's selectColumn method with
                    //the corresponding column number
                    int chipRow = game.selectColumn(col);

                    //If the selection was successful
                    if (chipRow != -1) {
                        setCell(chipRow, col, player);
                    } else {
                        //The column is full, alert the user
                        JOptionPane.showMessageDialog(null,
                                COLUMN_FULL_ERROR,
                                INVALID_SELECTION_TITLE,
                                JOptionPane.ERROR_MESSAGE);
                    }

                    //No need to continue. We found the button
                    break;
                }
            }

            if (e.getSource() == undoButton){
                //Call the undoMove method on the game
                int undone = game.undoMove();

                //If undo was preformed then undone is not -2
                if (undone != -2){
                    //Update the board based on the game object
                    updateBoardFromGameObject();

                    //Undo win indicator (if necessary)
                    if (undone != -1){
                        playerWins[undone] -= 1;
                        updateWinIndicators();
                    }
                }
            }

            //Checks for a win and updates the board
            checkForAndProcessWin();
        }
    }

    /*******************************************************************
     * Handles the clicks on the menu items: quit and newGame
     ******************************************************************/
    private class MenuButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGameButton){
                //Get rid of the old game panel
                remove(overallPanel);

                //Set up the new one
                setUpNewGamePanel(false);

                //Update the UI to reflect the new gamePanel
                validate();
                repaint();

                //Resize the parent frame appropriately
                packParentFrame();
            } else if (e.getSource() == quitButton){
                //Close the parent frame
                closeParentFrame();
            }
        }
    }
}

