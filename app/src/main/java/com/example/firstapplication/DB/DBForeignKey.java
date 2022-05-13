package com.example.firstapplication.DB;

public class DBForeignKey {
    private DBTableColumn _key;
    private DBTable _referenceTable;
    private DBTableColumn _referenceKey;

    public DBForeignKey(DBTableColumn key, DBTable referenceTable, DBTableColumn referenceKey)
    {
        _key = key;
        _referenceTable = referenceTable;
        _referenceKey = referenceKey;
    }

    public String info()
    {
        return  "foreign key (" + _key.get_name() + ") REFERENCES " + _referenceTable.get_name() + " (" + _referenceKey.get_name() + ")";
    }

    //"foreign key (" + KEY_LESSON_TIME + ") REFERENCES " + TABLE_TIMESTAMPS + "(" + KEY_ID +"),"
}
