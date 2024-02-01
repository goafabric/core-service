package org.goafabric.core.medicalrecords.controller.dto;

public record BodyMetrics(
        String id,
        String version,
        String bodyHeight,
        String bellyCircumference,
        String headCircumference,
        String bodyFat
) implements RecordAble {
    public MedicalRecordType type() {
        return MedicalRecordType.BODY_METRICS;
    }
    public String toDisplay() {
        return "Body Height: " + bodyHeight +
                " Belly Circumference: " + bellyCircumference +
                " Head Circumference: " + headCircumference +
                " Body Fat: " + bodyFat;
    }
}
