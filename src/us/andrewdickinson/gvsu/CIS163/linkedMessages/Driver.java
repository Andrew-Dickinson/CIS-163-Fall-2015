package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import javax.swing.*;

/***********************************************************************
 * Run the program for user interaction
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class Driver {
    public static void main(String[] args){
        //Create the frame and add a menu bar
        JFrame frame = new JFrame("Message Scrambler/De-scrambler");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new MessagePanel(new ScrambledMessage("Andrew"));

        //Add the panel to the frame
        frame.getContentPane().add(panel);

        //Configure the frame
        frame.setSize(450, 375);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
