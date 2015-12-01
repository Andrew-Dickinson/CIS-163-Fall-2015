package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.clipboardInteraction.ClipBoard;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs.StartupDialog;

import javax.swing.*;
import java.awt.*;

/***********************************************************************
 * Run the program for user interaction
 * Created by Andrew on 11/9/15.
 **********************************************************************/
public class ConsoleDriver {
    public static void main(String[] args){
        MessageConsole mc = new MessageConsole();

        while (true) mc.takeCommand();
    }
}
