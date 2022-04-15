package com.example.firstapplication;

/**Временной промежуток*/
public class TimeFrame {

    private TimeHourAndMinute _start;
    private TimeHourAndMinute _end;

    public TimeFrame(TimeHourAndMinute start, TimeHourAndMinute end) {
        set_start_and_end(start, end);
    }

    public TimeFrame(int start_minute, int end_minute) {
        set_start_and_end(start_minute, end_minute);
    }

    // region Basic Get and Set for start and end
    public TimeHourAndMinute get_start() {
        return new TimeHourAndMinute(this._start);
    }

    public void set_start_and_end(int start, int end) {
        set_start(start);
        set_end(end);
    }

    public void set_start_and_end(TimeHourAndMinute start, TimeHourAndMinute end) {
        set_start(start);
        set_end(end);
    }

    public void set_start(TimeHourAndMinute time) {
        this._start = new TimeHourAndMinute(time);
    }

    public void set_start(int minutes) {
        this._start = new TimeHourAndMinute(minutes);
    }

    public TimeHourAndMinute get_end() {
        return  new TimeHourAndMinute(this._end);
    }

    public void set_end(TimeHourAndMinute time) {
        this._end = new TimeHourAndMinute(time);
    }

    public void set_end(int minutes) {
        this._end = new TimeHourAndMinute(minutes);
    }
    // endregion

    public boolean CheckInRange(TimeHourAndMinute time)
    {
        if (_start.Compare(time) != CompareResults.Less) return false;
        if (_end.Compare(time) != CompareResults.More) return false;

        return true;
    }
}
