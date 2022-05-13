package com.example.firstapplication.model;

public class CellContent {
    private static final byte MAX_ROWS_IN_A_CELL = 3;
    private static final byte MAX_COLUMNS_IN_A_CELL = 3;

    private byte _rowsAmount;
    private byte _columnsAmount;
    // String[X][Y]: X - column number, Y - row number
    private String[][] _content;

    CellContent(byte rows, byte columns) {
        set_rowsAndColumns(rows,columns);
    }

    CellContent(CellContent cell) {
        set_content(cell.get_content());
    }

    public void set_rowsAndColumns(byte rows, byte columns) {
        if ((rows > MAX_ROWS_IN_A_CELL) || (rows < 1)) rows = 1;
        if ((columns > MAX_COLUMNS_IN_A_CELL) || (columns < 1)) columns = 1;
        this._rowsAmount = rows;
        this._columnsAmount = columns;
        _content = new String[columns][rows];
    }

    public void set_content(String[][] content)
    {
        if (content == null) return;
        int rowsAmount = content.length;
        int columnsAmount = content[0].length;
        if ((rowsAmount > MAX_COLUMNS_IN_A_CELL) || ((columnsAmount > MAX_ROWS_IN_A_CELL))) return;

        _rowsAmount = (byte) rowsAmount;
        _columnsAmount = (byte) columnsAmount;

        _content = content.clone();
    }

    public void set_content(byte row, byte column, String content){
        if (!check_if_there_space_in_place(row, column)) return;
        _content[column][row] = content;
    }

    public String[][] get_content() {
        return _content;
    }

    public String get_content_part(byte row, byte column) {
        if (!check_if_there_space_in_place(row, column)) return null;

        return _content[column][row];
    }

    private boolean check_if_there_space_in_place(byte row, byte column){
        return (row < _rowsAmount) && (column < _columnsAmount);
    }
}
