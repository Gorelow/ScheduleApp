package com.example.firstapplication;

public class TableStructure {
    private static final byte DAYS_IN_ONE_WEEK = 7;
    private static final byte MAX_WEEKS_IN_SCHEDULE_AMOUNT = 4;

    private byte _weeks;
    private Day[] _days;
    private TimeFrame[] _timeFrames;
    private Cell[][] _cells;

    public TableStructure() {
        copy(new TableStructure((byte) 1));
    }

    public TableStructure(byte weeks) {
        copy(new TableStructure(weeks, new Day[] {Day.Monday, Day.Tuesday, Day.Wednesday, Day.Thursday, Day.Friday, Day.Saturday, Day.Sunday}));
    }

    public TableStructure(byte weeks, Day[] days) {
        copy(new TableStructure(weeks, days, new TimeFrame[] {new TimeFrame(510, 555), new TimeFrame(570, 615), new TimeFrame(630, 675),
                                                                new TimeFrame(685, 760), new TimeFrame(750, 795), new TimeFrame(815,860), new TimeFrame(870, 915)}));
    }

    public TableStructure(byte weeks, Day[] days, TimeFrame[] timeFrames)
    {

    }

    public TableStructure(TableStructure tableStructure)
    {

    }

    public void copy(TableStructure tableStructure)
    {

    }
}
