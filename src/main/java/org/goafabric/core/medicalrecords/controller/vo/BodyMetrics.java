package org.goafabric.core.medicalrecords.controller.vo;

public record BodyMetrics(
        String id,
        String version,
        String bodyHeight,
        String bellyCircumference,
        String headCircumference,
        String bodyFat
) {
    public String toDisplay() {
        return "Body Height: " + bodyHeight +
                " Belly Circumference: " + bellyCircumference +
                " Head Circumference: " + headCircumference +
                " Body Fat: " + bodyFat;
    }
}
