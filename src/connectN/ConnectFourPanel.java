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
    private static final String DISPLAY_EMPTY = " ";
    /**
     * Defines the text to display on buttons and labels
     */
    private static final String SELECT_TEXT = "Select";
    private static final String FRAME_TITLE = "ConnectFour++";
    /**
     * These JLabels display the status of the game board
     */
    private JLabel[][] board;
    /**
     * These buttons allow the players to play chips
     */
    private JButton[] selection;
    /**
     * The game object this panel is associated with
     */
    private ConnectFourGame game;

    public ConnectFourPanel(int width, int height){
        //Set the JFrame title
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setTitle(FRAME_TITLE);

        this.game = new ConnectFourGame();

        //Used for all button events in this panel
        ButtonListener buttonListener = new ButtonListener();

        //Construct the board panel with a
        //two dimensional array of JLabels'
        JPanel boardPanel = new JPanel(new GridLayout(height, width));
        this.board = new JLabel[height][width];
        for (int row = 0; row < height; row++){
            for (int col = 0; col < width; col++){
                board[row][col] = new JLabel(DISPLAY_EMPTY);
                boardPanel.add(board[row][col]);
            }
        }

        //Construct an array of selection buttons
        this.selection = new JButton[width];
        JPanel buttonPanel = new JPanel(new GridLayout(1, width));
        for (int col = 0; col < width; col++){
            selection[col] = new JButton(SELECT_TEXT);
            selection[col].addActionListener(buttonListener);
            buttonPanel.add(selection[col]);
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int col = 0; col < selection.length; col++){
                if (e.getSource() == selection[col]){
                    //Col was selected
                }
            }
        }
    }
}

