package pl.valdemar.wire.model;

public class Result {
    private final String typeCode;
    private final Double length;

    private Result(String typeCode, Double length) {
        this.typeCode = typeCode;
        this.length = length;
    }

    public static Result of(String typeCode, Double length) {
        return new Result(typeCode, length);
    }

    public String getTypeCode() {
        return typeCode;
    }

    public Double getLength() {
        return length;
    }
}
