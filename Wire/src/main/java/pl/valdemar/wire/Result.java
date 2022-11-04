package pl.valdemar.wire;

public class Result {
    private String typeCode;
    private Double length;

    public Result(String typeCode, Double length) {
        this.typeCode = typeCode;
        this.length = length;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public Double getLength() {
        return length;
    }
}
