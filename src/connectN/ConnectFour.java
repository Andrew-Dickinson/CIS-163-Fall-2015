package connectN;

import countdowntimer.MultipleCDTPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

/***********************************************************************
 * Driver for the game of connectN. Creates a frame with a
 * ConnectFourPanel object inside of it.
 * Created by Andrew on 9/21/15.
 **********************************************************************/
public class ConnectFour {
    private static final String FRAME_TITLE = "ConnectFour++";

    public static void main(String args[]){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game");
        menuBar.add(menu);

        JMenuItem newGameMenuButton = new JMenuItem("New Game");
        menu.add(newGameMenuButton);

        JMenuItem quitGameMenuButton = new JMenuItem("Quit");
        menu.add(quitGameMenuButton);

        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);

        ConnectFourPanel panel = new ConnectFourPanel(quitGameMenuButton,
                newGameMenuButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
