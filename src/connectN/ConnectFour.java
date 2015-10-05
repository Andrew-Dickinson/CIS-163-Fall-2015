package connectN;

import javax.swing.*;

/***********************************************************************
 * Driver for connectN. Creates a frame with a ConnectFourPanel object
 * inside of it and a game menu with two buttons:
 *      - New Game: Creates a new game
 *      - Quit Game: Closes the panel
 *
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFour {
    /**
     * The title of the frame to display
     */
    private static final String FRAME_TITLE = "ConnectFour++";

    /**
     * The name of the game menu
     */
    public static final String GAME_MENU_TITLE = "Game";

    /**
     * The name of the new game menu item
     */
    public static final String NEW_GAME_MENU_ITEM = "New Game";

    /**
     * The name of the quit game menu item
     */
    public static final String QUIT_MENU_ITEM = "Quit Game";

    public static void main(String args[]){
        JMenuBar menuBar = new JMenuBar();

        //Create the Game menu
        JMenu menu = new JMenu(GAME_MENU_TITLE);
        menuBar.add(menu);

        //Create and add the new game button
        JMenuItem newGameMenuButton = new JMenuItem(NEW_GAME_MENU_ITEM);
        menu.add(newGameMenuButton);

        //Create and add the Quit button
        JMenuItem quitGameMenuButton = new JMenuItem(QUIT_MENU_ITEM);
        menu.add(quitGameMenuButton);

        //Create the frame and add a menu bar
        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);

        //Create the panel
        ConnectFourPanel panel = new ConnectFourPanel(quitGameMenuButton,
                newGameMenuButton);

        //Add the panel to the frame
        frame.getContentPane().add(panel);

        //Configure the frame
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
