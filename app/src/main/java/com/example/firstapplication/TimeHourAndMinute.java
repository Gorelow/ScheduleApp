package com.example.firstapplication;

public class TimeHourAndMinute {
    private static final byte HOURS_IN_A_DAY = 24;
    private static final byte MINUTES_IN_AN_HOUR = 60;
    private static final int MINUTES_IN_A_DAY = HOURS_IN_A_DAY * MINUTES_IN_AN_HOUR;

    private byte _hour;
    private byte _minute;

    // region Initiation
    TimeHourAndMinute(int minutes){
        set_time(minutes);
    }

    TimeHourAndMinute(TimeHourAndMinute timeHourAndMinute)
    {
        _hour = timeHourAndMinute.get_hour();
        _minute =  timeHourAndMinute.get_minute();
    }
    // endregion initiation

    // region Static staff
    public static byte getMinutesInAnHour() {
        return MINUTES_IN_AN_HOUR;
    }

    public static byte getHoursInADay() {
        return HOURS_IN_A_DAY;
    }

    public static int getMinutesInADay() {
        return MINUTES_IN_A_DAY;
    }
    // endregion

    // region Basic get and set functions
    public byte get_hour() {
        return _hour;
    }

    public byte get_minute() {
        return _minute;
    }

    public void set_hour(byte hour) {
        if (hour < 0) return;
        if (hour >= HOURS_IN_A_DAY) return;
        this._hour = hour;
    }

    public void set_minute(byte minute) {
        if (minute < 0) return;
        if (minute >= MINUTES_IN_AN_HOUR) return;
        this._minute = minute;
    }
    // endregion

    // region Get set time
    public void set_time(int minute)
    {
        if (minute < 0) return;
        if (minute >= MINUTES_IN_A_DAY) return;
        this._hour = (byte) (minute / MINUTES_IN_AN_HOUR);
        this._minute = (byte) (minute % MINUTES_IN_AN_HOUR);
    }

    public int get_time()
    {
        return  this._hour * MINUTES_IN_AN_HOUR + this._minute;
    }
    // endregion

    public CompareResults Compare(TimeHourAndMinute timeToCompareWith) {
        return Compare(timeToCompareWith.get_time());
    }

    public CompareResults Compare(int timeInMinutesToCompareWith) {
        int thisTimeInMinutes = get_time();
        if (thisTimeInMinutes > timeInMinutesToCompareWith) return CompareResults.More;
        if (thisTimeInMinutes < timeInMinutesToCompareWith) return CompareResults.Less;
        else return CompareResults.Equal;
    }
}
