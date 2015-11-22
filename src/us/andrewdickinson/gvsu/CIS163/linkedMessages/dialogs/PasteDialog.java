package us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.ScrambledMessage;

import javax.swing.*;
import java.awt.*;

/***********************************************************************
 * An implementation of SymmetricBulletProofDialog that allows the user
 * to select a location to paste the clipboard
 * Created by Andrew on 11/16/15.
 **********************************************************************/
public class PasteDialog extends SymmetricBulletProofDialog<ScrambledMessage> {
    /**
     * To be returned by the getDialogPrompt() method
     */
    private static final String DIALOG_PROMPT
            = "Select a location to paste";

    /**
     * The primary panel for objects in this implementation
     */
    private JPanel primaryPanel;

    /**
     * The message to start with
     */
    private ScrambledMessage preMessage;

    /**
     * The buttons used to selected the character
     */
    private JRadioButton[] selectionButtons;

    /**
     * The characters to insert into the message
     */
    private LinkedList<Character> clipboard;

    public PasteDialog(Component parent,
                       ScrambledMessage preMessage,
                       LinkedList<Character> clipboard){
        super(parent, preMessage);

        if (preMessage == null)
            preMessage = new ScrambledMessage();

        this.preMessage = preMessage;
        this.clipboard = clipboard;

        JPanel gridPanel = new JPanel(
                new GridLayout(1, preMessage.length() * 2 + 1)
        );

        ButtonGroup selectionGroup = new ButtonGroup();
        selectionButtons = new JRadioButton[preMessage.length() + 1];

        //Add the characters and their corresponding radio buttons
        for (int i = 0; i < preMessage.length(); i++){
            selectionButtons[i] = new JRadioButton();
            selectionButtons[i].addActionListener(getValidationListener());
            selectionGroup.add(selectionButtons[i]);
            gridPanel.add(selectionButtons[i]);
            gridPanel.add(
                    new JLabel(preMessage.getCharacter(i).toString())
            );
        }

        //Add the last radio button
        JRadioButton last = new JRadioButton();
        last.addActionListener(getValidationListener());
        selectionGroup.add(last);
        gridPanel.add(last);
        selectionButtons[preMessage.length()] = last;

        primaryPanel = new JPanel(new BorderLayout());
        primaryPanel.add(gridPanel, BorderLayout.CENTER);

        setDialogContentPanel(primaryPanel);
    }

    /*******************************************************************
     * Return the prompt for this dialog. Placed in the title
     * @return The prompt
     ******************************************************************/
    @Override
    protected String getDialogPrompt() {
        return DIALOG_PROMPT;
    }

    /*******************************************************************
     * Gets the changed version of the data based on the user's entries
     * @return The changed version of the data
     * @throws UnsupportedOperationException if isValidData() is false
     ******************************************************************/
    @Override
    protected ScrambledMessage getFinalData() {
        if (!isValidData())
            //This happens because getFinalData() doesn't make sense
            // right now. If isValidData() is false, then the fields
            // don't contain the correct data
            throw new UnsupportedOperationException();

        //Because isValidData() is true, we know this is not -1
        int replaceLoc = getSelected();

        try {
            //Clone it so we don't end up modifying the previous object
            ScrambledMessage postMessage
                    = (ScrambledMessage) preMessage.clone();

            //Insert the characters from the clipboard in the right spot
            for (int i = 0; i < clipboard.size(); i++){
                postMessage.insertCharacter(
                        replaceLoc + i,
                        clipboard.get(i)
                );
            }

            //Return the result
            return postMessage;
        } catch (CloneNotSupportedException e){
            //Will never occur. ScrambledMessage supports cloning
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Defined by the sub-class to indicate if the current state of the
     * fields represents valid data. Used to disable/enable the "Ok"
     * button. Sub-Class should also call showError() and showGood() as
     * appropriate for the fields
     *
     * @return True if the data is valid
     ******************************************************************/
    @Override
    protected boolean isValidData() {
        return getSelected() != -1;
    }

    /*******************************************************************
     * Gets the index of the currently selected character or -1 if none
     * are selected
     * @return The index of the character
     ******************************************************************/
    private int getSelected(){
        for (int i = 0; i < selectionButtons.length; i++){
            if (selectionButtons[i].isSelected())
                return i;
        }

        //We didn't find it
        return -1;
    }
}
