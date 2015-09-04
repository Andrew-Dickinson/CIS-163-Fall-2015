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
            constructTimer(0, 0, Integer.parseInt(timeData[0]));
        } else {
            if (timeString.length() == 0){
                //String is empty
                constructTimer(0, 0, 0);
            } else {
                //The string doesn't fit
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

    //TODO Finish method
    public int compareTo(CountDownTimer other){
        if (equals(other)){
            return 0;
        } else {

        }
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
}
