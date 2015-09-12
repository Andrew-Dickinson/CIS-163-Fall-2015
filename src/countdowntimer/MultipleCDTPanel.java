package countdowntimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andrew on 9/11/2015.
 */
public class MultipleCDTPanel extends JPanel{
    private final String SUSPEND_TEXT = "Suspend All";
    private final String UNSUSPEND_TEXT = "UnSuspend All";

    JButton suspendButton;

    public MultipleCDTPanel(int numberOfTimers){
        setLayout(new BorderLayout());

        ButtonListener buttonListener = new ButtonListener();

        CDTPanel[] timerPanels = new CDTPanel[numberOfTimers];
        JPanel gridPanel = new JPanel(new GridLayout(1, timerPanels.length, 30, 0));
        for (int i = 0; i < timerPanels.length; i++){
            timerPanels[i] = new CDTPanel();
            gridPanel.add(timerPanels[i]);
        }

        add(gridPanel, BorderLayout.CENTER);

        suspendButton = new JButton(SUSPEND_TEXT);
        suspendButton.addActionListener(buttonListener);
        JPanel suspendPanel = new JPanel(new FlowLayout());
        suspendPanel.add(suspendButton);
        add(suspendPanel, BorderLayout.SOUTH);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == suspendButton) {
                if (CountDownTimer.isSuspended()) {
                    suspendButton.setText(SUSPEND_TEXT);
                CountDownTimer.suspend(false);
                } else {
                    suspendButton.setText(UNSUSPEND_TEXT);
                    CountDownTimer.suspend(true);
                }
            }
        }
    }
}
