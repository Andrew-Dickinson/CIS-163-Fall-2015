package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.clipboardInteraction.ClipBoard;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs.StartupDialog;

import javax.swing.*;
import java.awt.*;

/***********************************************************************
 * Run the program for user interaction
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Driver {
    public static void main(String[] args){
        ClipBoard.retreiveClipBoard();

        //Create the frame and add a menu bar
        JFrame frame = new JFrame("Message Scrambler/De-scrambler");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Configure the frame
        frame.setSize(450, 425);
        frame.setResizable(false);
        frame.setVisible(true);

        //Center it on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
                          dim.height / 2 - frame.getSize().height / 2);

        StartupDialog dialog = new StartupDialog(frame);
        ScrambledMessage message = dialog.displayDialog();

        if (message == null) System.exit(0);

        MessagePanel panel = new MessagePanel(frame, message);

        //Add the panel to the frame
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        //Show the imported message if applicable
        if (message.exportChangeStack().size() != 0)
            panel.showDeScrambled();
    }
}
