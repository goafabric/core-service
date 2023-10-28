package org.goafabric.core.medicalrecords.controller.dto;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
