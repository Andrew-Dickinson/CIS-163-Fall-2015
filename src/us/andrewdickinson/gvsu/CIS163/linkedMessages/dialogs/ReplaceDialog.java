package us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.ScrambledMessage;

import javax.swing.*;
import java.awt.*;

/***********************************************************************
 * An implementation of SymmetricBulletProofDialog that allows the user to select
 * a character to replace and to type a character to replace it
 * Created by Andrew on 11/16/15.
 **********************************************************************/
public class ReplaceDialog extends SymmetricBulletProofDialog<ScrambledMessage> {
    /**
     * To be returned by the getDialogPrompt() method
     */
    private static final String DIALOG_PROMPT
            = "Replace a character";

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
     * The field to enter the new character into
     */
    private JTextField newCharacterField;

    public ReplaceDialog(Component parent, ScrambledMessage preMessage){
        super(parent, preMessage);

        if (preMessage.length() == 0)
            throw new UnsupportedOperationException();

        this.preMessage = preMessage;

        JPanel gridPanel = new JPanel(
                new GridLayout(2, preMessage.length())
        );


        ButtonGroup selectionGroup = new ButtonGroup();
        selectionButtons = new JRadioButton[preMessage.length()];

        for (int i = 0; i < preMessage.length(); i++){
            selectionButtons[i] = new JRadioButton();
            selectionButtons[i].addActionListener(getValidationListener());
            selectionGroup.add(selectionButtons[i]);
            gridPanel.add(selectionButtons[i]);
        }

        for (int i = 0; i < preMessage.length(); i++){
            gridPanel.add(
                    new JLabel(preMessage.getCharacter(i).toString())
            );
        }

        newCharacterField = new JTextField();
        newCharacterField.getDocument()
                .addDocumentListener(getFieldValidationListener());

        JPanel fieldLabelPanel = new JPanel(new GridLayout(1, 2));
        fieldLabelPanel.add(new JLabel("Replacement Character:"));
        fieldLabelPanel.add(newCharacterField);

        primaryPanel = new JPanel(new BorderLayout());
        primaryPanel.add(gridPanel, BorderLayout.CENTER);
        primaryPanel.add(fieldLabelPanel, BorderLayout.SOUTH);

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

        //We also know that the text is valid anyway
        Character replaceChar = newCharacterField.getText().charAt(0);

        try {
            //Clone it so we don't end up modifying the previous object
            ScrambledMessage postMessage
                    = (ScrambledMessage) preMessage.clone();

            //Preform the modification
            postMessage.replaceCharacter(replaceLoc, replaceChar);

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
        boolean locationValid = getSelected() != -1;
        boolean characterValid
                = newCharacterField.getText().length() == 1;

        if (characterValid){
            showGood(newCharacterField);
        } else {
            showError(newCharacterField);
        }

        return locationValid && characterValid;
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
