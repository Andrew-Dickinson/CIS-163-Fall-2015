package us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.ScrambledMessage;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/***********************************************************************
 * Displayed on startup of the program to allow the user to import an
 * existing message, or to create a new one
 * Created by Andrew on 11/21/15.
 **********************************************************************/
public class StartupDialog
        extends SymmetricBulletProofDialog<ScrambledMessage> {
    /**
     * The title of the dialog
     */
    private static final String DIALOG_PROMPT = "Create/Import Message";
    /**
     * The parent JPanel for this dialog
     */
    private Component parent;
    /**
     * The primary panel for this dialog
     */
    private JPanel primaryPanel;

    /**
     * The radio buttons that indicate which options the user is
     * prompted with
     */
    private JRadioButton createNewRadioButton;
    private JRadioButton deScrambleOldRadioButton;

    /**
     * The field for the user to enter in the text of a new message
     */
    private JTextField newMessageField;

    /**
     * The field for the user to enter in the text of a previously
     * scrambled message
     */
    private JTextField oldMessageField;

    /**
     * The button to launch the file choosing dialog for import
     */
    private JButton importPopupButton;

    /**
     * The label that lists the path of the currently selected file
     */
    private JLabel currentSelectedFileLabel;

    /**
     * The path of the currently selected file
     */
    private String currentSelectedFilePath;

    /**
     * Lists of the labels, used for greying out
     */
    private LinkedList<JLabel> newMessageLabels;
    private LinkedList<JLabel> importLabels;

    public StartupDialog(Component parent) {
        super(parent, null);

        this.parent = parent;

        primaryPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Select a message source");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        primaryPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        newMessageLabels = new LinkedList<>();
        importLabels = new LinkedList<>();

        ButtonGroup selectionGroup = new ButtonGroup();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 5));
        createNewRadioButton = new JRadioButton("Create new message");
        selectionGroup.add(createNewRadioButton);
        buttonPanel.add(createNewRadioButton);
        createNewRadioButton.addActionListener(getValidationListener());

        deScrambleOldRadioButton
                       = new JRadioButton("De-Scramble an old message");
        selectionGroup.add(deScrambleOldRadioButton);
        buttonPanel.add(deScrambleOldRadioButton);
        deScrambleOldRadioButton.addActionListener(getValidationListener());

        primaryPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(20, 0));

        JPanel createNewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        createNewPanel.setPreferredSize(new Dimension(320, 20));

        JLabel newMessageTextLabel = new JLabel("New Message Text: ");
        newMessageLabels.add(newMessageTextLabel);
        createNewPanel.add(newMessageTextLabel);
        newMessageField = new JTextField();
        newMessageField.setColumns(12);
        newMessageField.getDocument()
                .addDocumentListener(getFieldValidationListener());

        createNewPanel.add(newMessageField);

        JPanel importPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        importPanel.setPreferredSize(new Dimension(275, 90));

        JLabel scrambledMessageLabel = new JLabel("Scrambled Text: ");
        importLabels.add(scrambledMessageLabel);
        importPanel.add(scrambledMessageLabel);
        oldMessageField = new JTextField();
        oldMessageField.setColumns(13);
        importPanel.add(oldMessageField);
        oldMessageField.getDocument()
                .addDocumentListener(getFieldValidationListener());

        importPopupButton = new JButton("Import changes...");
        importPopupButton.addActionListener(new FilePopupListener());
        importPanel.add(importPopupButton);
        currentSelectedFileLabel = new JLabel("");
        importLabels.add(currentSelectedFileLabel);
        currentSelectedFileLabel.setHorizontalAlignment(JLabel.CENTER);
        importPanel.add(currentSelectedFileLabel);

        bottomPanel.add(createNewPanel, BorderLayout.WEST);

        bottomPanel.add(importPanel, BorderLayout.CENTER);

        primaryPanel.add(bottomPanel, BorderLayout.SOUTH);

        super.setDialogContentPanel(primaryPanel);
    }

    /*******************************************************************
     * Gets the entered data
     * @return The entered data
     * @throws IllegalStateException if isValidData() is false
     ******************************************************************/
    @Override
    protected ScrambledMessage getFinalData() {
        if (!isValidData())
            throw new IllegalStateException();

        try {
            if (deScrambleOldRadioButton.isSelected()) {
                return new ScrambledMessage(
                        oldMessageField.getText(),
                        currentSelectedFilePath
                );
            } else {
                //We know that one of the radio buttons has to be
                //selected because isValidData() is true
                return new ScrambledMessage(newMessageField.getText());
            }
        } catch (IOException e){
            //Will never occur. Pass an exception up just so we find it
            throw new IllegalStateException();
        }
    }

    /*******************************************************************
     * Defined by the sub-class to indicate if the current state of the
     * fields represents valid data. Used to disable/enable the "Ok"
     * button. Sub-Class should also call showError() and showGood() as
     * appropriate for the fields
     * @return True if the data is valid
     ******************************************************************/
    @Override
    protected boolean isValidData() {
        //Set the active fields and their individual validity
        updateActiveFields();

        //The validity depends on the top radio buttons
        if (createNewRadioButton.isSelected()){
            return newMessageField.getText().length() != 0;
        } else if (deScrambleOldRadioButton.isSelected()) {
            if (deScrambleOldRadioButton.getText().length() == 0
                    || currentSelectedFilePath == null){
                return false;
            } else {
                try {
                    new ScrambledMessage(
                            oldMessageField.getText(),
                            currentSelectedFilePath
                    );
                } catch (IllegalArgumentException|IOException e){
                    return false;
                }

                //If we made it this far, everything is good
                return true;
            }
        } else {
            //Neither is selected
            return false;
        }
    }

    private void setNewMessageGUIEnabled(boolean enabled){
        //Set the status of the field
        newMessageField.setEnabled(enabled);

        //Set the status of the labels
        for (int i = 0; i < newMessageLabels.size(); i++){
            newMessageLabels.get(i).setEnabled(enabled);
        }
    }

    private void setImportGUIEnabled(boolean enabled){
        //Set the status of the fields
        importPopupButton.setEnabled(enabled);
        oldMessageField.setEnabled(enabled);

        //Set the status of the labels
        for (int i = 0; i < importLabels.size(); i++){
            importLabels.get(i).setEnabled(enabled);
        }
    }

    private void updateActiveFields(){
        if (createNewRadioButton.isSelected()){
            setNewMessageGUIEnabled(true);

            //Disable the fields on the other side and set them to good
            setImportGUIEnabled(false);
            showGood(oldMessageField);

            if (newMessageField.getText().length() == 0) {
                showError(newMessageField);
            } else {
                showGood(newMessageField);
            }
        } else if (deScrambleOldRadioButton.isSelected()) {
            //Disable the field on the other side and set it to good
            setNewMessageGUIEnabled(false);
            showGood(newMessageField);

            setImportGUIEnabled(true);

            if (oldMessageField.getText().length() == 0) {
                showError(oldMessageField);
                importPopupButton.setEnabled(false);
            } else {
                showGood(oldMessageField);
            }
        } else {
            //Set the status of the fields
            setImportGUIEnabled(false);
            setNewMessageGUIEnabled(false);
            showError(newMessageField);
            showError(oldMessageField);
        }
    }

    /*******************************************************************
     * Return the prompt for this dialog. Placed in the title
     * @return The prompt
     ******************************************************************/
    @Override
    protected String getDialogPrompt() {
        return DIALOG_PROMPT;
    }

    private class FilePopupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();

            //Display a file choosing dialog to get the destination
            int status = fileChooser.showOpenDialog(parent);
            if (status == JFileChooser.APPROVE_OPTION){
                String path = fileChooser.getSelectedFile()
                                                     .getAbsolutePath();

                currentSelectedFilePath = path;

                Graphics graphics
                        = currentSelectedFileLabel.getGraphics();

                String modifiedPath = path;
                int width = graphics.getFontMetrics()
                                            .stringWidth(modifiedPath);
                while (width > 300){
                    modifiedPath = "..." + modifiedPath.substring(10);
                    width = graphics.getFontMetrics()
                            .stringWidth(modifiedPath);
                }

                currentSelectedFileLabel.setText(modifiedPath);

                if (!isValidData()){
                    JOptionPane.showMessageDialog(null,
                        "This file is invalid for the specified string",
                        "Invalid file", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                //The dialog was cancelled
                currentSelectedFilePath = null;
                currentSelectedFileLabel.setText("");
            }

            primaryPanel.revalidate();
            primaryPanel.repaint();

            updateErrorIndication();
        }
    }

}
