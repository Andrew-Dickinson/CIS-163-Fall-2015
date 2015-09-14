package countdowntimer;

import javax.swing.*;

/**
 * Created by Andrew on 9/5/15.
 */
public class CountDownTimerDriver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Count Down Timer");
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);

        MultipleCDTPanel multipanel = new MultipleCDTPanel(3);

        JPanel simpleCasing = new JPanel();
        simpleCasing.add(multipanel);

        frame.getContentPane().add(simpleCasing);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
