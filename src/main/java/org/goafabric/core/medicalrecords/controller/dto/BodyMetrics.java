package org.goafabric.core.medicalrecords.controller.dto;

public record BodyMetrics(
        String id,
        Long version,
        String bodyHeight,
        String bellyCircumference,
        String headCircumference,
        String bodyFat
) implements MedicalRecordAble {
    public MedicalRecordType type() { return MedicalRecordType.BODY_METRICS; }

    public String toDisplay() {
        return "Body Height: " + bodyHeight +
                " Belly Circumference: " + bellyCircumference +
                " Head Circumference: " + headCircumference +
                " Body Fat: " + bodyFat;
    }
}
