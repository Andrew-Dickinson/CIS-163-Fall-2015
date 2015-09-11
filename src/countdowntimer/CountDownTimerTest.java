package countdowntimer;

import org.junit.Test;

import java.util.IllegalFormatCodePointException;

import static org.junit.Assert.*;

/**
 * Contains various tests for the CountDownTimer class
 * Created by Andrew on 9/5/15.
 */
public class CountDownTimerTest {

    @Test
    public void testDefaultConstructor(){
        CountDownTimer def = new CountDownTimer();
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 0);
    }

    @Test
    public void testDefaultConstructorWithString(){
        CountDownTimer def = new CountDownTimer("");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 0);
    }

    @Test
    public void testDefaultConstructorWithSecondsValid(){
        CountDownTimer def = new CountDownTimer(24);
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 24);
    }

    @Test
    public void testDefaultConstructorWithSecondsStringValid(){
        CountDownTimer def = new CountDownTimer("24");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 24);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithSecondsNegative(){
        CountDownTimer def = new CountDownTimer(-4);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithSecondsNegativeWithString(){
        CountDownTimer def = new CountDownTimer("-4");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithSecondsHigh(){
        CountDownTimer def = new CountDownTimer(67);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithSecondsHighWithString(){
        CountDownTimer def = new CountDownTimer("67");
    }

    @Test
    public void testDefaultConstructorWithMinutesValid(){
        CountDownTimer def = new CountDownTimer(24, 0);
        assertTrue(def.getHours() == 0 && def.getMinutes() == 24 && def.getSeconds() == 0);
    }

    @Test
    public void testDefaultConstructorWithMinutesValidWithString(){
        CountDownTimer def = new CountDownTimer("24:0");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 24 && def.getSeconds() == 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithMinutesNegative(){
        CountDownTimer def = new CountDownTimer(-4, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithMinutesNegativeWithString(){
        CountDownTimer def = new CountDownTimer("-4:0");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithMinutesHigh(){
        CountDownTimer def = new CountDownTimer(67, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithMinutesHighWithString(){
        CountDownTimer def = new CountDownTimer("67:0");
    }

    @Test
    public void testDefaultConstructorWithHoursValid(){
        CountDownTimer def = new CountDownTimer(16, 0, 0);
        assertTrue(def.getHours() == 16 && def.getMinutes() == 0 && def.getSeconds() == 0);
    }

    @Test
    public void testDefaultConstructorWithHoursValidWithString(){
        CountDownTimer def = new CountDownTimer("16:0:0");
        assertTrue(def.getHours() == 16 && def.getMinutes() == 0 && def.getSeconds() == 0);
    }

    @Test
    public void testDefaultConstructorWithHoursValidAll(){
        CountDownTimer def = new CountDownTimer(4, 33, 15);
        assertTrue(def.getHours() == 4 && def.getMinutes() == 33 && def.getSeconds() == 15);
    }

    @Test
    public void testDefaultConstructorWithHoursValidAllWithString(){
        CountDownTimer def = new CountDownTimer("4:33:15");
        assertTrue(def.getHours() == 4 && def.getMinutes() == 33 && def.getSeconds() == 15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultConstructorWithAllInvalid(){
        CountDownTimer def = new CountDownTimer(60, 60, 60);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDefaultConstructorWithAllInvalidString(){
        CountDownTimer def = new CountDownTimer("60:60:60");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithHoursNegative() {
        CountDownTimer def = new CountDownTimer(-4, 0, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDefaultConstructorWithHoursNegativeWithString() {
        CountDownTimer def = new CountDownTimer("-4:0:0");
    }

    @Test
    public void testOtherClassConstructor(){
        CountDownTimer def1 = new CountDownTimer(38, 34, 53);
        CountDownTimer def2 = new CountDownTimer(def1);
        assertTrue(def1.getHours() == def2.getHours() &&
                   def1.getMinutes() == def2.getMinutes() &&
                   def1.getSeconds() == def2.getSeconds());
    }

    @Test
    public void testStringConstructorHMS(){
        CountDownTimer def = new CountDownTimer("03:45:23");
        assertTrue(def.getHours() == 3 && def.getMinutes() == 45 && def.getSeconds() == 23);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testStringConstructorHMSInvalid(){
        CountDownTimer def = new CountDownTimer("03:457:23");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testStringConstructorHMSInvalidWithText(){
        CountDownTimer def = new CountDownTimer("03:as:23");
    }

    @Test
    public void testStringConstructorMS(){
        CountDownTimer def = new CountDownTimer("45:23");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 45 && def.getSeconds() == 23);
    }

    @Test
    public void testStringConstructorS(){
        CountDownTimer def = new CountDownTimer("23");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 23);
    }

    @Test
    public void testStringConstructorEmpty(){
        CountDownTimer def = new CountDownTimer("");
        assertTrue(def.getHours() == 0 && def.getMinutes() == 0 && def.getSeconds() == 0);
    }

    @Test(expected= IllegalArgumentException.class)
    public void testStringConstructorInvalid(){
        CountDownTimer def = new CountDownTimer("sdafasldfj");
    }

    @Test(expected= IllegalArgumentException.class)
    public void testStringConstructorPartialInvalid(){
        CountDownTimer def = new CountDownTimer("10:df:12");
    }

    @Test
    public void testStaticEquals(){
        CountDownTimer def1 = new CountDownTimer(15, 43, 24);
        CountDownTimer def2 = new CountDownTimer(def1);
        assertTrue(CountDownTimer.equals(def1, def2));
    }

    @Test
    public void testInstanceEquals(){
        CountDownTimer def1 = new CountDownTimer(7, 4, 56);
        CountDownTimer def2 = new CountDownTimer(def1);
        assertTrue(def1.equals(def2));
    }

    @Test
    public void testInstanceCompareToEquals(){
        CountDownTimer def1 = new CountDownTimer(7, 4, 56);
        CountDownTimer def2 = new CountDownTimer(def1);
        assertTrue(def1.compareTo(def2) == 0);
    }

    @Test
    public void testInstanceCompareToLess(){
        CountDownTimer def1 = new CountDownTimer(7, 4, 56);
        CountDownTimer def2 = new CountDownTimer(7, 4, 57);
        assertTrue(def1.compareTo(def2) == -1);
    }

    @Test
    public void testInstanceCompareToGreater(){
        CountDownTimer def1 = new CountDownTimer(7, 5, 56);
        CountDownTimer def2 = new CountDownTimer(7, 4, 57);
        assertTrue(def1.compareTo(def2) == 1);
    }

    @Test
    public void testStaticCompareToEquals(){
        CountDownTimer def1 = new CountDownTimer(7, 4, 56);
        CountDownTimer def2 = new CountDownTimer(def1);
        assertTrue(CountDownTimer.compareTo(def1, def2) == 0);
    }

    @Test
    public void testStaticCompareToLess(){
        CountDownTimer def1 = new CountDownTimer(7, 4, 56);
        CountDownTimer def2 = new CountDownTimer(7, 4, 57);
        assertTrue(CountDownTimer.compareTo(def1, def2) == -1);
    }

    @Test
    public void testStaticCompareToGreater(){
        CountDownTimer def1 = new CountDownTimer(7, 5, 56);
        CountDownTimer def2 = new CountDownTimer(7, 4, 57);
        assertTrue(CountDownTimer.compareTo(def1, def2) == 1);
    }

    @Test
    public void testToString(){
        CountDownTimer def = new CountDownTimer(4, 20, 7);
        assertTrue(def.toString().equals("04:20:07"));
    }

    @Test
    public void testSubtractValid(){
        CountDownTimer def = new CountDownTimer(4, 33, 15);
        def.subtract(17);
        assertTrue(def.getHours() == 4 && def.getMinutes() == 32 && def.getSeconds() == 58);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubtractNegative(){
        CountDownTimer def = new CountDownTimer(4, 33, 15);
        def.subtract(-17);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSubtractTooFar(){
        CountDownTimer def = new CountDownTimer(0, 1, 15);
        def.subtract(200);
    }

    @Test
    public void testSubtractClassValid(){
        CountDownTimer def1 = new CountDownTimer(4, 33, 15);
        CountDownTimer def2 = new CountDownTimer(0, 3, 20);
        def1.subtract(def2);
        assertTrue(def1.getHours() == 4 && def1.getMinutes() == 29 && def1.getSeconds() == 55);
    }

    @Test
    public void testDecValid(){
        CountDownTimer def = new CountDownTimer(0, 1, 15);
        def.dec();
        assertTrue(def.getHours() == 0 && def.getMinutes() == 1 && def.getSeconds() == 14);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDecInValid(){
        CountDownTimer def = new CountDownTimer(0, 0, 0);
        def.dec();
    }

    @Test
    public void testAddValid(){
        CountDownTimer def = new CountDownTimer(4, 33, 15);
        def.add(56);
        assertTrue(def.getHours() == 4 && def.getMinutes() == 34 && def.getSeconds() == 11);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddNegative(){
        CountDownTimer def = new CountDownTimer(4, 33, 15);
        def.add(-17);
    }

    @Test
    public void testAddClassValid(){
        CountDownTimer def1 = new CountDownTimer(4, 33, 15);
        CountDownTimer def2 = new CountDownTimer(0, 3, 20);
        def1.add(def2);
        assertTrue(def1.getHours() == 4 && def1.getMinutes() == 36 && def1.getSeconds() == 35);
    }

    @Test
    public void testIncValid(){
        CountDownTimer def = new CountDownTimer(0, 1, 15);
        def.inc();
        assertTrue(def.getHours() == 0 && def.getMinutes() == 1 && def.getSeconds() == 16);
    }

    @Test
    public void testGetOverallSeconds(){
        CountDownTimer def1 = new CountDownTimer(15, 43, 24);
        assertTrue(def1.getOverallSeconds() == 15*3600 + 43*60 + 24);
    }

    @Test
    public void testSuspendActive(){
        CountDownTimer def1 = new CountDownTimer(100, 5, 7);

        //Save as a reference to original
        CountDownTimer def2 = new CountDownTimer(def1);

        CountDownTimer.suspend(true);

        //Should have no effect
        def1.inc();
        def1.add(370);
        def1.dec();
        def1.subtract(8);

        assertTrue(def1.equals(def2));

        CountDownTimer.suspend(false); //So other tests run correctly
    }

    @Test
    public void testSuspendInactive(){
        CountDownTimer def1 = new CountDownTimer(100, 5, 7);

        CountDownTimer.suspend(true);

        //Should have no effect
        def1.inc();
        def1.add(370);
        def1.dec();
        def1.subtract(8);

        //Disable suspension
        CountDownTimer.suspend(false);
        def1.inc();
        def1.add(24);
        def1.dec();
        def1.subtract(8);

        assertTrue(def1.getHours() == 100 && def1.getMinutes() == 5 && def1.getSeconds() == 23);

    }

    @Test
    public void testFileSavingAndLoading(){
        CountDownTimer def1 = new CountDownTimer(3, 53, 20);
        def1.save("countdowntimer1");
        CountDownTimer def2 = new CountDownTimer();
        def2.load("countdowntimer1");

        assertTrue(def1.equals(def2));
    }
}