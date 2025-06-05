package org.goafabric.core.medicalrecords.controller.dto;

@SuppressWarnings("java:S6218")
public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
