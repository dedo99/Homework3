package lucenex.model;

public class Cell {
    Boolean isHeader;
    String cleanedText;
    Coordinates Coordinates;

    public Cell(Boolean isHeader, String cleanedText, Coordinates coordinates) {
        this.isHeader = isHeader;
        this.cleanedText = cleanedText;
        Coordinates = coordinates;
    }

    public Boolean getHeader() {
        return isHeader;
    }

    public void setHeader(Boolean header) {
        isHeader = header;
    }

    public String getCleanedText() {
        return cleanedText;
    }

    public void setCleanedText(String cleanedText) {
        this.cleanedText = cleanedText;
    }

    public Coordinates getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        Coordinates = coordinates;
    }

    @Override
    public String toString() {
        return this.cleanedText + " row: " + this.getCoordinates().getRow() + " column:" + this.getCoordinates().getColumn();
    }
}
