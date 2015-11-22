package us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.ScrambledMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/***********************************************************************
 * An implementation of SymmetricBulletProofDialog that allows the user
 * to select a characters to delete to the clipboard
 * Created by Andrew on 11/16/15.
 **********************************************************************/
public class CutDialog extends SymmetricBulletProofDialog<ScrambledMessage> {
    /**
     * To be returned by the getDialogPrompt() method
     */
    private static final String DIALOG_PROMPT
            = "Select characters to cut";

    /**
     * The primary panel for objects in this implementation
     */
    private JPanel primaryPanel;

    /**
     * The message to start with
     */
    private ScrambledMessage preMessage;

    /**
     * The buttons used to select the characters
     */
    private JCheckBox[] selectionButtons;

    /**
     * Stores the characters that get cut by this dialog
     */
    private LinkedList<Character> cutCharacters;

    public CutDialog(Component parent, ScrambledMessage preMessage){
        super(parent, preMessage);

        if (preMessage == null)
            preMessage = new ScrambledMessage();

        this.preMessage = preMessage;

        JPanel gridPanel = new JPanel(
                new GridLayout(2, preMessage.length())
        );

        selectionButtons = new JCheckBox[preMessage.length()];

        for (int i = 0; i < preMessage.length(); i++){
            selectionButtons[i] = new JCheckBox();
            selectionButtons[i].addActionListener(getValidationListener());
            gridPanel.add(selectionButtons[i]);
        }

        for (int i = 0; i < preMessage.length(); i++){
            gridPanel.add(
                    new JLabel(preMessage.getCharacter(i).toString())
            );
        }

        primaryPanel = new JPanel();
        primaryPanel.add(gridPanel);

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
        Integer[] removeChars = getSelected();

        //Store the characters we're about to remove
        cutCharacters = new LinkedList<>();
        for (int i = 0; i < removeChars.length; i++) {
            cutCharacters.add(preMessage.getCharacter(removeChars[i]));
        }

        try {
            //Clone it so we don't end up modifying the previous object
            ScrambledMessage postMessage
                    = (ScrambledMessage) preMessage.clone();

            //Preform the modification
            //Work backwards so that we don't screw up the numbering
            for (int i = removeChars.length - 1; i >= 0; i--) {
                postMessage.removeCharacter(removeChars[i]);
            }

            //Return the result
            return postMessage;
        } catch (CloneNotSupportedException e){
            //Will never occur. ScrambledMessage supports cloning
            throw new IllegalArgumentException();
        }
    }

    /*******************************************************************
     * Gets the characters that the user deleted in LinkedList form
     * @return The selected cut in a LinkedList
     * @throws UnsupportedOperationException if the dialog is still open
     ******************************************************************/
    public LinkedList<Character> getCutCharacters(){
        if (cutCharacters == null)
            throw new UnsupportedOperationException();

        return cutCharacters;
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
        return getSelected().length != 0;
    }

    /*******************************************************************
     * Gets the indices of the currently selected characters or [] if
     * none are selected
     * @return The indices of the characters
     ******************************************************************/
    private Integer[] getSelected(){
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < selectionButtons.length; i++){
            if (selectionButtons[i].isSelected())
                selected.add(i);
        }

        return selected.toArray(new Integer[selected.size()]);
    }
}
