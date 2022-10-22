package lucenex.model;

public class JSONObject {
    String id;
    Cell[] cells;

    public JSONObject(String id, Cell[] cells) {
        this.id = id;
        this.cells = cells;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }
}
