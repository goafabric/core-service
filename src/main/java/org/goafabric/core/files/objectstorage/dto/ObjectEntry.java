package org.goafabric.core.files.objectstorage.dto;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
