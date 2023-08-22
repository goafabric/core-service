package org.goafabric.core.organization.controller.vo;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
