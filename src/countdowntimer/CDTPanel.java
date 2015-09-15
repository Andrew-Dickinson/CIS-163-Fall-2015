package countdowntimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/***********************************************************************
 * A gui with a numpad, display, and play & pause buttons for
 * interfacing with the CountDownTimer class
 * Created by Andrew on 9/11/15.
 **********************************************************************/
public class CDTPanel extends JPanel {
    private CountDownTimer countDownTimer;

    private JButton[] numpad_buttons;
    private JButton clear_button;
    private JButton enter_button;

    private JButton play_button;
    private JButton stop_button;

    private Timer javaTimer;
    private TimerListener timer;

    private JTextField timerDisplay;

    private String entered_string;

    public CDTPanel() {
        setLayout(new BorderLayout());

        entered_string = "";

        ButtonListener listener = new ButtonListener();
        numpad_buttons = new JButton[10];

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BorderLayout());

        JPanel numPadPanel = new JPanel();
        numPadPanel.setLayout(new GridLayout(4, 3));

        for (int i = 0; i < numpad_buttons.length; i++) {
            JButton button = new JButton();
            numpad_buttons[i] = button;
            button.setText("" + i);
            button.addActionListener(listener);
            if (i != 0)
                //We will add 0 at the end
                numPadPanel.add(button);
        }


        String enterUrlString
                = "/toolbarButtonGraphics/general/Export16.gif";
        enter_button = new JButton(getIconFromUrl(enterUrlString));
        enter_button.addActionListener(listener);

        String undoUrlString
                = "/toolbarButtonGraphics/general/Undo16.gif";
        clear_button = new JButton(getIconFromUrl(undoUrlString));
        clear_button.addActionListener(listener);

        //So zero is the last entry and centered
        numPadPanel.add(clear_button);
        numPadPanel.add(numpad_buttons[0]);
        numPadPanel.add(enter_button);

        controlsPanel.add(numPadPanel, BorderLayout.CENTER);

        String stopUrlString
                = "/toolbarButtonGraphics/general/Stop16.gif";
        stop_button = new JButton(getIconFromUrl(stopUrlString));
        stop_button.addActionListener(listener);

        String startUrlString
                = "/toolbarButtonGraphics/media/Play16.gif";
        play_button = new JButton(getIconFromUrl(startUrlString));
        play_button.addActionListener(listener);

        JPanel startStopPanel = new JPanel();
        startStopPanel.setLayout(new FlowLayout());

        startStopPanel.add(play_button);
        startStopPanel.add(stop_button);

        controlsPanel.add(startStopPanel, BorderLayout.NORTH);

        add(controlsPanel, BorderLayout.CENTER);

        timerDisplay = new JTextField();
        timerDisplay.setFont(new Font("Helvetica", Font.BOLD, 24));
        timerDisplay.setEditable(false);
        timerDisplay.setHorizontalAlignment(JTextField.CENTER);
        add(timerDisplay, BorderLayout.NORTH);

        timer = new TimerListener();
        javaTimer = new Timer(1000, timer);

        countDownTimer = new CountDownTimer();

        timerDisplay.setText(countDownTimer.toString());
    }

    /**
     * Adds colons and appropriate zeros
     *
     * @param entered The raw string entered
     * @return The string with semi colons
     */
    private static String formatEnteredString(String entered) {
        String tempString = entered;
        for (int i = tempString.length(); i < 6; i++) {
            tempString = "0" + tempString;
        }

        tempString = new StringBuilder(tempString)
                .insert(tempString.length() - 2, ":")
                .insert(tempString.length() - 4, ":").toString();

        return tempString;
    }

    /**
     * Get an icon from the Java Look and Feel Repository
     *
     * @param urlString url of the icon
     * @return The icon
     */
    private ImageIcon getIconFromUrl(String urlString) {
        URL url = this.getClass().getResource(urlString);
        return new ImageIcon(url);
    }

    /**
     * Sets the instance variable and display to reflect a new string
     *
     * @param entered The new string
     */
    private void updateEnteredString(String entered) {
        javaTimer.stop();
        entered_string = entered;
        String processed_string = formatEnteredString(entered_string);
        timerDisplay.setText(processed_string);
    }

    /**
     * Updates the CountDownTimer instance variable to reflect
     * the entered value
     * @return The success of this operation
     */
    private boolean updateCDTFromEnteredString(){
        String formatedString =
                formatEnteredString(entered_string);
        entered_string = "";
        try {
            countDownTimer = new CountDownTimer(formatedString);
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,
                    "The time '" + formatedString + "' is invalid",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            updateEnteredString("");
            return false;
        }
        return true;
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < numpad_buttons.length; i++) {
                if (e.getSource() == numpad_buttons[i]) {
                    updateEnteredString(entered_string + i);
                }
            }

            if (e.getSource() == clear_button) {
                updateEnteredString("");
                updateCDTFromEnteredString();
            } else if (e.getSource() == enter_button) {
                if (!entered_string.equals("")
                        && updateCDTFromEnteredString())
                    javaTimer.start();
            } else if (e.getSource() == stop_button) {
                javaTimer.stop();

                //To make sure the timer isn't blank
                timerDisplay.setText(countDownTimer.toString());
            } else if (e.getSource() == play_button) {
                if (!CountDownTimer.isSuspended()) {
                    if (entered_string.equals("")
                           && countDownTimer.getOverallSeconds() != 0) {
                        javaTimer.start();
                    } else if (!entered_string.equals("")) {
                        if (updateCDTFromEnteredString())
                            javaTimer.start();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Un-Suspend the timers to continue",
                            "Timers Suspended",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }

    private class TimerListener implements ActionListener {
        private boolean alternator = true;

        public void actionPerformed(ActionEvent e) {
            if (countDownTimer.getOverallSeconds() != 0){
                countDownTimer.dec();
                timerDisplay.setText(countDownTimer.toString());
            } else {
                //For blinking
                if (alternator) {
                    timerDisplay.setText("");
                    alternator = false;
                } else {
                    timerDisplay.setText(countDownTimer.toString());
                    alternator = true;
                }
            }
        }
    }
}


