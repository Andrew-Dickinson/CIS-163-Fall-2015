package us.andrewdickinson.gvsu.CIS163.linkedMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***********************************************************************
 * Created by Andrew on 11/21/15.
 **********************************************************************/
public class MessagePanel extends JPanel {
    /**
     * The GridPanel that displays the characters
     */
    private JPanel characterGridPanel;

    /**
     * The message displayed by this panel
     */
    private ScrambledMessage message;

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

    public MessagePanel(ScrambledMessage message){
        this.message = message;

        ButtonListener buttonListener = new ButtonListener();

        this.setLayout(new BorderLayout());

        //Initialize and fill the characterGridPanel
        updateCharacterGridPanel(message);

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
        add(container, BorderLayout.SOUTH);
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

    public class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == insertCharacterButton){
                BulletProofDialog<ScrambledMessage> dialog
                        = new InsertionDialog(getParent(), message);

                ScrambledMessage result = dialog.displayDialog();

                if (result != null)
                    message = result;
            } else if (e.getSource() == removeCharacterButton){
                BulletProofDialog<ScrambledMessage> dialog
                        = new DeletionDialog(getParent(), message);

                ScrambledMessage result = dialog.displayDialog();

                if (result != null)
                    message = result;
            } else if (e.getSource() == replaceCharacterButton){
                BulletProofDialog<ScrambledMessage> dialog
                        = new ReplaceDialog(getParent(), message);

                ScrambledMessage result = dialog.displayDialog();

                if (result != null)
                    message = result;
            } else if (e.getSource() == swapCharactersButton){
                BulletProofDialog<ScrambledMessage> dialog
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
            }

            updateCharacterGridPanel(message);
        }
    }
}
