package com.example.firstapplication.DB;

public class DBTable {
    private String _name;
    private DBTableColumn[] _columns;
    private DBForeignKey[] _foreignKeys;

    public DBTable(String name, DBTableColumn[] columns, DBForeignKey[] keys) {
        _name  = name;
        _columns = columns.clone();
        _foreignKeys = keys.clone();
    }

    public DBTable(String name, DBTableColumn[] columns) {
        _name  = name;
        _columns = columns.clone();
        _foreignKeys = null;
    }

    public String info()
    {
        String res = "table " + _name;
        res += "(";
        for (DBTableColumn column : _columns) {
            res += column.info();
            res += ",";
        }

        if (_foreignKeys != null)
        for (DBForeignKey foreignKey : _foreignKeys) {
            res += foreignKey.info();
            res += ",";
        }

        res = res.substring(0,res.length()-1); // удаляем последнюю запятую

        res += ")";
        return res;
    }

    public String get_name() {
        return _name;
    }
}
