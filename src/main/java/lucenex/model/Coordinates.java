package lucenex.model;

public class Coordinates {
    Double row;
    Double column;

    public Coordinates(Double row, Double column) {
        this.row = row;
        this.column = column;
    }

    public Double getRow() {
        return row;
    }

    public void setRow(Double row) {
        this.row = row;
    }

    public Double getColumn() {
        return column;
    }

    public void setColumn(Double column) {
        this.column = column;
    }
}
