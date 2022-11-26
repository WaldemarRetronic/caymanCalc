package pl.valdemar.wire.model;

import static com.google.common.base.Preconditions.checkArgument;

public class Wire {

    private final String rawMaterial;
    private final String processing;
    private final Double length;

    private Wire(String rawMaterial, String processing, Double length) {
        this.rawMaterial = rawMaterial;
        this.processing = processing;
        this.length = length;
    }

    public static Wire of(String rawMaterial, String processing, Double length) {
        checkArgument(length > 0);
        return new Wire(rawMaterial, processing, length);
    }

    public String getRawMaterial() {
        return rawMaterial;
    }

    public String getProcessing() {
        return processing;
    }

    public Double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return rawMaterial + "--" + processing + "--" + length;
    }
}
