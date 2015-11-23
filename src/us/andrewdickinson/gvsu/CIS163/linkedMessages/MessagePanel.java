package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import us.andrewdickinson.gvsu.CIS163.linkedMessages.dialogs.*;
import us.andrewdickinson.gvsu.CIS163.linkedMessages.linkedlist.LinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/***********************************************************************
 * Created by Andrew on 11/21/15.
 **********************************************************************/
public class MessagePanel extends JPanel {
    /**
     * The parent frame for this panel
     */
    private JFrame frame;

    /**
     * The GridPanel that displays the characters
     */
    private JPanel characterGridPanel;

    /**
     * The Jpanel that stores the clipboard and action buttons
     */
    private  JPanel clipAndActionPanel;

    /**
     * The message displayed by this panel
     */
    private ScrambledMessage message;

    /**
     * The JLabel that displays the clipboard
     */
    private JLabel clipboardLabel;

    /**
     * Stores the characters in the clipboard
     */
    private LinkedList<Character> clipboard;

    /**
     * The simple message action buttons
     */
    private JButton insertCharacterButton;
    private JButton removeCharacterButton;
    private JButton replaceCharacterButton;
    private JButton swapCharactersButton;

    /**
     * The random message action buttons
     */
    private JButton shuffleButton;
    private JButton insertRandomCharacterButton;
    private JButton removeRandomCharacterButton;
    private JButton replaceRandomCharacterButton;
    private JButton swapRandomCharactersButton;

    /**
     * The buttons to popup with a dialog with a the message in it
     */
    private JButton showScrambledMessageDialogButton;
    private JButton showDeScrambledMessageDialogButton;

    /**
     * The clipboard buttons
     */
    private JButton copyButton;
    private JButton cutButton;
    private JButton pasteButton;

    /**
     * The menu buttons
     */
    private JMenuItem quit;
    private JMenuItem export;
    private JMenuItem newMessage;
    private JMenuItem showScrambled;
    private JMenuItem showDeScrambled;

    /**
     * Shortcut buttons for some of the menu items
     */
    private JButton exportShortcut;
    private JButton newMessageShortcut;

    public MessagePanel(JFrame frame, ScrambledMessage message){
        this.message = message;
        this.frame = frame;

        ButtonListener buttonListener = new ButtonListener();

        this.setLayout(new BorderLayout());

        setupTopButtons(buttonListener);

        //Initialize and fill the characterGridPanel
        updateCharacterGridPanel(message);

        setupClipBoardAndActionButtons(buttonListener);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        quit = new JMenuItem("Quit");
        quit.addActionListener(buttonListener);
        export = new JMenuItem("Export Changes");
        export.addActionListener(buttonListener);
        newMessage = new JMenuItem("New Message");
        newMessage.addActionListener(buttonListener);
        showDeScrambled = new JMenuItem("Get DeScrambled");
        showDeScrambled.addActionListener(buttonListener);
        showScrambled = new JMenuItem("Get Scrambled");
        showScrambled.addActionListener(buttonListener);

        file.add(newMessage);
        file.add(export);
        file.addSeparator();
        file.add(showScrambled);
        file.add(showDeScrambled);
        file.addSeparator();
        file.add(quit);

        menuBar.add(file);

        frame.setJMenuBar(menuBar);
    }

    private void setupTopButtons(ButtonListener buttonListener){
        String exportIcon = "/toolbarButtonGraphics/general/Save16.gif";
        exportShortcut = new JButton(getIconFromUrl(exportIcon));
        exportShortcut.setMargin(new java.awt.Insets(1, 2, 1, 2));
        exportShortcut.addActionListener(buttonListener);

        String newIcon = "/toolbarButtonGraphics/general/New16.gif";
        newMessageShortcut = new JButton(getIconFromUrl(newIcon));
        newMessageShortcut.setMargin(new java.awt.Insets(1, 2, 1, 2));
        newMessageShortcut.addActionListener(buttonListener);

        String scrambledIcon = "/toolbarButtonGraphics/general/Find16.gif";
        showScrambledMessageDialogButton
                           = new JButton(getIconFromUrl(scrambledIcon));
        showScrambledMessageDialogButton
                .setMargin(new java.awt.Insets(1, 2, 1, 2));
        showScrambledMessageDialogButton.addActionListener(buttonListener);

        String deScrambledIcon = "/toolbarButtonGraphics/general/Replace16.gif";
        showDeScrambledMessageDialogButton
                         = new JButton(getIconFromUrl(deScrambledIcon));
        showDeScrambledMessageDialogButton
                .setMargin(new java.awt.Insets(1, 2, 1, 2));
        showDeScrambledMessageDialogButton.addActionListener(buttonListener);

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topButtons.add(newMessageShortcut);
        topButtons.add(exportShortcut);
        topButtons.add(showScrambledMessageDialogButton);
        topButtons.add(showDeScrambledMessageDialogButton);

        add(topButtons, BorderLayout.NORTH);
    }

    /*******************************************************************
     * Initializes the characterGridPanel based on a certain message
     * @param message The message to base the grid panel on
     ******************************************************************/
    private void updateCharacterGridPanel(ScrambledMessage message){
        if (characterGridPanel != null)
            remove(characterGridPanel);

        JPanel subPanel = new JPanel(
                new GridLayout(2, message.length(), 10, 10)
        );

        for (int i = 0; i < message.length(); i++){
            JLabel label = new JLabel(Integer.toString(i));
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            subPanel.add(label);
        }

        for (int i = 0; i < message.length(); i++){
            JLabel label = new JLabel(message.getCharacter(i).toString());
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            subPanel.add(label);
        }

        JPanel container = new JPanel();
        container.add(subPanel);

        JScrollPane scrollPane = new JScrollPane(
                container,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );



        characterGridPanel = new JPanel();
        scrollPane.setPreferredSize(new Dimension(400, 90));
        characterGridPanel.add(scrollPane);
        add(characterGridPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void setupActionButtons(ButtonListener buttonListener){
        JPanel actionsPanel = new JPanel(new BorderLayout(10, 10));
        actionsPanel.setSize(new Dimension(300, 300));

        //Set up the simple (non-random) buttons
        actionsPanel.add(
                setupSimpleButtons(buttonListener),
                BorderLayout.CENTER
        );

        //Set up the random buttons
        actionsPanel.add(
                setupRandomButtons(buttonListener),
                BorderLayout.EAST
        );

        JPanel container = new JPanel();
        container.add(actionsPanel);
        clipAndActionPanel.add(container, BorderLayout.CENTER);
    }

    private JPanel setupSimpleButtons(ButtonListener buttonListener){
        JPanel simpleButtons = new JPanel(new GridLayout(5, 1));

        simpleButtons.add(new JLabel("Manual"));

        insertCharacterButton = new JButton("Insert a Character");
        insertCharacterButton.addActionListener(buttonListener);
        simpleButtons.add(insertCharacterButton);

        removeCharacterButton = new JButton("Remove Characters");
        removeCharacterButton.addActionListener(buttonListener);
        simpleButtons.add(removeCharacterButton);

        replaceCharacterButton = new JButton("Replace a Character");
        replaceCharacterButton.addActionListener(buttonListener);
        simpleButtons.add(replaceCharacterButton);

        swapCharactersButton = new JButton("Swap Two Characters");
        swapCharactersButton.addActionListener(buttonListener);
        simpleButtons.add(swapCharactersButton);

        return simpleButtons;
    }

    public JPanel setupRandomButtons(ButtonListener buttonListener){
        JPanel randomButtons = new JPanel(new GridLayout(6, 1));

        randomButtons.add(new JLabel("Random"));

        shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(buttonListener);
        randomButtons.add(shuffleButton);

        insertRandomCharacterButton = new JButton("Insert a Character");
        insertRandomCharacterButton.addActionListener(buttonListener);
        randomButtons.add(insertRandomCharacterButton);

        removeRandomCharacterButton = new JButton("Remove a Character");
        removeRandomCharacterButton.addActionListener(buttonListener);
        randomButtons.add(removeRandomCharacterButton);

        replaceRandomCharacterButton = new JButton("Replace a Character");
        replaceRandomCharacterButton.addActionListener(buttonListener);
        randomButtons.add(replaceRandomCharacterButton);

        swapRandomCharactersButton = new JButton("Swap Two Characters");
        swapRandomCharactersButton.addActionListener(buttonListener);
        randomButtons.add(swapRandomCharactersButton);

        return randomButtons;
    }

    private void setupClipboard(ButtonListener buttonListener){
        clipboard = null;

        JPanel clipboardPanel = new JPanel(new BorderLayout(0, 15));

        JPanel clipboardButtonPanel = new JPanel(new BorderLayout(3, 0));
        clipboardButtonPanel.setSize(new Dimension(150, 10));

        copyButton = new JButton("Copy");
        copyButton.addActionListener(buttonListener);
        clipboardButtonPanel.add(copyButton, BorderLayout.WEST);

        cutButton = new JButton("Cut");
        cutButton.addActionListener(buttonListener);
        clipboardButtonPanel.add(cutButton, BorderLayout.CENTER);

        pasteButton = new JButton("Paste");
        pasteButton.addActionListener(buttonListener);
        clipboardButtonPanel.add(pasteButton, BorderLayout.EAST);

        JPanel container = new JPanel();
        container.add(clipboardButtonPanel);
        clipboardPanel.add(container, BorderLayout.SOUTH);

        clipboardLabel = new JLabel();
        clipboardLabel.setHorizontalAlignment(JLabel.CENTER);
        updateClipboardDisplay();

        clipboardPanel.add(clipboardLabel, BorderLayout.CENTER);

        clipAndActionPanel.add(clipboardPanel, BorderLayout.SOUTH);
    }

    private void setupClipBoardAndActionButtons(ButtonListener bl){
        clipAndActionPanel = new JPanel(new BorderLayout(0, 20));

        setupClipboard(bl);
        setupActionButtons(bl);

        add(clipAndActionPanel, BorderLayout.SOUTH);
    }

    /*******************************************************************
     * Makes the clipboard JLabel reflect the clipboard object
     ******************************************************************/
    private void updateClipboardDisplay(){
        if (clipboard == null) {
            clipboardLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            clipboardLabel.setText("Your clipboard is empty");

            pasteButton.setEnabled(false);
        } else {
            clipboardLabel.setFont(new Font("Arial", Font.BOLD, 13));

            String out = "Clipboard: \"";

            for (int i = 0; i < clipboard.size(); i++){
                out += clipboard.get(i) + " ";
            }

            //Get rid of the trailing space and add a "
            out = out.substring(0, out.length() - 1) + "\"";

            clipboardLabel.setText(out);

            pasteButton.setEnabled(true);
        }
    }

    /*******************************************************************
     * Displays a dialog with the De-Scrambled message in it
     ******************************************************************/
    public void showDeScrambled(){
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.add(new JLabel("The De-Scrambled message is:"), BorderLayout.NORTH);

        JTextField uneditable = new JTextField(
                message.getDeScrambled(),
                message.getDeScrambled().length() + 3
        );
        uneditable.setEditable(false);
        uneditable.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel uneditableContainer = new JPanel();
        uneditableContainer.add(uneditable);

        panel.add(uneditableContainer, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(getParent(), panel,
            "De-Scrambled Message", JOptionPane.PLAIN_MESSAGE);
    }

    /*******************************************************************
     * Displays a dialog with the Scrambled message in it
     ******************************************************************/
    public void showScrambled(){
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.add(new JLabel("The Scrambled message is:"), BorderLayout.NORTH);

        JTextField uneditable = new JTextField(
                message.toString(),
                message.toString().length() + 3
        );
        uneditable.setEditable(false);
        uneditable.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel uneditableContainer = new JPanel();
        uneditableContainer.add(uneditable);

        panel.add(uneditableContainer, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(getParent(), panel,
                "Scrambled Message", JOptionPane.PLAIN_MESSAGE);;
    }

    /*******************************************************************
     * Get an icon from the Java Look and Feel Repository
     * @param urlString url of the icon
     * @return The icon
     ******************************************************************/
    private ImageIcon getIconFromUrl(String urlString) {
        URL url = this.getClass().getResource(urlString);
        return new ImageIcon(url);
    }

    private void replaceThisPanel(ScrambledMessage message){
        MessagePanel panel
                = new MessagePanel(frame, message);

        //Add the panel to the frame
        frame.getContentPane().remove(this);
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == insertCharacterButton){
                    SymmetricBulletProofDialog<ScrambledMessage> dialog
                            = new InsertionDialog(getParent(), message);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                } else if (e.getSource() == removeCharacterButton){
                    SymmetricBulletProofDialog<ScrambledMessage> dialog
                            = new DeletionDialog(getParent(), message);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                } else if (e.getSource() == replaceCharacterButton){
                    SymmetricBulletProofDialog<ScrambledMessage> dialog
                            = new ReplaceDialog(getParent(), message);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                } else if (e.getSource() == swapCharactersButton){
                    SymmetricBulletProofDialog<ScrambledMessage> dialog
                            = new SwapDialog(getParent(), message);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                } else if (e.getSource() == shuffleButton){
                    message.randomlyScramble(10);
                } else if (e.getSource() == insertRandomCharacterButton){
                    message.insertRandomCharacter();
                } else if (e.getSource() == removeRandomCharacterButton){
                    message.removeRandomCharacter();
                } else if (e.getSource() == replaceRandomCharacterButton){
                    message.replaceRandomCharacter();
                } else if (e.getSource() == swapRandomCharactersButton){
                    message.swapRandomCharacters();
                } else if (e.getSource() == copyButton){
                    CopyDialog dialog = new CopyDialog(getParent(), message);

                    LinkedList<Character> result = dialog.displayDialog();

                    if (result != null)
                        clipboard = result;
                } else if (e.getSource() == cutButton){
                    CutDialog dialog = new CutDialog(getParent(), message);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                        clipboard = dialog.getCutCharacters();
                } else if (e.getSource() == pasteButton){
                    SymmetricBulletProofDialog<ScrambledMessage> dialog
                        = new PasteDialog(getParent(), message, clipboard);

                    ScrambledMessage result = dialog.displayDialog();

                    if (result != null)
                        message = result;
                }  else if (quit == e.getSource()) {
                    System.exit(0);
                }else if (export == e.getSource()
                        || exportShortcut == e.getSource()) {
                    JFileChooser fileChooser = new JFileChooser();

                    //Display a file choosing dialog to get the destination
                    int status = fileChooser.showSaveDialog(getParent());
                    if (status == JFileChooser.APPROVE_OPTION){
                        String path =
                            fileChooser.getSelectedFile().getAbsolutePath();

                        try {
                            message.saveChangeStackToFile(path);
                        } catch (IOException err) {
                            JOptionPane.showMessageDialog(getParent(),
                                "An error occurred while writing this file",
                                "Unknown Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (newMessage == e.getSource()
                        || newMessageShortcut == e.getSource()) {
                    StartupDialog dia = new StartupDialog(getParent());
                    ScrambledMessage result = dia.displayDialog();

                    //If result is null it was canceled, so do nothing
                    if (result != null){
                        //If it wasn't, replace this panel with a new one
                        replaceThisPanel(result);
                    }
                } else if (showDeScrambled == e.getSource()
                        || showDeScrambledMessageDialogButton
                            == e.getSource()){
                    showDeScrambled();
                } else if (showScrambled == e.getSource()
                        || showScrambledMessageDialogButton
                        == e.getSource()){
                    showScrambled();
                }
            } catch (UnsupportedOperationException err){
                JOptionPane.showMessageDialog(getParent(),
                    "The message is too short to preform this operation",
                    "Too Short", JOptionPane.WARNING_MESSAGE);
            }


            updateCharacterGridPanel(message);
            updateClipboardDisplay();
        }
    }
}
