package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***********************************************************************
 * A dialog with a specific intended Modification on a message.
 * Modification type specified by DialogType
 * Created by Andrew on 11/15/15.
 **********************************************************************/
public abstract class BulletProofDialog<E> {
    /**
     * The parent JPanel for this dialog
     */
    Component parent;

    /**
     * The Ok and Cancel buttons that will respectively,
     * advance or cancel the data entry process
     */
    JButton dialogOkButton;
    JButton dialogCancelButton;

    /**
     * The primary panel that will be displayed in the dialog box
     */
    JPanel primaryDialogPanel;

    /**
     * The listener to apply to any button or radio-button that can
     * modify the validity of the data
     */
    ChangeListener validationListener;

    /**
     * The listener to apply to any text field that can modify the
     * validity of the data
     */
    FieldUpdateListener fieldValidationListener;

    /*******************************************************************
     * Instantiates (but does not display) this dialog with a
     * parent component
     * @param parent The component that this dialog is the child of
     ******************************************************************/
    public BulletProofDialog(Component parent){
        //Set the parent property to be queried later and provided to
        //the launched dialog
        this.parent = parent;

        //Instantiate the primary panel for this dialog
        primaryDialogPanel = new JPanel();
        primaryDialogPanel.setLayout(new BorderLayout());

        //Set up the ok and cancel buttons
        setUpOkCancelButtons();

        //Call the sub-class setup routine
        JPanel contentPanel = createDialogContentPanel();
        primaryDialogPanel.add(contentPanel, BorderLayout.CENTER);
    }

    /*******************************************************************
     * Indicates an error in the field. Undoes showGood()
     * @param field The field to modify
     ******************************************************************/
    protected static void showError(JTextField field){
        field.setBackground(Color.PINK);
    }

    /*******************************************************************
     * Indicates no error in the field. Undoes showError()
     * @param field The field to modify
     ******************************************************************/
    protected static void showGood(JTextField field){
        field.setBackground(Color.WHITE);
    }

    /*******************************************************************
     * Gets the optionPane instance from a component directly inside it.
     * A static method used in the listener below
     * @param parent The component
     * @return The jOptionPane instance
     ******************************************************************/
    private static JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent)parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    /*******************************************************************
     * Called by the constructor. Sub-Classes must use this method to
     * instantiate their GUI components and group them into a JPanel.
     * @return the JPanel that will be displayed in this dialog
     ******************************************************************/
    protected abstract JPanel createDialogContentPanel();

    /*******************************************************************
     * Create Ok an Cancel buttons and add them to the primary panel
     ******************************************************************/
    private void setUpOkCancelButtons(){
        //Create the button listener
        DialogButtonsListener dialogListener =
                new DialogButtonsListener();

        //Create a housing panel
        JPanel okCancelPanel = new JPanel();

        //Create the cancel button
        dialogCancelButton = new JButton("Cancel");
        dialogCancelButton.addActionListener(dialogListener);
        okCancelPanel.add(dialogCancelButton);

        //Create the okay button
        dialogOkButton = new JButton("Ok");
        dialogOkButton.addActionListener(dialogListener);
        okCancelPanel.add(dialogOkButton);

        //Add the housing panel to the primary panel
        primaryDialogPanel.add(okCancelPanel, BorderLayout.SOUTH);
    }

    /*******************************************************************
     * Display this dialog
     * @param oldData The data to start with
     * @return The resulting data or null if canceled
     ******************************************************************/
    public E displayDialog(E oldData) {
        setPreData(oldData);

        //Launch a dialog box with the options
        int status = JOptionPane.showOptionDialog(parent,
                primaryDialogPanel, "Please enter data",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new Object[]{dialogOkButton, dialogCancelButton},
                dialogOkButton);

        //If the user ended up hitting okay
        if (status == JOptionPane.YES_OPTION) {
            return getFinalData();
        }

        //The dialog box was canceled
        return null;
    }

    /*******************************************************************
     * Sets the original version of the data before the user modifies it
     * @param preData The original version of the data
     ******************************************************************/
    protected abstract void setPreData(E preData);

    /*******************************************************************
     * Gets the changed version of the data based on the user's entries
     * @return The changed version of the data
     * @throws IllegalStateException if isValidData() is false
     ******************************************************************/
    protected abstract E getFinalData();

    /*******************************************************************
     * Calls the sub-class's isValidData() method and uses the result
     * to update the state of the "Ok" button
     ******************************************************************/
    protected void updateErrorIndication(){
        if (isValidData()){
            //No problems. Enable the "Ok" button'
            dialogOkButton.setEnabled(true);
        } else {
            //We got problems, disable the "Ok" button
            dialogOkButton.setEnabled(false);
        }
    }

    /*******************************************************************
     * Defined by the sub-class to indicate if the current state of the
     * fields represents valid data. Used to disable/enable the "Ok"
     * button. Sub-Class should also call showError() and showGood() as
     * appropriate for the fields
     * @return True if the data is valid
     ******************************************************************/
    protected abstract boolean isValidData();

    /*******************************************************************
     * Gets an ActionListener that when fired, calls this class'
     * updateErrorIndication() method
     * @return The listener
     ******************************************************************/
    public ChangeListener getValidationListener() {
        return validationListener;
    }

    /*******************************************************************
     * Gets a DocumentListener that when fired, calls this class'
     * updateErrorIndication() method
     * @return The listener
     ******************************************************************/
    public FieldUpdateListener getFieldValidationListener() {
        return fieldValidationListener;
    }

    /*******************************************************************
     * A listener that calls updateErrorIndication() when it is fired
     ******************************************************************/
    private class ChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateErrorIndication();
        }
    }

    /*******************************************************************
     * DocumentListener to call updateErrorIndication() any
     * time anything happens to the text fields
     ******************************************************************/
    private class FieldUpdateListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateErrorIndication();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateErrorIndication();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateErrorIndication();
        }
    }

    /*******************************************************************
     * Process the OK and Cancel button clicks by passing the clicks on
     * to the appropriate methods in the enclosing JOptionPane
     ******************************************************************/
    private class DialogButtonsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //The parent jOptionPane for these buttons
            JOptionPane pane = getOptionPane((JComponent) e.getSource());

            if (e.getSource() == dialogCancelButton){
                pane.setValue(dialogCancelButton);
            }

            if (e.getSource() == dialogOkButton){
                pane.setValue(dialogOkButton);
            }
        }
    }
}
