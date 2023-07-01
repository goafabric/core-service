package org.goafabric.core.files.objectstorage.controller.vo;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
