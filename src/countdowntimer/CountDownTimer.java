package countdowntimer;

import java.util.Objects;

/**
 * Created by Andrew on 9/4/15.
 */
public class CountDownTimer {

    //Stores the current time data
    private int hours;
    private int minutes;
    private int seconds;

    /**
     * Constructs a CountDownTimer with the hours, minutes, and seconds set to 0
     */
    public CountDownTimer() {
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    /**
     * Constructs a CountDownTimer with hours and minutes = 0
     * @param seconds The value of the seconds instance variable
     */
    public CountDownTimer(int seconds) {
        constructTimer(0, 0, seconds);
    }

    /**
     * Constructs a CountDownTimer with hours = 0
     * @param minutes The value of the minutes instance variable
     * @param seconds The value of the seconds instance variable
     */
    public CountDownTimer(int minutes, int seconds) {
        constructTimer(0, minutes, seconds);
    }

    /**
     * Constructs a CountDownTimer with the given parameters
     * @param hours The value of the hours instance variable
     * @param minutes The value of the minutes instance variable
     * @param seconds The value of the seconds instance variable
     */
    public CountDownTimer(int hours, int minutes, int seconds) {
        constructTimer(hours, minutes, seconds);
    }

    /**
     * Constructs a CountDownTimer based on another CountDownTimer object
     * @param other The CountDownTimer to duplicate
     */
    public CountDownTimer(CountDownTimer other){
        constructTimer(other.getHours(), other.getMinutes(), other.getSeconds());
    }

    /**
     * Constructs a CountDownTimer based on a string
     * @param timeString A string that can be in one of the following formats:
     *                   - hh:mm:ss
     *                   - mm:ss (Will set hh to zero)
     *                   - ss (Will set mm, hh to zero)
     *                   - "" (Will set all to zero)
     */
    public CountDownTimer(String timeString){
        String[] timeData = timeString.split(":");
        if (timeData.length == 3){
            constructTimer(Integer.parseInt(timeData[0]),
                           Integer.parseInt(timeData[1]),
                           Integer.parseInt(timeData[2]));
        } else if (timeData.length == 2){
            constructTimer(0, Integer.parseInt(timeData[0]), Integer.parseInt(timeData[1]));
        } else if (timeData.length == 1){
            if (timeString.length() == 0){
                //String is empty
                constructTimer(0, 0, 0);
                return; //To avoid throwing an exception
            }

            try {
                constructTimer(0, 0, Integer.parseInt(timeData[0]));
            } catch (NumberFormatException e){
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Tests equality of two CountDownTimers
     * @param c1 The first CountDownTimer
     * @param c2 The second CountDownTimer
     * @return True if c1 and c2 have equal hours, minutes, and seconds
     */
    public static boolean equals(CountDownTimer c1, CountDownTimer c2){
        return c1.equals(c2);
    }

    /**
     * Compares c1 to c2
     * @param c1 The first CountDownTimer
     * @param c2 The second CountDownTimer
     * @return 1 if c1 is greater than c2, -1 if c1 is less than c2, and 0 if they're equal
     */
    public static int compareTo(CountDownTimer c1, CountDownTimer c2){
        return c1.compareTo(c2);
    }

    /**
     * Sets the instance variables with the given parameters
     * @param hours The value of the hours instance variable
     * @param minutes The value of the minutes instance variable
     * @param seconds The value of the seconds instance variable
     */
    private void constructTimer(int hours, int minutes, int seconds){
        if (hours < 0 || minutes < 0 || minutes > 60 || seconds < 0 || seconds > 60)
            throw new IllegalArgumentException();

        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Tests equality
     * @param other The other object to test
     * @return True if other is CountDownTimer and hours, minutes, and seconds are equal
     */
    public boolean equals(Object other){
        if (!(other instanceof CountDownTimer)) {
            return false;
        } else {
            CountDownTimer tempCountDownTimer = (CountDownTimer) other;
            return  (tempCountDownTimer.getHours() == getHours() &&
                     tempCountDownTimer.getMinutes() == getMinutes() &&
                     tempCountDownTimer.getSeconds() == getSeconds());
        }
    }

    /**
     * Compares this to other
     * @param other The other CountDownObject to compare to
     * @return 1 if this is greater than other, -1 if this is less than other, and 0 if they're equal
     */
    public int compareTo(CountDownTimer other){
        if (equals(other)){
            return 0;
        } else {
            int tempThisSeconds = getOverallSeconds();
            int tempOtherSeconds = other.getOverallSeconds();
            if (tempOtherSeconds < tempThisSeconds){
                //This is greater
                return 1;
            } else {
                //This is less
                return -1;
            }
        }
    }

    /**
     * Returns a string version of the timer data
     * @return A string in the format hh:mm:ss
     */
    public String toString(){
        String hourString = "" + hours;
        String minuteString = "" + minutes;
        String secondString = "" + seconds;
        if (hours < 10){
            hourString = "0" + hourString;
        }
        if (minutes < 10){
            minuteString = "0" + minuteString;
        }
        if (seconds < 10){
            secondString = "0" + secondString;
        }
        return hourString + ":" + minuteString + ":" + secondString;
    }

    /**
     * Adds or subtracts the indicated amount in seconds
     * @param amount The amount in seconds. Negative to subtract. Positive to add
     * @throws IllegalArgumentException If subtraction goes to less than zero
     */
    private void modify(int amount){
        int tempOverallSeconds = getOverallSeconds();
        tempOverallSeconds += amount;
        if (tempOverallSeconds < 0){
            throw new IllegalArgumentException();
        }

        hours = (tempOverallSeconds / 3600); //Integers are rounded down automatically
        minutes = tempOverallSeconds % 3600;
        seconds = tempOverallSeconds % 60;

    }

    /**
     * Subtracts the specified amount
     * @param amount The amount to subtract
     * @throws IllegalArgumentException If amount < 0 or amount > getOverallSeconds()
     */
    public void subtract(int amount){
        if (amount < 0 || amount > getOverallSeconds()){
            throw new IllegalArgumentException();
        }
        modify(-amount);
    }

    /**
     * Subtracts the other CountDownTimer from this one
     * @param other The CountDownTimer to subtract based on
     */
    public void subtract(CountDownTimer other){
        subtract(other.getOverallSeconds());
    }

    /**
     * Adds the specified amount to the timer
     * @param amount The amount to add
     */
    public void add(int amount){
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        modify(amount);
    }

    /**
     * Adds the other CountDownTimer to this one
     * @param other The CountDownTimer to add based on
     */
    public void add(CountDownTimer other){
        add(other.getOverallSeconds());
    }


    /**
     * Subtracts 1 from the timer
     */
    public void dec(){
        if (getOverallSeconds() > 0){
            subtract(1);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Adds 1 to the timer
     */
    public void inc(){
        add(1);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Gets the total value of the timer in seconds
     * @return The total value in seconds
     */
    public int getOverallSeconds(){
        return hours * 3600 + minutes * 60 + seconds;
    }
}
