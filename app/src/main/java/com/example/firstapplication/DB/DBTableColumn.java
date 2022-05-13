package com.example.firstapplication.DB;

public class DBTableColumn {
    private DBDataType _dataType;
    private String _name;
    private boolean _isPrimaryKey;

    public DBTableColumn(String name, DBDataType dataType)
    {
        _name = name;
        _dataType = dataType;
        _isPrimaryKey = false;
    }

    public DBTableColumn(String name, DBDataType dataType, boolean isPrimaryKey)
    {
        _name = name;
        _dataType = dataType;
        _isPrimaryKey = isPrimaryKey;
    }

    public String get_name() {
        return _name;
    }

    public DBDataType get_dataType() {
        return _dataType;
    }

    public String info()
    {
        return _name + " " + _dataType.toString() + (_isPrimaryKey? " primary key" : "");
    }
}
