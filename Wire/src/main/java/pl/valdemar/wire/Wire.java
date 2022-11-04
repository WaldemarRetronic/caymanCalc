package pl.valdemar.wire;

public class Wire {

    private String colourTypeCode;
    private String typeCode;
    private Double length;

    public Wire(String colourTypeCode, String typeCode, Double length) {
        this.colourTypeCode = colourTypeCode;
        this.typeCode = typeCode;
        this.length = length;
    }

    public String getColourTypeCode() {
        return colourTypeCode;
    }

    public void setColourTypeCode(String colourTypeCode) {
        this.colourTypeCode = colourTypeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return colourTypeCode + "--" + typeCode + "--" + length;
    }
}
