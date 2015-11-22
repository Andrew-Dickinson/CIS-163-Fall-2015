package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/***********************************************************************
 * An implementation of SymmetricBulletProofDialog that allows the user to select
 * characters to copy
 * Created by Andrew on 11/16/15.
 **********************************************************************/
public class CopyDialog extends
        BulletProofDialog<ScrambledMessage, LinkedList<Character>> {

    /**
     * To be returned by the getDialogPrompt() method
     */
    private static final String DIALOG_PROMPT
            = "Select characters to copy";

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

    public CopyDialog(Component parent, ScrambledMessage preMessage){
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
    protected LinkedList<Character> getFinalData() {
        if (!isValidData())
            //This happens because getFinalData() doesn't make sense
            // right now. If isValidData() is false, then the fields
            // don't contain the correct data
            throw new UnsupportedOperationException();

        //Because isValidData() is true, we know this is not empty
        Integer[] selectedChars = getSelected();

        LinkedList<Character> out = new LinkedList<>();

        for (int i = 0; i < selectedChars.length; i++) {
            out.add(preMessage.getCharacter(selectedChars[i]));
        }

        //Return the result
        return out;
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
