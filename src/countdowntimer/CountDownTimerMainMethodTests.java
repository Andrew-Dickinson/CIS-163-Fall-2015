package countdowntimer;

/***********************************************************************
 * Prints the output from a couple constructors. Is a poor test system
 * Created by Andrew on 9/5/15.
 **********************************************************************/
public class CountDownTimerMainMethodTests {
    public static void main(String[] args){
        CountDownTimer s = new CountDownTimer("2:59:8");
        System.out.println("Time: " + s);

        s = new CountDownTimer("20:8");
        System.out.println("Time: " + s);

        s = new CountDownTimer("8");
        System.out.println("Time: " + s);

        CountDownTimer s1 = new CountDownTimer(25, 2, 20);
        System.out.println("Time: " + s1);

        s1.subtract(1000);
        System.out.println("Time: " + s1);

        s1.add(1000);
        System.out.println("Time: " + s1);

        CountDownTimer s2 = new CountDownTimer(40, 10, 20);
        s2.subtract(100);
        for (int i = 0; i < 4000; i++)
            s2.dec();
        System.out.println("Time: " + s2);
    }
}
